<template>
	<view>
		<view class="acea-row row-around nav">
			<template v-for="item in navList">
				<view :key="item.type" :class="['acea-row', 'row-middle', type === item.type ? 'on' : '']"
					@click="setType(item.type)">{{ item.name }}</view>
			</template>
		</view>
		<view style="height: 106rpx;"></view>
		<view class='coupon-list' v-if="couponsList.length">
			<view class='item acea-row row-center-wrapper' v-for="(item,index) in couponsList" :key="index">
				<view class='money' :class='item.isUse ? "moneyGray" : "" '>
					<view>￥<text class='num'>{{item.money?Number(item.money):''}}</text></view>
					<view class="pic-num">满{{item.minPrice?Number(item.minPrice):''}}元可用</view>
				</view>
				<view class='text'>
					<view class='condition line2'>
						<span class='line-title' :class='(item.isUse==true || item.isUse==2)?"gray":""'
							v-if='item.useType===1'>通用</span>
						<span class='line-title' :class='(item.isUse==true || item.isUse==2)?"gray":""'
							v-else-if='item.useType===3'>品类</span>
						<span class='line-title' :class='(item.isUse==true || item.isUse==2)?"gray":""' v-else>商品</span>
						<span>{{item.name}}</span>
					</view>
					<view class='data acea-row row-between-wrapper'>
						<view v-if="item.day>0">领取后{{item.day}}天内可用</view>
						<view v-else>
							{{ item.useStartTimeStr&& item.useEndTimeStr ? item.useStartTimeStr + " - " + item.useEndTimeStr : ""}}
						</view>
						<view class='bnt gray' v-if="item.isUse==true">已领取</view>
						<view class='bnt bg-color' v-else @click='getCoupon(item.id,index)'>立即领取</view>
					</view>
				</view>
			</view>
		</view>
		<view class='loadingicon acea-row row-center-wrapper'>
			<text class='loading iconfont icon-jiazai'
				:hidden='loading==false'></text>{{couponsList.length?loadTitle:''}}
		</view>
		<view class='noCommodity' v-if="!couponsList.length && isShow && !loading">
			<view class='pictrue'>
				<image src='../../../static/images/noCoupon.png'></image>
			</view>
		</view>
		<!-- #ifdef MP -->
		<!-- <authorize @onLoadFun="onLoadFun" :isAuto="isAuto" :isShowAuth="isShowAuth" @authColse="authColse"></authorize> -->
		<!-- #endif -->
	</view>
</template>

<script>
	import {
		setCouponReceive
	} from '@/api/user.js';
	
	import {
		getCoupons
	} from '@/api/store.js';
	
	import {
		toLogin
	} from '@/libs/login.js';
	import {
		mapGetters
	} from "vuex";
	// #ifdef MP
	import authorize from '@/components/Authorize';
	// #endif
	export default {
		components: {
			// #ifdef MP
			authorize
			// #endif
		},
		data() {
			return {
				couponsList: [],
				loading: false,
				loadend: false,
				loadTitle: '加载更多', //提示语
				page: 1,
				limit: 20,
				isAuto: false, //没有授权的不会自动授权
				isShowAuth: false, //是否隐藏授权
				type: 1,
				isShow: false,
				navList: [{
						type: 1,
						name: '通用券',
						count: 0
					},
					{
						type: 2,
						name: '商品券',
						count: 0
					},
					{
						type: 3,
						name: '品类券',
						count: 0
					},
				],
				count: 0
			};
		},
		computed: mapGetters(['isLogin']),
		watch: {
			isLogin: {
				handler: function(newV, oldV) {
					if (newV) {
						this.getUseCoupons();
					}
				},
				deep: true
			}
		},
		onLoad() {

			if (this.isLogin) {
				this.getUseCoupons();

			} else {
				toLogin();
			}
		},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {
			this.getUseCoupons();
		},
		methods: {
			onLoadFun() {
				this.getUseCoupons();
			},
			// 授权关闭
			authColse: function(e) {
				this.isShowAuth = e
			},
			getCoupon: function(id, index) {
				let that = this;
				let list = that.couponsList;
				let ids = [];
				ids.push(id);
				//领取优惠券
				setCouponReceive(id).then(function(res) {
					list[index].isUse = true;
					that.$set(that, 'couponsList', list);
					that.$util.Tips({
						title: '领取成功'
					});
				}, function(res) {
					return that.$util.Tips({
						title: res
					});
				})
			},
			/**
			 * 获取领取优惠券列表
			 */
			getUseCoupons: function() {
				let that = this
				if (that.loadend) return false;
				if (that.loading) return false;
				that.loading = true;
				getCoupons({
					page: that.page,
					limit: that.limit,
					type: that.type
				}).then(res => {
					let list = res.data,
						loadend = list.length < that.limit;
					let couponsList = that.$util.SplitArray(list, that.couponsList);
					that.$set(that, 'couponsList', couponsList);
					that.loadend = loadend;
					that.loadTitle = loadend ? '我也是有底线的' : '加载更多';
					that.page = that.page + 1;
					that.loading = false;
					that.isShow = true;
				}).catch(err => {
					that.loading = false;
					that.loadTitle = '加载更多';
				});
			},
			setType: function(type) {
				if (this.type !== type) {
					this.type = type;
					this.couponsList = [];
					this.page = 1;
					this.loadend = false;
					this.getUseCoupons();
				}
			}
		}
	}
</script>

<style scoped>
	.nav {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 106rpx;
		background-color: #FFFFFF;
		font-size: 30rpx;
		color: #999999;
		z-index: 9;
	}

	.nav .acea-row {
		border-top: 5rpx solid transparent;
		border-bottom: 5rpx solid transparent;
		cursor: pointer;
	}

	.nav .acea-row.on {
		border-bottom-color: #009600;
		color: #009600;
	}

	.condition .line-title {
		width: 90rpx;
		padding: 0 10rpx;
		box-sizing: border-box;
		background: rgba(255, 247, 247, 1);
		border: 1px solid rgba(232, 51, 35, 1);
		opacity: 1;
		border-radius: 20rpx;
		font-size: 20rpx;
		color: #E83323;
		margin-right: 12rpx;
	}

	.condition .line-title.gray {
		border-color: #BBB;
		color: #bbb;
		background-color: #F5F5F5;
	}

	.coupon-list .pic-num {
		color: #FFFFFF;
		font-size: 24rpx;
	}
</style>
