<template>
	<div class="headerBox">
		<div class="tabBox">
			<el-tag size="large" class="tabClass" :key="index" v-for="(item,index) in tagList" closable
				:disable-transitions="false" @close="CloseTab(index)" @click="GoTab(item.name)">{{item.title}}</el-tag>
		</div>
		<div class="userBox">
			<el-avatar :size="30" :src="userCover" />
			<el-dropdown trigger="click">
				<span class="userSpen">
					{{UserName}}{{userType}}<el-icon class="el-icon--right"><arrow-down /></el-icon>
				</span>
				<template #dropdown>
					<el-dropdown-menu>
						<el-dropdown-item @click="UserMsg">个人信息</el-dropdown-item>
						<el-dropdown-item @click="changePassword">修改密码</el-dropdown-item>
						<el-dropdown-item @click="openCloseLogin">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</template>
			</el-dropdown>

			<!-- 		<spen class="userSpen">{{UserName}}{{userType}}</spen>
			<spen class="userSpen ClikeHover" @click="openCloseLogin">退出登录</spen>
			<spen class="userSpen ClikeHover">修改密码</spen> -->
		</div>
	</div>
</template>

<script setup>
	import {
		ref,
		computed,
		onMounted,
		watch
	} from "vue"
	import {
		ElLoading,
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import {
		useCookies
	} from "vue3-cookies";
	import {
		useRouter,
		useRoute
	} from 'vue-router'
	import API from '../api/config'
	const router = useRouter()
	const route = useRoute()
	const {
		cookies
	} = useCookies();
	const UserName = ref()
	const userType = ref()
	const tagList = ref([])
	var userCover = ref('')
	var Type = cookies.get("userType")

	const getStudentUserData = ()=>{
		API({
			url:"/studentApi/selectByIdStudent",
			method:"get"
		}).then((res)=>{
			UserName.value = res.data.data.name
			userCover.value = "http://127.0.0.1:8081"+res.data.data.cover
			if(userCover.value == null) {
				userCover.value = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
			}
		})
	}
	
	const getTeacherUserData = ()=>{
		API({
			url:"/teacherApi/GetTeacherUserData",
			method:"get"
		}).then((res)=>{
			UserName.value = res.data.data.name
			if(res.data.data.cover == null) {
				userCover.value = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
			}else
				userCover.value = "http://127.0.0.1:8081"+res.data.data.cover

		})
	}
	const getDeanUserData = ()=>{
		API({
			url:"/deanApi/getDeanUserData",
			method:"get"
		}).then((res)=>{
			UserName.value = res.data.data.name
			if(res.data.data.cover == null) {
				userCover.value = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
			}else
				userCover.value = "http://127.0.0.1:8081"+res.data.data.cover
	
		})
	}
	const getAdminUserData = ()=>{
		API({
			url:"/adminApi/GetAdminUserData",
			method:"get"
		}).then((res)=>{
			UserName.value = res.data.data.name
			if(res.data.data.cover == null) {
				userCover.value = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
			}else
				userCover.value = "http://127.0.0.1:8081"+res.data.data.cover
	
		})
	}
	if (Type == "1") {
		userType.value = "同学"
		getStudentUserData()
	} else if (Type == "2") {
		userType.value = "老师"
		getTeacherUserData()
	} else if (Type == "3") {
		userType.value = "教务员"
		getDeanUserData()
	} else if (Type == "4") {
		userType.value = "管理员"
		getAdminUserData()
	}
	const openCloseLogin = () => {
		ElMessageBox.confirm(
				'是否确认退出登录',
				'Warning', {
					confirmButtonText: 'OK',
					cancelButtonText: 'Cancel',
					type: 'warning',
				}
			)
			.then(() => {
				closeLogin()
			})
			.catch(() => {

			})
	}
	const closeLogin = () => {
		ElMessage({
			type: 'success',
			message: '退出成功',
		})
		cookies.remove("token")
		cookies.remove("userName")
		if(cookies.get("userType")=="4"){
			router.push({
				name: "adminLogin"
			})
		}else {
			router.push({
				name: "Login"
			})
		}
	}
	const UserMsg = () =>{
		if(cookies.get("userType")=="1"){
			router.push({
				name: "StudentUserData"
			})
		}else {
			router.push({
				name: "UserData"
			})
		}
	}
	const CloseTab = (index) => {
		tagList.value.splice(index, 1);
	}
	const GoTab = (toName) => {
		router.push({
			name: toName
		})
	}
	const changePassword = () => {
		router.push({
			name: "changePassword"
		})
	}
	watch(() => router.currentRoute.value.name, (toPath) => {
		var state = 0
		if(tagList.value.length > 9){
			tagList.value.shift()
		}
		for (var i = 0; i < tagList.value.length; i++) {
			if (tagList.value[i].name == toPath) {
				state = 1
				break;
			}
		}
		if (state == 0) {
			var dic = {
				title: "",
				name: toPath
			}
			if (toPath == "home") {
				dic.title = "首页"
			} else if (toPath == "representations") {
				dic.title = "成绩申述记录"
			} else if (toPath == "score") {
				dic.title = "查看成绩"
			} else if (toPath == "SelectClassTable") {
				dic.title = "课程表"
			} else if (toPath == "chooseClass") {
				dic.title = "选课"
			} else if (toPath == "selectClass") {
				dic.title = "已选课程"
			} else if (toPath == "changePassword") {
				dic.title = "修改密码"
			} else if (toPath == "classPlan") {
				dic.title = "课程安排"
			} else if (toPath == "StudenClassTable") {
				dic.title = "学生课程表"
			} else if (toPath == "uploadStudentScores") {
				dic.title = "成绩发布"
			} else if (toPath == "studentrepResentations") {
				dic.title = "成绩申述"
			} else if (toPath == "studentStatus") {
				dic.title = "学籍信息管理"
			} else if (toPath == "teacherManage") {
				dic.title = "教师管理"
			} else if (toPath == "examinationManage") {
				dic.title = "考试管理"
			} else if (toPath == "teacherCourseManage") {
				dic.title = "教师课程管理"
			} else if (toPath == "DeanClassManage") {
				dic.title = "班级管理"
			} else if (toPath == "courseManage") {
				dic.title = "课程管理"
			} else if(toPath == "electiveCourseMag") {
				dic.title = "选修课管理"
			} else if(toPath == "deanCurriculum") {
				return;
			} else if(toPath == "teacherCurriculum") {
				return;
			} else if(toPath == "ClassCourseMag") {
				return;
			} else if(toPath == "deanAuthorityMag") {
				dic.title = "教务员权限管理"
			} else if(toPath == "deanOperateLog") {
				dic.title = "教务员操作日志"
			} else if(toPath == "deanLog") {
				return;
			} else if(toPath == "academyMag") {
				dic.title = "学院管理"
			} else if(toPath == "specialtyMag") {
				dic.title = "专业管理"
			} else if(toPath == "adminStudentGetChooseCourse") {
				dic.title = "学生已选课"
			} else if(toPath == "adminStudenChooseCourse") {
				dic.title = "学生选课"
			} else if(toPath == "adminStudentScoresRepresentations") {
				dic.title = "成绩申述查询"
			} else if(toPath == "adminStudentScoresSelect") {
				dic.title = "成绩查询"
			} else if(toPath == "adminTeacherLookStudentCourse") {
				dic.title = "学生课程安排"
			} else if(toPath == "adminTeacherRepresentations") {
				dic.title = "学生申述处理"
			} else if(toPath == "adminTeacherCorrectingScore") {
				dic.title = "成绩批改"
			} else if(toPath == "adminMag") {
				dic.title = "管理员用户管理"
			}else if(toPath == "StudentUserData" || toPath=="UserData") {
				dic.title = "个人信息"
			}
			


			tagList.value.push(dic)
		}

	}, {
		immediate: true,
		deep: true
	})
</script>
<style src="../static/CommonHeader.scss" lang="scss" scoped></style>