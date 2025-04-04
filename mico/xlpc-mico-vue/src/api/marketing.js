

import request from '@/utils/request'

/**
 * 优惠券 列表
 * @param pram
 */
export function marketingListApi(params) {
  return request({
    url: '/store/coupon/list',
    method: 'get',
    params
  })
}

/**
 * 用户管理 优惠券列表
 * @param pram
 */
export function marketingSendApi(params) {
  return request({
    url: '/store/coupon/send/list',
    method: 'get',
    params
  })
}

/**
 * 优惠券 详情
 * @param pram
 */
export function couponInfoApi(params) {
  return request({
    url: '/store/coupon/info',
    method: 'post',
    params
  })
}

/**
 * 优惠券 发送
 * @param pram
 */
export function couponUserApi(params) {
  return request({
    url: '/store/coupon/user/receive',
    method: 'post',
    params
  })
}

/**
 * 优惠券 发送
 * @param pram
 */
export function couponSaveApi(data) {
  return request({
    url: '/store/coupon/save',
    method: 'post',
    data
  })
}

/**
 * 优惠券 修改状态
 * @param pram
 */
export function couponIssueStatusApi(params) {
  return request({
    url: '/store/coupon/update/status',
    method: 'post',
    params
  })
}

/**
 * 优惠券 删除
 * @param pram
 */
export function couponDeleteApi(params) {
  return request({
    url: '/store/coupon/delete',
    method: 'post',
    params
  })
}

/**
 * 会员领取记录 列表
 * @param pram
 */
export function couponUserListApi(params) {
  return request({
    url: '/store/coupon/user/list',
    method: 'get',
    params
  })
}

/**
 * 积分日志 列表
 * @param pram
 */
export function integralListApi(params, data) {
  return request({
    url: '/user/integral/list',
    method: 'post',
    params,
    data
  })
}

/**
 * 秒杀配置 列表
 * @param pram
 */
export function seckillListApi(params) {
  return request({
    url: '/store/seckill/manger/list',
    method: 'get',
    params
  })
}

/**
 * 秒杀配置 详情
 * @param pram
 */
export function seckillInfoApi(params) {
  return request({
    url: '/store/seckill/manger/info',
    method: 'get',
    params
  })
}

/**
 * 秒杀配置 新增
 * @param pram
 */
export function seckillSaveApi(data) {
  return request({
    url: '/store/seckill/manger/save',
    method: 'post',
    data
  })
}

/**
 * 秒杀配置 修改
 * @param pram
 */
export function seckillUpdateApi(params, data) {
  return request({
    url: '/store/seckill/manger/update',
    method: 'post',
    params,
    data
  })
}

/**
 * 秒杀配置 删除
 * @param pram
 */
export function seckillDeleteApi(params) {
  return request({
    url: '/store/seckill/manger/delete',
    method: 'get',
    params
  })
}


/**
 * 秒杀商品 列表
 * @param pram
 */
export function seckillStoreListApi(params) {
  return request({
    url: '/store/seckill/list',
    method: 'get',
    params
  })
}

/**
 * 秒杀商品 详情
 * @param pram
 */
export function seckillStoreInfoApi(params) {
  return request({
    url: '/store/seckill/info',
    method: 'get',
    params
  })
}

/**
 * 秒杀商品 新增
 * @param pram
 */
export function seckillStoreSaveApi(data) {
  return request({
    url: '/store/seckill/save',
    method: 'post',
    data
  })
}

/**
 * 秒杀商品 修改
 * @param pram
 */
export function seckillStoreUpdateApi(params, data) {
  return request({
    url: '/store/seckill/update',
    method: 'post',
    params,
    data
  })
}

/**
 * 秒杀商品 删除
 * @param pram
 */
export function seckillStoreDeleteApi(params) {
  return request({
    url: '/store/seckill/delete',
    method: 'get',
    params
  })
}

/**
 * 秒杀商品 修改状态
 */
export function seckillStoreStatusApi(params) {
  return request({
    url: '/store/seckill/update/status',
    method: 'post',
    params
  })
}

/**
 * 秒杀配置 修改状态
 */
export function seckillConfigStatusApi(id,params) {
  return request({
    url: `/store/seckill/manger/update/status/${id}`,
    method: 'post',
    params
  })
}

/**
 * 砍价商品 列表
 */
export function bargainListApi(params) {
  return request({
    url: `/store/bargain/list`,
    method: 'get',
    params
  })
}

/**
 * 砍价商品 新增
 */
export function bargainSaveApi(data) {
  return request({
    url: `/store/bargain/save`,
    method: 'POST',
    data
  })
}

/**
 * 砍价商品 详情
 */
export function bargainInfoApi(params) {
  return request({
    url: `/store/bargain/info`,
    method: 'get',
    params
  })
}

/**
 * 砍价商品 编辑
 */
export function bargainUpdateApi(params, data) {
  return request({
    url: `/system//store/bargain/update`,
    method: 'post',
    params,
    data
  })
}

/**
 * 砍价商品 删除
 */
export function bargainDeleteApi(params) {
  return request({
    url: `/store/bargain/delete`,
    method: 'get',
    params
  })
}

/**
 * 砍价列表 详情
 */
export function bargainOrderPinkApi(id) {
  return request({
    url: `/store/bargain/bargain_list/${id}`,
    method: 'get'
  })
}

/**
 * 砍价列表 列表
 */
export function bargainListListApi(params) {
  return request({
    url: `/store/bargain/bargain_list`,
    method: 'get',
    params
  })
}

/**
 * 拼团商品 修改拼团状态
 */
export function bargainStatusApi(params) {
  return request({
    url: `/store/bargain/update/status`,
    method: 'post',
    params
  })
}

/**
 * 拼团商品 列表
 */
export function combinationListApi(params) {
  return request({
    url: `/store/combination/list`,
    method: 'get',
    params
  })
}

/**
 * 拼团商品 删除
 */
export function combinationDeleteApi(params) {
  return request({
    url: `/store/combination/delete`,
    method: 'get',
    params
  })
}

/**
 * 拼团商品 新增
 */
export function combinationSaveApi(data) {
  return request({
    url: `/store/combination/save`,
    method: 'post',
    data
  })
}

/**
 * 拼团商品 修改
 */
export function combinationUpdateApi(params,data) {
  return request({
    url: `/store/combination/update`,
    method: 'post',
    params,
    data
  })
}

/**
 * 拼团商品 详情
 */
export function combinationInfoApi(params) {
  return request({
    url: `/store/combination/info`,
    method: 'get',
    params
  })
}

/**
 * 拼团商品 修改拼团状态
 */
export function combinationStatusApi(params) {
  return request({
    url: `/store/combination/update/status`,
    method: 'post',
    params
  })
}

/**
 * 拼团列表 列表
 */
export function combineListApi(params) {
  return request({
    url: `/store/combination/combine/list`,
    method: 'get',
    params
  })
}

/**
 * 拼团列表 统计
 */
export function combineStatisticsApi(params) {
  return request({
    url: `/store/combination/statistics`,
    method: 'get',
    params
  })
}

/**
 * 拼团列表 详情
 */
export function combineOrderPinkApi(id) {
  return request({
    url: `/store/combination/order_pink/${id}`,
    method: 'get'
  })
}

/**
 * 砍价 导出
 */
export function exportBargainApi(params) {
  return request({
    url: `/system/export/excel/bargain/product`,
    method: 'get',
    params
  })
}

/**
 * 拼团 导出
 */
export function exportcombiantionApi(params) {
  return request({
    url: `/system/export/excel/combiantion/product`,
    method: 'get',
    params
  })
}

/**
 * 视频号 草稿列表
 */
export function draftListApi(params) {
  return request({
    url: `/system/pay/component/product/draft/list`,
    method: 'get',
    params
  })
}


/**
 * 视频号 商品列表
 */
export function videoProductListApi(params) {
  return request({
    url: `/system/pay/component/product/list`,
    method: 'get',
    params
  })
}

/**
 * 视频号 类目
 */
export function catListApi(params) {
  return request({
    url: `/system/pay/component/cat/get/list`,
    method: 'get'
  })
}

/**
 * 视频号 添加
 */
export function videoAddApi(data) {
  return request({
    url: `/system/pay/component/product/add`,
    method: 'post',
    data
  })
}

/**
 * 视频号 编辑
 */
export function videoUpdateApi(data) {
  return request({
    url: `/system/pay/component/product/update`,
    method: 'post',
    data
  })
}

/**
 * 视频号 上架
 */
export function videoUpApi(proId) {
  return request({
    url: `/system/pay/component/product/listing/${proId}`,
    method: 'post'
  })
}

/**
 * 视频号 下架
 */
export function videoDownApi(proId) {
  return request({
    url: `/system/pay/component/product/delisting/${proId}`,
    method: 'post'
  })
}

/**
 * 视频号 删除
 */
export function videoDelApi(proId) {
  return request({
    url: `/system/pay/component/product/delete/${proId}`,
    method: 'post'
  })
}

/**
 * 视频号 草稿商品详情
 */
export function draftInfoApi(id) {
  return request({
    url: `/system/pay/component/product/draft/get/${id}`,
    method: 'get'
  })
}

/**
 * 视频号 草稿商品编辑
 */
export function draftUpdateApi(data) {
  return request({
    url: `/system/pay/component/product/update`,
    method: 'post',
    data
  })
}
