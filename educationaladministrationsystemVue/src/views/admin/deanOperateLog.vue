<template>
	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createTime" label="添加时间" min-width="120" />
		<el-table-column prop="id" label="ID" min-width="120" />
		<el-table-column prop="name" label="名字" min-width="120" />
		<el-table-column prop="phone" label="手机" min-width="120" />
		<el-table-column prop="email" label="邮箱" min-width="120" />
		<el-table-column label="操作" min-width="180">
			<template #default="scope">
				<el-button key="编辑课程表" type="primary" @click="OpenLookLog(scope.row.id)" link>
					查看操作日志
				</el-button>
			</template>
		</el-table-column>
	</el-table>

	<el-dialog v-model="DialogVisible" title="选择学院" width="500" center>
		<div style="width: 100%; display: flex;flex-direction: row;	align-items: center;justify-content: center;">
			<span style="margin-right: 12px;font-size: 16px;font-weight: 500;">学院: </span>
			<el-select v-model="academy" placeholder="选择专业">
				<el-option v-for="item in academyList" :key="item.id" :label="item.name"  :value="item.id" />
			</el-select>
		</div>

		<template #footer>
			<div class="dialog-footer">
				<el-button @click="DialogVisible = false">取消</el-button>
				<el-button type="primary" @click="GoLookLog()">
					去查看
				</el-button>
			</div>
		</template>
	</el-dialog>
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
		useRouter
	} from 'vue-router'
	const DialogVisible = ref(false)
	const router = useRouter()
	const tableData = ref([])
	const page = ref(1)
	const pageSize = ref(5)
	const total = ref()
	const showPage = ref(false)
	const academyList = ref([])
	const academy = ref('')
	var LookLogId = 0
	const OpenLookLog = (id)=>{
		LookLogId = id
		DialogVisible.value = true
		API({
			url:"/adminApi/GetDeanAcademyList",
			method:"get",
			params:{
				deanId:id
			}
		}).then((res)=>{
			academyList.value = res.data.data
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GetDeanList = () => {
		API({
			url: "/adminApi/GetDeanUser",
			method: "get",
			params: {
				page: page.value,
				pageSize: pageSize.value,
			}
		}).then((res) => {
			total.value = res.data.data.total
			if (res.data.data.pages <= 1) {
				showPage.value = true
			}
			tableData.value = res.data.data.list;
		}).catch((error) => {
			ElMessage({
				type: 'warning',
				message: error.response.data.msg,
			})
		})
	}
	const GoLookLog = (id) => {
		if(academy.value == ''){
			ElMessage({
				type: 'warning',
				message: "未选中学院",
			})
		}else{
			router.push({
				name: "deanLog",
				query: {
					id: LookLogId,
					academyId:academy.value
				}
			})
		}

	}
	onMounted(() => {
		GetDeanList()
	})
</script>

<style>
</style>