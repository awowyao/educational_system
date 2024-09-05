<template>
	<view>

		<el-input v-model="search" style="width: 220px; margin-left: 16px;" placeholder="输入课程名" />
		<el-button type="primary" style="font-size: 13px;margin-left: 20px;" @click="getCourseTable">查询</el-button>
	</view>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column align="center" label="考试" prop="examName" min-width="160px"/>
		<el-table-column align="center" label="课程" prop="name"  min-width="160px"/>
		<el-table-column align="center" label="老师" prop="teacherName" min-width="100px" />
		<el-table-column align="center" label="成绩" prop="scores" min-width="80px"/>
		<el-table-column label="操作">
			<template #default="scope">
				<el-button type="primary" v-if="scope.row.scoresState==1" @click="openTiJiao(scope.row)">申诉</el-button>
				<el-tag v-else type="info">未发布成绩</el-tag>
			</template>
		</el-table-column>
	</el-table>
	<div
		style="width: calc(100% - 300px);display: flex;flex-direction: row;justify-content: center; margin-top: 50px;margin-left: 30px;">
		<el-pagination v-model:current-page="page" :hide-on-single-page="showPage" v-model:page-size="pageSize"
			:page-sizes="[5, 10, 20, 30]" layout="sizes, prev, pager, next" :total="total" @change="getCourseTable" />
	</div>
</template>

<script setup>
	// alert(12456)
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import API from '../../api/config'
	const search = ref('')
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)

	const tableData = ref([{
		class: '语文课',
		teacher: 'Tom',
		scorePoints: '3.0',
		score: '80'
	}, ])

	const openTiJiao = (row) => {
		ElMessageBox.confirm(
				'确定要申诉吗',
				'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning',
				}
			)
			.then(() => {
				representations(row)

			})
			.catch(() => {
				ElMessage({
					type: 'info',
					message: '取消成功',
				})
			})
	}

	const representations = (row) => {
		API({
			url: "/studentApi/ComplaintCourse",
			method: "post",
			data: {
				'CourseId': row.id,
				'seId': row.seId,
				'teacherId': row.teacherId,
			}
		}).then(() => {
			ElMessage({
				type: 'success',
				message: '申请申诉成功',
			})
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const getCourseTable = () => {
		API({
			url: "/studentApi/GetStudentScores",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
				select: search.value
			}
		}).then((res) => {
			total.value = res.data.data.total
			if(res.data.data.pages <= 1){
				showPage.value = true
			}
			page.value = res.data.data.pageNum
			tableData.value = res.data.data.list
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	onMounted(() => {
		getCourseTable()
	})
</script>

<style>
</style>