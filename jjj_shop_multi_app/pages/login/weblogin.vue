<template>
	<view :data-theme='theme()' :class="theme() || ''" class="login-container" :style="'height: '+phoneHeight+'px;'">
		<view class="skip" @click="gotoPage('/pages/index/index')">跳过→</view>
		<view class="p-30-75" v-if="is_login==1">
			<view class="login_topbpx">
				<view class="login_tit">登录</view>
			</view>
			<view class="group-bd">
				<view class="form-level d-s-c">
					<view class="val flex-1 input_botom"><input type="text" v-model="formData.mobile" placeholder="请填写手机号" /></view>
				</view>
				<view class="form-level d-s-c">
					<view class="val flex-1 input_botom"><input type="text" password="true" v-model="loging_password" placeholder="请输入密码" /></view>
				</view>
			</view>
		</view>
		<view style="padding-top: 80rpx;" class="btns p-30-75" v-if="is_login==1"><button  @click="formSubmit">登录</button></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				/*表单数据对象*/
				formData: {
					/*手机号*/
					mobile: '',
					/*验证码*/
					code: '',
				},
				loging_password: '',
				register: {
					mobile: '',
					password: '',
					repassword: '',
					code: ''
				},
				ip: '',
				isShow: true,
				is_login: 1,
				phoneHeight:0
			};
		},
		onLoad(e) {
			
		},
		onShow() {
			this.init()
		},
		methods: {
			/*初始化*/
			init() {
				let self = this;
				uni.getSystemInfo({
					success(res) {
						self.phoneHeight = res.windowHeight;
					}
				});
			},
			/*提交*/
			formSubmit() {
				let self = this;
				let formdata = {
					mobile: self.formData.mobile,
				}
				let url = ''
				if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(self.formData.mobile)) {
					console.log(self.formData.mobile)
					uni.showToast({
						title: '手机有误,请重填！',
						duration: 2000,
						icon: 'none'
					});
					return;
				}
				
				if (self.loging_password == '') {
					uni.showToast({
						title: '密码不能为空！',
						duration: 2000,
						icon: 'none'
					});
					return;
				}
				formdata.password = self.loging_password;
				url = 'user/user/phoneLogin'
				
				uni.showLoading({
					title: '正在提交'
				});
				self._post(
					url,
					formdata,
					result => {
						// 记录token userId
						uni.setStorageSync('token', result.data.token);
						uni.setStorageSync('userId', result.data.loginUserVo.userId);
						// 获取登录前页面
						let url = '/' + uni.getStorageSync('currentPage');
						let pageOptions = uni.getStorageSync('currentPageOptions');
						if(Object.keys(pageOptions).length > 0){
							url += '?';
							for(let i in pageOptions){
								url += i + '=' + pageOptions[i] + '&';
							}
							url = url.substring(0, url.length - 1);
						}
						
						// 执行回调函数
						self.gotoPage(url);
					},
					false,
					() => {
						uni.hideLoading();
					}
				);
			},
		}
	};
</script>

<style lang="scss" scoped>
	page{
		background-color: #FFFFFF;
	}
	.p-30-75 {
		padding: 30rpx 75rpx;
	}

	.p-0-75 {
		padding: 0 75rpx;
	}

	.t-r {
		text-align: right;
	}

	.login-container {
		position: relative;
		background: #ffffff;
	}

	.login-container input {
		height: 88rpx;
		line-height: 88rpx;
	}

	.wechatapp {
		padding: 80rpx 0 48rpx;
		border-bottom: 1rpx solid #e3e3e3;
		margin-bottom: 72rpx;
		text-align: center;
	}

	.wechatapp .header {
		width: 190rpx;
		height: 190rpx;
		border: 2px solid #fff;
		margin: 0rpx auto 0;
		border-radius: 50%;
		overflow: hidden;
		box-shadow: 1px 0px 5px rgba(50, 50, 50, 0.3);
	}

	.auth-title {
		color: #585858;
		font-size: 34rpx;
		margin-bottom: 40rpx;
	}

	.auth-subtitle {
		color: #888;
		margin-bottom: 88rpx;
		font-size: 28rpx;
	}

	.login-btn {
		padding: 0 20rpx;
	}

	.login-btn button {
		height: 88rpx;
		line-height: 88rpx;
		background: #04be01;
		color: #fff;
		font-size: 30rpx;
		border-radius: 999rpx;
		text-align: center;
	}

	.no-login-btn {
		margin-top: 20rpx;
		padding: 0 20rpx;
	}

	.no-login-btn button {
		height: 88rpx;
		line-height: 88rpx;
		background: #dfdfdf;
		color: #fff;
		font-size: 30rpx;
		border-radius: 999rpx;
		text-align: center;
	}

	.get-code-btn {
		width: 200rpx;
		height: 80rpx;
		line-height: 76rpx;
		padding: 0rpx 30rpx;
		border-radius: 40rpx;
		white-space: nowrap;
		// border: 1rpx solid $dominant-color;
		background-color: #FFFFFF;
		@include font_color('font_color');
		font-size: 30rpx;
	}

	.get-code-btn[disabled='true'] {
		// border: 1rpx solid #cccccc;
		@include font_color('gray9');
		background-color: #FFFFFF;
	}

	.btns button {
		height: 90rpx;
		line-height: 90rpx;
		font-size: 34rpx;
		border-radius: 45rpx;
		@include background_color('background_color');
		color: #ffffff;
	}

	.login_topbpx {
		padding: 181rpx 0;
		padding-bottom: 110rpx;
	}

	.login_tit {
		font-size: 52rpx;
		font-weight: 600;
		margin-bottom: 33rpx;
	}

	.login_top {
		font-size: 24rpx;
		color: #adafb3;
	}

	.input_botom {
		border-bottom: 1px solid #f4f4f4;
	}
	.bottom_nav{
		width: 100%;
		position: absolute;
		bottom: 100rpx;
	}
	.bottom-box{
		width: 70%;
		margin: 0 auto;
	}
	.other_tit{
		height: 1rpx;
		background-color: #CACACA;
		width: 100%;
		line-height: 1rpx;
		text-align: center;
	}
	.weixin_box{
		background-color: #04BE01;
		border-radius: 50%;
		width: 80rpx;
		height: 80rpx;
		line-height: 80rpx;
		text-align: center;
	}
	.weixin_box .icon-weixin{
		font-size: 40rpx;
		color: #FFFFFF;
	}
	// .btns .bg-green{
	// 	background-color: #04BE01;
	// }
	.agreement {
		border-radius: 50%;
		width: 28rpx;
		height: 28rpx;
		border: 2rpx solid;
		@include border_color('border_color');
		background: #fff;
		position: relative;
		margin-right: 10rpx;
		box-sizing: border-box;
	}
	
	.agreement.active::after {
		content: '';
		width: 16rpx;
		height: 16rpx;
		@include background_color('background_color');
		border-radius: 50%;
		position: absolute;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		margin: auto;
	}
	.skip{
		position: absolute;
		top: 80rpx;
		right: 30rpx;
		font-size: 28rpx;
		color: #999999;
	}
</style>
