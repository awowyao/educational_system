<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAddDean">添加教务员</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="120" />
		<el-table-column prop="id" label="ID" min-width="120" />
		<el-table-column prop="name" label="名字" min-width="120" />
		<el-table-column prop="phone" label="手机" min-width="120" />
		<el-table-column prop="email" label="邮箱" min-width="120" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="OpenDelete(scope.row.id, scope.row.name)">删除</el-button>
				<el-button type="primary" @click="openEditDean(scope.row.id)">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="GetDeanList()" />
	</div>
	<el-dialog v-model="addDeanDialog" title="添加教务员" width="600">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="登录ID" :label-width="DialogWidth">
				<el-input v-model.number="Form.id" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="Form.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="Form.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="邮箱" :label-width="DialogWidth">
				<el-input v-model="Form.email" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="权限管理" :label-width="DialogWidth">
				<el-button @click="innerVisible = true">编辑权限</el-button>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditDialog = false">取消</el-button>
				<el-button type="primary" @click="AddDean">
					提交
				</el-button>
			</span>
		</template>
		<el-dialog v-model="innerVisible" width="500" title="编辑权限" append-to-body>
			<div style="max-height: 300px;overflow:auto;">
				<el-tree ref="treeRef" :data="TreeData" show-checkbox default-expand-all node-key="id" highlight-current
					:props="defaultProps" />
			</div>
			<template #footer>
				<span class="dialog-footer">
					<el-button type="primary" @click="innerVisible = false">
						确定
					</el-button>
				</span>
			</template>
		</el-dialog>
	</el-dialog>
	
	<el-dialog v-model="editDeanDialog" title="编辑教务员" width="600">
		<el-form :model="Form" class="dialogForm">
			<el-form-item label="名字" :label-width="DialogWidth">
				<el-input v-model="Form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="Form.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="Form.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="邮箱" :label-width="DialogWidth">
				<el-input v-model="Form.email" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="权限管理" :label-width="DialogWidth">
				<el-button @click="innerVisible2 = true">编辑权限</el-button>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditDialog = false">取消</el-button>
				<el-button type="primary" @click="EditDean">
					提交
				</el-button>
			</span>
		</template>
		<el-dialog v-model="innerVisible2" width="500" title="编辑权限" append-to-body>
			<div style="max-height: 300px;overflow:auto;">
				<el-tree ref="treeRefE" :data="TreeData" show-checkbox default-expand-all node-key="id" highlight-current
					:props="defaultProps" :default-checked-keys="chooseTree"/>
			</div>
			<template #footer>
				<span class="dialog-footer">
					<el-button type="primary" @click="innerVisible2 = false">
						确定
					</el-button>
				</span>
			</template>
		</el-dialog>
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
	const treeRef = ref(null)
	const treeRefE = ref(null)
	const defaultProps = {
		children: 'children',
		label: 'label',
	}
	const TreeData = ref([])
	const tableData = ref([])
	const addDeanDialog = ref(false)
	const editDeanDialog = ref(false)
	const innerVisible = ref(false)
	const innerVisible2 = ref(false)
	const DialogWidth = 220;
	const chooseTree = ref([])
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
	const openAddDean = () => {
		if(treeRef.value !=null){
			treeRef.value.setCheckedKeys([], false)
		}
		Form.value = ref({
			Id: "",
			name: "",
			phone: "",
			email: ""
		})
		addDeanDialog.value = true
	}
	const AddDean = () => {
		API({
			url:"/adminApi/AddDeanUser",
			method:"post",
			data:Form.value
		}).then((res) =>{
			API({
				url:"/adminApi/AddDeanAuthority",
				method:"post",
				data:{
					deanId:Form.value.id,
					AuthorityList:treeRef.value.getCheckedKeys(false)
				}
			}).then((res)=>{
				ElMessage({
					type: 'success',
					message: res.data.msg,
				})
				addDeanDialog.value = false
				GetDeanList()
			}).catch((error) =>{
				ElMessage({
					type: 'warning',
					message: error.response.data.msg,
				})
			})
		}).catch((error) =>{
				ElMessage({
					type: 'warning',
					message: error.response.data.msg,
				})
			})
	}
	const openEditDean= (id)=>{
		editDeanDialog.value = true
		chooseTree.value = []
		Form.value = ref({
			Id: "",
			name: "",
			phone: "",
			email: ""
		})
		API({
			url:"/adminApi/GetDeanData",
			method:"get",
			params:{
				deanId:id
			}
		}).then((res) =>{
			if(treeRefE.value == null) {
				chooseTree.value = res.data.data.authorityList
			}else {
				treeRefE.value.setCheckedKeys([], false)
				treeRefE.value.setCheckedKeys(res.data.data.authorityList, false)
			}
			Form.value = res.data.data
			Form.value.sex = Form.value.sex.toString()
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const EditDean = ()=>{
		if(treeRefE.value == null) {
			Form.value.authorityList = chooseTree.value
		}else {
			Form.value.authorityList = treeRefE.value.getCheckedKeys(false)
		}
		API({
			url:"/adminApi/EditDeanUser",
			method:"post",
			data:Form.value
		}).then((res) =>{
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			editDeanDialog.value = false
			GetDeanList()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GetDeanList = () => {
		API({
			url: "/adminApi/GetDeanUser",
			method: "get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
			}
		}).then((res) => {
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
				showPage.value = true
			}
			tableData.value = res.data.data.list;
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	const OpenDelete = (id, name) => {
		ElMessageBox.confirm(
				'是否确定删除登录ID为:' + id + "名字为:" + name + "的教务员吗？",
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url:"adminApi/DeleteDeanUser",
					method:"post",
					data:{
						id:id
					}
				}).then((res)=>{
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
					GetDeanList()
				})
			}).catch(() => {
	
			})
	}
	const GetAuthorityTree = () =>{
		API({
			url: "/adminApi/GetAuthorityList",
			method: "get"
		}).then((res) => {
			TreeData.value = res.data.data;
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	} 
	onMounted(() => {
		GetDeanList();
		GetAuthorityTree();
	})
</script>

<style>
</style>