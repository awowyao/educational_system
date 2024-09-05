<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAddacademy">添加学院</el-button>
		<el-input v-model="search" placeholder="搜索学院名字或ID" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="GetAcademyList">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="create_time" label="添加时间" min-width="120" />
		<el-table-column prop="id" label="ID" min-width="80" />
		<el-table-column prop="name" label="名字" min-width="120" />
		<el-table-column prop="teacherNumber" label="教师人数" min-width="120" />
		<el-table-column prop="studentNumber" label="学生人数" min-width="120" />
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
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="GetDeanList()" />
	</div>
	<el-dialog v-model="AddacademyDialog" title="添加学院" width="500">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="学院ID" :label-width="DialogWidth">
				<el-input v-model.number="Form.id" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="学院名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="AddacademyDialog = false">取消</el-button>
				<el-button type="primary" @click="AddAcademy">
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

	const defaultProps = {
		children: 'children',
		label: 'label',
	}
	const search = ref('')
	const tableData = ref([])
	const AddacademyDialog = ref(false)
	const DialogWidth = 140;
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const Form = ref({
		id: "",
		name: "",
		phone: "",
		email: ""
	})
	const openAddacademy = () => {
		Form.value = ref({
			id: "",
			name: "",
		})
		AddacademyDialog.value = true
	}
	const AddAcademy = () => {
		API({
			url: "/adminApi/addAcademy",
			method: "post",
			data: Form.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			GetAcademyList()
			AddacademyDialog.value = false

		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GetAcademyList = () => {
		API({
			url: "/adminApi/GetAcademyList",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				select: search.value
			}
		}).then((res) => {
			tableData.value = res.data.data.list
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const openEdit = (id, name) => {
		ElMessageBox.prompt('输入新的名字', '更改名字', {
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
			})
			.then(({
				value
			}) => {
				API({
					url:"/adminApi/editAcademy",
					method:"post",
					data:{
						id:id,
						name:value
					}
				}).then(()=>{
					ElMessage({
						type: 'success',
						message: `更改成功`,
					})
					GetAcademyList()
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
	const OpenDelete = (id, name) => {
		ElMessageBox.confirm(
				'是否确定删除学院编号为:' + id + "名字为:" + name + "的学院吗？",
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url: "adminApi/DeleteAcademy",
					method: "post",
					data: {
						id: id
					}
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
					GetAcademyList()
				}).catch((error) =>{
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
			}).catch(() => {

			})
	}
	onMounted(() => {
		GetAcademyList()
	})
</script>

<style>
</style>