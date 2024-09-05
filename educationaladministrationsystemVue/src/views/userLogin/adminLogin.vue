<template>
	<view class="LoginApp">
		<view class="LogsinBox" style="height: 30rem;" @keyup.enter="UserLogin">
			<h1 class="LoginH1">管理员登录</h1>
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
			</view>
			<view class="UserBotton" style="margin-top: 20px;">
				<el-button class="UserBottonD" style="width: 250px;" color="#626aef" :disabled="LoginVlue" @click="UserLogin">登录
				</el-button>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ElLoading,
		ElMessage
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
	const UserName = ref()
	const UserPwd = ref()
	UserName.value = proxy.$cookies.get('userName')
	const UserLogin = () => {
		API({
			url: "/loginApi/adminLogin",
			method: "post",
			data: {
				userName: UserName.value,
				password: md5(UserPwd.value),
				"userType": 4
			},

		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '登录成功',
			})
			proxy.$cookies.set('userName', res.data.data.name, '2d')
			proxy.$cookies.set('token', res.data.data.token, '2d')
			proxy.$cookies.set('userType', res.data.data.userType, '2d')
			router.push({
				name: "adminMag"
			})
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: '登录失败',
			})
		})
	}
</script>

<style scoped src="../../static/UserLoginCss.css"></style>