<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAddCourse">添加课程</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="120" />
		<el-table-column prop="name" label="课程名" min-width="120" />
		<el-table-column prop="textbooks" label="教程" min-width="120" />
		<el-table-column prop="teacherName" label="授课老师" min-width="120" />
		<el-table-column prop="specialtyName" label="专业" min-width="120" />
		
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="OpenDelete(scope.row.id, scope.row.name)">删除</el-button>
			</template>
		</el-table-column>
	</el-table>
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

	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const addCourseDialog = ref(false)
	const tableData = ref([])
	var SpecialtyList = ref([])
	var courseList = ref([])
	var teacherList = ref([])
	const options = ref([])
	const CourseForm = ref({
		academyId:"",
		course:"",
		teacherId:""
		
	})
	const DialogWidth = 220;
	const openAddCourse = (id)=>{
		addCourseDialog.value = true
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
	const getTeacherList = (id, mode) => {
		CourseForm.value.teacherId = ''
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
	const addClassCourse = () =>{
		var id = route.query.id
		API({
			url:"/classApi/addClassCourse",
			method:"post",
			data:{
				courseId:CourseForm.value.course,
				teacherId:CourseForm.value.teacherId,
				classId:id
			}
		}).then((res) =>{
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			addCourseDialog.value = false
			getCourseData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		addCourseDialog.value = false
	}
	const getCourseData = () => {
		var id = route.query.id
		API({
			url: "/classApi/GetClassCourse",
			method: "get",
			params: {
				ClassId: id,
			}
		}).then((res) => {

			tableData.value = res.data.data
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
			url: "/classApi/DeleteClassCourse",
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
		getOptionsList()

	})
</script>

<style src="../../static/dean/teacherManage.scss" lang="scss" scoped></style>