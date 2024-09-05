<template>
	<view>
		<el-select v-model="GradeValue" class="m-2" placeholder="Select"  style="width: 240px" clearable @clear="GetStudentData()">
			<el-option v-for="item in GradeOptions" :key="item.id" :label="item.name" :value="item.id" @click="GetStudentData(item.id)" />
		</el-select>
		
	</view>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="id" label="学号" min-width="180" />
		<el-table-column prop="name" label="名字" min-width="180" />
		<el-table-column prop="className" label="班级" min-width="180" />
		<el-table-column label="课程表" min-width="180">
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
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="GetStudentData" />
	</div>
</template>

<script setup>
	import {
		onMounted,
		ref
	} from 'vue';
	import API from '../../api/config'
	import {ElMessage} from 'element-plus'
	const GradeOptions = ref([])
	const GradeValue = ref('')
	const tableData = ref(
		[{
			studentID: '123',
			studentName: '张三',
			studentClass: '计科1班',
		}, ])
		const page = ref(1)
		const pageSize = ref(5)
		const total = ref()
		const showPage = ref(false)
	const GetTeacherClass = () =>{
		API({
			url:"/teacherApi/GetByTeacherClass",
			method:"get",
			params:{
				teacherId:24011801
			}
		}).then((res) =>{
			
			GradeOptions.value = res.data.data
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GetStudentData = (classId) => {
		if(GradeValue.value) {
			classId = GradeValue.value
		}
		API({
			url:"/teacherApi/GetByTeacherStudent",
			method:"get",
			params:{
				page: page.value,
				pageSize: pageSize.value,
				classId:classId
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
	
	const lookCurriculum = (id, cid) => {
		location.href = "/teacherManage/studentCourseTable?id="+id+"&cid="+cid
	}
	onMounted(()=>{
		GetStudentData()
		GetTeacherClass()
	})
	
</script>

<style>
</style>