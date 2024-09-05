<template>
	<div>
		<el-table :data="classTableData" style="width: 100%">
			<el-table-column prop="ClassTime" label="课程时间" width="100" />

			<el-table-column align="center" label="星期一" width="180">
				<template #default="scope">
					<div v-if="scope.row.Monday.class">
						<p class="classTableConlumn">{{scope.row.Monday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Monday.teacher}}</p>
						<p class="classTableConlumn">课程类型:{{scope.row.Monday.courseType}}</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Monday',1)">修改课程</el-button>
						<!-- <el-button  type="primary" link @click="addCurriculumClass(scope.row.ClassTime, 'Monday')">添加课程</el-button> -->
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Monday',2)">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期二" width="180">
				<template #default="scope">
					<div v-if="scope.row.Tuesday.class">
						<p class="classTableConlumn">{{scope.row.Tuesday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Tuesday.teacher}}</p>
						<p class="classTableConlumn">课程类型:{{scope.row.Tuesday.courseType}}</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Tuesday',1)">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Tuesday',2)">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期三" width="180">
				<template #default="scope">
					<div v-if="scope.row.Wednesday.class">
						<p class="classTableConlumn">{{scope.row.Wednesday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Wednesday.teacher}}</p>
						<p class="classTableConlumn">课程类型:{{scope.row.Wednesday.courseType}}</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Wednesday',1)">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Wednesday',2)">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期四" width="180">
				<template #default="scope">
					<div v-if="scope.row.Thursday.class">
						<p class="classTableConlumn">{{scope.row.Thursday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Thursday.teacher}}</p>
						<p class="classTableConlumn">课程类型:{{scope.row.Thursday.courseType}}</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Thursday',1)">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Thursday',2)">添加课程</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" label="星期五" width="180">
				<template #default="scope">
					<div v-if="scope.row.Friday.class">
						<p class="classTableConlumn">{{scope.row.Friday.class}}</p>
						<p class="classTableConlumn">上课老师:{{scope.row.Friday.teacher}}</p>
						<p class="classTableConlumn">课程类型:{{scope.row.Friday.courseType}}</p>
						<el-button type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Friday',1)">修改课程</el-button>
					</div>
					<el-button v-else type="primary" link @click="openCurriculumClass(scope.row.ClassTime, 'Friday',2)">添加课程</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
	
	<el-dialog v-model="manageDialog" :title="manTitle" width="600">
		
			<el-form-item label="授课班级" label-width="200">
				<el-select v-model="chooseClass" placeholder="选择班级" value-key="id">
					<el-option :label="item.name" :key="index" v-for="(item, index) in classList" :value="item" @click="getCourseList(item.id)" />
				</el-select>
			</el-form-item>
			<el-form-item label="课程" label-width="200">
				<el-select v-model="chooseCourse" placeholder="选择课程" value-key="id">
					<el-option :label="item.name" :key="index" v-for="(item, index) in courseList"  :value="item" />
				</el-select>
			</el-form-item>

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
	import API from '../../api/config'
	const chooseCourse = ref('')
	import {
		ElMessage,
	} from 'element-plus'
	var route = useRoute()
	const courseList = ref([])
	const classList = ref([])
	const classTableData = ref([])
	const manageDialog = ref(false)
	const EditDialog = ref(false)
	const options = ref([])
	const manTitle = ref('添加课程')
	var oldClass = {"ClassTime":"","Week":"","id":"","class":"","teacher":"","teacherId":"",
	"className":"",
	"classId":""}
	var newClass = {"ClassTime":"","Week":"","id":"",
	"class":"","teacher":"","teacherId":"",
	"className":"",
	"classId":""}
	const chooseAcademyId = ref('')
	const chooseClass = ref('')
	var chooseClassTime = ''
	var chooseWeek = ''
	const openCurriculumClass = (classTime, Week, mode)=>{
		if(mode == 1) {
			manTitle.value = "修改课程"
		}else{
			manTitle.value = "添加课程"
		}
		chooseClassTime = classTime
		chooseWeek = Week
		chooseAcademyId.value = ''
		chooseCourse.value = ''
		chooseClass.value = ''

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
				newClass.classId = chooseClass.value.id
				newClass.className = chooseClass.value.name
				newClass.id = chooseCourse.value.id
				newClass.class = chooseCourse.value.name
				newClass.teacher = route.query.name
				newClass.teacherId = route.query.id
				
				if(!chooseCourse.value.name){
					classTableData.value[i][chooseWeek].className = ""
					classTableData.value[i][chooseWeek].classId = ""
					classTableData.value[i][chooseWeek].id = ""
					classTableData.value[i][chooseWeek].class = ""
					classTableData.value[i][chooseWeek].teacher = ""
					classTableData.value[i][chooseWeek].teacherId = ""
				}else{
					classTableData.value[i][chooseWeek].className = chooseClass.value.name
					classTableData.value[i][chooseWeek].classId = chooseClass.value.id
					classTableData.value[i][chooseWeek].id = chooseCourse.value.id
					classTableData.value[i][chooseWeek].class = chooseCourse.value.name
					classTableData.value[i][chooseWeek].teacher = route.query.name
					classTableData.value[i][chooseWeek].teacherId = route.query.id
				}

			}
		}
		editCurriculum()
		manageDialog.value = false
		
	}
	const editCurriculum = () =>{
		var id = route.query.id
		var cid = route.query.cid
		API({
			url:"/classApi/editTeacherCurriculum",
			method:"post",
			data:{
				id:id,
				cid:cid,
				curriculumData:classTableData.value,
				oldCurData:oldClass,
				newCurData:newClass
			}
		})
		
	}
	const GetCurriculum = () => {
		var id = route.query.id
		var cid = route.query.cid
		if(cid == "null") {
			cid = 0
		}
		API({
			url: "/classApi/GetTeacherCurriculum",
			method: 'get',
			params: {
				id: id,
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

	const getCourseList = (id, mode)=>{

		var tid = route.query.id
		API({
			url:"/deanApi/selectByClassIdTeacherIdGetCourse",
			method:"get",
			params:{
				id:id,
				teacherId:tid
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
	const getClassList = () => {
		chooseClass.value = ''
		var id = route.query.id
		API({
			url: "/deanApi/selectByTeacherGetClass",
			method: "get",
			params:{
				id: id
			}
		}).then((res) => {
			classList.value = res.data.data

		}).catch((error) =>{
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	} 
	onMounted(() => {
		GetCurriculum()
		getClassList()
	})
</script>

<style src="../../static/student/selectClassTable.scss" lang="scss" scoped></style>