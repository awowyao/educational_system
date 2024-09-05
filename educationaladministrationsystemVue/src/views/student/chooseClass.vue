<template>
	<view style="margin-right: 20px;">
		<el-select v-model="chooseCollege" placeholder="选择学院" clearable @clear="getCourseData">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
				@click="getCourseData" />
		</el-select>
	</view>
	<el-input style="width: 200px; height: 34px;font-size: 15px; " v-model="search"
		placeholder="搜索课程" />
	<el-button type="primary" style="font-size: 16px;margin-left: 20px;" @click="getCourseData">查询</el-button>

	<el-table :data="tableData" style="width: 100%">
		<el-table-column align="center" label="课程" prop="name" min-width="160" />
		<el-table-column align="center" label="授课老师" prop="teacherName" min-width="160" />
		<el-table-column align="center" label="学院" prop="academyName" min-width="160" />
		<el-table-column align="center" label="上课时间" prop="classTime" min-width="160" />
		<el-table-column align="center" label="已选人数" prop="chooseNub" min-width="80"/>
		<el-table-column align="center" label="人数" prop="total" min-width="80"/>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button size="small" @click="openChooseCourse(scope.row.id, scope.row.name)">选课</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getCourseData" />
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import API from '../../api/config'
	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const GradeValue = ref('')
	const chooseCollege = ref('')
	var CollegeList = []
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const search = ref('')
	const GradeOptions = [{
			value: 'Option2',
			label: 'Option2',
		},
		{
			value: 'Option3',
			label: 'Option3',
		},
		{
			value: 'Option4',
			label: 'Option4',
		},
	]

	const tableData = ref([])

	const getCourseData = () => {
		var SelectData = search.value

		API({
			url: "/studentApi/getElectiveCourse",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				academyId: chooseCollege.value,
				select: SelectData
			}
		}).then((res) => {
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
				showPage.value = true
			}
			page.value = res.data.data.pageNum
			tableData.value = res.data.data.list
			
			for(var i=0;i<tableData.value.length;i++){
				var courseTime = tableData.value[i].classTime.split("/")
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
				tableData.value[i].classTime = courseTime.join("/")
			}
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
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
						cid:id
					}
				}).then((res) =>{
					ElMessage({
						type: 'success',
						message: '选择成功',
					})
					getCourseData()
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
	onMounted(() => {
		getCourseData()
		GetCollegeList()
	})
</script>

<style>
</style>