<template>
	<view>
		<view class='productList'>
			<view class='search bg-color acea-row row-between-wrapper'>
				<!-- #ifdef H5 -->
				<view class="iconfont icon-xiangzuo" @click="goback()"></view>
				<!-- #endif -->
				<view class='input acea-row row-between-wrapper'><text class='iconfont icon-sousuo'></text>
					<input placeholder='搜索商品名称' placeholder-class='placeholder' confirm-type='search' name="search"
						:value='where.keyword' @confirm="searchSubmit"></input>
				</view>
				<view class='iconfont' :class='is_switch==true?"icon-pailie":"icon-tupianpailie"' @click='Changswitch'>
				</view>
			</view>
			<view class='nav acea-row row-middle'>
				<view class='item' :class='title ? "font-color":""' @click='set_where(1)'>{{title ? title:'默认'}}</view>
				<view class='item' @click='set_where(2)'>
					价格
					<image v-if="price==1" src='../../static/images/up.png'></image>
					<image v-else-if="price==2" src='../../static/images/down.png'></image>
					<image v-else src='../../static/images/horn.png'></image>
				</view>
				<view class='item' @click='set_where(3)'>
					销量
					<image v-if="stock==1" src='../../static/images/up.png'></image>
					<image v-else-if="stock==2" src='../../static/images/down.png'></image>
					<image v-else src='../../static/images/horn.png'></image>
				</view>
				<!-- down -->
				<view class='item' :class='nows ? "font-color":""' @click='set_where(4)'>新品</view>
			</view>
			<view :class='is_switch==true?"":"listBox"' v-if="productList.length>0">
				<view class='list acea-row row-between-wrapper' :class='is_switch==true?"":"on"'>
					<view class='item' :class='is_switch==true?"":"on"' hover-class='none'
						v-for="(item,index) in productList" :key="index" @click="godDetail(item)">
						<view class='pictrue' :class='is_switch==true?"":"on"'>
							<image :src='item.image' :class='is_switch==true?"":"on"'></image>
							<span class="pictrue_log_class"
								:class="is_switch === true ? 'pictrue_log_big' : 'pictrue_log'"
								v-if="item.activityH5 && item.activityH5.type === '1'">秒杀</span>
							<span class="pictrue_log_class"
								:class="is_switch === true ? 'pictrue_log_big' : 'pictrue_log'"
								v-if="item.activityH5 && item.activityH5.type === '2'">砍价</span>
							<span class="pictrue_log_class"
								:class="is_switch === true ? 'pictrue_log_big' : 'pictrue_log'"
								v-if="item.activityH5 && item.activityH5.type === '3'">拼团</span>
						</view>
						<view class='text' :class='is_switch==true?"":"on"'>
							<view class="text-info">
								<view class='name line1'>{{item.storeName}}</view>
								<view class='money font-color' :class='is_switch==true?"":"on"'>￥<text
										class='num'>{{item.price}}</text></view>
								<view class='vip acea-row row-between-wrapper' :class='is_switch==true?"":"on"'>
									<view class='vip-money' v-if="item.vip_price && item.vip_price > 0">￥{{item.vip_price}}
										<image src='../../static/images/vip.png'></image>
									</view>
									<view>已售{{Number(item.sales) + Number(item.ficti) || 0}}{{item.unitName}}</view>
								</view>
							</view>
							<view class="and-cart">
								<image class="pic" src="/static/images/3-002.png" @click.stop="joinCart(item.id)" />
							</view>
							
						</view>
					</view>
				</view>
				<view class='loadingicon acea-row row-center-wrapper' v-if='productList.length > 0'>
					<text class='loading iconfont icon-jiazai' :hidden='loading==false'></text>{{loadTitle}}
				</view>
			</view>
		</view>
		<view class='noCommodity' v-if="productList.length==0 && where.page > 1">
			<view class='pictrue'>
				<image src='../../static/images/noShopper.png'></image>
			</view>
			<recommend :hostProduct="hostProduct"></recommend>
		</view>
		<home></home>
		<!-- 组件 -->
		<productWindow :attr="attr" :isShow='1' :iSplus='1' :iScart='1' @myevent="onMyEvent" @ChangeAttr="ChangeAttr"
			@ChangeCartNum="ChangeCartNum" @attrVal="attrVal" @iptCartNum="iptCartNum" id='product-window' @goCat="joinCart(curProductId)">
		</productWindow>
	</view>
</template>

<script>
	import {
		getProductDetail,
		postCartAdd,
		getProductslist,
		getProductHot
	} from '@/api/store.js';
	import recommend from '@/components/recommend';
	import productWindow from '@/components/productWindow';
	import home from '@/components/home';
	import {
		mapGetters
	} from "vuex";
	import {
		goShopDetail
	} from '@/libs/order.js'
	export default {
		computed: mapGetters(['uid']),
		components: {
			recommend,
			productWindow,
			home
		},
		data() {
			return {
				productList: [],
				is_switch: false,
				where: {
					keyword: '',
					priceOrder: '',
					salesOrder: '',
					news: 0,
					page: 1,
					limit: 20,
					cid: 0,
				},
				price: 0,
				stock: 0,
				nows: false,
				loadend: false,
				loading: false,
				loadTitle: '加载更多',
				title: '',
				hostProduct: [],
				hotPage: 1,
				hotLimit: 10,
				hotScroll: false,
				
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
			};
		},
		onLoad: function(options) {
			this.$set(this.where, 'cid', options.cid || 0);
			this.title = options.title || '';
			this.$set(this.where, 'keyword', options.searchValue || '');
			this.get_product_list();
			
			options.type == undefined || options.type == null ? this.type = 'normal' : this.type = options.type;
		},
		methods: {
			goback() {
				// #ifdef H5
				return history.back();
				// #endif
				// #ifndef H5
				return uni.navigateBack({
					delta: 1,
				})
				// #endif
			},
			// 去详情页
			godDetail(item) {
				goShopDetail(item, this.uid).then(res => {
					uni.navigateTo({
						url: `/pages/goods_details/index?id=${item.id}`
					})
				})
			},
			Changswitch: function() {
				let that = this;
				that.is_switch = !that.is_switch
			},
			searchSubmit: function(e) {
				let that = this;
				that.$set(that.where, 'keyword', e.detail.value);
				that.loadend = false;
				that.$set(that.where, 'page', 1)
				this.get_product_list(true);
			},
			/**
			 * 获取我的推荐
			 */
			get_host_product: function() {
				let that = this;
				if (that.hotScroll) return
				getProductHot(
					that.hotPage,
					that.hotLimit,
				).then(res => {
					that.hotPage++
					that.hotScroll = res.data.list.length < that.hotLimit
					that.hostProduct = that.hostProduct.concat(res.data.list)
					// that.$set(that, 'hostProduct', res.data)
				});
			},
			//点击事件处理
			set_where: function(e) {
				switch (e) {
					case 1:
						return;
						break;
					case 2:
						if (this.price == 0) this.price = 1;
						else if (this.price == 1) this.price = 2;
						else if (this.price == 2) this.price = 0;
						this.stock = 0;
						break;
					case 3:
						if (this.stock == 0) this.stock = 1;
						else if (this.stock == 1) this.stock = 2;
						else if (this.stock == 2) this.stock = 0;
						this.price = 0
						break;
					case 4:
						this.nows = !this.nows;
						break;
				}
				this.loadend = false;
				this.$set(this.where, 'page', 1);
				this.get_product_list(true);
			},
			//设置where条件
			setWhere: function() {
				if (this.price == 0) this.where.priceOrder = '';
				else if (this.price == 1) this.where.priceOrder = 'asc';
				else if (this.price == 2) this.where.priceOrder = 'desc';
				if (this.stock == 0) this.where.salesOrder = '';
				else if (this.stock == 1) this.where.salesOrder = 'asc';
				else if (this.stock == 2) this.where.salesOrder = 'desc';
				this.where.news = this.nows ? 1 : 0;
			},
			//查找产品
			get_product_list: function(isPage) {
				let that = this;
				that.setWhere();
				if (that.loadend) return;
				if (that.loading) return;
				if (isPage === true) that.$set(that, 'productList', []);
				that.loading = true;
				that.loadTitle = '';
				getProductslist(that.where).then(res => {
					let list = res.data.list;
					let productList = that.$util.SplitArray(list, that.productList);
					let loadend = list.length < that.where.limit;
					that.loadend = loadend;
					that.loading = false;
					that.loadTitle = loadend ? '已全部加载' : '加载更多';
					that.$set(that, 'productList', productList);
					that.$set(that.where, 'page', that.where.page + 1);
					if (that.productList.length === 0) {
						this.get_host_product();
					} 
				}).catch(err => {
					that.loading = false;
					that.loadTitle = '加载更多';
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
					if (this.attr.cartAttr === true && this.isOpen === false) {
						
						return (this.isOpen = true);
					}
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
			}
		},
		onPullDownRefresh() {

		},
		onReachBottom() {
			if (this.productList.length > 0) {
				this.get_product_list();
			} else {
				this.get_host_product();
			}

		}
	}
</script>

<style scoped lang="scss">
	.iconfont {
		color: #fff;
	}
    .listBox{
		padding: 20px 15px;
		margin-top: 154rpx;
	}
	.productList .search {
		width: 100%;
		height: 86rpx;
		padding-left: 23rpx;
		box-sizing: border-box;
		position: fixed;
		left: 0;
		top: 0;
		z-index: 9;
	}

	.productList .search .input {
		// width: 640rpx;
		height: 60rpx;
		background-color: #fff;
		border-radius: 50rpx;
		padding: 0 20rpx;
		box-sizing: border-box;
	}

	.productList .search .input input {
		/* #ifdef H5 */
		width: 528rpx;
		/* #endif */
		/* #ifndef H5 */
		width: 548rpx;
		/* #endif */
		height: 100%;
		font-size: 26rpx;
	}

	.productList .search .input .placeholder {
		color: #999;
	}

	.productList .search .input .iconfont {
		font-size: 35rpx;
		color: #555;
	}

	.productList .search .icon-pailie,
	.productList .search .icon-tupianpailie {
		color: #fff;
		width: 62rpx;
		font-size: 40rpx;
		height: 86rpx;
		line-height: 86rpx;
	}

	.productList .nav {
		height: 86rpx;
		color: #454545;
		position: fixed;
		left: 0;
		width: 100%;
		font-size: 28rpx;
		background-color: #fff;
		margin-top: 86rpx;
		top: 0;
		z-index: 9;
	}

	.productList .nav .item {
		width: 25%;
		text-align: center;
	}

	.productList .nav .item.font-color {
		font-weight: bold;
	}

	.productList .nav .item image {
		width: 15rpx;
		height: 19rpx;
		margin-left: 10rpx;
	}

	.productList .list {
		padding: 0 30rpx;
		margin-top: 192rpx;

	}

	.productList .list.on {
		border-radius: 14rpx;
		margin-top: 0 !important;
		background-color: #fff;
		padding: 40rpx 0 0 0;
		// margin: 20rpx 0;
		// background-color: #fff;
	}

	.productList .list .item {
		width: 335rpx;
		background-color: #fff;
		border-radius: 14rpx;
		margin-bottom: 20rpx;
	}

	.productList .list .item.on {
		width: 100%;
		display: flex;
		padding: 0 24rpx 50rpx 24rpx;
		margin: 0;
		border-radius: 14rpx;
	}

	.productList .list .item .pictrue {
		position: relative;
		width: 100%;
		height: 335rpx;
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
		padding: 18rpx 20rpx;
		font-size: 30rpx;
		color: #222;
		
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: flex-end;
		
		.text-info {
			overflow: hidden;
		}
		
		.and-cart .pic {
			width: 50rpx;
			height: 50rpx;
		}
	}

	.productList .list .item .text.on {
		width: 456rpx;
		padding: 0 0 0 20rpx;
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

	.noCommodity {
		background-color: #fff;
		padding-bottom: 30rpx;
		margin-top: 172rpx;
	}
</style>
