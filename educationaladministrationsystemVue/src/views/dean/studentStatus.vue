<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAdd()">添加学生</el-button>
		<el-cascader v-model="chooseCascader" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="search" placeholder="搜索学生名字或学号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="chooseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="100" />
		<el-table-column prop="id" label="学号" min-width="100" />
		<el-table-column prop="name" label="名字" min-width="100"/>
		<el-table-column prop="academyName" label="学院" min-width="100" />
		<el-table-column prop="className" label="班级"  min-width="100"/>
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button type="primary" @click="openDelete(scope.row.name,scope.row.id)">删除</el-button>
				<el-button type="primary" @click="openEditStudent(scope.row.id)">编辑</el-button>
				<el-button type="primary" @click="resetPassword(scope.row.name,scope.row.id)">重置密码</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getStudentData()" />
	</div>
	<el-dialog v-model="manageDialog" title="添加学生" width="600">
		<el-form :model="StudenForm" class="dialogForm">
			<el-form-item label="姓名" :label-width="DialogWidth">
				<el-input v-model="StudenForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="StudenForm.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="StudenForm.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value"
						@click="getClassList(item.value,'1')" />
				</el-select>
			</el-form-item>
			<el-form-item label="班级" :label-width="DialogWidth">
				<el-select v-model="StudenForm.classId" placeholder="选择学院">
					<el-option v-for="(item, index) in ClassList" :label="item.name" :key="index" :value="item.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="StudenForm.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="监护人" :label-width="DialogWidth">
				<el-input v-model="StudenForm.guardian" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="监护人电话" :label-width="DialogWidth">
				<el-input v-model="StudenForm.guardianPhone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="addStudent">
					提交
				</el-button>
			</span>
		</template>
	</el-dialog>

	<el-dialog v-model="EditDialog" title="编辑学生" width="600">
		<el-form :model="StudenForm" class="dialogForm">
			<el-form-item label="姓名" :label-width="DialogWidth">
				<el-input v-model="StudenForm.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="性别" :label-width="DialogWidth">
				<el-radio-group v-model="StudenForm.sex">
					<el-radio label="1">男</el-radio>
					<el-radio label="2">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="StudenForm.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value"
						@click="getClassList(item.value, '1')" />
				</el-select>
			</el-form-item>
			<el-form-item label="班级" :label-width="DialogWidth">
				<el-select v-model="StudenForm.classId" placeholder="选择学院">
					<el-option v-for="(item, index) in ClassList" :label="item.name" :key="index" :value="item.id" />
				</el-select>
			</el-form-item>
			<el-form-item label="手机号" :label-width="DialogWidth">
				<el-input v-model="StudenForm.phone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="监护人" :label-width="DialogWidth">
				<el-input v-model="StudenForm.guardian" autocomplete="off" style="width: 216px;" />
			</el-form-item>
			<el-form-item label="监护人电话" :label-width="DialogWidth">
				<el-input v-model="StudenForm.guardianPhone" autocomplete="off" style="width: 216px;" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="EditDialog = false">取消</el-button>
				<el-button type="primary" @click="editStudent">
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
	const DialogWidth = 230;
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	var chooseAcS = {
		'acId':0,
		'classId':0
	}
	var editId = ''
	var ClassList = ref([])
	const StudenForm = ref({
		name: '',
		sex: '',
		phone: '',
		guardian: '',
		guardianPhone: '',
		academyId: '',
		classId: '',
	})
	const search = ref('')
	const tableData = ref([])
	const options = ref([])

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

	const openAdd = () => {
		StudenForm.value = ref({
			name: '',
			sex: '',
			phone: '',
			guardian: '',
			guardianPhone: '',
			academyId: '',
			classId: '',
		})
		manageDialog.value = true

	}
	const deleteStudent = (id) => {
		API({
			url: "/deanApi/deleteStudent",
			method: "post",
			data: {
				id: id
			}
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '删除成功',
			})
			getStudentData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const openEditStudent = (id) => {
		StudenForm.value = ref({
			name: '',
			sex: '',
			phone: '',
			guardian: '',
			guardianPhone: '',
			academyId: '',
			classId: '',
		})
		API({
			url: "/deanApi/selectByIdStudent",
			method: "get",
			params: {
				StudentId: id
			}
		}).then((res) => {
			StudenForm.value = res.data.data
			StudenForm.value.sex = StudenForm.value.sex.toString()
			getClassList(StudenForm.value.academyId, "2")
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		EditDialog.value = true
		editId = id
	}
	const openDelete = (name, id) => {
		ElMessageBox.confirm(
				'是否确定删除学号为:' + id + "名字为:" + name + "的学生",
				'Warning', {
					confirmButtonText: '是',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				deleteStudent(id)
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
					url: "/deanApi/resetStudentPassword",
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
	const addStudent = () => {

		API({
			url: "/deanApi/addStudent",
			method: "post",
			data: StudenForm.value,
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getStudentData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = false
	}
	const editStudent = () => {
		API({
			url: "/deanApi/editStudent",
			method: "post",
			data: StudenForm.value,
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getStudentData()
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})

		EditDialog.value = false

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

	const getOptionsList = () => {
		API({
			url: "/deanApi/selectAcademyOrClass",
			method: "get"
		}).then((res) => {
			options.value = res.data.data;
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const getClassList = (academyId, mode) => {

		if (mode == '1') {
			StudenForm.value.classId = ''
		}
		API({
			url: "/deanApi/selectClass",
			method: "get",
			params: {
				academyId: academyId
			}
		}).then((res) => {

			ClassList.value = res.data.data;
		}).catch((error) =>{
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