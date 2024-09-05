<template>
	<view>
		<el-select v-model="chooseCollege" placeholder="选择学院"  clearable @clear="getTeacherData">
			<el-option :label="item.label" :value="item.value" v-for="(item, index) in CollegeList" 
			:key="index" 
			@click="getTeacherData" />
		</el-select>
		<el-input v-model="search" placeholder="搜索教师名字或教号" style="width: 200px;margin-left: 18px;" />
		<el-button type="primary" style="margin-left: 6px;" @click="getTeacherData">搜索</el-button>
	</view>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="180" />
		<el-table-column prop="id" label="教号" min-width="90" />
		<el-table-column prop="name" label="名字" min-width="180"/>
		<el-table-column prop="academyName" label="学院" min-width="180"/>
		<el-table-column label="课程表" min-width="180">
			<template #default="scope">
				<el-button key="编辑课程表" type="primary"
				@click="editCurriculum(scope.row.id, scope.row.curriculumId, scope.row.name)" link>
				编辑课程表
				</el-button>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getTeacherData()" />
	</div>
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
	import { useRoute } from 'vue-router'
	var route = useRoute()
	const tableData = ref()
	const search = ref('')
	var CollegeList = []
	const chooseCollege = ref('')
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
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
	
	const editCurriculum = (id, cid, name)=>{
		location.href = "/dean/teacherCurriculum?id="+id+"&cid="+cid+"&name="+name
	}
	onMounted(()=>{
		getTeacherData()
		GetCollegeList()
	})
</script>

<style>
</style>