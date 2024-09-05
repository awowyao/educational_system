<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAddUser">添加管理员用户</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="120" />
		<el-table-column prop="id" label="ID" min-width="80" />
		<el-table-column prop="name" label="名字" min-width="120" />
		<el-table-column prop="email" label="邮箱" min-width="120" />
		<el-table-column prop="phone" label="手机号" min-width="120" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="DeleteUser(scope.row.id, scope.row.name)">删除</el-button>
				<el-button type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>
	<el-dialog v-model="AddAdminUserDialog" title="添加管理员用户" width="550">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="登录用户名" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="登录密码" :label-width="DialogWidth">
				<el-input v-model="Form.password"  type="password" placeholder="请输入密码" show-password style="width: 216px;" />
			</el-form-item>
			<el-form-item label="邮箱" :label-width="DialogWidth">
				<el-input v-model="Form.email" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="Form.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="AddAdminUserDialog = false">取消</el-button>
				<el-button type="primary" @click="AddUser">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>
	
	<el-dialog v-model="EditAdminUserDialog" title="编辑管理员用户" width="550">
		<el-form :model="EditForm" class="dialogForm">
			<el-form-item label="登录用户名" :label-width="DialogWidth">
				<el-input v-model="EditForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="邮箱" :label-width="DialogWidth">
				<el-input v-model="EditForm.email" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="EditForm.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditAdminUserDialog = false">取消</el-button>
				<el-button type="primary" @click="EditUser">
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
	import md5 from 'js-md5';
	import API from '../../api/config'
	const tableData = ref([])
	const DialogWidth = 180;
	const AddAdminUserDialog = ref(false)
	const EditAdminUserDialog = ref(false)
	const Form = ref({
		name:'',
		password:'',
		email:'',
		phone:''
	})
	const EditForm = ref({
		name:'',
		password:'',
		email:'',
		phone:''
	})
	const openAddUser = ()=>{
		AddAdminUserDialog.value = true;
	}
	const AddUser = ()=>{
		Form.value.password = md5(Form.value.password)
		API({
			url:"/adminApi/AddAdminUser",
			method:"post",
			data:Form.value
		}).then(()=>{
			ElMessage({
				type: 'success',
				message: "添加成功",
			})
			Form.value = {
				name:'',
				password:'',
				email:'',
				phone:''
			}
			AddAdminUserDialog.value = false
			getUserList()
		})
	}
	const getUserList = ()=>{
		API({
			url:"/adminApi/GetAdminUser",
			method:"get"
		}).then((res)=>{
			tableData.value = res.data.data.list
		}).catch(()=>{
			ElMessage({
				type: 'warning',
				message: "获取失败",
			})
		})
	}
	const openEdit = (uid) =>{
		EditAdminUserDialog.value = true
		API({
			url:"/adminApi/GetByIdAdmin",
			method:"get",
			params:{
				id:uid
			}
		}).then((res)=>{
			EditForm.value = res.data.data
		})
	}
	const EditUser = ()=>{
		API({
			url:"/adminApi/EditAdminUser",
			method:"post",
			data:EditForm.value
		}).then((res)=>{
			ElMessage({
				type: 'success',
				message: "修改成功",
			})
			EditAdminUserDialog.value = false
			getUserList()
		})
	}
	
	const DeleteUser = (id, name)=>{
		ElMessageBox.confirm(
				'是否确定删除用户名为:'+ name + "的管理员吗？",
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url: "adminApi/DeleteAdminUser",
					method: "post",
					data: {
						id: id
					}
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
					getUserList()
				}).catch((error) =>{
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
			}).catch(() => {
		
			})
	}
	onMounted(()=>{
		getUserList()
	})
</script>

<style>
</style>