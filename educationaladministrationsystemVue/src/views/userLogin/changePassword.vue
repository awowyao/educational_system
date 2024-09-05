<template>
	<el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" label-width="120px" style="width: 450px;">
		<el-form-item label="旧密码" prop="oldPass">
			<el-input v-model="ruleForm.oldPass" type="password" autocomplete="off" />
		</el-form-item>
		<el-form-item label="新密码" prop="pass">
			<el-input v-model="ruleForm.pass" type="password" autocomplete="off" />
		</el-form-item>
		<el-form-item label="再次输入新密码" prop="checkPass">
			<el-input v-model="ruleForm.checkPass" type="password" autocomplete="off" />
		</el-form-item>

		<el-form-item>
			<el-button type="primary" :disabled="buttonDisabled" @click="submitForm">提交</el-button>
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
	const buttonDisabled = ref(true)
	const ruleForm =ref({
		pass:"",
		checkPass:"",
		oldPass:""
	})
	const checkcPass =(rule, value, callback)=>{
		let regMoney = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;

		if (value && (value!=ruleForm.value.pass)) {
			callback(new Error("和新密码不一致"));
		} else {
			if (value!=ruleForm.value.oldPass && value && regMoney.test(value)){
				buttonDisabled.value = false
			}
			return callback();
		}
	}
	const checkPass =(rule, value, callback)=>{
		let regMoney = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;
		if (value && !regMoney.test(value)){
			callback(new Error("必须是包含字母和数字8-16位的密码"));
		}else if (value==ruleForm.value.oldPass) {
			callback(new Error("新密码不能和原密码一样"));
		} else {
			return callback();
		}
	}
	const rules = {
        //使用自定义验证规则   validator 属性
        pass: [{ validator: checkPass, trigger: "blur" }],
		checkPass: [{ validator: checkcPass, trigger: "blur" }],
      }
	const submitForm = ()=>{
		newPassForm = ruleForm.value
		// newPassForm.oldPass = md5(newPassForm.oldPass)
		// newPassForm.checkPass = md5(newPassForm.checkPass)
		// newPassForm.pass = md5(newPassForm.pass)
		API({
			url:"/loginApi/updatePassword",
			method:"post",
			data:{
				oldPass:md5(newPassForm.oldPass),
				checkPass:md5(newPassForm.checkPass),
				pass:md5(newPassForm.pass)
			}
		}).then((res) =>{
			ElMessage({
				type: 'success',
				message: '修改成功请重新登录',
			})
			if(cookies.get("userType")=="4"){
				location.href="/adminLogin"
			}else{
				location.href="/login"
			}
			
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: '修改失败',
			})
		})
	}

</script>

<style>
</style>