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
				<el-button key="查询学生课程" type="primary" @click="lookStudentClassTable(scope.row.id)" link>
					查询学生课程
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getTeacherData" />
	</div>
	<el-dialog v-model="studenScoreVisible" style="max-height: 400px;overflow: auto;" title="查看学生课程" width="600">
		<el-table :data="tableDataT" style="width: 100%">
			<el-table-column align="center" prop="id" label="学号" min-width="120" />
			<el-table-column prop="name" label="名字" min-width="100" />
			<el-table-column prop="className" label="班级" min-width="120" />
			<el-table-column label="课程表" min-width="120">
				<template #default="scope">
					<el-button key="查看课程表" type="primary" v-if="scope.row.curriculumId!=null"
						@click="lookCurriculum(scope.row.id, scope.row.curriculumId)" link>
						查看课程表
					</el-button>
					<el-button key="未安排课程" type="primary" v-else link disabled>
						未安排课程
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-dialog v-model="CurriculumVisible" style="height: 400px;overflow: auto;" title="成绩查看" width="900">
			<el-table :data="CurriculumtableData" style="width: 100%">
				<el-table-column align="center" prop="ClassTime" label="课程时间" width="100" />
				<el-table-column align="center" label="星期一" width="150" >
					<template #default="scope">
						<div class="courseTable">
							<div v-if="scope.row.Monday.class">
								<p class="classTableConlumn">{{scope.row.Monday.class}}</p>
								<p class="classTableConlumn">上课老师:{{scope.row.Monday.teacher}}</p>
								<!-- <el-button  type="primary" link @click="addCurriculumClass(scope.row.ClassTime, 'Monday')">添加课程</el-button> -->
							</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" label="星期二" width="150">
					<template #default="scope">
						<div class="courseTable">
							<div v-if="scope.row.Tuesday.class">
								<p class="classTableConlumn">{{scope.row.Tuesday.class}}</p>
								<p class="classTableConlumn">上课老师:{{scope.row.Tuesday.teacher}}</p>
							</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" label="星期三" width="150">
					<template #default="scope">
						<div class="courseTable">
							<div v-if="scope.row.Wednesday.class">
								<p class="classTableConlumn">{{scope.row.Wednesday.class}}</p>
								<p class="classTableConlumn">上课老师:{{scope.row.Wednesday.teacher}}</p>
							</div>
						</div>
				
					</template>
				</el-table-column>
				<el-table-column align="center" label="星期四" width="150">
					<template #default="scope">
						<div class="courseTable">
							<div v-if="scope.row.Thursday.class" >
								<p class="classTableConlumn">{{scope.row.Thursday.class}}</p>
								<p class="classTableConlumn">上课老师:{{scope.row.Thursday.teacher}}</p>
							</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" label="星期五" width="150">
					<template #default="scope">
						<div class="courseTable">
							<div v-if="scope.row.Friday.class" class="courseTable">
								<p class="classTableConlumn">{{scope.row.Friday.class}}</p>
								<p class="classTableConlumn">上课老师:{{scope.row.Friday.teacher}}</p>
							</div>
						</div>
				
					</template>
				</el-table-column>
			</el-table>
			
		</el-dialog>
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
	const CurriculumVisible = ref(false)
	const CurriculumtableData = ref([])
	const studenScoreVisible = ref(false)
	var chooseId = 0
	const lookStudentClassTable = (id) => {
		chooseId = id
		API({
			url: "/teacherApi/GetByTeacherStudent",
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
	const lookCurriculum = (id, cid)=>{
		API({
			url: "/studentApi/GetStudentCurriculum",
			method: 'get',
			params: {
				sId: id,
				cid: cid
			}
		}).then((res) => {
			var json = JSON.parse(res.data.data.curriculumData);
			CurriculumtableData.value = json;
			CurriculumVisible.value = true
			
		}).catch((error) =>{
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