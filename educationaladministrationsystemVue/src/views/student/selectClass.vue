<template>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column align="center" label="课程" prop="name" min-width="100"/>
		<el-table-column align="center" label="授课老师" prop="teacherName" min-width="100" />
		<el-table-column align="center" label="学院" prop="academyName" min-width="100" />
		<el-table-column align="center" label="上课时间" prop="classTime" min-width="100"/>
		<el-table-column align="center" label="已选人数" prop="chooseNub" min-width="80"/>
		<el-table-column align="center" label="人数" prop="total" min-width="80"/>
		<el-table-column align="center" label="操作" min-width="100">
			<template #default="scope">
				<el-button size="small" @click="openCancelClass(scope.row.id, scope.row.name)">取消选课</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="GetChooseCourseList" />
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
	const tableData = ref([])
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const GetChooseCourseList = ()=>{
		API({
			url:"/studentApi/GetChooseCourseList",
			method:"get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
			}
		}).then((res)=>{
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
	
	const openCancelClass = (id,courseName) => {
		ElMessageBox.confirm(
				'是否取消课程：'+courseName,
				'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url:"/studentApi/cancelCourse",
					method:"post",
					data:{
						cid:id
					}
				}).then((res) =>{
					ElMessage({
						type: 'success',
						message: '取消成功',
					})
					GetChooseCourseList()
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
	onMounted(()=>{
		GetChooseCourseList()
	})
</script>

<style>
</style>