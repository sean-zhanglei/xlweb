<template>
	<view>
		<view class="page-index" :class="{'bgf':navIndex >0}">
		    <!-- #ifdef APP || MP || MP-WEIXIN -->
				<view class="header-status" :style="'height:' + (topHeight)"></view>
		    <!-- #endif -->
			<!-- #ifdef H5 || APP || MP || MP-WEIXIN -->
			<view class="header mp-header" :style="'padding-top:'+ (searchPadding) + ';padding-bottom:'+ (searchPadding) + ';height:' + (searchHeight) + ';top:' + (topHeight)">
				<view class="serch-wrapper flex">
					<view class="logo">
						<image :src="logoUrl" mode=""></image>
					</view>
					<navigator url="/pages/goods_search/index" class="input" :style="'margin-right:'+(searchMargin)" hover-class="none"><text
							class="iconfont icon-xiazai5"></text>
						搜索商品</navigator>
				</view>
			</view>
			<!-- #endif -->
			<!-- 首页展示 -->
			<view class="page_content" :style="'margin-top:'+(marTop)" v-if="navIndex == 0">
				<view class="mp-bg"></view>
				<!-- banner -->
				<view class="swiper" v-if="imgUrls.length">
					<swiper indicator-dots="true" :autoplay="true" :circular="circular" :interval="interval"
						:duration="duration" indicator-color="rgba(255,255,255,0.6)" indicator-active-color="#fff">
						<block v-for="(item,index) in imgUrls" :key="index">
							<swiper-item>
								<navigator :url='item.url' class='slide-navigator acea-row row-between-wrapper'
									hover-class='none'>
									<image :src="item.pic" class="slide-image" lazy-load></image>
								</navigator>
							</swiper-item>
						</block>
					</swiper>
				</view>
				<!-- 新闻简报 -->
				<view class='notice acea-row row-middle row-between' v-if="roll.length">
					<view class="pic">
						<image src="/static/images/xinjian.png"></image>
					</view>
					<text class='line'>|</text>
					<view class='swipers'>
						<swiper :indicator-dots="indicatorDots" :autoplay="autoplay" interval="2500" duration="500"
							vertical="true" circular="true">
							<block v-for="(item,index) in roll" :key='index'>
								<swiper-item>
									<navigator class='item' :url='item.url' hover-class='none'>
										<view class='line1'>{{item.info}}</view>
									</navigator>
								</swiper-item>
							</block>
						</swiper>
					</view>
					<view class="iconfont icon-xiangyou"></view>
				</view>
				<!-- menu -->
				<view class='nav acea-row' v-if="menus.length">
					<block v-for="(item,index) in menus" :key="index">
						<navigator class='item' v-if="item.show == '1'" :url='item.url' open-type='switchTab'
							hover-class='none'>
							<view class='pictrue'>
								<image :src='item.pic'></image>
							</view>
							<view class="menu-txt">{{item.name}}</view>
						</navigator>
						<navigator class='item' v-else :url='item.url' hover-class='none'>
							<view class='pictrue'>
								<image :src='item.pic'></image>
							</view>
							<view class="menu-txt">{{item.name}}</view>
						</navigator>
					</block>
				</view>
				<!-- 优惠券 -->
				<view class="couponIndex" v-if="couponList.length>0">
					<view class="acea-row" style="height: 100%;">
						<view class="titBox">
							<view class="tit1">领取优惠券</view>
							<view class="tit2">福利大礼包，省了又省</view>
							<navigator class='item' url='/pages/users/user_get_coupon/index' hover-class='none'>
								<view class="tit3">查看全部 <text class="iconfont icon-xiangyou"></text></view>
							</navigator>
						</view>
						<view class="listBox acea-row">
							<view class="list" :class='item.isUse ? "listHui" : "listActive" '
								v-for="(item, index) in couponList.slice(0,2)" :key="index">
								<view class="tit line1" :class='item.isUse ? "pricehui" : "titActive" '>{{item.name}}
								</view>
								<view class="price" :class='item.isUse ? "pricehui" : "icon-color" '>
									{{item.money?Number(item.money):''}}<text class="yuan">元</text></view>
								<view class="ling" v-if="!item.isUse" :class='item.isUse ? "pricehui" : "icon-color" '
									@click="getCoupon(item.id,index)">领取</view>
								<view class="ling" v-else :class='item.isUse ? "pricehui fonthui" : "icon-color" '>已领取
								</view>
								<view class="priceM">满{{item.minPrice?Number(item.minPrice):''}}元可用</view>
							</view>
						</view>

					</view>
				</view>
				<!-- 活动-->
				<a_seckill></a_seckill>
				<b_combination></b_combination>
				<c_bargain></c_bargain>

				<!-- 首页推荐 -->
				<!-- :class="iSshowH?'on':''" -->
				<view class="sticky-box" :style="'top:'+(marTop)">
					<scroll-view class="scroll-view_H" style="width: 100%;" scroll-x="true" scroll-with-animation
						:scroll-left="tabsScrollLeft" @scroll="scroll">
						<view class="tab nav-bd" id="tab_list">
							<view id="tab_item" :class="{ 'active': listActive == index}" class="item"
								v-for="(item, index) in explosiveMoney" :key="index" @click="ProductNavTab(item,index)">
								<view class="txt">{{item.name}}</view>
								<view class="label">{{item.info}}</view>
							</view>
						</view>
					</scroll-view>
				</view>

				<!-- 首发新品 -->
				<view class="index-product-wrapper" :class="iSshowH?'on':''">
					<view class="list-box animated" :class='tempArr.length > 0?"fadeIn on":""'>
						<view class="item" v-for="(item,index) in tempArr" :key="index" @click="goDetail(item)">
							<view class="pictrue">
								<span class="pictrue_log pictrue_log_class"
									v-if="item.activityH5 && item.activityH5.type === '1'">秒杀</span>
								<span class="pictrue_log pictrue_log_class"
									v-if="item.activityH5 && item.activityH5.type === '2'">砍价</span>
								<span class="pictrue_log pictrue_log_class"
									v-if="item.activityH5 && item.activityH5.type === '3'">拼团</span>
								<image :src="item.image" mode=""></image>
							</view>
							<view class="product-info">
								<view class="text-info">
									<view class="title line2">{{item.storeName}}</view>
								</view>
								<view class="product-buy">
									<view class="product-data">
										<view class="stock-info">
											<view class="stock-status">
												已售
												<view class="txt" v-if="(item.sales / ((item.stock + item.sales) == 0 ? 1 : (item.stock + item.sales))) > 0.8">库存紧张</view>
											</view>
											<view class="stock-num">
												<text>{{item.sales + item.ficti}}</text>
												<!-- <text>{{item.unitName}}</text> -->
												<progress activeColor="#fea10f" backgroundColor="#fff" border-radius="1" :percent="(item.sales / ((item.stock + item.sales) == 0 ? 1 : (item.stock + item.sales))  * 100).toFixed(1)" :show-info="true" />
											</view>
										</view>
										<view class="price-info">
											<view class="old-price"><text>¥{{item.otPrice}}</text></view>
											<view class="price">
												<text>￥</text>{{item.price}}
												<view class="txt" v-if="item.checkCoupon">券</view>
											</view>
										</view>
									</view>
									<view class="bnt acea-row" v-if="item.stock == 0">
										<button disabled="true" class="nobuy bnts bg-color-hui" form-type="submit">已售罄</button>
									</view>
									<view class="bnt acea-row" v-else>
										<button class="joinCart bnts"
												@click.stop="joinCart(item.id)">加入购物车</button>
										<button class="buy bnts" @click.stop="goBuy(item.id)">立即购买</button>
									</view>
								</view>
							</view>
						</view>
					</view>
					<view class='loadingicon acea-row row-center-wrapper' v-if="goodScroll">
						<text class='loading iconfont icon-jiazai' :hidden='loading==false'></text>
					</view>
					<view class="mores-txt flex" v-if="!goodScroll">
						<text>我是有底线的</text>
					</view>
				</view>
				<!-- 组件 -->
				<productWindow :attr="attr" :isShow='1' :iSplus='1' :iScart='1' @myevent="onMyEvent" @ChangeAttr="ChangeAttr"
					@ChangeCartNum="ChangeCartNum" @attrVal="attrVal" @iptCartNum="iptCartNum" id='product-window' @goCat="joinCart(curProductId)">
				</productWindow>
			</view>
		</view>
	</view>
</template>

<script>
	
	import Auth from '@/libs/wechat';
	import Cache from '../../utils/cache';
	let app = getApp();
	import {
		getIndexData,
		getCoupons,
		setCouponReceive
	} from '@/api/api.js';
	// #ifdef MP-WEIXIN
	import {
		getTemlIds
	} from '@/api/api.js';
	// import {
	// 	SUBSCRIBE_MESSAGE,
	// 	TIPS_KEY
	// } from '@/config/cache';
	// #endif
	// #ifdef H5  
	import {
		follow
	} from '@/api/public.js';
	// #endif
	import {
		getShare
	} from '@/api/public.js';
	import a_seckill from './components/a_seckill';
	import b_combination from './components/b_combination';
	import c_bargain from './components/c_bargain';
	import goodList from '@/components/goodList';
	import promotionGood from '@/components/promotionGood';
	import couponWindow from '@/components/couponWindow';
	import productWindow from '@/components/productWindow';
	import ClipboardJS from "@/plugin/clipboard/clipboard.js";
	import {
		goShopDetail
	} from '@/libs/order.js'
	import {
		mapGetters
	} from "vuex";
	import tabNav from '@/components/tabNav.vue'
	import countDown from '@/components/countDown';
	import {
		getProductDetail,
		postCartAdd,
		getCategoryList,
		getProductslist,
		getProductHot,
		getGroomList
	} from '@/api/store.js';
	// import {
	// 	setVisit
	// } from '@/api/user.js'
	import recommend from '@/components/recommend';
	// #ifdef MP
	import authorize from '@/components/Authorize';
	// #endif
	import {
		silenceBindingSpread
	} from '@/utils';
	// #ifndef MP
	import {
		kefuConfig
	} from "@/api/public";
	import {
		getWechatConfig
	} from "@/api/public";
	// #endif
	import Loading from '@/components/Loading/index.vue';
	const arrTemp = ["beforePay", "afterPay", "refundApply", "beforeRecharge", "createBargain", "pink"];
	export default {
		computed: mapGetters(['isLogin', 'uid']),
		components: {
			tabNav,
			goodList,
			promotionGood,
			couponWindow,
			productWindow,
			countDown,
			a_seckill,
			b_combination,
			c_bargain,
			recommend,
			// #ifdef MP
			authorize,
			// #endif
			Loading
		},
		data() {
			return {
				searchHeight: '',
				searchPadding: '',
				searchMargin: '',
				topHeight: '',
				loaded: false,
				loading: false,
				isAuto: false, //没有授权的不会自动授权
				isShowAuth: false, //是否隐藏授权
				navIndex: 0,
				navTop: [],
				followUrl: "",
				followHid: true,
				followCode: false,
				logoUrl: "",
				imgUrls: [],
				itemNew: [],
				menus: [],
				bastInfo: '',
				fastInfo: '',
				fastList: [],
				firstInfo: '',
				salesInfo: '',
				indicatorDots: false,
				circular: true,
				autoplay: true,
				interval: 3000,
				duration: 500,
				window: false,
				iShidden: false,
				// navH: "",
				newGoodsBananr: '',
				couponList: [],
				liveList: [],
				hotList: [{
					pic: '/static/images/hot_001.png'
				}, {
					pic: '/static/images/hot_002.png'
				}, {
					pic: '/static/images/hot_003.png'
				}],
				ProductNavindex: 0,
				marTop: '',
				childID: 0,
				loadend: false,
				loadTitle: '加载更多',
				sortProduct: [],
				where: {
					cid: 0,
					page: 1,
					limit: 10,
				},
				is_switch: false,
				hotPage: 1,
				hotLimit: 10,
				hotScroll: false,
				explosiveMoney: [],
				prodeuctTop: 0,
				searchH: 0,
				isFixed: false,
				goodType: 0, //精品推荐Type
				goodScroll: true, //精品推荐开关
				params: { //精品推荐分页
					page: 1,
					limit: 10,
				},
				tempArr: [], //精品推荐临时数组
				roll: [], // 新闻简报
				site_name: '', //首页title
				iSshowH: true,
				configApi: {}, //分享类容配置
				spikeList: [], // 秒杀
				point: '',
				privacyStatus: false, // 隐私政策是否同意过
				tabsScrollLeft: 0, // tabs当前偏移量
				scrollLeft: 0,
				lineColor: 'red',
				lineStyle: {}, // 下划线位置--动态甲酸
				listActive: 0, // 当前选中项

				duration: 0.2 ,// 下划线动画时长
				curProductId: '',
				type: "" ,//视频号普通商品类型
				productValue: [], //系统属性
				attrTxt: '请选择', //属性页面提示
				attrValue: '', //已选属性
				attr: {
					cartAttr: false,
					productAttr: [],
					productSelect: {}
				},
				productInfo: {} ,//商品详情
				isOpen: false, //是否打开属性组件
			}
		},
		watch: {
			ProductNavindex(newVal) { // 监听当前选中项
				this.setTabList()
			},
			listActive(newVal) { // 监听当前选中项
				this.setTabList()
			}
		},
		mounted() {
			this.setTabList()
		},
		onLoad(options) {
			var that = this;
			// 获取系统信息
			uni.getSystemInfo({
				success(res) {
					that.$store.commit("SYSTEM_PLATFORM", res.platform);
				}
			});
			uni.getLocation({
				type: 'gcj02',
				altitude: true,
				geocode: true,
				success: function(res) {
					try {
						uni.setStorageSync('user_latitude', res.latitude);
						uni.setStorageSync('user_longitude', res.longitude);
					} catch {}
				}
			});
			let self = this;
			
			// #ifdef MP
			
			this.marTop = app.globalData.mbAllHeight + app.globalData.statusBarHeight + 'px';
			this.topHeight = app.globalData.statusBarHeight + 'px';
			
			this.searchHeight = app.globalData.mbAllHeight + 'px';
			this.searchPadding = app.globalData.mbTopHeight + 'px';
			this.searchMargin = app.globalData.mbPaddingRight + 'px';
			// #endif
			
			// #ifndef MP
			this.marTop = '78rpx';
			this.topHeight = '0rpx';
			
			this.searchHeight = '78rpx'; // 58 + 2 * 10
			this.searchPadding = '10rpx';
			this.searchMargin = '30rpx'; // 30rpx
			// #endif
			
			this.isLogin && silenceBindingSpread();
			// Promise.all([this.getAllCategory(), this.getIndexConfig()
			// 	// , this.setVisit()
			// ]);
			this.getIndexConfig();
			
			// #ifdef MP
			this.getTemlIds()
			// #endif
			
			options.type == undefined || options.type == null ? that.type = 'normal' : that.type = options.type;
		},
		onShow() {
			let self = this
			uni.setNavigationBarTitle({
				title: self.site_name
			})
		},
		methods: {
			getCoupon: function(id, index) {
				let that = this;
				//领取优惠券
				setCouponReceive(id).then(function(res) {
					that.$set(that.couponList[index], 'isUse', true);
					that.$util.Tips({
						title: '领取成功'
					});
				}, function(res) {
					return that.$util.Tips({
						title: res
					});
				})
			},
			clickSort(index) {
				this.listActive = index
			},
			// scroll-view滑动事件
			scroll(e) {
				this.scrollLeft = e.detail.scrollLeft;
			},
			setTabList() {
				this.$nextTick(() => {
					//this.setLine()
					this.scrollIntoView()
				})
			},
			// 计算tabs位置
			scrollIntoView() { // item滚动
				let lineLeft = 0;
				this.getElementData('#tab_list', (data) => {
					let list = data[0]
					this.getElementData(`#tab_item`, (data) => {
						let el = data[this.listActive]
						lineLeft = el.width / 2 + (-list.left) + el.left - list.width / 2 - this.scrollLeft
						this.tabsScrollLeft = this.scrollLeft + lineLeft
					})
				})
			},
			//  计算下划线位置
			setLine() {
				let lineWidth = 0,
					lineLeft = 0
				this.getElementData(`#tab_item`, (data) => {
					let el = data[this.listActive]
					lineWidth = el.width / 2
					// lineLeft = el.width * (this.currentIndex + 0.5)  // 此种只能针对每个item长度一致的
					lineLeft = el.width / 2 + (-data[0].left) + el.left
					this.lineStyle = {
						width: `${lineWidth}px`,
						transform: `translateX(${lineLeft}px) translateX(-50%)`,
						transitionDuration: `${this.duration}s`
					};
				})
			},
			getElementData(el, callback) {
				uni.createSelectorQuery().in(this).selectAll(el).boundingClientRect().exec((data) => {
					callback(data[0]);
				});
			},
			// #ifdef MP
			getTemlIds() {
				for (var i in arrTemp) {
					this.getTem(arrTemp[i]);
				}
			},
			getTem(data) {
				getTemlIds({
					type: data
				}).then(res => {
					if (res.data) {
						let arr = res.data.map((item) => {
							return item.tempId
						})
						wx.setStorageSync('tempID' + data, arr);
					}
				})
			},
			// #endif
			// 首页数据
			getIndexConfig: function() {
				let that = this;
				getIndexData().then(res => {
					uni.setNavigationBarTitle({
						title: '首页'
					})
					that.$set(that, "logoUrl", res.data.logoUrl);
					that.$set(that, "site_name", '首页');
					that.$set(that, "imgUrls", res.data.banner);
					that.$set(that, "menus", res.data.menus);
					that.$set(that, "roll", res.data.roll ? res.data.roll : []);
					// #ifdef H5
					that.$store.commit("SET_CHATURL", res.data.yzfUrl);
					Cache.set('chatUrl', res.data.yzfUrl);
					// #endif
					that.$set(that, "explosiveMoney", res.data.explosiveMoney);
					that.goodType = res.data.explosiveMoney[0].type
					this.getGroomList();
					this.shareApi();
					this.getcouponList();
					// #ifdef H5
					// that.subscribe = res.data.subscribe;
					// #endif

				})
			},
			getcouponList() {
				let that = this;
				getCoupons({
					page: 1,
					limit: 6
				}).then(res => {
					that.$set(that, "couponList", res.data);
					// 小程序判断用户是否授权；
					// #ifdef MP
					uni.getSetting({
						success(res) {
							if (!res.authSetting['scope.userInfo']) {
								that.window = that.couponList.length ? true : false;
							} else {
								that.window = false;
								that.iShidden = true;
							}
						}
					});
					// #endif
					// #ifndef MP
					if (that.isLogin) {
						that.window = false;
					} else {
						that.window = res.data.length ? true : false;
					}
					// #endif
				}).catch(err => {
					return this.$util.Tips({
						title: err
					});
				});
			},
			shareApi: function() {
				getShare().then(res => {
					this.$set(this, 'configApi', res.data);
					// #ifdef H5
					this.setOpenShare(res.data);
					// #endif
				})
			},
			getChatUrL() {
				kefuConfig().then(res => {
					let data = res.data;
					this.$store.commit("SET_CHATURL", data.yzfUrl);
					Cache.set('chatUrl', data.yzfUrl);
				})
			},
			// 微信分享；
			setOpenShare: function(data) {
				let that = this;
				if (that.$wechat.isWeixin()) {
					let configAppMessage = {
						desc: data.synopsis,
						title: data.title,
						link: location.href,
						imgUrl: data.img
					};
					that.$wechat.wechatEvevt(["updateAppMessageShareData", "updateTimelineShareData"],
						configAppMessage);
				}
			},
			// 授权关闭
			authColse: function(e) {
				this.isShowAuth = e
			},
			// 授权回调
			onLoadFun() {

			},
			// 首发新品切换
			ProductNavTab(item, index) {
				this.listActive = index
				this.goodType = item.type
				this.listActive = index
				this.ProductNavindex = index
				this.tempArr = []
				this.params.page = 1
				this.goodScroll = true
				let onloadH = true
				this.getGroomList(onloadH)
			},
			// 首发新品详情
			goDetail(item) {
				if (item.activityH5 && item.activityH5.type === "2" && !this.isLogin) {
					toLogin();
				} else {
					goShopDetail(item, this.uid).then(res => {
						uni.navigateTo({
							url: `/pages/goods_details/index?id=${item.id}`
						})
					})
				}
			},
			// 分类详情
			godDetail(item) {
				goShopDetail(item, this.uid).then(res => {
					uni.navigateTo({
						url: `/pages/goods_details/index?id=${item.id}`
					})
				})
			},
			// 精品推荐
			getGroomList(onloadH) {
				this.loading = true
				let type = this.goodType;
				if (!this.goodScroll) return
				if (onloadH) {
					this.iSshowH = true
				}
				getGroomList(type, this.params).then(({
					data
				}) => {
					// this.iSshowH = false
					// this.loading = false
					this.goodScroll = data.list.length >= this.params.limit
					this.params.page++
					this.tempArr = this.tempArr.concat(data.list)
				})
			},
			/**
			 * 获取我的推荐
			 */
			get_host_product: function() {
				let that = this;
				that.loading = true;

				if (that.hotScroll) return
				getProductHot(
					that.hotPage,
					that.hotLimit,
				).then(res => {
					that.hotPage++
					that.hotScroll = res.data.list.length < that.hotLimit
					that.hostProduct = that.hostProduct.concat(res.data.list)
				});
			},
			/**
			 * 打开属性加入购物车
			 * 
			 */
			joinCart: function(id) {
				this.$set(this, 'curProductId', id);
				//是否登录
				if (this.isLogin === false) {
					toLogin();
				} else {
					this.goCat(1,id);
				}
			},
			/**
			 * 立即购买
			 */
			goBuy: function(id) {
				this.$set(this, 'curProductId', id);
				if (this.isLogin === false) {
					toLogin();
				} else {
					this.goCat(0,id);
				}
			},
			onMyEvent: function() {
				this.$set(this.attr, 'cartAttr', false);
				this.$set(this, 'isOpen', false);
			},
			/**
			 * 属性变动赋值
			 * 
			 */
			ChangeAttr: function(res) {
				let productSelect = this.productValue[res];
				if (productSelect) {
					this.$set(this.attr.productSelect, "image", productSelect.image);
					this.$set(this.attr.productSelect, "price", productSelect.price);
					this.$set(this.attr.productSelect, "stock", productSelect.stock);
					this.$set(this.attr.productSelect, "unique", productSelect.id);
					this.$set(this.attr.productSelect, "cart_num", 1);
					this.$set(this, "attrValue", res);
					this.$set(this, "attrTxt", "已选择");
				} else {
					this.$set(this.attr.productSelect, "image", this.productInfo.image);
					this.$set(this.attr.productSelect, "price", this.productInfo.price);
					this.$set(this.attr.productSelect, "stock", 0);
					this.$set(this.attr.productSelect, "unique", this.productInfo.id);
					this.$set(this.attr.productSelect, "cart_num", 1);
					this.$set(this, "attrValue", "");
					this.$set(this, "attrTxt", "请选择");
				}
			},
			/**
			 * 购物车数量加和数量减
			 * 
			 */
			ChangeCartNum: function(changeValue) {
				//changeValue:是否 加|减
				//获取当前变动属性
				let productSelect = this.productValue[this.attrValue];
				//如果没有属性,赋值给商品默认库存
				if (productSelect === undefined && !this.attr.productAttr.length)
					productSelect = this.attr.productSelect;
				//无属性值即库存为0；不存在加减；
				if (productSelect === undefined) return;
				let stock = productSelect.stock || 0;
				let num = this.attr.productSelect;
				if (changeValue) {
					num.cart_num++;
					if (num.cart_num > stock) {
						this.$set(this.attr.productSelect, "cart_num", stock);
						this.$set(this, "cart_num", stock);
					}
				} else {
					num.cart_num--;
					if (num.cart_num < 1) {
						this.$set(this.attr.productSelect, "cart_num", 1);
					}
				}
			},
			attrVal(val) {
				this.$set(this.attr.productAttr[val.indexw], 'index', this.attr.productAttr[val.indexw].attrValues[val
					.indexn]);
			},
			/**
			 * 购物车手动填写
			 * 
			 */
			iptCartNum: function(e) {
				this.$set(this.attr.productSelect, 'cart_num', e ? e : 1);
			},
			
			/*
			 * 加入购物车
			 */
			goCat: function(num,id) {
				let that = this;
				getProductDetail(id, this.type).then(res => {
					
					let productInfo = res.data.productInfo;
					this.$set(this, 'productInfo', productInfo);
					this.$set(this.attr, 'productAttr', res.data.productAttr);
					this.$set(this, 'productValue', res.data.productValue);
					
					let productAttr = this.attr.productAttr.map(item => {
						return {
							attrName : item.attrName,
							attrValues: item.attrValues.split(','),
							id:item.id,
							isDel:item.isDel,
							productId:item.productId,
							type:item.type
						 }
					});
					this.$set(this.attr,'productAttr',productAttr);
					
					// 判断是否存在产品详情信息
					if (! (this.attrValue != undefined && this.attrValue != '')) {
						// 未设置过值
						this.DefaultSelect();
					}
					
					if (num === 1) {
						let productSelect = this.productValue[this.attrValue];
						//打开属性
						if (this.attrValue) {
							//默认选中了属性，但是没有打开过属性弹窗还是自动打开让用户查看默认选中的属性
							this.attr.cartAttr = !this.isOpen ? true : false;
							// 清空选中的属性
							this.attrValue = '';
						} else {
							if (this.isOpen) this.attr.cartAttr = true;
							else this.attr.cartAttr = !this.attr.cartAttr;
						}
						//只有关闭属性弹窗时进行加入购物车
						if (this.attr.cartAttr === true && this.isOpen === false)
							return (this.isOpen = true);
						//如果有属性,没有选择,提示用户选择
						if (
							this.attr.productAttr.length &&
							productSelect.stock === 0 &&
							this.isOpen === true
						)
						return this.$util.Tips({
							title: "产品库存不足，请选择其它"
						});
						
						let q = {
							productId: parseFloat(id),
							cartNum: parseFloat(this.attr.productSelect.cart_num),
							isNew: false,
							productAttrUnique: this.attr.productSelect !== undefined ?
								this.attr.productSelect.unique : this.productInfo.id
						};
						postCartAdd(q).then(function(res) {
								that.isOpen = false;
								that.attr.cartAttr = false;
								that.$util.Tips({
									title: "添加购物车成功",
									success: () => {}
								});
							})
							.catch(res => {
								that.isOpen = false;
								return that.$util.Tips({
									title: res
								});
							});
					} else {
						this.getPreOrder(id);
					}
				});
			},
			/**
			 * 默认选中属性
			 * 
			 */
			DefaultSelect: function() {
				let productAttr = this.attr.productAttr;
				let value = [];
				for (let key in this.productValue) {
					if (this.productValue[key].stock > 0) {
						value = this.attr.productAttr.length ? key.split(",") : [];
						break;
					}
				}
				for (let i = 0; i < productAttr.length; i++) {
					this.$set(productAttr[i], "index", value[i]);
				}
				//sort();排序函数:数字-英文-汉字；
				let productSelect = this.productValue[value.join(",")];
				if (productSelect && productAttr.length) {
					this.$set(
						this.attr.productSelect,
						"storeName",
						this.productInfo.storeName
					);
					this.$set(this.attr.productSelect, "image", productSelect.image);
					this.$set(this.attr.productSelect, "price", productSelect.price);
					this.$set(this.attr.productSelect, "stock", productSelect.stock);
					this.$set(this.attr.productSelect, "unique", productSelect.id);
					this.$set(this.attr.productSelect, "cart_num", 1);
					this.$set(this, "attrValue", value.join(","));
					this.$set(this, "attrTxt", "已选择");
				} else if (!productSelect && productAttr.length) {
					this.$set(
						this.attr.productSelect,
						"storeName",
						this.productInfo.storeName
					);
					this.$set(this.attr.productSelect, "image", this.productInfo.image);
					this.$set(this.attr.productSelect, "price", this.productInfo.price);
					this.$set(this.attr.productSelect, "stock", 0);
					this.$set(this.attr.productSelect, "unique", this.productInfo.id);
					this.$set(this.attr.productSelect, "cart_num", 1);
					this.$set(this, "attrValue", "");
					this.$set(this, "attrTxt", "请选择");
				} else if (!productSelect && !productAttr.length) {
					this.$set(
						this.attr.productSelect,
						"storeName",
						this.productInfo.storeName
					);
					this.$set(this.attr.productSelect, "image", this.productInfo.image);
					this.$set(this.attr.productSelect, "price", this.productInfo.price);
					this.$set(this.attr.productSelect, "stock", this.productInfo.stock);
					this.$set(
						this.attr.productSelect,
						"unique",
						this.productInfo.id || ""
					);
					this.$set(this.attr.productSelect, "cart_num", 1);
					this.$set(this, "attrValue", "");
					this.$set(this, "attrTxt", "请选择");
				}
			},
			/**
			 * 预下单
			 */
			getPreOrder: function(id) {
				this.$Order.getPreOrder(this.type === 'normal' ? 'buyNow' : 'video', [{
					"attrValueId": parseFloat(this.attr.productSelect.unique),
					"productId": parseFloat(id),
					"productNum": parseFloat(this.attr.productSelect.cart_num)
				}]);
			}
		},
		mounted() {
			let self = this
			// #ifdef H5
			//self.getChatUrL();
			// 获取H5 搜索框高度
			let appSearchH = uni.createSelectorQuery().select(".serch-wrapper");
			appSearchH.boundingClientRect(function(data) {
				self.searchH = data.height
			}).exec()
			// #endif
		},
		/**
		 * 用户点击右上角分享
		 */
		// #ifdef MP
		onShareAppMessage: function() {
			return {
				title: this.configApi.title,
				imageUrl: this.configApi.img,
				desc: this.configApi.synopsis,
				path: '/pages/index/index'
			};
		},
		// #endif
		onReachBottom() {
			if (this.navIndex == 0) {
				// 首页加载更多
				if (this.params.page != 1) {
					this.getGroomList();
				}
			} else {
				// 分类栏目加载更多
				if (this.sortProduct.length > 0) {
					//this.get_product_list();
				} else {
					this.get_host_product();
				}
			}
		},
	}
</script>
<style>
	page {
		display: flex;
		flex-direction: column;
		height: 100%;
		
		/* #ifdef H5 */
		background-color: #fff;
		/* #endif */

	}
</style>
<style lang="scss">
	.notice {
		width: 100%;
		height: 70rpx;
		border-radius: 10rpx;
		background-color: #fff;
		margin-bottom: 25rpx;
		line-height: 70rpx;
		padding: 0 14rpx;

		.line {
			color: #CCCCCC;
		}

		.pic {
			width: 130rpx;
			height: 36rpx;

			image {
				width: 100%;
				height: 100%;
				display: block !important;
			}
		}

		.swipers {
			height: 100%;
			width: 444rpx;
			overflow: hidden;

			swiper {
				height: 100%;
				width: 100%;
				overflow: hidden;
				font-size: 22rpx;
				color: #333333;
			}
		}

		.iconfont {
			color: #999999;
			font-size: 20rpx;
		}
	}

	.couponIndex {
		width: auto;
		height: 238rpx;
		background-image: url('~@/static/images/yhjsy.png');
		background-size: 100% 100%;
		padding-left: 42rpx;
		margin-bottom: 30rpx;

		.titBox {
			padding: 47rpx 0;
			text-align: center;
			height: 100%;

			.tit1 {
				color: #FFEBD2;
				font-size: 34rpx;
				font-weight: 600;
			}

			.tit2 {
				color: #FFEBD2;
				font-size: 22rpx;
				margin: 10rpx 0 26rpx 0;
			}

			.tit3 {
				color: #FFDAAF;
				font-size: 24rpx;

				.iconfont {
					font-size: 20rpx;
				}
			}
		}

		.listBox {
			padding: 14rpx 0;

			.listActive {
				background-image: url('~@/static/images/lingyhj.png');
				background-size: 100% 100%;
			}

			.listHui {
				background-image: url('~@/static/images/weiling.png');
				background-size: 100% 100%;
			}

			.list {
				width: 170rpx;
				height: 210rpx;
				padding: 16rpx 0;
				text-align: center;
				margin-left: 24rpx;

				.tit {
					font-size: 18rpx;
					padding: 0 26rpx;
				}

				.titActive {
					color: #C99959;
				}

				.price {
					font-size: 46rpx;
					font-weight: 900;
					margin-top: 4rpx;
				}

				.pricehui {
					color: #B2B2B2;
				}

				.fonthui {
					background-color: #F5F5F5 !important;
				}

				.yuan {
					font-size: 24rpx;
				}

				.ling {
					font-size: 24rpx;
					margin-top: 9.5rpx;
					width: 102rpx;
					height: 36rpx;
					line-height: 36rpx;
					background-color: #FFE5C7;
					border-radius: 28rpx;
					margin: auto;
				}

				.priceM {
					color: #FFDAAF;
					font-size: 22rpx;
					margin-top: 14rpx;
				}
			}
		}
	}

	.sticky-box {
		/* #ifndef APP-PLUS-NVUE */
		display: flex;
		position: -webkit-sticky;
		/* #endif */
		position: sticky;
		/* #ifdef H5*/
		top: var(--window-top);
		/* #endif */

		z-index: 4;
		flex-direction: row;
		margin: 0px;
		background: #f5f5f5;
		padding: 30rpx 0;
	}

	.listAll {
		width: 20%;
		text-indent: 62rpx;
		font-size: 30rpx;
		border-left: 1px #eee solid;
		margin: 1% 0;
		padding: 5rpx;
		position: relative;

		image {
			position: absolute;
			left: 20rpx;
			top: 8rpx;
		}
	}

	.tab {
		position: relative;
		display: flex;
		font-size: 28rpx;
		white-space: nowrap;

		&__item {
			flex: 1;
			padding: 0 20rpx;
			text-align: center;
			height: 60rpx;
			line-height: 60rpx;
			color: #666;

			&.active {
				color: #09C2C9;
			}
		}
	}

	.tab__line {
		display: block;
		height: 6rpx;
		position: absolute;
		bottom: 0;
		left: 0;
		z-index: 1;
		border-radius: 3rpx;
		position: relative;
		background: #2FC6CD;
	}

	.scroll-view_H {
		/* 文本不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
		white-space: nowrap;
		width: 100%;
	}


	.privacy-wrapper {
		z-index: 999;
		position: fixed;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		background: #7F7F7F;

		.privacy-box {
			position: absolute;
			left: 50%;
			top: 50%;
			transform: translate(-50%, -50%);
			width: 560rpx;
			padding: 50rpx 45rpx 0;
			background: #fff;
			border-radius: 20rpx;

			.title {
				text-align: center;
				font-size: 32rpx;
				text-align: center;
				color: #333;
				font-weight: 700;
			}

			.content {
				margin-top: 20rpx;
				line-height: 1.5;
				font-size: 26rpx;
				color: #666;
				text-indent: 54rpx;

				i {
					font-style: normal;
					color: $theme-color;
				}
			}

			.btn-box {
				margin-top: 40rpx;
				text-align: center;
				font-size: 30rpx;

				.btn-item {
					height: 82rpx;
					line-height: 82rpx;
					background: linear-gradient(90deg, #F67A38 0%, #F11B09 100%);
					color: #fff;
					border-radius: 41rpx;
				}

				.btn {
					padding: 30rpx 0;
				}
			}
		}
	}

	.header-status {
		position: fixed;
		top: 0px;
		width: 100%;
		z-index: 999;
		background-color:$theme-color;
	}
	.page-index {
		display: flex;
		flex-direction: column;
		min-height: 100%;
		background: linear-gradient(180deg, #fff 0%, #f5f5f5 100%);

		/* #ifdef H5 */
		.header {
			position: fixed;
			z-index: 200000;
			width: 100%;
			padding-left: 30rpx;
			// padding-right: 30rpx;
			
			background-color: $theme-color;

			.serch-wrapper {
				align-items: center;

				.logo {
					width: 58rpx;
					height: 58rpx;
					margin-right: 24rpx;
				}

				image {
					width: 58rpx;
					height: 58rpx;
				}

				.input {
					display: flex;
					align-items: center;
					width: 100%;
					height: 58rpx;
					padding: 0 0 0 30rpx;
					background: rgba(247, 247, 247, 1);
					border: 1px solid rgba(241, 241, 241, 1);
					border-radius: 29rpx;
					color: #BBBBBB;
					font-size: 26rpx;

					.iconfont {
						margin-right: 20rpx;
						font-size: 26rpx;
						color: #666666;
					}
				}
			}

			.tabNav {
				padding-top: 24rpx;
			}
		}
		/* #endif */
		
		/* #ifdef MP || APP || MP-WEIXIN */
		.mp-header {
			z-index: 999;
			width: 100%;
			position: fixed;
			padding-left: 30rpx;
			padding-right: 30rpx;
			
			background-color: $theme-color;

			.serch-wrapper {
				align-items: center;

				.logo {
					width: 58rpx;
					height: 58rpx;
					margin-right: 24rpx;
				}
				image {
					width: 58rpx;
					height: 58rpx;
				}

				.input {
					display: flex;
					align-items: center;
					width: 100%;
					height: 58rpx;
					padding: 0 0 0 30rpx;
					background: rgba(247, 247, 247, 1);
					border: 1px solid rgba(241, 241, 241, 1);
					border-radius: 29rpx;
					color: #BBBBBB;
					font-size: 28rpx;

					.iconfont {
						margin-right: 20rpx;
					}
				}
			}
		}

		/* #endif */

		.page_content {
			background-color: #f5f5f5;
			/* #ifdef H5 */
			// margin-top: 20rpx !important;
			/* #endif */
			padding: 0 30rpx;

			.swiper {
				position: relative;
				width: 100%;
				height: 280rpx;
				margin: 0 auto;
				border-radius: 10rpx;
				overflow: hidden;
				margin-bottom: 25rpx;
				/* #ifdef MP */
				z-index: 10;
				margin-top: 20rpx;

				/* #endif */
				swiper,
				.swiper-item,
				image {
					width: 100%;
					height: 280rpx;
					border-radius: 10rpx;
				}
			}

			.nav {
				padding-bottom: 26rpx;
				background: #fff;
				opacity: 1;
				border-radius: 14rpx;
				width: 100%;
				margin-bottom: 30rpx;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;
					width: 20%;
					margin-top: 30rpx;

					image {
						width: 82rpx;
						height: 82rpx;
					}
				}
			}


			.nav-bd {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;

					.txt {
						font-size: 32rpx;
						color: #282828;
					}

					.label {
						display: flex;
						align-items: center;
						justify-content: center;
						width: 124rpx;
						height: 32rpx;
						margin-top: 5rpx;
						font-size: 24rpx;
						color: #999;
					}

					&.active {
						color: $theme-color;

						.txt {
							color: $theme-color;
						}

						.label {
							background: linear-gradient(90deg, $bg-star 0%, $bg-end 100%);
							border-radius: 16rpx;
							color: #fff;
						}
					}
				}
			}

			.index-product-wrapper {
				margin-bottom: 110rpx;

				&.on {
					min-height: 1500rpx;
				}

				.list-box {
					display: flex;
					flex-wrap: wrap;
					justify-content: space-between;

					.item {
						width: 680rpx;
						margin-bottom: 20rpx;
						background-color: #fff;
						border-radius: 10rpx;
						overflow: hidden;
						display: flex;

						image {
							width: 260rpx;
							height: 260rpx;
							margin: 15rpx 0rpx 15rpx 0rpx;
							border-radius: 10rpx;
						}

						.product-info {
							.text-info {
								padding: 20rpx 20rpx 15rpx;
								width: 420rpx;
								height: 85rpx;
							
								.title {
									color: #222222;
								}
							}
							
							.product-buy {
								padding: 0rpx 20rpx 15rpx;
								width: 420rpx;
								height: 205rpx;
								display: flex;
								flex-flow: column;
								justify-content: space-between;
								align-items: flex-end;
								
								.product-data {
									display: flex;
									flex-direction: row;
									justify-content: space-between;
									align-items: flex-end;
									width: 100%;
									
									.stock-info {
										padding: 15rpx 0rpx 15rpx;
										
										.stock-num {
											margin-top: 8rpx;
											font-size: 26rpx;
											color: #AAAAAA;
											
											text {
												margin-right: 2px;
												font-size: 20rpx;
												font-weight: bold;
												color: red;
											}
										}
										
										.stock-status {
											display: flex;
											align-items: flex-end;
											color: red;
											font-size: 34rpx;
											font-weight: 800;
										
											text {
												padding-bottom: 4rpx;
												font-size: 24rpx;
												font-weight: 800;
											}
										
											.txt {
												display: flex;
												align-items: center;
												justify-content: center;
												width: 112rpx;
												height: 28rpx;
												margin-left: 15rpx;
												margin-bottom: 10rpx;
												border: 1px solid red;
												border-radius: 4rpx;
												font-size: 22rpx;
												font-weight: normal;
											}
										}
									}
									
									.price-info {
										padding: 15rpx 0rpx 15rpx;
										
										.old-price {
											margin-top: 8rpx;
											font-size: 26rpx;
											color: #AAAAAA;
											text-decoration: line-through;
										
											text {
												margin-right: 2px;
												font-size: 20rpx;
											}
										}
										
										.price {
											display: flex;
											align-items: flex-end;
											color: $theme-color;
											font-size: 34rpx;
											font-weight: 800;
										
											text {
												padding-bottom: 4rpx;
												font-size: 24rpx;
												font-weight: 800;
											}
										
											.txt {
												display: flex;
												align-items: center;
												justify-content: center;
												width: 28rpx;
												height: 28rpx;
												margin-left: 15rpx;
												margin-bottom: 10rpx;
												border: 1px solid $theme-color;
												border-radius: 4rpx;
												font-size: 22rpx;
												font-weight: normal;
											}
										}
									}
								}
								
								.bnt{
									.bnts {
										text-align: center;
										color: #fff;
										font-size: 28rpx;
									}
									flex-wrap: nowrap;
									.joinCart {
										border-radius: 50rpx 0 0 50rpx;
										background-image: linear-gradient(to right, #fea10f 0%, #fa8013 100%);
									}
									.buy {
										border-radius: 0 50rpx 50rpx 0;
										background-image: linear-gradient(to right, #55aa7f 0%, #009600 100%);
										min-width: 136rpx;
									}
									.nobuy {
										border-radius: 50rpx 50rpx 50rpx 50rpx;
										background-image: linear-gradient(to right, #55aa7f 0%, #009600 100%);
										min-width: 136rpx;
									}
								}
							}
						}
					}

					&.on {
						display: flex;
					}
				}
			}
		}
	}

	.productList {
		/* #ifdef H5 */
		padding-bottom: 140rpx;
		/* #endif */
	}

	.productList .list {
		padding: 0 20rpx;
	}

	.productList .list.on {
		background-color: #fff;
		border-top: 1px solid #f6f6f6;
	}

	.productList .list .item {
		width: 345rpx;
		margin-top: 20rpx;
		background-color: #fff;
		border-radius: 10rpx;
	}

	.productList .list .item.on {
		width: 100%;
		display: flex;
		border-bottom: 1rpx solid #f6f6f6;
		padding: 30rpx 0;
		margin: 0;
	}

	.productList .list .item .pictrue {
		position: relative;
		width: 100%;
		height: 345rpx;
	}

	.productList .list .item .pictrue.on {
		width: 180rpx;
		height: 180rpx;
	}

	.productList .list .item .pictrue image {
		width: 100%;
		height: 100%;
		border-radius: 20rpx 20rpx 0 0;
	}

	.productList .list .item .pictrue image.on {
		border-radius: 6rpx;
	}

	.productList .list .item .text {
		padding: 20rpx 17rpx 26rpx 17rpx;
		font-size: 30rpx;
		color: #222;
	}

	.productList .list .item .text.on {
		width: 508rpx;
		padding: 0 0 0 22rpx;
	}

	.productList .list .item .text .money {
		font-size: 26rpx;
		font-weight: bold;
		margin-top: 8rpx;
	}

	.productList .list .item .text .money.on {
		margin-top: 50rpx;
	}

	.productList .list .item .text .money .num {
		font-size: 34rpx;
	}

	.productList .list .item .text .vip {
		font-size: 22rpx;
		color: #aaa;
		margin-top: 7rpx;
	}

	.productList .list .item .text .vip.on {
		margin-top: 12rpx;
	}

	.productList .list .item .text .vip .vip-money {
		font-size: 24rpx;
		color: #282828;
		font-weight: bold;
	}

	.productList .list .item .text .vip .vip-money image {
		width: 46rpx;
		height: 21rpx;
		margin-left: 4rpx;
	}

	.pictrue {
		position: relative;
	}

	.fixed {
		z-index: 100;
		position: fixed;
		left: 0;
		top: 0;
		background: linear-gradient(90deg, red 50%, #ff5400 100%);

	}

	.mores-txt {
		width: 100%;
		align-items: center;
		justify-content: center;
		height: 70rpx;
		color: #999;
		font-size: 24rpx;

		.iconfont {
			margin-top: 2rpx;
			font-size: 20rpx;
		}
	}

	.menu-txt {
		font-size: 24rpx;
		color: #454545;
	}

	.mp-bg {
		position: absolute;
		left: 0;
		width: 100%;
		height: 304rpx;
		background: linear-gradient(180deg, #009600 0%, #F5F5F5 100%, #00aa00 100%);
		// border-radius: 0 0 30rpx 30rpx;


	}

	.stats {
		position: absolute;
		left: 0px;
		top: 0px;
		z-index: 2000000;
		width: 750rpx;
		height: var(--status-bar-height);
		background: #ffffff;
	}
</style>