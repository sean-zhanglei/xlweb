import request from "@/utils/request.js";

/**
 * 门店列表
 * @returns {*}
 */
export function storeListApi(data) {
	return request.post("system/store/list", data, {}, 1);
}

/**
 * 文章分类列表
 * 
*/
export function getArticleCategoryList(){
  return request.get('system/article/category/list',{},{noAuth:true})
}

/**
 * 文章列表
 * @param int cid
 * 
*/
export function getArticleList(cid,data){
  return request.get('system/article/list/' + cid, data,{noAuth:true})
}

/**
 * 文章 热门列表
 * 
*/
export function getArticleHotList(){
  return request.get('system/article/hot/list',{},{noAuth:true});
}

/**
 * 文章 轮播列表
 * 
*/
export function getArticleBannerList(){
  return request.get('system/article/banner/list',{},{noAuth:true})
}

/**
 * 文章详情
 * @param int id 
 * 
*/
export function getArticleDetails(id){
  return request.get('system/article/info',id,{noAuth:true});
}

/**
 * 获取城市信息
 */
export function getCity() {
  return request.get('system/city/list', { }, { noAuth: true });
}