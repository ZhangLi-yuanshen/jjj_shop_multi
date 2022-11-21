<template>
	<view class="diy-page">
		<!-- #ifdef APP-PLUS -->
		<view class="tc d-b-c  header"
			:style="topBarHeight() == 0 ? '': 'height:'+topBarHeight()+'px;padding-top:'+(topBarTop() + 5)+'px'">
			<view class="reg180" :style="topBarHeight() == 0 ? '': 'height:'+topBarHeight()+'px;'">
				<text   v-if="hasPage()" @click="goback"  class="icon iconfont icon-jiantou"></text>
			</view>
			<view class="gray3 f28">{{page_info.params.name}}</view>
			<view class="gray3 m20" @click="showShare()"><text class="icon iconfont icon-share1"></text></view>
		</view>
		<view :style="topBarHeight() == 0 ? '': 'height:'+topBarHeight()+'px;padding-top:'+(topBarTop() + 5)+'px'"
			class="ww100"></view>
		<!-- #endif -->
		<diy :diyItems="items"></diy>
		<!--底部弹窗-->
		<share :isbottmpanel="isbottmpanel" @close="closeBottmpanel"></share>
	</view>
</template>

<script>
	import diy from '@/components/diy/diy.vue';
	import share from '@/components/mp-share.vue';
	export default {
		components: {
			diy,
			share
		},
		data() {
			return {
				/*页面ID*/
				pageId: null,
				/*diy类别*/
				items:{},
				/*页面信息*/
				pageInfo:{},
				/*分享*/
				isbottmpanel: false,
				url: '',
			}
		},
		onLoad(e) {
			this.pageId = e.pageId;
			this.getData();
			//#ifdef H5
			this.url = window.location.href;
			//#endif
		},
		methods: {
			hasPage(){
				var pages = getCurrentPages();
				return pages.length > 1;
			},
			goback() {
				uni.navigateBack();
			},
			/*获取数据*/
			getData() {
				let self = this;
				self._get('index/diy', {
					pageId: self.pageId,
					url: self.url
				}, function(res) {
					self.pageInfo = res.data.page.page;
					self.items = res.data.page.items;
					self.setPage(self.pageInfo);
				});
			},
			
			/*设置页面*/
			setPage(page){
				
				uni.setNavigationBarTitle({
				    title: page.params.name
				});
				
				let colors='#000000';
				if(page.style.titleTextColor=='white'){
					//字母要小写
					colors='#ffffff'
				}
				uni.setNavigationBarColor({
				    frontColor: colors,
				    backgroundColor: page.style.titleBackgroundColor
				})
				
			},
			/*分享当前页面*/
			onShareAppMessage() {
				let self = this;
				let params = self.getShareUrlParams({
					pageId: self.pageId
				});
				return {
					title: self.pageInfo.params.name,
					path: '/pages/diy-page/diy-page?' + params
				};
			},
		},
	}
</script>

<style>

</style>
