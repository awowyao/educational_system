<template>
	<div class="LogClass">
		<h2 >操作日志</h2>
		<el-empty v-if="textList==''" description="无操作记录" />
		<p class="LogP" v-for="(item,index) in textList" :key="index">{{item}}</p>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'
	import API from '../../api/config'
	import {
		useRoute
	} from 'vue-router'
	const textList = ref([])
	const route = useRoute()
	const GetDeanLog = () => {
		let id = route.query.id;
		let academyId = route.query.academyId;
		API({
			url: "/adminApi/GetDeanLog",
			method: "get",
			params: {
				deanId: id,
				"academyId": academyId
			}
		}).then((res) => {
			textList.value = res.data.data
		})
	}
	onMounted(() => {
		GetDeanLog()
	})
</script>

<style>
	.LogClass {
		overflow: auto;
		background-color: rgba(232, 232, 232, 0.3);
		padding: 0px 16px 16px 16px;
	}

	.LogP {
		margin: 0;
		margin-top: 4px;
		text-overflow: ellipsis;
		overflow: hidden;
		word-break: break-all;
		white-space: nowrap;
	}
</style>