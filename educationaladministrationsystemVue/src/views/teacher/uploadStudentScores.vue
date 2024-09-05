<template>
	<view>
		<el-cascader v-model="GradeValue" @change="chooseData" :options="options" :props="props1" clearable />
		<el-input v-model="studentInput" placeholder="输入学生名字或学号" style="width: 180px; margin-left: 20px;" />
		<el-button type="primary" size="Default" style="font-size: 14px;margin-left: 8px;" @click="getTableData">查询</el-button>

	</view>

	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="id" label="学号" min-width="120" />
		<el-table-column prop="name" label="学生名字" min-width="100" />
		<el-table-column prop="className" label="班级" min-width="100" />
		<el-table-column prop="courseName" label="课程" min-width="100" />
		<el-table-column prop="scoreState" label="成绩" :filters="scoreSteta" :filter-method="filterTag"
			filter-placement="bottom-end" min-width="90">
			<template #default="scope">
				<el-tag :type="scope.row.scoresState === 0 ? '' : 'success'"
					disable-transitions>
					<text v-if="scope.row.scoresState==0">未发布</text>
					<text v-else>{{ scope.row.scores }}</text>
				</el-tag>
			</template>
		</el-table-column>
		<el-table-column label="操作" min-width="200">
			<template #default="scope">
				<el-button type="primary" @click="openUpScore(scope.row.seId, '发布')" v-if="scope.row.scoresState==0">发布</el-button>
				<el-button type="primary" @click="openUpScore(scope.row.seId, '修改')" v-else>修改</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getTableData" />
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import API from '../../api/config'
	const GradeValue = ref([])
	const options = ref([])
	const scoreSteta = ref([{
			text: '未发布',
			value: 0
		},
		{
			text: '已发布',
			value: 1
		},
	])
	const studentInput = ref('')
	const props1 = {
		checkStrictly: true,
	}
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const tableData = ref([])
	var classCourseDic = {
		'classId':0,
		'courseId':0
	}
	const getTableData = ()=>{
		API({
			url:"/teacherApi/GetByExamStudent",
			method:"get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
				Select:studentInput.value,
				classId:classCourseDic.classId,
				courseId:classCourseDic.courseId
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
	const filterTag = (value, row) => {
		return row.scoresState === value
	}

	
	const openUpScore = (id, title) => {
		ElMessageBox.prompt('请输入'+title+'后的成绩', title+'成绩', {
				confirmButtonText: 'OK',
				cancelButtonText: 'Cancel',
				inputPattern: /^[0-9]$|^[0-9]{2}$|^[1][0][0]$/,
				inputErrorMessage: '不是合法的成绩输入',
			})
			.then(({
				value
			}) => {
				API({
					url:'/teacherApi/editStudentScores',
					method:'post',
					data:{
						'id': id,
						'scores':value
					}
				}).then(() =>{
					ElMessage({
						type: 'success',
						message: '操作成功',
					})
					getTableData()
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
	const getClassAndCourse = ()=>{
		API({
			url:"/teacherApi/GetClassAndCourse",
			method:"get"
		}).then((res)=>{
			options.value = res.data.data
		}).catch((error) =>{
					ElMessage({
						type: 'warning',
						message: error.response.data.msg,
					})
				})
	}
	const chooseData = ()=>{
		if(GradeValue.value!=null && GradeValue.value.length == 2){
			classCourseDic.classId = GradeValue.value[0]
			classCourseDic.courseId = GradeValue.value[1]
		}else if(GradeValue.value!=null && GradeValue.value.length == 1){
			classCourseDic.classId = GradeValue.value[0]
			classCourseDic.courseId = 0
		}else {
			classCourseDic.classId = 0
			classCourseDic.courseId = 0
		}
		getTableData()
	}
	onMounted((res)=>{
		getTableData(0)
		getClassAndCourse()
	})
</script>

<style>
</style>