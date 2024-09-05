<template>
	<el-table :data="tableData" style="width: 100%" height="250">
		<el-table-column fixed prop="createTime" label="提交申诉时间" min-width="150" />
		<el-table-column prop="id" label="学号" min-width="120" />
		<el-table-column prop="name" label="学生名字" min-width="120" />
		<el-table-column prop="courseName" label="申诉科目" min-width="120" />
		<el-table-column prop="className" label="班级" min-width="120" />
		<el-table-column prop="scores" label="成绩" min-width="120" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button v-if="scope.row.complaintState == 0" type="primary" @click="openUpScore(scope.row.seId)">修改成绩</el-button>
				<el-button v-if="scope.row.complaintState == 0" type="primary" @click="openTiJiao(scope.row.seId)">驳回</el-button>
				<el-button v-else-if="scope.row.complaintState == 1" type="primary" disabled>已修改成绩</el-button>
				<el-button v-else-if="scope.row.complaintState == 2" type="primary" disabled>已驳回</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getDataTable" />
	</div>
</template>

<script setup>
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import {
		onMounted,
		ref,
	} from 'vue'
	import API from '../../api/config'
	const tableData = ref([])
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const openTiJiao = (id) => {
		ElMessageBox.confirm(
				'确定要驳回吗',
				'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning',
				}
			)
			.then(() => {
				overrule(id)
				
			})
			.catch(() => {
				ElMessage({
					type: 'warning',
					message: '取消成功',
				})
			})
	}

	const openUpScore = (id) => {
		ElMessageBox.prompt('请输入修改后的成绩', '修改成绩', {
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
				inputPattern: /^[0-9]$|^[0-9]{2}$|^[1][0][0]$/,
				inputErrorMessage: '不是合法的成绩输入',
			})
			.then(({
				value
			}) => {
				API({
					url:'/teacherApi/editStudentScores',
					method:'post',
					data:{
						'id': id,
						'scores':value,
						'def':2
					}
				}).then((res) =>{
					ElMessage({
						type: 'success',
						message: '操作成功',
					})
					getDataTable()
				}).catch((error) =>{
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
			})
			.catch((res) => {
				
			})
	}

	const getDataTable = () =>{
		API({
			url:"/teacherApi/GetStudentComplaint",
			method:"get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
			}
		}).then((res)=>{
			total.value = res.data.data.total
			if(res.data.data.pages == 1 || res.data.data.pages == 0){
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
	
	const overrule = (id) => {
		API({
			url:"/teacherApi/DeleteStudentComplaint",
			method:"post",
			data:{
				id:id
			}
		}).then(()=>{
			ElMessage({
				type: 'success',
				message: '驳回成功',
			})
			getDataTable()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})

	}
	onMounted(() =>{
		getDataTable()
	})
</script>

<style>
</style>