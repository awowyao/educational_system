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
		<el-table-column prop="teacherName" label="授课老师" min-width="180" />
		<el-table-column prop="academyName" label="学院" min-width="180" />
		<el-table-column prop="total" label="限制人数" min-width="100" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="OpenDelete(scope.row.id, scope.row.name)">删除</el-button>
				<el-button type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>

	<el-dialog v-model="manageDialog" title="添加选修课" style="width: 700px;">
		<el-form :model="CourseForm" class="dialogForm">
			<el-form-item label="课程名" :label-width="DialogWidth">
				<el-input v-model="CourseForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="限制人数" :label-width="DialogWidth">
				<el-input v-model.number="CourseForm.total" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="上课时间" :label-width="DialogWidth">
				<el-cascader v-model="CourseForm.classTime" :options="weekList" :props="props" @change="handleChange" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="CourseForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
						@click="getTeacherList(item.value)" />
				</el-select>
			</el-form-item>

			<el-form-item label="授课教师" :label-width="DialogWidth">
				<el-radio-group v-model="CourseForm.teacherId" style="width: 400px;" v-if="teacherList.length != 0">
					<el-radio :label="item.id" v-for="(item, index) in teacherList"
						:key="index">{{item.name}}</el-radio>
				</el-radio-group>
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

	<el-dialog v-model="EditDialog" title="编辑选修课" style="width: 700px;">
		<el-form :model="CourseForm" class="dialogForm">
			<el-form-item label="课程名" :label-width="DialogWidth">
				<el-input v-model="CourseForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="限制人数" :label-width="DialogWidth">
				<el-input v-model="CourseForm.total" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="CourseForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
						@click="getTeacherList(item.value)" />
				</el-select>
			</el-form-item>
			<el-form-item label="上课时间" :label-width="DialogWidth">
				<el-cascader v-model="CourseForm.classTime" :options="weekList" :props="props" @change="handleChange" />

			</el-form-item>
			<el-form-item label="授课教师" :label-width="DialogWidth">
				<el-radio-group v-model="CourseForm.teacherId" style="width: 400px;" v-if="teacherList.length != 0">
					<el-radio :label="item.id" v-for="(item, index) in teacherList"
						:key="index">{{item.name}}</el-radio>
				</el-radio-group>
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
	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const props = {
		expandTrigger: 'hover',
	}
	var CollegeList = []
	const checkTeacher = ref('')
	const DialogWidth = 230;
	const chooseCollege = ref('')
	const search = ref('')
	const tableData = ref([])
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	var ClassTimeList = [{
			label: "(1,2)",
			value: "(1,2)",
		},
		{
			label: "(3,4)",
			value: "(3,4)",
		},
		{
			label: "(5,6)",
			value: "(5,6)",
		},
		{
			label: "(7,8)",
			value: "(7,8)",
		},
		{
			label: "(9,10)",
			value: "(9,10)",
		},
	]
	var weekList = [{
			label: "星期一",
			value: "Monday",
			children: ClassTimeList
		},
		{
			label: "星期二",
			value: "Thursday",
			children: ClassTimeList
		},
		{
			label: "星期三",
			value: "Friday",
			children: ClassTimeList
		},
		{
			label: "星期四",
			value: "Wednesday",
			children: ClassTimeList
		},
		{
			label: "星期五",
			value: "Tuesday",
			children: ClassTimeList
		}
	]
	const CourseForm = ref({
		name: '',
		total: '',
		academyId: '',
		teacherId: '',
		classTime: '',
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
			total: '',
			academyId: '',
			teacherId: '',
			classTime: '',
		})
		checkTeacher.value = ''
		manageDialog.value = true
	}
	const openEdit = (id) => {
		checkTeacher.value = ''
		API({
			url: "/courseApi/GetElectiveCourseData",
			method: "get",
			params: {
				id: id
			}
		}).then((res) => {

			CourseForm.value = res.data.data
			CourseForm.value.classTime = CourseForm.value.classTime.split("/")
			getTeacherList(CourseForm.value.academyId)
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = true
	}

	const editCourse = (id) => {
		CourseForm.value.classTime = CourseForm.value.classTime.join("/")
		API({
			url: "/courseApi/editElectiveCourse",
			method: "post",
			data: CourseForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			EditDialog.value = false
			getCourseData()
		}).catch((error) =>{
			CourseForm.value.classTime = CourseForm.value.classTime.split("/")
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		
	}
	const addCourse = () => {
		CourseForm.value.classTime = CourseForm.value.classTime.join("/")
		API({
			url: "/courseApi/addElectiveCourse",
			method: "post",
			data: CourseForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			manageDialog.value = false
			getCourseData()
		}).catch((error) => {
			CourseForm.value.classTime = CourseForm.value.classTime.split("/")
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})

		
	}
	const getCourseData = () => {
		var SelectData = search.value
		var page = route.query.page
		var PageSize = route.query.PageSize
		API({
			url: "/courseApi/GetElectiveCourse",
			method: "get",
			params: {
				pahe: page,
				pageSize: PageSize,
				acId: chooseCollege.value,
				Select: SelectData
			}
		}).then((res) => {
			tableData.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const getTeacherList = (acId) => {
		API({
			url: "/deanApi/selectTeacher",
			method: "get",
			params: {
				pageSize: 100,
				acId: acId,
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
			url: "/courseApi/deleteElectiveCourse",
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