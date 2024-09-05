<template>

	<el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" style="width: 450px;">
		<el-form-item label="姓名" prop="name">
			<el-text>{{ruleForm.name}}</el-text>
		</el-form-item>
		<el-form-item label="性别" prop="sex" v-if="!ruleForm.sex">
			<el-radio-group  v-model="ruleForm.sex">
				<el-radio label="1">男</el-radio>
				<el-radio label="2">女</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item label="头像" prop="cover">
			<el-upload class="avatar-uploader" :headers="headers" :on-success="upSuccess"
				action="http://localhost:8081/loginApi/upload" :show-file-list="false">
				<img v-if="ruleForm.cover" :src="ruleForm.cover" style="width: 200px;height: 200px;" />
				<el-icon v-else class="avatar-uploader-icon">
					<Plus />
				</el-icon>
			</el-upload>
		</el-form-item>
		<el-form-item label="手机号" prop="phone">
			<el-text>{{ruleForm.phone}}</el-text>
		</el-form-item>
		<el-form-item label="邮箱" prop="email">
			<el-input v-model="ruleForm.email" placeholder="Please input" />
		</el-form-item>
		<el-form-item>
			<el-button type="primary" style="margin-left: 20%;margin-top: 20px;" :disabled="buttonDisabled"
				@click="submitForm">保存修改</el-button>
		</el-form-item>
	</el-form>



</template>

<script setup>
	import {
		ref,
		onMounted,
	} from 'vue';
	import md5 from 'js-md5';
	import {
		useCookies
	} from "vue3-cookies";
	import API from '../../api/config'
	import {
		ElLoading,
		ElMessage
	} from 'element-plus';
	var newPassForm = {}
	const {
		cookies
	} = useCookies();
	const buttonDisabled = ref(false)
	var headers = {
		token: cookies.get("token")
	}
	var GetUrl = ""
	var EditUrl = ""
	const checkEmail = (rule, value, callback) => {
		let regMoney = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/

		if (!value || !regMoney.test(value)) {
			callback(new Error("邮箱格式不正确"));
			buttonDisabled.value = true
		}else {
			buttonDisabled.value = false
			return callback();
		}
	}
	const rules = {
		//使用自定义验证规则   validator 属性
		email: [{
			validator: checkEmail,
			trigger: "blur"
		}]
	}
	const ruleForm = ref({
		name: "",
		sex: "",
		phone: "",
		guardian: "",
		guardianPhone: "",
		email: "",
		cover: ""
	})
	const upSuccess = (f) => {
		ElMessage({
			type: 'success',
			message: '修改成功',
		})
		ruleForm.value.cover = "http://127.0.0.1:8081" + f.data
	}
	const getUserData = () => {
		API({
			url: GetUrl,
			method: "get"
		}).then((res) => {
			ruleForm.value = res.data.data
			ruleForm.value.sex = ruleForm.value.sex + ""
			if(ruleForm.value.cover != null) {
				ruleForm.value.cover = "http://127.0.0.1:8081" + ruleForm.value.cover
			}else {
				ruleForm.value.cover = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
			}
			
		})
	}
	const submitForm = () => {
		API({
			url: EditUrl,
			method: "post",
			data: ruleForm.value
		}).then((res) => {
			ElMessage({
				type: 'success',
				message: '修改成功',
			})
			getUserData()
		})
	}
	onMounted(() => {
		if(cookies.get("userType") == "2"){
			GetUrl = "/teacherApi/GetTeacherUserData"
			EditUrl = "/teacherApi/editTeacherUserData"
		}else if(cookies.get("userType") == "3"){
			GetUrl = "/deanApi/getDeanUserData"
			EditUrl = "/deanApi/editDeanUserData"
		}
		else if(cookies.get("userType") == "4"){
			GetUrl = "/adminApi/GetAdminUserData"
			EditUrl = "/adminApi/EditAdminUser"
		}
		getUserData()
	})
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed var(--el-border-color);
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		transition: var(--el-transition-duration-fast);
	}

	.avatar-uploader .el-upload:hover {
		border-color: var(--el-color-primary);
	}

	.el-icon.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		text-align: center;
	}
</style>