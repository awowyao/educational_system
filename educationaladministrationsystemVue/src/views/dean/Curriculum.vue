<template>
	<div>
		<el-table :data="classTableData" style="width: 100%">
			<el-table-column prop="ClassTime" label="课程时间" width="100" />

			<el-table-column align="center" label="星期一" width="180">
				<template #default="scope">
					<div v-if="scope.row.Monday.class">
						<p class="classTableConlumn">{{scope.row.Monday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Monday.teacher}}</p>
						<p class="classTableConlumn">课程类型:必修</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Monday')">修改课程</el-button>
						<!-- <el-button  type="primary" link @click="addCurriculumClass(scope.row.ClassTime, 'Monday')">添加课程</el-button> -->
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Monday')">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期二" width="180">
				<template #default="scope">
					<div v-if="scope.row.Tuesday.class">
						<p class="classTableConlumn">{{scope.row.Tuesday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Tuesday.teacher}}</p>
						<p class="classTableConlumn">课程类型:必修</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Tuesday')">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Tuesday')">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期三" width="180">
				<template #default="scope">
					<div v-if="scope.row.Wednesday.class">
						<p class="classTableConlumn">{{scope.row.Wednesday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Wednesday.teacher}}</p>
						<p class="classTableConlumn">课程类型:必修</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Wednesday')">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Wednesday')">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期四" width="180">
				<template #default="scope">
					<div v-if="scope.row.Thursday.class">
						<p class="classTableConlumn">{{scope.row.Thursday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Thursday.teacher}}</p>
						<p class="classTableConlumn">课程类型:必修</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Thursday')">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Thursday')">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期五" width="180">
				<template #default="scope">
					<div v-if="scope.row.Friday.class">
						<p class="classTableConlumn">{{scope.row.Friday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Friday.teacher}}</p>
						<p class="classTableConlumn">课程类型:必修</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Friday')">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Friday')">添加课程</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
	
	<el-dialog v-model="manageDialog" title="添加课程" width="500">
		
	<!-- 		<el-form-item label="课程学院" label-width="200">
				<el-select v-model="chooseAcademyId" placeholder="选择学院">
					<el-option v-for="(item, index) in options" :label="item.label" :key="index" :value="item.value" @click="getCourseList(item.value,'1')"/>
				</el-select>
			</el-form-item> -->
			<el-form-item label="课程" label-width="150">
				<el-select v-model="chooseCourse" value-key="id" placeholder="选择课程">
					<el-option :label="item.name" :key="index" v-for="(item, index) in courseList"  :value="item" />
				</el-select>
			</el-form-item>
<!-- 			<el-form-item label="授课老师" label-width="200">
				<el-select v-model="chooseTeacher" value-key="id" placeholder="选择教师">
					<el-option :label="item.name" :key="index" v-for="(item, index) in teacherList"  :value="item" />
				</el-select>
			</el-form-item> -->
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="manageDialog = false">取消</el-button>
				<el-button type="primary" @click="addCurriculumClass">
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
	} from "vue"
	import {
		useRoute
	} from 'vue-router'
	import {
		ElMessage,
	} from 'element-plus'
	import API from '../../api/config'
	const chooseCourse = ref('')
	
	var route = useRoute()
	const courseList = ref([])
	const teacherList = ref([])
	const classTableData = ref([])
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	const options = ref([])
	var oldClass = {"ClassTime":"","Week":"","id":"","class":"","teacher":"","teacherId":"","className":"",
	"classId":""}
	var newClass = {"ClassTime":"","Week":"","id":"","class":"",
	"teacher":"",
	"teacherId":"",
	"className":route.query.name,
	"classId":route.query.id}
	const chooseAcademyId = ref('')
	const chooseTeacher = ref()
	var chooseClassTime = ''
	var chooseWeek = ''
	const openCurriculumClass = (classTime, Week)=>{
		chooseClassTime = classTime
		chooseWeek = Week
		var id = route.query.id
		API({
			url:"/classApi/GetClassCourse",
			method:"get",
			params:{
				"ClassId":id
			}
		}).then((res) =>{
			courseList.value = res.data.data
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		chooseAcademyId.value = ''
		chooseCourse.value = ''
		chooseTeacher.value = ''

		manageDialog.value = true
	}
	const addCurriculumClass = () => {
		for (var i=0;i<classTableData.value.length;i++) {
			if(classTableData.value[i].ClassTime == chooseClassTime){
				oldClass.id = classTableData.value[i][chooseWeek].id
				oldClass.class = classTableData.value[i][chooseWeek].class
				oldClass.teacher = classTableData.value[i][chooseWeek].teacher
				oldClass.teacherId = classTableData.value[i][chooseWeek].teacherId
				oldClass.className = classTableData.value[i][chooseWeek].className
				oldClass.classId = classTableData.value[i][chooseWeek].classId
				oldClass.ClassTime = chooseClassTime
				oldClass.Week = chooseWeek
				newClass.ClassTime = chooseClassTime
				newClass.Week = chooseWeek

				newClass.id = chooseCourse.value.id
				newClass.class = chooseCourse.value.name
				newClass.teacher = chooseCourse.value.teacherName
				newClass.teacherId = chooseCourse.value.teacherId



				if(!chooseCourse.value.name){
					classTableData.value[i][chooseWeek].className = ""
					classTableData.value[i][chooseWeek].classId = ""
					classTableData.value[i][chooseWeek].id = ""
					classTableData.value[i][chooseWeek].class = ""
					classTableData.value[i][chooseWeek].teacher = ""
					classTableData.value[i][chooseWeek].teacherId = ""
				}else{
					classTableData.value[i][chooseWeek].id = chooseCourse.value.id
					classTableData.value[i][chooseWeek].class = chooseCourse.value.name
					classTableData.value[i][chooseWeek].teacher = chooseCourse.value.teacherName
					classTableData.value[i][chooseWeek].teacherId = chooseCourse.value.teacherId
					classTableData.value[i][chooseWeek].className = route.query.name
					classTableData.value[i][chooseWeek].classId = route.query.id
				}
			}
		}
		editCurriculum()
		manageDialog.value = false
		
	}
	const editCurriculum = () =>{
		var cid = route.query.cid
		var id = route.query.id
		API({
			url:"/classApi/editCurriculum",
			method:"post",
			data:{
				id:cid,
				cid:id,
				curriculumData:classTableData.value,
				oldCurData:oldClass,
				newCurData:newClass
			}
		}).then((res)=>{
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
		
	}
	const GetCurriculum = () => {
		var id = route.query.id
		var name = route.query.name
		var cid = route.query.cid
		API({
			url: "/classApi/GetCurriculum",
			method: 'get',
			params: {
				id: id,
				cid: cid
			}
		}).then((res) => {
			if(cid == 0){
				location.href = "/dean/Curriculum?id="+id+"&cid="+res.data.data.id+"&name="+name
			}else{
				var json = JSON.parse(res.data.data.curriculumData);
				classTableData.value = json;
				console.log(classTableData.value)
			}
			
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}

	const getCourseList = (id, mode)=>{
		chooseCourse.value = ''
		chooseTeacher.value = ''
		teacherList.value = []
		API({
			url:"/classApi/ByAcademyIdGetCourse",
			method:"get",
			params:{
				academyId:id
			}
		}).then((res) => {
			courseList.value = res.data.data;
		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const getTeacherList = (id, mode) => {
		chooseTeacher.value = ''
		API({
			url: "/classApi/ByCourseIdGetTeacher",
			method: "get",
			params: {
				id: id
			}
		}).then((res) => {
			teacherList.value = res.data.data
			
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