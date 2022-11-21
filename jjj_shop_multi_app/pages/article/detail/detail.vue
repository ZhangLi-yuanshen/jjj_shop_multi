<template>
	<view :data-theme="theme()" :class="theme() || ''" class="article-detail" v-if="loadding">
		<view class="title fb">{{ article.articleTitle }}</view>
		<view class="info d-b-c f24">
			<view>
				<text class="red">{{ article.categoryName }}</text>
				<text class="ml30">{{ article.createTime }}</text>
			</view>
		</view>
		<view class="article-content" v-html="article.articleContent"></view>
		<tabBar></tabBar>
	</view>
</template>

<script>
import utils from '@/common/utils.js';
export default {
	data() {
		return {
			/*是否加载完成*/
			loadding: false,
			indicatorDots: true,
			autoplay: true,
			interval: 2000,
			duration: 500,
			/*文章id*/
			articleId: 0,
			/*文章详情*/
			article: {
				image: {}
			}
		};
	},
	onLoad(e) {
		/*分类id*/
		this.articleId = e.articleId;
		//#ifdef H5
		//            this.url = window.location.href;
		//#endif
	},
	mounted() {
		/*获取产品详情*/
		this.getData();
	},
	methods: {
		/*获取文章详情*/
		getData() {
			let self = this;
			uni.showLoading({
				title: '加载中'
			});
			self.loading = true;
			let articleId = self.articleId;
			self._get(
				'plus/article/article/detail',
				{
					articleId: articleId
				},
				function(res) {
					/*详情内容格式化*/
					res.data.articleContent = utils.format_content(res.data.articleContent);
					console.log(res.data.articleContent);
					self.article = res.data;
					self.loadding = true;
					uni.hideLoading();
				}
			);
		}
	}
};
</script>

<style>
.article-detail {
	padding: 30rpx;
	background: #ffffff;
}

.article-detail .title {
	font-size: 44rpx;
}

.article-detail .info {
	padding: 40rpx 0;
	color: #999999;
}

.article-detail .article-content {
	width: 100%;
	box-sizing: border-box;
	line-height: 60rpx;
	font-size: 34 rpx;
	overflow: hidden;
}
.article-detail .article-content image,
.article-detail .article-content img {
	display: block;
	max-width: 100%;
}
</style>
