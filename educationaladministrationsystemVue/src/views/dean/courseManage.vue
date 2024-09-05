<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAdd">添加课程</el-button>
		<el-select v-model="chooseCollege" placeholder="选择学院" clearable @clear="getCourseData">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
				@click="getCourseData" />
		</el-select>
		<el-input v-model="search" placeholder="搜索课程名字或课程ID" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="getCourseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="180" />
		<el-table-column prop="name" label="课程名" min-width="180" />
		<el-table-column prop="academyName" label="学院" min-width="180" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="OpenDelete(scope.row.id, scope.row.name)">删除</el-button>
				<el-button type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getCourseData()" />
	</div>
	<el-dialog v-model="manageDialog" title="添加课程" style="width: 700px;">
		<el-form :model="CourseForm" class="dialogForm">
			<el-form-item label="课程名" :label-width="DialogWidth">
				<el-input v-model="CourseForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="教材" :label-width="DialogWidth">
				<el-input v-model="CourseForm.textbooks" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="CourseForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
						@click="getTeacherList(item.value)" />
				</el-select>
			</el-form-item>
			<el-form-item label="专业" :label-width="DialogWidth">
				<el-select v-model="CourseForm.specialtyName" placeholder="选择专业">
					<el-option v-for="(item, index) in SpecialtyList" :label="item.name" :key="index" :value="item.name" />
				</el-select>
			</el-form-item>
			<el-form-item label="授课教师" :label-width="DialogWidth">
				<el-checkbox-group v-model="checkList" style="width: 400px;" v-if="teacherList.length != 0">
					<el-checkbox :label="item.id" v-for="(item, index) in teacherList" :key="index">{{item.name}}</el-checkbox>
				</el-checkbox-group>
				<span v-else>该学院无教师</span>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="addCourse">
					添加
				</el-button>
			</span>
		</template>
	</el-dialog>

	<el-dialog v-model="EditDialog" title="编辑课程" style="width: 700px;">
		<el-form :model="CourseForm" class="dialogForm">
			<el-form-item label="课程名" :label-width="DialogWidth">
				<el-input v-model="CourseForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="教材" :label-width="DialogWidth">
				<el-input v-model="CourseForm.textbooks" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="CourseForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
						@click="getTeacherList(item.value)" />
				</el-select>
			</el-form-item>
			<el-form-item label="专业" :label-width="DialogWidth">
				<el-select v-model="CourseForm.specialtyName" placeholder="选择专业">
					<el-option v-for="(item, index) in SpecialtyList" :label="item.name" :key="index" :value="item.name" />
				</el-select>
			</el-form-item>
			<el-form-item label="授课教师" :label-width="DialogWidth">
				<el-checkbox-group v-model="checkList" style="width: 400px;" v-if="teacherList.length != 0">
					<el-checkbox :label="item.id" v-for="(item, index) in teacherList" :key="index">{{item.name}}</el-checkbox>
				</el-checkbox-group>
				<span v-else>该学院无教师</span>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="editCourse">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script setup>
	import {
		onMounted,
		ref
	} from 'vue';
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'
	import API from '../../api/config'
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const chooseCollege = ref('')
	var CollegeList = []
	const checkList = ref([])
	const DialogWidth = 230;
	const search = ref('')
	const tableData = ref([])
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	var SpecialtyList = ref([])
	const CourseForm = ref({
		name: '',
		textbooks: '',
		academyId: '',
		TeacherList:[],
		specialtyName: '',
	})
	var teacherList = ref([])
	const GetCollegeList = () => {
		API({
			url: "/deanApi/selectAcademyOrClass",
			method: "get",

		}).then((res) => {
			CollegeList = res.data.data
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})

	}
	const openAdd = () => {
		CourseForm.value = ref({
			name: '',
			sex: '',
			phone: '',
			academyId: '',
		})
		checkList.value = []
		manageDialog.value = true
	}
	const openEdit = (id) => {
		checkList.value = []
		API({
			url: "/deanApi/selectCourseData",
			method: "get",
			params: {
				id: id
			}
		}).then((res) => {
			
			CourseForm.value = res.data.data
			checkList.value = res.data.data.teacherList
			getTeacherList(CourseForm.value.academyId)
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = true
	}
	const getSpecialtyList = (academyId, mode) => {
		if(mode=="1"){
			CourseForm.value.specialtyName = ''
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
	const editCourse = (id) => {
		CourseForm.value.teacherList = checkList.value
		API({
			url: "/deanApi/editCourse",
			method: "post",
			data: CourseForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getCourseData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = false
	}
	const addCourse = () => {
		API({
			url: "/deanApi/addCourse",
			method: "post",
			data: CourseForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			API({
				url: "/deanApi/addTecherByCourse",
				method: "post",
				data: {
					courseName:CourseForm.value.name,
					academyId:CourseForm.value.academyId,
					TeacherList:checkList.value,
					}
			})
			getCourseData()
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		
		manageDialog.value = false
	}
	const getCourseData = () => {
		var SelectData = search.value

		API({
			url: "/deanApi/selectCourse",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				acId: chooseCollege.value,
				SelectData: SelectData
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
	const getTeacherList = (acId) => {
		getSpecialtyList(acId, "2")
		API({
			url: "/deanApi/selectTeacher",
			method: "get",
			params:{
				pageSize:100,
				acId:acId,
			}
		}).then((res) => {
			teacherList.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		
	}
	const OpenDelete = (id, name) => {
		ElMessageBox.confirm(
				'是否确定删除课程ID为:' + id + "名字为:" + name + "的课程吗？",
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				deleteCourse(id)
			}).catch(() => {

			})
	}
	const deleteCourse = (id) => {
		API({
			url: "/deanApi/deleteCourse",
			method: "post",
			data: {
				id: id
			}
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '删除成功',
			})
			getCourseData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	onMounted(() => {
		getCourseData()
		GetCollegeList()
	})
</script>

<style src="../../static/dean/teacherManage.scss" lang="scss" scoped></style>