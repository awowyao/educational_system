<template>
	<div>
		<el-cascader v-model="chooseCascader" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="search" placeholder="搜索学生名字或学号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="chooseData">搜索</el-button>
	</div>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="100" />
		<el-table-column prop="id" label="学号" min-width="100" />
		<el-table-column prop="name" label="名字" min-width="100" />
		<el-table-column prop="academyName" label="学院" min-width="100" />
		<el-table-column prop="className" label="班级" min-width="100" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button key="查看成绩" type="primary" @click="lookScores(scope.row.id)" link>
					查看成绩
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getStudentData()" />
	</div>

	<el-dialog v-model="studenScoreVisible" style="max-height: 400px;overflow: auto;" title="成绩查看" width="800">
		<el-table :data="studentTableData" style="width: 100%">
			<el-table-column align="center" label="考试" prop="examName" min-width="120" />
			<el-table-column align="center" label="课程" prop="name" min-width="120" />
			<el-table-column align="center" label="老师" prop="teacherName" min-width="80" />
			<el-table-column align="center" label="成绩" min-width="80">
				<template #default="scope">
					<span v-if="scope.row.scoresState==1">{{scope.row.scores}}</span>
					<el-tag v-else type="info">未发布成绩</el-tag>
				</template>

			</el-table-column>
		</el-table>
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
	const studenScoreVisible = ref(false)
	const studentTableData = ref([])

	var chooseAcS = {
		'acId': 0,
		'classId': 0
	}


	const search = ref('')
	const tableData = ref([])
	const options = ref([])
	const lookScores = (id) => {
		API({
			url: "/studentApi/GetStudentScores",
			method: "get",
			params: {
				page: 1,
				pageSize: 100,
				studentId: id
			}
		}).then((res) => {
			studentTableData.value = res.data.data.list
			studenScoreVisible.value = true
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

	const getOptionsList = () => {
		API({
			url: "/deanApi/selectAcademyOrClass",
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


	onMounted(() => {
		getStudentData()
		getOptionsList()
	});
</script>

<style>
</style>