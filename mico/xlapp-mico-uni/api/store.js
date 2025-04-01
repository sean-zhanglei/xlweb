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
	return request.get('store/product/reply/config/' + id,{},{
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
	return request.get('store/product/reply/product/' + id, {},{
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
 * 获取购物车列表
 * @param numType boolean true 购物车数量,false=购物车产品数量
 */
export function getCartCounts(numType,type) {
	return request.get("store/cart/count?numType=" + numType + "&type=" + type);
}
/**
 * 获取购物车列表
 * 
 */
export function getCartList(data) {
	return request.get("store/cart/list", data);
}

/**
 * 修改购物车数量
 * @param int cartId  购物车id
 * @param int number 修改数量
 */
export function changeCartNum(cartId, number) {
	return request.post("store/cart/num", {
		id: cartId,
		number: number
	}, {}, 1);
}
/**
 * 清除购物车
 * @param object ids join(',') 切割成字符串
 */
export function cartDel(ids) {
	if (typeof ids === 'object')
		ids = ids.join(',');
	return request.post('store/cart/delete', {
		ids: ids
	}, {}, 1);
}

/**
 * 购物车重选提交
 * 
 */
export function getResetCart(data) {
	return request.post('store/cart/resetcart', data);
}

/**
 * 获取当前金额能使用的优惠卷
 * @param string price
 * 
 */
export function getCouponsOrderPrice(preOrderNo) {
	return request.get(`store/coupons/order/${preOrderNo}`)
}
