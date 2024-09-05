<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAdd">添加教师</el-button>
		<el-select v-model="chooseCollege" placeholder="选择学院" clearable @clear="getTeacherData">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
			:key="index" 
			@click="getTeacherData" />
		</el-select>
		<el-input v-model="search" placeholder="搜索教师名字或教号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="getTeacherData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="180" />
		<el-table-column prop="id" label="教号" min-width="90" />
		<el-table-column prop="name" label="名字" min-width="180"/>
		<el-table-column prop="academyName" label="学院" min-width="180"/>
		<el-table-column label="操作" min-width="180">
			<template #default="scope" >
				<el-button type="primary" @click="OpendeleteTeacher(scope.row.id, scope.row.name)">删除</el-button>
				<el-button type="primary" @click="openEdit(scope.row.id)">编辑</el-button>
				<el-button type="primary" @click="resetPassword(scope.row.name,scope.row.id)">重置密码</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getTeacherData" />
	</div>
	<el-dialog v-model="manageDialog" title="添加教师"  width="600">
		<el-form :model="TeacherForm" class="dialogForm">
			<el-form-item label="姓名" :label-width="DialogWidth">
				<el-input v-model="TeacherForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="TeacherForm.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="TeacherForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
					:key="index" 
					@click="getTeacherData" />
				</el-select>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="TeacherForm.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="addTeacher">
					添加
				</el-button>
			</span>
		</template>
	</el-dialog>
	
	<el-dialog v-model="EditDialog" title="编辑教师" width="600">
		<el-form :model="TeacherForm" class="dialogForm">
			<el-form-item label="姓名" :label-width="DialogWidth">
				<el-input v-model="TeacherForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="TeacherForm.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="TeacherForm.academyId" placeholder="选择学院">
					<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
					:key="index" 
					@click="getTeacherData" />
				</el-select>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="TeacherForm.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="editTeacher">
					添加
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
	import { useRoute } from 'vue-router'
	var route = useRoute()
	const chooseCollege = ref('')
	var CollegeList = []
	const DialogWidth = 230;
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const tableData = ref([])
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	const search = ref('')
	const TeacherForm = ref({
		name: '',
		sex: '',
		phone: '',
		academyId: '',
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
	const openAdd = () => {
		TeacherForm.value = ref({
			name: '',
			sex: '',
			phone: '',
			academyId: '',
		})
		manageDialog.value = true
	}
	const openEdit = (id) => {
		API({
			url:"/deanApi/selectByIdTeacher",
			method:"get",
			params:{Id:id}
		}).then((res) => {
			TeacherForm.value = res.data.data
			TeacherForm.value.sex = TeacherForm.value.sex.toString()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = true
	}
	const editTeacher = (id) => {
		API({
			url:"/deanApi/editTeacher",
			method:"post",
			data:TeacherForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getTeacherData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = false
	}
	const addTeacher = () => {
		API({
			url:"/deanApi/addTeacher",
			method:"post",
			data:TeacherForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getTeacherData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = false
	}
	const getTeacherData = () => {
		var SelectData = search.value

		API({
			url: "/deanApi/selectTeacher",
			method: "get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
				acId:chooseCollege.value,
				SelectData:SelectData
			}
		}).then((res) => {
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
	const OpendeleteTeacher = (id, name) => {
		ElMessageBox.confirm(
				'是否确定删除教号为:' + id + "名字为:" + name + "的教师吗？",
				'Warning', {
					confirmButtonText: '是',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				deleteTeacher(id)
			}).catch(() => {
		
			})
	}
	const deleteTeacher = (id) => {
		API({
			url: "/deanApi/deleteTeacher",
			method: "post",
			data: {
				id: id
			}
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '删除成功',
			})
			getTeacherData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const resetPassword = (name, id) => {
		ElMessageBox.confirm(
				'是否确定重置学号为:' + id + "名字为:" + name + "的密码",
				'Warning', {
					confirmButtonText: '是',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url: "/deanApi/resetTeacherPassword",
					method: "post",
					data: {
						"id": id,
					},
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
				}).catch((error) => {
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
	
			}).catch(() => {
	
			})
	}
	onMounted(() => {
		getTeacherData()
		GetCollegeList()
	})
</script>

<style src="../../static/dean/teacherManage.scss" lang="scss" scoped></style>