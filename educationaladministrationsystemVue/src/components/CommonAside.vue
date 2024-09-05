<template>
	<el-menu active-text-color="#ffd04b" background-color="#545c64" class="elSystemMenu" :default-active="idnex"
		text-color="#fff" style="height: auto;min-height: 100%;width: 220px;">
		<h2 style="margin: 0 12px; padding: 12px 0; color: #FAFCFF;font-family: '微软雅黑';cursor: default;">教务管理系统</h2>
		<el-menu-item :index="item.path" v-for="item in noChildren" :key="item.path" @click="clickMenu(item)">
			<el-icon :class="'iconfont '+item.icon ">
				
				<!-- <component :is="item.icon" /> -->
			</el-icon>
			<span>{{item.label}}</span>
		</el-menu-item>
		<!-- 		<el-sub-menu :index="index+''" v-for="(item, index) in hesChildren" :key="item.path">
			<template #title>
				<el-icon>
					<component :is="item.icon" />
				</el-icon>
				<span>{{item.label}}</span>
			</template>
			<el-menu-item-group>
				<el-menu-item :index="childern.path" v-for="childern in item.childern" :key="childern.path"
					@click="clickMenu(childern)">
					<el-icon>
						<component :is="childern.icon" />
					</el-icon>
					{{childern.label}}
				</el-menu-item>
			</el-menu-item-group>
		</el-sub-menu> -->

		<el-sub-menu :index="index+''" v-for="(item, index) in twoHesChildren" :key="item.path">
			<template #title>
				<el-icon :class="'iconfont '+item.icon ">
					<!-- <component :is="item.icon" /> -->
				</el-icon>
				<span>{{item.label}}</span>
			</template>
			<div style="margin: 0;" v-for="(childern1, index2) in item.childern" :key="childern1.path">
			
					<el-menu-item v-if="!childern1.childern" :index="childern1.path" @click="clickMenu(childern1)">
						<el-icon :class="'iconfont '+childern1.icon ">
							<!-- <component :is="childern1.icon" /> -->
						</el-icon>
						{{childern1.label}}
					</el-menu-item>
		

				<el-sub-menu :index="index+'-'+index2" v-if="childern1.childern">
					<template #title>
						<el-icon :class="'iconfont '+childern1.icon ">
							<!-- <component :is="childern1.icon" /> -->
						</el-icon>
						<span>{{childern1.label}}</span>
					</template>
					<el-menu-item-group>
						<el-menu-item :index="childern.path" v-for="childern in childern1.childern" :key="childern.path"
							@click="clickMenu(childern)">
							<el-icon :class="'iconfont '+childern.icon ">
								<component :is="childern.icon" />
							</el-icon>
							{{childern.label}}
						</el-menu-item>
					</el-menu-item-group>
				</el-sub-menu>
			</div>


		</el-sub-menu>

	</el-menu>
</template>

<script setup>
	import {
		ref,
		computed,
		onMounted,
	} from "vue"
	import {
		useCookies
	} from "vue3-cookies";
	const {
		cookies
	} = useCookies();
	import {
		useRouter,
		useRoute
	} from 'vue-router'

	const router = useRouter()
	const route = useRoute()
	const idnex = ref("")
	const studentList = ref([{
			path: '/StudenMsg/ClassTable',
			name: 'SelectClassTable',
			label: '课程表',
			icon: 'icon-kechengbiao'
		},
		{
			label: '信息查询',
			icon: 'icon-xinxichaxun-',
			childern: [{
					path: '/StudenMsg/score',
					name: 'score',
					label: '查看成绩',
					icon: 'icon-chengji-'
				},
				{
					path: '/StudenMsg/representations',
					name: 'representations',
					label: '成绩申述记录',
					icon: 'icon-shenshu'
				}
			]
		},
		{
			label: "选课中心",
			icon: 'icon-xuankezhongxin',
			childern: [{
					path: '/chooseClass/choose',
					name: 'chooseClass',
					label: '开始选课',
					icon: 'icon-XYXK_YH'
				},
				{
					path: '/chooseClass/selectClass',
					name: 'selectClass',
					label: '已选课程',
					icon: 'icon-yixuan'
				}
			]
		},
	]);

	const teacherList = ref([
		{
			label: '信息查询',
			icon: 'icon-xinxichaxun-',
			childern: [{
					path: '/teacherMsg/StudenClassTable',
					name: 'StudenClassTable',
					label: '学生课程表',
					icon: 'icon-kechengbiao'
				},
				{
					path: '/teacherMsg/classPlan',
					name: 'classPlan',
					label: '课程安排',
					icon: 'icon-kechenganpai'
				},
			]
		},
		{
			label: '成绩管理',
			icon: 'icon-chengjiguanli-chengjichaxun',
			childern: [{
					path: '/scoresManage/uploadStudentScores',
					name: 'uploadStudentScores',
					label: '学生成绩发布',
					icon: 'icon-chengjifabu'
				},
				{
					path: '/scoresManage/studentrepResentations',
					name: 'studentrepResentations',
					label: '学生成绩申诉',
					icon: 'icon-chuli1'
				},
			]
		},
	])
	
	const deanList = ref([
		{
			label: '课程信息管理',
			icon: 'icon-kechengxinxi',
			childern: [{
					path: '/dean/teacherCourseManage',
					name: 'teacherCourseManage',
					label: '教师课程安排管理',
					icon: 'icon-jiaoshi',
				},
				{
					path: '/dean/ClassManage',
					name: 'DeanClassManage',
					label: '班级管理',
					icon: 'icon-banjiguanli',
				},
				{
					path: '/dean/courseManage',
					name: 'courseManage',
					label: '课程管理',
					icon: 'icon-kecheng_m',
				},
				{
					path: '/dean/electiveCourseMag',
					name: 'electiveCourseMag',
					label: '选修课管理',
					icon: 'icon-xuanxiuke',
				}
			]
		},
		{
			path: '/dean/studentStatus',
			name: 'studentStatus',
			label: '学籍信息管理',
			icon: 'icon-xuejiguanli'
		},
		{
			path: '/dean/teacherManage',
			name: 'teacherManage',
			label: '教师管理',
			icon: 'icon-jiaoshiguanli'
		},
		{
			path: '/dean/examinationManage',
			name: 'examinationManage',
			label: '考试信息管理',
			icon: 'icon-kaoshixinxi'
		},
	])

	var adminList = [
		{
				path: '/admin/adminMag',
				name: 'adminMag',
				label: '管理员用户管理',
				icon: 'icon-guanliyuanguanli'
		},
		{
			label: '学生功能',
			icon: 'icon-xuexiao_xuesheng',
			childern: [{
					path: '/admin/studentScoresSelect',
					name: 'adminStudentScoresSelect',
					label: '成绩查询',
					icon: 'icon-chengji-',
				},
				{
					path: '/admin/studentScoresRepresentations',
					name: 'adminStudentScoresRepresentations',
					label: '成绩申述查询',
					icon: 'icon-shenshu',
				},
				{
					path: '/admin/studenChooseCourse',
					name: 'adminStudenChooseCourse',
					label: '学生选课',
					icon: 'icon-XYXK_YH',
				},
				{
					path: '/admin/studentGetChooseCourse',
					name: 'adminStudentGetChooseCourse',
					label: '学生已选课',
					icon: 'icon-yixuan',
				},
			]
		},
		{
			label: '教师功能',
			icon: 'icon-jiaoshiguanli',
			childern: [{
					path: '/admin/teacherCorrectingScore',
					name: 'adminTeacherCorrectingScore',
					label: '成绩批改',
					icon: 'icon-zuoyepigai',
				},
				{
					path: '/admin/teacherRepresentations',
					name: 'adminTeacherRepresentations',
					label: '学生申述处理',
					icon: 'icon-shujuchuli',
				},
				{
					path: '/admin/teacherLookStudentCourse',
					name: 'adminTeacherLookStudentCourse',
					label: '学生课程安排',
					icon: 'icon-kechenganpai',
				},
			]
		},
		{
			label: '教务员功能模块',
			icon: 'icon-jiaowuyuanshenhe',
			childern: [{
					path: '/dean/studentStatus',
					name: 'studentStatus',
					label: '学籍信息管理',
					icon: 'icon-xuejiguanli'
				},
				{
					path: '/dean/teacherManage',
					name: 'teacherManage',
					label: '教师管理',
					icon: 'icon-jiaoshiguanli'
				},
				{
					path: '/dean/examinationManage',
					name: 'examinationManage',
					label: '考试信息管理',
					icon: 'icon-kaoshixinxi'
				},
				{
					label: '课程信息管理',
					icon: 'icon-kechengxinxi',
					childern: [{
							path: '/dean/teacherCourseManage',
							name: 'teacherCourseManage',
							label: '教师课程安排管理',
							icon: 'icon-jiaoshi',
						},
						{
							path: '/dean/ClassManage',
							name: 'DeanClassManage',
							label: '班级管理',
							icon: 'icon-banjiguanli',
						},
						{
							path: '/dean/courseManage',
							name: 'courseManage',
							label: '课程管理',
							icon: 'icon-kecheng_m',
						},
						{
							path: '/dean/electiveCourseMag',
							name: 'electiveCourseMag',
							label: '选修课管理',
							icon: 'icon-xuanxiuke',
						}
					]
				},

			]
		},
		{
			path: '/admin/academyMag',
			name: 'academyMag',
			label: '学院管理',
			icon: 'icon-xueyuanguanli',
		},
		{
			path: '/admin/specialtyMag',
			name: 'specialtyMag',
			label: '专业管理',
			icon: 'icon-biaotizhuanyeguanli',
		},
		{
			path: '/admin/deanAuthorityMag',
			name: 'deanAuthorityMag',
			label: '教务员权限管理',
			icon: 'icon-quanxianguanli',
		},
		{
			path: '/admin/deanOperateLog',
			name: 'deanOperateLog',
			label: '教务员操作日志',
			icon: 'icon-rizhi',
		}
		
	]
	var AsideList = ref([])

	var userType = cookies.get("userType");
	if (userType == "1") {
		AsideList = studentList
	} else if (userType == "2") {
		AsideList = teacherList
	} else if (userType == "3") {
		AsideList = deanList
	} else if (userType == "4") {
		AsideList.value = adminList
	}
	
	let noChildren = computed(() => {
		return AsideList.value.filter(item => !item.childern)
	});

	let hesChildren = computed(() => {
		return AsideList.value.filter(item => item.childern)
	});
	let twoHesChildren = computed(() => {
		return hesChildren.value.filter(item => item.childern)
	});
	const that = this;
	let clickMenu = (item) => {
		router.push({
			name: item.name
		})
	}
	onMounted(() => {
		idnex.value = route.path
	})
</script>

<style src="../static/CommonAside.scss" lang="scss" scoped></style>
