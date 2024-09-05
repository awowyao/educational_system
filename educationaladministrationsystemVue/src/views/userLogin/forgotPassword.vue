<template>
	<view class="LoginApp">
		<view class="LogsinBox" @keyup.enter="UserLogin">
			<h1 class="LoginH1">重置密码</h1>
			<view class="LoginFrom">
				<view class="UserFrom">
					<span class="LoginText"  style="width:119px;">新密码:</span>
					<el-input class="LoginInput" v-model="UserPwd" type="password" placeholder="请输入密码" show-password />
				</view>
				<span id='LoginUserPWDID' class="UserErrowText LoginUserPwd">密码不能为空</span>
				<view class="UserFrom" >
					<span class="LoginText" style="width:119px;">再次输入:</span>
					<el-input class="LoginInput" v-model="checkUserPwd" type="password" placeholder="请输入密码" show-password />
				</view>
				<span id='LoginUserPWDID' class="UserErrowText LoginUserPwd">密码不能为空</span>

			</view>
			<view class="UserBotton">
				<el-button class="UserBottonD" color="#626aef" style="width: 300px;height: 50px;" :disabled="LoginVlue" @click="ForgotPassword">确定修改密码</el-button>

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
		ref
	} from 'vue';
	import API from '../../api/config'
	import md5 from 'js-md5';
	import {
		useRouter,
		useRoute
	} from 'vue-router'
	const router = useRouter()
	const route = useRoute()
	const checkUserPwd = ref()
	const UserPwd = ref()
	const ForgotPassword = () =>{
		var id = route.query.uid
		API({
			url:"/loginApi/ForgotPassword",
			method:"post",
			data:{
				uid:id,
				UserPwd:md5(UserPwd.value),
				checkUserPwd:md5(checkUserPwd.value)
			}
		}).then((res)=>{
			ElMessage({
				type: 'success',
				message: '修改成功',
			})
			router.push({
				name: "Login"
			})
		}).catch((error)=>{
			ElMessage({
				type: 'warning',
				message: '修改失败请重新发送邮箱',
			})
			router.push({
				name: "Login"
			})
		})
	}
</script>

<style scoped src="../../static/UserLoginCss.css"></style>