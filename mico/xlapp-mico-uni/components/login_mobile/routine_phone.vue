<template>
	<view v-if="isPhoneBox">
		<view class="mobile-bg" @click="close"></view>
		<view class="mobile-mask animated" :class="{slideInUp:isUp}">
			<view class="info-box">
				<image :src="logoUrl"></image>
				<view class="title">获取授权</view>
				<view class="txt">用于手机号快捷登录</view>
			</view>
			<button class="sub_btn" open-type="getPhoneNumber" @getphonenumber="getphonenumber">手机号快捷登录</button>
		</view>
	</view>
</template>
<script>
	const app = getApp();
	import Routine from '@/libs/routine';
	import {
		loginMobile,
		registerVerify,
		getCodeApi,
		getUserInfo
	} from "@/api/user";
	import { getLogo, getUserPhone } from '@/api/infra';
	export default{
		name:'routine_phone',
		props:{
			isPhoneBox:{
				type:Boolean,
				default:false,
			},
			logoUrl:{
				type:String,
				default:'',
			},
			authKey:{
				type:String,
				default:'',
			}
		},
		data(){
			return {
				keyCode:'',
				account:'',
				codeNum:'',
				isStatus:false
			}
		},
		mounted() {
		},
		methods:{
			// #ifdef MP
			// 小程序获取手机号码
			getphonenumber(e){
				uni.showLoading({ title: '加载中' });
				Routine.getCode()
					.then(code => {
						this.getUserPhoneNumber(e.detail.encryptedData, e.detail.iv, code);
					})
					.catch(error => {
						uni.hideLoading();
					});
			},
			// 小程序获取手机号码回调
			getUserPhoneNumber(encryptedData, iv, code) {
				getUserPhone({
					encryptedData: encryptedData,
					iv: iv,
					code: code,
					key:this.authKey,
					type: 'routine'
				})
					.then(res => {
						this.$store.commit('LOGIN', {
							token: res.data.token
						});
						this.$store.commit("SETUID", res.data.uid);
						this.getUserInfo();
					})
					.catch(res => {
						uni.hideLoading();
						this.$util.Tips({
							title: res
						});
					});
			},
			/**
			 * 获取个人用户信息
			 */
			getUserInfo: function() {
				let that = this;
				getUserInfo().then(res => {
					uni.hideLoading();
					that.userInfo = res.data
					that.$store.commit("UPDATE_USERINFO", res.data);
					that.isStatus = true
					this.close()
				});
			},
			// #endif
			close(){
				this.$emit('close',{isStatus:this.isStatus})
			}
		}
	}
	
</script>

<style lang="scss">
	.mobile-bg{
		position: fixed;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		background: rgba(0,0,0,0.5);
	}
	.mobile-mask {
		z-index: 20;
		position: fixed;
		left: 0;
		bottom: 0;
		width: 100%;
		padding: 67rpx 30rpx;
		background: #fff;
		.info-box{
			display:flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			image{
				width: 150rpx;
				height: 150rpx;
				border-radius: 10rpx;
			}
			.title{
				margin-top: 30rpx;
				margin-bottom: 20rpx;
				font-size: 36rpx;
			}
			.txt{
				font-size: 30rpx;
				color: #868686;
			}
		}
		.sub_btn{
			width: 690rpx;
			height: 86rpx;
			line-height: 86rpx;
			margin-top: 60rpx;
			background: $theme-color;
			border-radius: 43rpx;
			color: #fff;
			font-size: 28rpx;
			text-align: center;
		}
	}
	.animated{
		animation-duration:.4s
	}
</style>
