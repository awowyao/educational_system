<template>
	<div>

		<el-cascader v-model="chooseCascader" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="search" placeholder="搜索学生名字或学号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="chooseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="100" />
		<el-table-column prop="id" label="学号" min-width="100" />
		<el-table-column prop="name" label="名字" min-width="100" />
		<el-table-column prop="academyName" label="学院" min-width="100" />
		<el-table-column prop="className" label="班级" min-width="100" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button key="学生选课" type="primary" @click="lookChooseCourse(scope.row.id)" link>
					学生选课
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getStudentData()" />
	</div>

	<el-dialog v-model="studenScoreVisible" style="max-height: 400px;overflow: auto;" title="选择选修课" width="700">
		<el-table :data="studentTableData" style="width: 100%">
		<el-table-column align="center" label="课程" prop="name" min-width="100" />
		<el-table-column align="center" label="授课老师" prop="teacherName" min-width="80" />
		<el-table-column align="center" label="学院" prop="academyName" min-width="100" />
		<el-table-column align="center" label="上课时间" prop="classTime" min-width="80" />
		<el-table-column align="center" label="已选人数" prop="chooseNub" min-width="80"/>
		<el-table-column align="center" label="人数" prop="total" min-width="60"/>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button size="small" @click="openChooseCourse(scope.row.id, scope.row.name)">选课</el-button>
			</template>
		</el-table-column>
		</el-table>
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
	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const props1 = {
		checkStrictly: true,
	}
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const chooseCascader = ref('')
	const studenScoreVisible = ref(false)
	const studentTableData = ref([])

	var chooseAcS = {
		'acId': 0,
		'classId': 0
	}


	const search = ref('')
	const tableData = ref([])
	const options = ref([])
	var chooseStudent = 0
	const openChooseCourse = (id,courseName) => {
		ElMessageBox.confirm(
				'是否确认选'+courseName,
				'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url:"/studentApi/chooseCourse",
					method:"post",
					data:{
						cid:id,
						studentId:chooseStudent
					}
				}).then((res) =>{
					ElMessage({
						type: 'success',
						message: '选择成功',
					})
					lookChooseCourse()
				}).catch((error) =>{
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
	
			})
			.catch(() => {
				
			})
	}
	const lookChooseCourse = (id) => {
		chooseStudent = id
		API({
			url: "/studentApi/getElectiveCourse",
			method: "get",
			params: {
				page: 1,
				pageSize: 100,
				studentId:id
			}
		}).then((res) => {
			studentTableData.value = res.data.data.list
			for(var i=0;i<studentTableData.value.length;i++){
				var courseTime = studentTableData.value[i].classTime.split("/")
				var week ="星期五"
				if(courseTime[0] == "Monday"){
					week = "星期一"
				}else if(courseTime[0] == "Thursday"){
					week = "星期二"
				}else if(courseTime[0] == "Friday"){
					week = "星期三"
				}else if(courseTime[0] == "Wednesday"){
					week = "星期四"
				}
				courseTime[0] = week
				studentTableData.value[i].classTime = courseTime.join("/")
				
			}
			studenScoreVisible.value = true
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const chooseData = () => {
		var choose = chooseCascader.value

		if (choose) {
			if (choose.length == 2) {
				var academyId = choose[0]
				var classId = choose[1]
				chooseAcS.acId = academyId
				chooseAcS.classId = classId
				getStudentData()
			}
			if (choose.length == 1) {
				academyId = choose[0]
				chooseAcS.acId = academyId
				chooseAcS.classId = 0
				getStudentData()
			}
		} else {
			chooseAcS.acId = 0
			chooseAcS.classId = 0
			getStudentData()
		}
	}



	const getStudentData = () => {
		var SelectStudent = search.value
		API({
			url: "/deanApi/selectStudent",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				acId: chooseAcS.acId,
				classId: chooseAcS.classId,
				SelectStudent: SelectStudent
			}
		}).then((res) => {
			total.value = res.data.data.total
			if (res.data.data.pages <= 1) {
				showPage.value = true
			}
			tableData.value = res.data.data.list
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	const getOptionsList = () => {
		API({
			url: "/deanApi/selectAcademyOrClass",
			method: "get"
		}).then((res) => {
			options.value = res.data.data;
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}


	onMounted(() => {
		getStudentData()
		getOptionsList()
	});
</script>

<style>
</style>