import { createRouter, createWebHistory } from 'vue-router'

const routes = [
	{
		path: '/login',
		name: 'Login',
		component: () => import('../views/userLogin/Login.vue')
	},
	{
		path: '/adminLogin',
		name: 'adminLogin',
		component: () => import('../views/userLogin/adminLogin.vue')
		
	},
	{
		path: '/forgotPassword',
		name: 'forgotPassword',
		component: () => import('../views/userLogin/forgotPassword.vue')
		
	},
	
  {
	path: '/',
	// name: '/',
	component: () => import('@/views/Main.vue'),
	children: [{
			path: '/',
			name: 'home',
			component: () => import('../views/home/home.vue')
		},
		{
				path: '/StudentUserData',
				name: 'StudentUserData',
				component: () => import('../views/userLogin/StudentUserData.vue')
		},
		{
				path: '/UserData',
				name: 'UserData',
				component: () => import('../views/userLogin/UserData.vue')
		},
		{
				path: '/changePassword',
				name: 'changePassword',
				component: () => import('../views/userLogin/changePassword.vue')
		},
		// 学生功能在这开始
		{
			path: '/StudenMsg/ClassTable',
			name: 'SelectClassTable',
			component: () => import(/* webpackChunkName: "about" */ '../views/student/selectClassTable.vue')
		},
		{
			path: '/StudenMsg/score',
			name: 'score',
			component: () => import(/* webpackChunkName: "about" */ '../views/student/scoresSelect.vue')
		},
		{
			path:'/StudenMsg/representations',
			name:'representations',
			component: () => import('../views/student/representations.vue')
		},
		{
			path:'/chooseClass/choose',
			name:'chooseClass',
			component: () => import('../views/student/chooseClass.vue')
		},
		{
			path:'/chooseClass/selectClass',
			name:'selectClass',
			component: () => import('../views/student/selectClass.vue')
		},
		
		// 教师页面
		{
			path:'/teacherMsg/StudenClassTable',
			name:'StudenClassTable',
			component: () => import('../views/teacher/StudenClassTable.vue')
		},
		{
			path:'/teacherMsg/classPlan',
			name:'classPlan',
			component: () => import('../views/teacher/classPlan.vue')
			
		},
		{
			path:'/scoresManage/uploadStudentScores',
			name:'uploadStudentScores',
			component: () => import('../views/teacher/uploadStudentScores.vue')
			
		},
		{
			path:'/scoresManage/studentrepResentations',
			name:'studentrepResentations',
			component: () => import('../views/teacher/studentrepResentations.vue')
			
		},
		{
			path:'/teacherManage/studentCourseTable',
			name:'studentCourseTable.vue',
			component: () => import('../views/teacher/studentCourseTable.vue')
			
		},
		// 以下为教务员页面
		{
			path:'/dean/studentStatus',
			name:'studentStatus',
			component: () => import('../views/dean/studentStatus.vue')
		},
		{
			path:'/dean/teacherManage',
			name:'teacherManage',
			component: () => import('../views/dean/teacherManage.vue')
		},
		{
			path:'/dean/teacherCourseManage',
			name:'teacherCourseManage',
			component: () => import('../views/dean/teacherCourseManage.vue')
		},
		{
			path:'/dean/courseManage',
			name:'courseManage',
			component: () => import('../views/dean/courseManage.vue')
		},
		{
			path:'/dean/examinationManage',
			name:'examinationManage',
			component: () => import('../views/dean/examinationManage.vue')
		},
		{
			path:'/dean/ClassManage',
			name:'DeanClassManage',
			component: () => import('../views/dean/ClassManage.vue')
		},
		{
			path:'/dean/Curriculum',
			name:'deanCurriculum',
			component: () => import('../views/dean/Curriculum.vue')
		},
		{
			path:'/dean/teacherCurriculum',
			name:'teacherCurriculum',
			component: () => import('../views/dean/teacherCurriculum.vue')
		},
		{
			path:'/dean/ClassCourseMag',
			name:'ClassCourseMag',
			component: () => import('../views/dean/ClassCourseManage.vue')
		},
		{
			path:'/dean/electiveCourseMag',
			name:'electiveCourseMag',
			component: () => import('../views/dean/electiveCourseMag.vue')
		},
		
		// 以下为管理员
		{
			path: '/admin/deanAuthorityMag',
			name: 'deanAuthorityMag',
			component: () => import('../views/admin/deanAuthorityMag.vue')
		},
		{
			path: '/admin/deanOperateLogg',
			name: 'deanOperateLog',
			component: () => import('../views/admin/deanOperateLog.vue')
		},
		{
			path:'/admin/deanLog',
			name: 'deanLog',
			component: () => import('../views/admin/deanLog.vue')
		},
		{
			path:'/admin/academyMag',
			name: 'academyMag',
			component: () => import('../views/admin/academyMag.vue')
		},
		{
			path:'/admin/specialtyMag',
			name: 'specialtyMag',
			component: () => import('../views/admin/specialtyMag.vue')
		},
		{
			path:'admin/studentScoresSelect',
			name: 'adminStudentScoresSelect',
			component: () => import('../views/admin/studentScoresSelect.vue')
		},
		{
			path:'/admin/studentScoresRepresentations',
			name: 'adminStudentScoresRepresentations',
			component: () => import('../views/admin/studentScoresRepresentations.vue')
		},
		{
			path:'/admin/studenChooseCourse',
			name: 'adminStudenChooseCourse',
			component: () => import('../views/admin/studenChooseCourse.vue')
		},
		{
			path:'/admin/studentGetChooseCourse',
			name: 'adminStudentGetChooseCourse',
			component: () => import('../views/admin/studentGetChooseCourse.vue')
		},
		{
			path: '/admin/teacherCorrectingScore',
			name: 'adminTeacherCorrectingScore',
			component: () => import('../views/admin/teacherCorrectingScore.vue')
		},
		{
			path: '/admin/teacherRepresentations',
			name: 'adminTeacherRepresentations',
			component: () => import('../views/admin/teacherRepresentations.vue')
		},
		{
			path: '/admin/teacherLookStudentCourse',
			name: 'adminTeacherLookStudentCourse',
			component: () => import('../views/admin/teacherLookStudentCourse.vue')
		},
		{
			path: '/admin/adminMag',
			name: 'adminMag',
			component: () => import('../views/admin/adminMag.vue')
		},
	]
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
