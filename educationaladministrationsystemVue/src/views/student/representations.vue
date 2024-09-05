<template>
	<el-table :data="tableData" style="width: 100%" height="250">
		<el-table-column fixed prop="createTime" label="提交申诉时间" min-width="150" />
		<el-table-column prop="name" label="申诉科目" min-width="120" />
		<el-table-column prop="teacherName" label="处理老师" min-width="120" />
		<el-table-column label="处理进度" min-width="320" >
			<template #default="scope">
				<el-tag class="ml-2" type="info" v-if="scope.row.complaintState==0">未处理</el-tag>
				<el-tag class="ml-2" type="success" v-else-if="scope.row.complaintState==1">已修改</el-tag>
				<el-tag class="ml-2" type="danger" v-else-if="scope.row.complaintState==2">驳回</el-tag>
				<el-tag class="ml-2" type="danger" v-else>未知</el-tag>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getCourseTable" />
	</div>
</template>

<script setup>
	import {
		onMounted,
		ref
	} from 'vue'
	import {
		ElMessage
	} from 'element-plus';
	import API from '../../api/config'
	const tableData = ref([])
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const GetComplaintTable = ()=>{
		API({
			url:"/studentApi/GetComplaintTable",
			method:"get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
			}
		}).then((res) =>{
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
				showPage.value = true
			}
			page.value = res.data.data.pageNum
			tableData.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	onMounted(()=>{
		GetComplaintTable()
	})
</script>

<style>
</style>