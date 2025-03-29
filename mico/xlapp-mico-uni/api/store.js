import request from "@/utils/request.js";

/**
 * 获取产品详情
 * @param int id
 * 
 */
export function getProductDetail(id, type) {
	return request.get('store/product/detail/' + id + '?type=' + type, {}, {
		noAuth: true
	});
}

/**
 * 产品分享二维码 推广员
 * @param int id
 */
// #ifndef MP
export function getProductCode(id) {
	return request.get('store/product/code/' + id, {});
}
// #endif
// #ifdef MP
export function getProductCode(id) {
	return request.get('store/product/code/' + id, {
		user_type: 'routine'
	});
}
// #endif

/**
 * 添加收藏
 * @param int id
 * @param string category product=普通产品,product_seckill=秒杀产品
 */
export function collectAdd(id, category) {
	return request.post('store/collect/add', {
		id: id,
		'category': category === undefined ? 'product' : category
	});
}

/**
 * 取消收藏产品
 * @param int id
 */
export function collectDel(proId) {
	return request.post(`store/collect/cancel/${proId}`);
}


/**
 * 删除收藏产品
 * @param string id
 */
export function collectDelete(ids) {
	return request.post(`store/collect/delete`,ids);
}

/**
 * 购车添加
 * 
 */
export function postCartAdd(data) {
	return request.post('store/cart/save', data, {});
}

/**
 * 获取分类列表
 * 
 */
export function getCategoryList() {
	return request.get('store/product/category', {}, {
		noAuth: true
	});
}

/**
 * 获取产品列表
 * @param object data
 */
export function getProductslist(data) {
	return request.get('store/product/list', data, {
		noAuth: true
	});
}

/**
 商品购买记录 TOP 10
 * @param object {}
 */
export function getProductsTopBuy10list(productId) {
	return request.get('store/product/top10buy-list/' + productId, {}, {
		noAuth: true
	});
}

/**
 * 获取推荐产品
 * 
 */
export function getProductHot(page, limit) {
	return request.get("store/product/hot", {
		page: page === undefined ? 1 : page,
		limit: limit === undefined ? 4 : limit
	}, {
		noAuth: true
	});
}
/**
 * 批量收藏
 * 
 * @param object id  产品编号 join(',') 切割成字符串
 * @param string category 
 */
export function collectAll(id, category) {
	return request.post('store/collect/all', {
		id: id,
		category: category === undefined ? 'product' : category
	});
}

/**
 * 首页产品的轮播图和产品信息
 * @param int type 
 * 
 */
export function getGroomList(type, data) {
	return request.get('user/index/product/' + type, data, {
		noAuth: true
	});
}

/**
 * 获取收藏列表
 * @param object data
 */
export function getCollectUserList(data) {
	return request.get('user/collect/user', data)
}

/**
 * 获取产品评论
 * @param int id
 * @param object data
 * 
 */
export function getReplyList(id, data) {
	return request.get('store/reply/list/' + id, data,{
		noAuth: true
	})
}

/**
 * 产品评价数量和好评度
 * @param int id
 */
export function getReplyConfig(id) {
	return request.get('store/reply/config/' + id,{},{
		noAuth: true
	});
}


/**
 * 优品推荐
 * @param object data
 */
export function getProductGood() {
	return request.get('store/product/good',{},{ noAuth : true});
}

/**
 * 详情页产品评论
 * @param int id
 * @param object data
 * 
 */
export function getReplyProduct(id) {
	return request.get('store/reply/product/' + id, {},{
		noAuth: true
	})
}

/**
 * 优惠券列表
 * @param object data
*/
export function getCoupons(data){
  return request.get('store/coupons/list',data,{noAuth:true})
}

/**
 * 我的优惠券
 * @param int types 0全部  1未使用 2已使用
*/
export function getUserCoupons(data){
  return request.get('store/coupon/list',data)
}