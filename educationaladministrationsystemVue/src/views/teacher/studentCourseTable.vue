<template>
	<div>
		<el-table border :data="classTableData" style="width: 100%">
			<el-table-column align="center" prop="ClassTime" label="课程时间" width="100" />
			<el-table-column align="center" label="星期一" width="180" >
				<template #default="scope">
					<div class="courseTable">
						<div v-if="scope.row.Monday.class">
							<p class="classTableConlumn">{{scope.row.Monday.class}}</p>
							<p class="classTableConlumn">上课老师:{{scope.row.Monday.teacher}}</p>
							<p class="classTableConlumn">课程类型:{{scope.row.Monday.courseType}}</p>
							<!-- <el-button  type="primary" link @click="addCurriculumClass(scope.row.ClassTime, 'Monday')">添加课程</el-button> -->
						</div>
					</div>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期二" width="180">
				<template #default="scope">
					<div class="courseTable">
						<div v-if="scope.row.Tuesday.class">
							<p class="classTableConlumn">{{scope.row.Tuesday.class}}</p>
							<p class="classTableConlumn">上课老师:{{scope.row.Tuesday.teacher}}</p>
							<p class="classTableConlumn">课程类型:{{scope.row.Tuesday.courseType}}</p>
						</div>
					</div>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期三" width="180">
				<template #default="scope">
					<div class="courseTable">
						<div v-if="scope.row.Wednesday.class">
							<p class="classTableConlumn">{{scope.row.Wednesday.class}}</p>
							<p class="classTableConlumn">上课老师:{{scope.row.Wednesday.teacher}}</p>
							<p class="classTableConlumn">课程类型:{{scope.row.Wednesday.courseType}}</p>
						</div>
					</div>

				</template>
			</el-table-column>
			<el-table-column align="center" label="星期四" width="180">
				<template #default="scope">
					<div class="courseTable">
						<div v-if="scope.row.Thursday.class" >
							<p class="classTableConlumn">{{scope.row.Thursday.class}}</p>
							<p class="classTableConlumn">上课老师:{{scope.row.Thursday.teacher}}</p>
							<p class="classTableConlumn">课程类型:{{scope.row.Thursday.courseType}}</p>
						</div>
					</div>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期五" width="180">
				<template #default="scope">
					<div class="courseTable">
						<div v-if="scope.row.Friday.class" class="courseTable">
							<p class="classTableConlumn">{{scope.row.Friday.class}}</p>
							<p class="classTableConlumn">上课老师:{{scope.row.Friday.teacher}}</p>
							<p class="classTableConlumn">课程类型:{{scope.row.Friday.courseType}}</p>
						</div>
					</div>

				</template>
			</el-table-column>
		</el-table>
	</div>
	
</template>

<script setup>
	import {
		onMounted,
		ref
	} from "vue"
	import {
		useRoute
	} from 'vue-router'
	import {ElMessage} from 'element-plus'
	import API from '../../api/config'
	const chooseCourse = ref('')
	var route = useRoute()
	const classTableData = ref([])
	
	const GetCurriculum = () => {
		var id = route.query.id
		var cid = route.query.cid
		API({
			url: "/studentApi/GetStudentCurriculum",
			method: 'get',
			params: {
				sId: id,
				cid: cid
			}
		}).then((res) => {
			if(cid == 0){
				location.href = "/dean/teacherCurriculum?id="+id+"&cid="+res.data.data.id+"&name="+route.query.name
			}else{
				var json = JSON.parse(res.data.data.curriculumData);
				classTableData.value = json;
			}
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}



	onMounted(() => {
		GetCurriculum()
	})
</script>

<style src="../../static/student/selectClassTable.scss" lang="scss" scoped></style>