<template>
	<div style="min-width: 1000px;">
		<el-button style="margin-right: 18px;" type="primary" @click="openAdd()">添加班级</el-button>
		<el-cascader v-model="chooseCascader" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="search" placeholder="搜索班级" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="chooseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="min-width: 1000px;">
		<el-table-column prop="createTime" label="添加时间" min-width="180" />
		<el-table-column prop="year" label="入学年份" min-width="180" />
		<el-table-column prop="name" label="班级" min-width="100" />
		<el-table-column prop="specialtyName" min-width="100" label="专业" />
		<el-table-column prop="number" min-width="80" label="人数" />
		<el-table-column label="操作" min-width="500">
			<template #default="scope">
				<el-button type="primary" @click="openAddCourse(scope.row.id, scope.row.specialtyName)">添加课程</el-button>
				<el-button type="primary" @click="openDelete(scope.row.name, scope.row.id, scope.row.specialtyName)">删除</el-button>
				<el-button type="primary" @click="openEditClass(scope.row.id)">编辑</el-button>
				<el-button type="primary" @click="GoClassCourseMag(scope.row.id)">班级课程管理</el-button>
				<el-button type="primary" @click="GoClassCourse(scope.row.id, scope.row.curriculumId, scope.row.name)">课程安排</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getClassData()" />
	</div>
	<el-dialog v-model="manageDialog" title="添加班级"  width="600">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="班级名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="入学年份" :label-width="DialogWidth">
				<el-input v-model="Form.year" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="Form.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value" @click="getSpecialtyList(item.value,'1')"/>
				</el-select>
			</el-form-item>
			
			<el-form-item label="专业" :label-width="DialogWidth">
				<el-select v-model="Form.specialtyName" placeholder="选择专业">
					<el-option v-for="(item, index) in SpecialtyList" :label="item.name" :key="index" :value="item.name" />
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="add">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>
	
	<el-dialog v-model="addCourseDialog" title="添加班级课程" width="600">
		<el-form :model="CourseForm" class="dialogForm">
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="CourseForm.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value" @click="GetCourseTable(item.value)"/>
				</el-select>
			</el-form-item>
			<el-form-item label="课程" :label-width="DialogWidth">
				<el-select v-model="CourseForm.course" placeholder="选择课程">
					<el-option v-for="(item, index) in courseList" :label="item.name" :key="index" :value="item.id" @click="getTeacherList(item.id)" />
				</el-select>
			</el-form-item>
			
			<el-form-item label="授课老师" :label-width="DialogWidth">
				<el-select v-model="CourseForm.teacherId" value-key="id" placeholder="选择教师">
					<el-option :label="item.name" :key="index" v-for="(item, index) in teacherList"  :value="item.id" />
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditDialog = false">取消</el-button>
				<el-button type="primary" @click="addClassCourse">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>
	
	<el-dialog v-model="EditDialog" title="编辑班级"  width="600">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="班级名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="入学年份" :label-width="DialogWidth">
				<el-input v-model="Form.year" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="Form.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value" @click="getSpecialtyList(item.value,'1')"/>
				</el-select>
			</el-form-item>
			
			<el-form-item label="专业" :label-width="DialogWidth">
				<el-select v-model="Form.specialtyName" placeholder="选择专业">
					<el-option v-for="(item, index) in SpecialtyList" :label="item.name" :key="index" :value="item.name" />
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditDialog = false">取消</el-button>
				<el-button type="primary" @click="editClass">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'
	import API from '../../api/config'
	import { useRoute } from 'vue-router'
	var route = useRoute()
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const props1 = {
		checkStrictly: true,
	}
	const chooseCascader = ref('')
	const DialogWidth = 220;
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	
	const addCourseDialog = ref(false)
	var editId = ''
	var SpecialtyList = ref([])
	var courseList = ref([])
	var teacherList = ref([])
	const Form = ref({
			name: '',
			year: '',
			academyId: '',
			specialtyName: '',
			
		})
	const CourseForm = ref({
		academyId:"",
		course:"",
		teacherId:""
		
	})
	var chooseAcS = {
		'acId':0,
		'sName':0
	}
	const search = ref('')
	const tableData = ref([])
	const options = ref([])
	const openAddCourse = (id)=>{
		CourseForm.value = {
			academyId:"",
			course:"",
			teacherId:""
		}
		editId = id
		addCourseDialog.value = true
	}
	const chooseData = () => {
		var choose = chooseCascader.value
		if(choose){
			if(choose.length == 2) {
				var academyId = choose[0]
				var classId = choose[1]
				chooseAcS.acId = academyId
				chooseAcS.sName = classId
				getClassData()
			}
			if(choose.length == 1) {
				academyId = choose[0]
				chooseAcS.acId = academyId
				chooseAcS.sName = 0
				getClassData()
			}
		}else{
			chooseAcS.acId = 0
			chooseAcS.sName = 0
			getClassData()
		}
		

	}
	
	const openAdd = () => {
		Form.value = ref({
			name: '',
			year: '',
			academyId: '',
			specialtyName: '',
			
		})
		manageDialog.value = true
		
	}
	const deleteClass = (id) => {
		API({
			url: "/classApi/deleteClass",
			method: "post",
			data: {
				id: id
			}
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '删除成功',
			})
			getClassData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const openEditClass = (id) => {
		Form.value = ref({
			name: '',
			year: '',
			academyId: '',
			specialtyName: '',
		})
		API({
			url:"/classApi/selectByIdClass",
			method:"get",
			params:{
				id:id
			}
		}).then((res) => {
			Form.value = res.data.data
			getSpecialtyList(Form.value.academyId, "2")
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = true
		editId = id
	}
	const getTeacherList = (id, mode) => {

		API({
			url: "/classApi/ByCourseIdGetTeacher",
			method: "get",
			params: {
				id: id
			}
		}).then((res) => {
			teacherList.value = res.data.data
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	} 
	const openDelete = (name, id, s) => {
		ElMessageBox.confirm(
				'是否确定删除专业为:' + s + "名字为:" + name+ "，" + "的班级吗?",
				'Warning', {
					confirmButtonText: '是',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				deleteClass(id)
			}).catch(() => {

			})
	}
	const add = () => {

		API({
			url:"/classApi/addClass",
			method:"post",
			data:Form.value,
		}).then((res)=>{
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getClassData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = false
	}
	
	const addClassCourse = () =>{
		API({
			url:"/classApi/addClassCourse",
			method:"post",
			data:{
				courseId:CourseForm.value.course,
				teacherId:CourseForm.value.teacherId,
				classId:editId
			}
		}).then((res) =>{
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			addCourseDialog.value = false
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: "该课程已加入",
			})
		})
		addCourseDialog.value = false
	}
	const editClass = () => {
		API({
			url:"/classApi/editClass",
			method:"post",
			data:Form.value,
		}).then((res)=>{
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getClassData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		
		EditDialog.value = false
		
	}
	const getClassData = () => {
		var Select = search.value

		API({
			url: "/deanApi/getClass",
			method: "get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
				specialtyName:chooseAcS.sName,
				academyId:chooseAcS.acId,
				Select:Select
			}
		}).then((res) => {
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
				showPage.value = true
			}
			tableData.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	const GetCourseTable = (academyId) =>{
		CourseForm.value.teacherId = ''
		CourseForm.value.course = ''
		courseList = ref([])
		teacherList = ref([])
		API({
			url:"/deanApi/selectCourse",
			method:"get",
			params:{
				acId: academyId,
				pageSize:100
			}
		}).then((res) => {
			courseList.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const getOptionsList = () => {
		API({
			url: "/deanApi/selectAcademyOrSpecialty",
			method: "get"
		}).then((res) => {
			options.value = res.data.data;
		})
	}
	const getSpecialtyList = (academyId, mode) => {
		if(mode=="1"){
			Form.value.specialtyName = ''
		}
		API({
			url: "/classApi/getSpecialty",
			method: "get",
			params:{
				academyId: academyId
			}
		}).then((res) => {
			
			SpecialtyList.value = res.data.data;
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GoClassCourseMag = (id) => {
		location.href = "/dean/ClassCourseMag?id="+id
	}
	const GoClassCourse = (id, cid,name)=> {
		
		if (!cid){
			cid = 0
		}
		location.href = "/dean/Curriculum?id="+id+"&cid="+cid+"&name="+name
	}
	onMounted(() => {
		getClassData()
		getOptionsList()
	});
</script>

<style>
</style>