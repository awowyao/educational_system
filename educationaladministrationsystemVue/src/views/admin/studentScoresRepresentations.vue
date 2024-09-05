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
				<el-button key="查看申述记录" type="primary" @click="lookRepresent(scope.row.id)" link>
					查看申述记录
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getStudentData()" />
	</div>

	<el-dialog v-model="studenScoreVisible" style="max-height: 400px;overflow: auto;" title="成绩申述" width="600">
		<el-table :data="studentTableData" style="width: 100%">
			<el-table-column align="center" fixed prop="createTime" label="提交申诉时间" min-width="150" />
			<el-table-column align="center" prop="name" label="申诉科目" min-width="120" />
			<el-table-column align="center" prop="teacherName" label="处理老师" min-width="120" />
			<el-table-column align="center" label="处理进度" min-width="190">
				<template #default="scope">
					<el-tag class="ml-2" type="info" v-if="scope.row.complaintState==0">未处理</el-tag>
					<el-tag class="ml-2" type="success" v-else-if="scope.row.complaintState==1">已修改</el-tag>
					<el-tag class="ml-2" type="danger" v-else-if="scope.row.complaintState==2">驳回</el-tag>
					<el-tag class="ml-2" type="danger" v-else>未知</el-tag>
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
	const lookRepresent = (id) => {
		API({
			url: "/studentApi/GetComplaintTable",
			method: "get",
			params: {
				page: 1,
				pageSize: 100,
			}
		}).then((res) => {
			studentTableData.value = res.data.data.list
			studenScoreVisible.value = true;
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