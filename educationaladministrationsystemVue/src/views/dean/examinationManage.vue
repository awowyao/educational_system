<template>
	<div>
		<el-button style="margin-right: 18px;" type="primary" @click="openAdd">添加考试</el-button>
		<el-cascader v-model="chooseCascader" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="search" placeholder="搜索科目名字或考试名" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="chooseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" width="180" />
		<el-table-column prop="id" label="考试Id" width="180" />
		<el-table-column prop="name" label="考试名字" />
		<el-table-column label="考试科目">
			<template #default="scope">
				<span v-if="scope.row.courseName!=null">{{scope.row.courseName}}</span>
				<span v-else>{{scope.row.electveCourseName}}</span>
			</template>
		</el-table-column>
		<el-table-column prop="specialtyName" label="专业">
			<template #default="scope">
				<span v-if="scope.row.courseName!=null">{{scope.row.specialtyName}}</span>
				<span v-else>选修课程</span>
			</template>
		</el-table-column>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary"
					@click="openDelete(scope.row.id,scope.row.name,scope.row.courseName)">删除</el-button>
				<el-button type="primary" @click="openEdit(scope.row.id, scope.row.electveCourseName)">编辑</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getExamList()" />
	</div>
	<el-dialog v-model="manageDialog" :title="dialogTitle" width="700">
		<el-form :model="form" class="dialogForm">
			<el-form-item label="名字" :label-width="DialogWidth">
				<el-input v-model="form.name" autocomplete="off" style="width: 216px;" />
			</el-form-item>

			<el-form-item label="学院" :label-width="DialogWidth">
				<el-select v-model="form.academyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value"
						@click="getSpecialtyList(item.value,'1')" />
						
				</el-select>
			</el-form-item>
			<el-form-item label="选择课程类型" :label-width="DialogWidth">
				<el-radio-group v-model="courseType">
					<el-radio label="1" @click="getCourseList('0', '1')">必修课</el-radio>
					<el-radio label="2" @click="getEleCourseList(form.academyId)">选修课</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="专业" :label-width="DialogWidth" v-if="courseType == '1'">
				<el-select v-model="form.specialtyName" placeholder="选择专业">
					<el-option v-for="(item, index) in SpecialtyList" :label="item.name" :key="index" :value="item.name"
						@click="getCourseList(item.name, '1')" />
				</el-select>
			</el-form-item>

			<el-form-item v-if="courseType == '1'" label="科目" :label-width="DialogWidth">
				<el-select v-model="form.courseId" placeholder="选择科目">
					<el-option v-for="(item, index) in courseList" :label="item.name" :key="index" :value="item.id" @click="form.electveCourseId = null" />
				</el-select>
			</el-form-item>
			
			<el-form-item v-else label="科目" :label-width="DialogWidth">
				<el-select v-model="form.electveCourseId" placeholder="选择科目">
					<el-option v-for="(item, index) in courseList" :label="item.name" :key="index" :value="item.id" @click="form.courseId = null" />
				</el-select>
			</el-form-item>
			<el-form-item label="开始时间" :label-width="DialogWidth">
				<el-date-picker value-format="YYYY-MM-DD hh:mm:ss" v-model="form.examBeginTime" type="datetime"
					placeholder="Select date and time" />
			</el-form-item>

			<el-form-item label="考试时长(单位为分钟)" :label-width="DialogWidth">
				<el-select filterable allow-create v-model="form.examTime" placeholder="选择或输入时长">
					<el-option label="60" value="60" />
					<el-option label="90" value="90" />
					<el-option label="120" value="120" />
				</el-select>
			</el-form-item>

		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">Cancel</el-button>
				<el-button v-if="dialogType == 1" type="primary" @click="addExam">
					提交
				</el-button>
				<el-button v-else type="primary" @click="editExam">
					提交
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
	import {
		useRoute
	} from 'vue-router'
	import API from '../../api/config'
	const props1 = {
		checkStrictly: true,
	}
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const dialogTitle = ref('')
	const dialogType = ref('')
	const courseType = ref("1")
	var route = useRoute()
	const DialogWidth = 230;
	const chooseCascader = ref('')
	const search = ref('')
	var chooseAcS = {
		'acId': 0,
		"SName": "0"
	}
	const tableData = ref([])
	const manageDialog = ref(false)
	var SpecialtyList = ref([])
	var courseList = ref([])
	const form = ref({
		name: '',
		examBeginTime: '',
		examTime: '',
		academyId: '',
		specialtyName: '',
		courseId: '',
	})
	const options = ref([])
	const addExam = () => {

		API({
			url: "/examApi/addExam",
			method: "post",
			data: form.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getExamList()
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = false
	}

	const editExam = () => {
		API({
			url: "/examApi/editExam",
			method: "post",
			data: form.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: res.data.msg,
			})
			getExamList()
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = false
	}

	const openAdd = () => {
		dialogTitle.value = "添加考试"
		dialogType.value = 1
		form.value = {
			name: '',
			examBeginTime: '',
			examTime: '',
			academyId: '',
			specialtyName: null,
			courseId: '',
		}
		manageDialog.value = true
	}

	const openDelete = (id, name, s) => {
		ElMessageBox.confirm(
				'是否确定删除科目' + s + "的" + name + "，" + "考试吗?",
				'Warning', {
					confirmButtonText: '是',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				API({
					url: "/examApi/deleteExam",
					method: "post",
					data: {
						id: id
					}
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: res.data.msg,
					})
					getExamList()
				}).catch((error) => {
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
			}).catch(() => {

			})
	}
	const openEdit = (id,neme) => {
		if(neme != null){
			courseType.value = "2"
		}
		dialogTitle.value = "编辑考试"
		dialogType.value = 2
		form.value = {
			name: '',
			examBeginTime: '',
			examTime: '',
			academyId: '',
			specialtyName: null,
			courseId: '',
		}
		API({
			url: "/examApi/selectByIdExam",
			method: "get",
			params: {
				id: id
			}
		}).then((res) => {

			getSpecialtyList(res.data.data.academyId, "2")
			if(neme == null){
				getCourseList(res.data.data.specialtyName, "2")
			}else {
				getEleCourseList(res.data.data.academyId)
			}
			form.value = res.data.data
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		manageDialog.value = true
	}
	const getEleCourseList = (id) =>{
		if(id) {
			API({
				url: "/courseApi/GetElectiveCourse",
				method: "get",
				params: {
					pahe: 1,
					pageSize: 100,
					acId: id,
				}
			}).then((res) => {
				courseList.value = res.data.data.list
			}).catch((error) =>{
				
			})
		}

	}
	const getOptionsList = () => {
		API({
			url: "/deanApi/selectAcademyOrSpecialty",
			method: "get"
		}).then((res) => {
			options.value = res.data.data;
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	const getExamList = () => {
		var Select = search.value

		API({
			url: "/examApi/selectExamList",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				SelectData: Select,
				acId: chooseAcS.acId,
				specialtyName: chooseAcS.SName
			}
		}).then((res) => {
			total.value = res.data.data.total
			if (res.data.data.pages <= 1) {
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
	const chooseData = () => {
		var choose = chooseCascader.value
		if (choose) {
			if (choose.length == 2) {
				var academyId = choose[0]
				var specialtyName = choose[1]
				chooseAcS.acId = academyId
				chooseAcS.SName = specialtyName
				getExamList()
			}
			if (choose.length == 1) {
				academyId = choose[0]
				chooseAcS.acId = academyId
				chooseAcS.SName = ""
				getExamList()
			}
		} else {
			chooseAcS.acId = 0
			chooseAcS.SName = null
			getExamList()
		}
	}
	const getSpecialtyList = (academyId, mode) => {
		if (mode == "1") {
			form.value.specialtyName = null
			form.value.courseId = ''
			courseList.value = []
		}
		if(courseType.value == "2"){
			getEleCourseList(academyId)
		}else {
			API({
				url: "/classApi/getSpecialty",
				method: "get",
				params: {
					academyId: academyId
				}
			}).then((res) => {
			
				SpecialtyList.value = res.data.data;
			}).catch((error) => {
				ElMessage({
					type: 'warning',
					message: error.response.data.msg,
				})
			})
		}
		
	}
	const getCourseList = (specialty, mode) => {
		if (mode == "1") {
			form.value.courseId = ''
		}
		API({
			url: "/examApi/BySpecialtyGetCourse",
			method: "get",
			params: {
				Specialty: specialty
			}
		}).then((res) => {
			courseList.value = res.data.data
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	onMounted(() => {
		getExamList()
		getOptionsList()
	})
</script>

<style src="../../static/dean/teacherManage.scss" lang="scss" scoped></style>