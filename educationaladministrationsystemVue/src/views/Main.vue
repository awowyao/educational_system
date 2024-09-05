<template>
	<el-container style="width: 100vw;height:100vh;" >
		<el-aside width="auto" style="height: 100%;">
			<common-aside />
		</el-aside>
		<el-container>
			<el-header><common-header /></el-header>
			<!-- <common-Tab></common-Tab> -->
			<el-main>
				<router-view />
			</el-main>
		</el-container>
	</el-container>
</template>


<script>
	import CommonHeader from '../components/CommonHeader.vue'
	import CommonAside from '../components/CommonAside.vue'
	export default {
		components: {
			CommonHeader,
			CommonAside
			// CommonTab
		},
	}
	
</script>

<style>
	.el-header {
		background-color: rgba(238, 238, 238, 0.4);
		/* border-bottom: 0.5px solid rgba(0, 0, 0, 0.4); */
	}
	.el-aside, .el-header, .el-main {
		/* margin: 0; */
	}
</style>