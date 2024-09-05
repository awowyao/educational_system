<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAddSpecialty">添加专业</el-button>
		<el-select v-model="chooseCollege" placeholder="选择学院" clearable @clear="GetSpecialtyList">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
			:key="index" 
			@click="GetSpecialtyList" />
		</el-select>
		<el-input v-model="search" placeholder="搜索专业名字" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="GetSpecialtyList">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="create_time" label="添加时间" min-width="120" />
		<el-table-column prop="id" label="ID" min-width="80" />
		<el-table-column prop="name" label="名字" min-width="120" />
		<el-table-column prop="studentNumber" label="学生人数" min-width="120" />
		<el-table-column prop="classNumber" label="班级数量" min-width="120" />
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
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="GetSpecialtyList()" />
	</div>
	<el-dialog v-model="AddSpecialtyDialog" title="添加专业" width="500">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="选择学院" :label-width="DialogWidth">
				<el-select v-model="Form.academy_id" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
					:key="index" />
				</el-select>
			</el-form-item>
			<el-form-item label="专业名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="AddSpecialtyDialog = false">取消</el-button>
				<el-button type="primary" @click="AddSpecialty">
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
	const chooseSpecialty = ref('')
	const defaultProps = {
		children: 'children',
		label: 'label',
	}
	var CollegeList = []
	const search = ref('')
	const chooseCollege = ref('')
	const tableData = ref([])
	const AddSpecialtyDialog = ref(false)
	const DialogWidth = 140;
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const Form = ref({
		id: "",
		name: "",
		academy_id:"",
	})
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
	const openAddSpecialty = () => {
		Form.value = ref({
			id: "",
			name: "",
			academy_id:"",
		})
		AddSpecialtyDialog.value = true
	}
	const AddSpecialty = () => {
		API({
			url: "/adminApi/addSpecialty",
			method: "post",
			data: Form.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			GetSpecialtyList()
			AddSpecialtyDialog.value = false
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GetSpecialtyList = () => {
		API({
			url: "/adminApi/GetSpecialtyList",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				select: search.value,
				acId:chooseCollege.value
			}
		}).then((res) => {
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
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
	const openEdit = (id, name) => {
		ElMessageBox.prompt('输入新的名字', '更改名字', {
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
			})
			.then(({
				value
			}) => {
				API({
					url:"/adminApi/editSpecialty",
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
					GetSpecialtyList()
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
				'是否确定删除名字为:'+ name + "的专业吗？",
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url: "adminApi/DeleteSpecialty",
					method: "post",
					data: {
						id: id
					}
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
					GetSpecialtyList()
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
		GetSpecialtyList()
		GetCollegeList()
	})
</script>

<style>
</style>