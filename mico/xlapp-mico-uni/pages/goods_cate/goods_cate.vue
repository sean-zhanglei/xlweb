<template>
	<view class='productSort'>
		<view class='header acea-row row-center-wrapper'>
			<view class='acea-row row-between-wrapper input'>
				<text class='iconfont icon-sousuo'></text>
				<input type='text' placeholder='点击搜索商品信息' @confirm="searchSubmitValue" confirm-type='search' name="search"
				 placeholder-class='placeholder'></input>
			</view>
		</view>
		<view class='aside' :style="{bottom: tabbarH + 'px',height: height + 'rpx'}">
			<scroll-view scroll-y="true" scroll-with-animation='true' style="height: 100%;">
				<view class='item acea-row row-center-wrapper' :class='index==navActive?"on":""' v-for="(item,index) in productList"
			 :key="index" @click='tap(index,"b"+index)'><text>{{item.name}}</text></view>
			 </scroll-view>
			
		</view>
		<view class='conter'>
			<scroll-view scroll-y="true" :scroll-into-view="toView" :style='"height:"+height+"rpx;margin-top: 78rpx;"' @scroll="scroll"
			 scroll-with-animation='true'>
				<block v-for="(item,index) in productList" :key="index">
					
					<view class='listw' :id="'b'+index">
						<view class='title acea-row row-center-wrapper'>
							<view class='line'></view>
							<view class='name'>{{item.name}}</view>
							<view class='line'></view>
						</view>
						<view class='list acea-row'>
							<block v-for="(itemn,indexn) in item.child" :key="indexn">
								<view hover-class='none' @click.stop="godProductListDetail(itemn, index, indexn)" class='item acea-row row-column row-middle'>
							        <view class='picture' :style="{'background-color':itemn.extra?'none':'#f7f7f7'}">
										<image :src='itemn.extra'></image>
									</view>
									<view class='name line1'>{{itemn.name}}</view>
								</view>
							</block>
						</view>
						
						<view class="listBox" v-if="item.productListDetailLimit20 && item.productListDetailLimit20.length > 0">
							<view class='list acea-row row-between-wrapper on'>
								<view class='item on' hover-class='none'
									v-for="(itemDetail,indexDetail) in item.productListDetailLimit20" :key="indexDetail" @click.stop="godDetail(itemDetail)">
									<view class='pictrue on'>
										<image :src='itemDetail.image' class="on"></image>
										<span class="pictrue_log pictrue_log_class"
											v-if="! itemDetail.activityH5 && itemDetail.otPrice > itemDetail.price">折扣</span>
										<span class="pictrue_log_class pictrue_log_big"
											v-if="itemDetail.activityH5 && itemDetail.activityH5.type === '1'">秒杀</span>
										<span class="pictrue_log_class pictrue_log_big"
											v-if="itemDetail.activityH5 && itemDetail.activityH5.type === '2'">砍价</span>
										<span class="pictrue_log_class pictrue_log_big"
											v-if="itemDetail.activityH5 && itemDetail.activityH5.type === '3'">拼团</span>
									</view>
									<view class='text on'>
										<view class="text-info">
											<view class='name line1'>{{itemDetail.storeName}}</view>
											<view class='money font-color on'>￥<text
													class='num'>{{itemDetail.price}}</text></view>
											<view class='vip acea-row row-between-wrapper on'>
												<view class='vip-money' v-if="itemDetail.vip_price && itemDetail.vip_price > 0">￥{{itemDetail.vip_price}}
													<image src='../../static/images/vip.png'></image>
												</view>
												<view>已售{{Number(itemDetail.sales) + Number(itemDetail.ficti) || 0}}{{itemDetail.unitName}}</view>
											</view>
										</view>
										<view class="and-cart">
											<image class="pic" src="/static/images/3-002.png" @click.stop="joinCart(itemDetail.id)" />
										</view>
										
									</view>
								</view>
							</view>
						</view>
						<view v-if="item.productListDetailLimit20 && item.productListDetailLimit20.length > 1">
							<navigator hover-class='none' :url='"/pages/goods_list/index?cid="+item.id+"&title="+item.name'>
								<button style="font-size: 26rpx;">更多{{item.name}}...</button>
							</navigator>
						</view>
						<view class='noCommodity' v-if="item.productListDetailLimit20 && item.productListDetailLimit20.length == 0">
							<view class='pictrue'>
								<image src='../../static/images/noShopper.png'></image>
							</view>
						</view>
					</view>
				</block>
				<view :style='"height:"+(height-300)+"rpx;"' v-if="number<15"></view>
			</scroll-view>
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
		getCategoryList,
		getProductDetail,
		postCartAdd,
		getProductslist
	} from '@/api/store.js';
	import {
		goShopDetail
	} from '@/libs/order.js'
	import {
		getCartCounts
	} from '@/api/order.js';
	import ClipboardJS from "@/plugin/clipboard/clipboard.js";
	import home from '@/components/home';
	import productWindow from '@/components/productWindow';
	import {
		mapGetters
	} from "vuex";
	
	export default {
		components: {
			home,
			productWindow
		},
		data() {
			return {
				navlist: [],
				productList: [],
				navActive: 0,
				number: "",
				height: 0,
				hightArr: [],
				toView: "",
				tabbarH: 0,
				where: {
					keyword: '',
					priceOrder: '',
					salesOrder: '',
					news: 0,
					page: 1,
					limit: 20,
					cid: 0,
				},
				
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
		onLoad(options) {
			options.type == undefined || options.type == null ? this.type = 'normal' : this.type = options.type;
			this.getAllCategory();
		},
		onShow(){
			let that = this;
			this.$nextTick(() => {
			    // 刷新购物车数量
			    getCartCounts(true, 'sum').then(res => {
			    	let cartCount = res.data.count;
			    	that.$store.commit("SET_TABBAR_BADGE", '' + cartCount);
			    });
			});
			
		},
		methods: {
			infoScroll: function() {
				let that = this;
				let len = that.productList.length;
				let child = that.productList[len - 1]&&that.productList[len - 1].child?that.productList[len - 1].child:[];
				this.number = child?child.length:0;
				
				//设置商品列表高度
				uni.getSystemInfo({
					success: function(res) {
						that.height = (res.windowHeight) * (750 / res.windowWidth) - 78;
					},
				});
				let hightArr = [];
				for (let i = 0; i < len; i++) {
					//获取元素所在位置
					let query = uni.createSelectorQuery().in(this);
					let idView = "#b" + i;
					query.select(idView).boundingClientRect();
					query.exec(function(res) {
						let top = res[0].top;
						hightArr.push(top);
						that.hightArr = hightArr
					});
				};
			},
			tap: function(index, id) {
				this.toView = id;
				this.navActive = index;
			},
			getAllCategory: function() {
				let that = this;
				getCategoryList().then(res => {
					let productList = res.data;
					that.$set(that, 'productList', productList);
					if (productList.length > 0) {
						productList.forEach(function(cate, index) {
							let child = cate.child;
							if (child.length > 0) {
								// 默认获取第一个子分类
								that.get_product_list(child[0].id, index);
							} else {
								that.$set(that.productList[index], 'productListDetailLimit20', []);
							}
						}, this);
					}
					setTimeout(function(){
						that.infoScroll();
					},500)
				})
			},
			
			godProductListDetail: function(childCateItem, cateIndex, cateChildIndex) {
				let that = this;
				that.where.cid = childCateItem.id;
				// 按销量排序
				that.where.salesOrder = 'desc';
				getProductslist(that.where).then(res => {
					let list = res.data.list;
					let productListDetailLimit20 = that.$util.SplitArray(list, that.productListDetailLimit20);
					that.$set(that.productList[cateIndex], 'productListDetailLimit20', productListDetailLimit20);
				}).catch(err => {
				});
			},
			get_product_list: function(childCateId, index) {
				let that = this;
				that.where.cid = childCateId;
				// 按销量排序
				that.where.salesOrder = 'desc';
				getProductslist(that.where).then(res => {
					let list = res.data.list;
					let productListDetailLimit20 = that.$util.SplitArray(list, that.productListDetailLimit20);
					that.$set(that.productList[index], 'productListDetailLimit20', productListDetailLimit20);
				}).catch(err => {
				});
			},
			// 去详情页
			godDetail(item) {
				goShopDetail(item, this.uid).then(res => {
					uni.navigateTo({
						url: `/pages/goods_details/index?id=${item.id}`
					})
				})
			},
			scroll: function(e) {
				let scrollTop = e.detail.scrollTop;
				let scrollArr = this.hightArr;
				for (let i = 0; i < scrollArr.length; i++) {
					if (scrollTop >= 0 && scrollTop < scrollArr[1] - scrollArr[0]) {
						this.navActive = 0
					} else if (scrollTop >= scrollArr[i] - scrollArr[0] && scrollTop < scrollArr[i + 1] - scrollArr[0]) {
						this.navActive = i
					} else if (scrollTop >= scrollArr[scrollArr.length - 1] - scrollArr[0]) {
						this.navActive = scrollArr.length - 1
					}
				}
			},
			searchSubmitValue: function(e) {
				if (this.$util.trim(e.detail.value).length > 0)
					uni.navigateTo({
						url: '/pages/goods_list/index?searchValue=' + e.detail.value
					})
				else
					return this.$util.Tips({
						title: '请填写要搜索的产品信息'
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
					if (! (this.attrValue !== undefined && this.attrValue !== '')) {
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
								success: () => {
									// 刷新购物车数量
									getCartCounts(true, 'sum').then(res => {
										let cartCount = res.data.count;
										that.$store.commit("SET_TABBAR_BADGE", '' + cartCount);
									});
								}
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
		}
	}
</script>

<style scoped lang="scss">
	.productSort {
		overflow: hidden;
	}
	.productSort .header {
		/* #ifdef MP */
		margin-top:var(--status-bar-height);
		/* #endif */
		height: 78rpx;
		background-color: #fff;
		position: fixed;
		left: 0;
		right: 0;
		top: 0;
		z-index: 9;
		border-bottom: 1rpx solid #f5f5f5;
	}
	
	.productSort .header .input {
		width: 700rpx;
		height: 60rpx;
		background-color: #f5f5f5;
		border-radius: 50rpx;
		box-sizing: border-box;
		padding: 0 25rpx;
	}
	
	.productSort .header .input .iconfont {
		font-size: 26rpx;
		color: #555;
	}
	
	.productSort .header .input .placeholder {
		color: #999;
	}
	
	.productSort .header .input input {
		font-size: 26rpx;
		height: 100%;
		width: 597rpx;
	}
	
	.productSort .aside {
		position: fixed;
		width: 180rpx;
		left: 0;
		top:0;
		background-color: #f7f7f7;
		overflow-y: scroll;
		overflow-x: hidden;
		/* #ifdef H5 */
		margin-top: 78rpx;
		/* #endif */
		height: auto;
		/* #ifdef MP */
		margin-top: calc(78rpx + var(--status-bar-height));
		/* #endif */
	}
	
	.productSort .aside .item {
		height: 100rpx;
		width: 100%;
		font-size: 26rpx;
		color: #424242;
	}
	
	.productSort .aside .item.on {
		background-color: #fff;
		border-left: 4rpx solid #009600;
		width: 100%;
		text-align: center;
		color: #009600;
		font-weight: bold;
	}
	
	.productSort .conter {
		/* #ifdef MP */
		margin: calc(78rpx + var(--status-bar-height)) 0 0 180rpx;
		/* #endif */
		/* #ifdef H5 */
		margin: 78rpx 0 0 180rpx;
		/* #endif */
		padding: 0 14rpx;
		background-color: #fff;
	}
	
	.productSort .conter .listw {
		padding-top: 20rpx;
	}
	
	.productSort .conter .listw .title {
		height: 90rpx;
	}
	
	.productSort .conter .listw .title .line {
		width: 100rpx;
		height: 2rpx;
		background-color: #f0f0f0;
	}
	
	.productSort .conter .listw .title .name {
		font-size: 28rpx;
		color: #333;
		margin: 0 30rpx;
		font-weight: bold;
	}
	
	.productSort .conter .list {
		flex-wrap: wrap;
	}
	
	.productSort .conter .list .item {
		width: 100rpx;
		margin-top: 26rpx;
	}
	
	.productSort .conter .list .item .picture {
		width: 36rpx;
		height: 36rpx;
		border-radius: 50%;
	}
	
	.productSort .conter .list .item .picture image {
		width: 100%;
		height: 100%;
		border-radius: 50%;
		div{
			background-color: #f7f7f7;
		}
	}
	
	.productSort .conter .list .item .name {
		font-size: 24rpx;
		color: #333;
		height: 56rpx;
		line-height: 56rpx;
		width: 120rpx;
		text-align: center;
	}
	
	.productSort .conter .listBox {
		padding: 0px 15px;
	}
	
	.productSort .conter .listBox .list {
		padding: 0 30rpx;
	
	}
	
	.productSort .conter .listBox .list.on {
		border-radius: 14rpx;
		margin-top: 0 !important;
		background-color: #fff;
		padding: 40rpx 0 0 0;
		// margin: 20rpx 0;
		// background-color: #fff;
	}
	
	.productSort .conter .listBox .list .item {
		width: 335rpx;
		background-color: #fff;
		border-radius: 14rpx;
		margin-bottom: 20rpx;
	}
	
	.productSort .conter .listBox .list .item.on {
		width: 100%;
		display: flex;
		padding: 0 24rpx 50rpx 24rpx;
		margin: 0;
		border-radius: 14rpx;
	}
	
	.productSort .conter .listBox .list .item .pictrue {
		position: relative;
		width: 100%;
		height: 335rpx;
	}
	
	.productSort .conter .listBox .list .item .pictrue.on {
		width: 180rpx;
		height: 180rpx;
	}
	
	.productSort .conter .listBox .list .item .pictrue image {
		width: 100%;
		height: 100%;
		border-radius: 20rpx 20rpx 0 0;
	}
	
	.productSort .conter .listBox .list .item .pictrue image.on {
		border-radius: 6rpx;
	}
	
	.productSort .conter .listBox .list .item .text {
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
	
	.productSort .conter .listBox .list .item .text.on {
		width: 256rpx;
		padding: 0 0 0 20rpx;
	}
	
	.productSort .conter .listBox .list .item .text .money {
		font-size: 26rpx;
		font-weight: bold;
		margin-top: 8rpx;
	}
	
	.productSort .conter .listBox .list .item .text .money.on {
		margin-top: 50rpx;
	}
	
	.productSort .conter .listBox .list .item .text .money .num {
		font-size: 34rpx;
	}
	
	.productSort .conter .listBox .list .item .text .vip {
		font-size: 22rpx;
		color: #aaa;
		margin-top: 7rpx;
	}
	
	.productSort .conter .listBox .list .item .text .vip.on {
		margin-top: 12rpx;
	}
	
	.productSort .conter .listBox .list .item .text .vip .vip-money {
		font-size: 24rpx;
		color: #282828;
		font-weight: bold;
	}
	
	.productSort .conter .listBox .list .item .text .vip .vip-money image {
		width: 46rpx;
		height: 21rpx;
		margin-left: 4rpx;
	}
	
	.productSort .conter .noCommodity {
		background-color: #fff;
		padding-bottom: 30rpx;
	}
</style>
