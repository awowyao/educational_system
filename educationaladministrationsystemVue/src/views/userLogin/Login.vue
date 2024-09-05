<template>
	<view class="LoginApp">
		<view class="LogsinBox" @keyup.enter="UserLogin">
			<h1 class="LoginH1">登录</h1>
			<view class="LoginFrom">
				<view class="UserFrom">
					<span class="LoginText">账号:</span>
					<el-input class="LoginInput" v-model="UserName" placeholder="请输入账号" clearable />
				</view>
				<span id="LoginUserNameID" class="UserErrowText">账号不能为空</span>
				<view class="UserFrom">
					<span class="LoginText">密码:</span>
					<el-input class="LoginInput" v-model="UserPwd" type="password" placeholder="请输入密码" show-password />
				</view>
				<span id='LoginUserPWDID' class="UserErrowText LoginUserPwd">密码不能为空</span>
				<view class="UserFrom">
					<span class="LoginText">角色:</span>
					<el-radio-group size="large" v-model="userType">
						<el-radio-button label=1>学生</el-radio-button>
						<el-radio-button label=2>教师</el-radio-button>
						<el-radio-button label=3>教务员</el-radio-button>
					</el-radio-group>
				</view>
			</view>
			<view class="UserBotton">
				<el-button class="UserBottonD" color="#626aef" :disabled="LoginVlue" @click="UserLogin">登录
				</el-button>
				<el-button class="UserBottonD" color="#626aef" @click="openForgotPassword">忘记密码</el-button>
			</view>
		</view>
	</view>
	<el-dialog v-model="dialogFormVisible" title="忘记密码" width="460">
		<el-form :model="form">
			<el-form-item label="登录ID" :label-width="130">
				<el-input v-model="form.id" autocomplete="off" style="width: 230px;" />
			</el-form-item>
			<el-form-item label="角色" :label-width="130">
				<el-radio-group size="large" v-model="form.Type">
					<el-radio-button label=1>学生</el-radio-button>
					<el-radio-button label=2>教师</el-radio-button>
					<el-radio-button label=3>教务员</el-radio-button>
				</el-radio-group>
			</el-form-item>

		</el-form>
		<template #footer>
			<div class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取消</el-button>
				<el-button type="primary" @click="ForgotPassword">
					确定
				</el-button>
			</div>
		</template>
	</el-dialog>
</template>

<script setup>
	import {
		ElLoading,
		ElMessage,
		ElMessageBox
	} from 'element-plus';
	import {
		ref,
		onMounted
	} from 'vue';
	import API from '../../api/config'
	import md5 from 'js-md5';
	import {
		useRouter,
		useRoute
	} from 'vue-router'
	const router = useRouter()
	const route = useRoute()
	import {
		getCurrentInstance
	} from 'vue'
	const {
		proxy
	} = getCurrentInstance()
	const form = ref({
		id:'',
		Type:''
	})
	const dialogFormVisible = ref(false)
	const UserName = ref()
	const UserPwd = ref()
	const userType = ref(1)
	if (proxy.$cookies.get("userType") != null) {
		userType.value = proxy.$cookies.get("userType")
	}
	if (proxy.$cookies.get("userId") != null) {
		UserName.value = proxy.$cookies.get("userId")
	}
	const openForgotPassword = ()=>{
		dialogFormVisible.value = true
	}
	
	const ForgotPassword = ()=>{
		API({
			url:"/loginApi/sentForgotPassword",
			method:"post",
			data:form.value
		}).then((res)=>{
			ElMessage({
				type: 'success',
				message: res.data.data,
			})
		})
	}
	const UserLogin = () => {

		API({
			url: "/loginApi/login",
			method: "post",
			data: {
				userName: UserName.value,
				password: md5(UserPwd.value),
				"userType": userType.value
			},
			headers: {

			}
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '登录成功',
			})
			proxy.$cookies.set('userName', res.data.data.name, '2d')
			proxy.$cookies.set('token', res.data.data.token, '2d')
			proxy.$cookies.set('userType', res.data.data.userType, '2d')
			proxy.$cookies.set('userId', res.data.data.id, '2d')

			if (userType.value == "1") {
				router.push({
					name: "SelectClassTable"
				})
			} else if (userType.value == "2") {
				router.push({
					name: "classPlan"
				})
			} else if (userType.value == "3") {
				router.push({
					name: "studentStatus"
				})
			}
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: '登录失败',
			})
		})
	}
</script>

<style scoped src="../../static/UserLoginCss.css"></style>