<template>
	<div>
		<el-select v-model="chooseCollege" placeholder="选择学院" clearable @clear="getTeacherData">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" :key="index"
				@click="getTeacherData" />
		</el-select>
		<el-input v-model="search" placeholder="搜索教师名字或教号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="getTeacherData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="180" />
		<el-table-column prop="id" label="教号" min-width="90" />
		<el-table-column prop="name" label="名字" min-width="180" />
		<el-table-column prop="academyName" label="学院" min-width="180" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button key="处理学生申述" type="primary" @click="lookStudentScores(scope.row.id)" link>
					处理学生申述
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getTeacherData" />
	</div>
	<el-dialog v-model="studenScoreVisible" style="max-height: 400px;overflow: auto;" title="查看学生申述" width="800">
		<el-table :data="tableDataT" style="width: 100%">
			<el-table-column align="center" fixed prop="createTime" label="提交申诉时间" min-width="150" />
			<el-table-column align="center" prop="id" label="学号" min-width="100" />
			<el-table-column align="center" prop="name" label="学生名字" min-width="100" />
			<el-table-column align="center" prop="courseName" label="申诉科目" min-width="100" />
			<el-table-column align="center" prop="className" label="班级" min-width="100" />
			<el-table-column align="center" prop="scores" label="成绩" min-width="60" />
			<el-table-column align="center" label="操作" min-width="120">
				<template #default="scope">
					<el-button v-if="scope.row.complaintState == 0" type="primary"
						@click="openUpScore(scope.row.seId)">修改成绩</el-button>
					<el-button v-if="scope.row.complaintState == 0" type="primary"
						@click="openTiJiao(scope.row.seId)">驳回</el-button>
					<el-button v-else-if="scope.row.complaintState == 1" type="primary" disabled>已修改成绩</el-button>
					<el-button v-else-if="scope.row.complaintState == 2" type="primary" disabled>已驳回</el-button>
				</template>
			</el-table-column>
		</el-table>
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
	import {
		useRoute
	} from 'vue-router'
	var route = useRoute()
	const chooseCollege = ref('')
	var CollegeList = []

	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const tableData = ref([])
	const tableDataT = ref([])
	const search = ref('')
	const studenScoreVisible = ref(false)
	var chooseId = 0
	const lookStudentScores = (id) => {
		chooseId = id
		API({
			url: "/teacherApi/GetStudentComplaint",
			method: "get",
			params: {
				page: 1,
				pageSize: 100,
				teacherId: id
			}
		}).then((res) => {
			tableDataT.value = res.data.data.list
			studenScoreVisible.value = true
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})

	}
	const GetCollegeList = () => {
		API({
			url: "/deanApi/selectAcademyOrClass",
			method: "get",

		}).then((res) => {
			CollegeList = res.data.data
		}).catch((error) => {
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
			lookStudentScores(chooseId)
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	
	}
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
	const openUpScore = (id, title) => {
		ElMessageBox.prompt('请输入' + title + '后的成绩', title + '成绩', {
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
				inputPattern: /^[0-9]$|^[0-9]{2}$|^[1][0][0]$/,
				inputErrorMessage: '不是合法的成绩输入',
			})
			.then(({
				value
			}) => {
				API({
					url: '/teacherApi/editStudentScores',
					method: 'post',
					data: {
						'id': id,
						'scores': value,
						'def': 2
					}
				}).then((res) => {
					ElMessage({
						type: 'success',
						message: '操作成功',
					})
					lookStudentScores(chooseId)
				}).catch((error) => {
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})

			})
			.catch(() => {

			})
	}
	const getTeacherData = () => {
		var SelectData = search.value

		API({
			url: "/deanApi/selectTeacher",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				acId: chooseCollege.value,
				SelectData: SelectData
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
	onMounted(() => {
		getTeacherData()
		GetCollegeList()
	})
</script>

<style src="../../static/dean/teacherManage.scss" lang="scss" scoped></style>