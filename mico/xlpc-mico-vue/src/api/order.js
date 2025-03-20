

import request from '@/utils/request'

/**
 * 订单 列表
 * @param prams
 */
export function orderListApi(params) {
  return request({
    url: '/store/order/list',
    method: 'get',
    params
  })
}

/**
 * 订单 列表 获取各状态数量
 * @param params
 */
export function orderStatusNumApi(params) {
  return request({
    url: '/store/order/status/num',
    method: 'get',
    params
  })
}

/**
 * 订单 列表 数据统计
 * @param params
 */
export function orderListDataApi(params) {
  return request({
    url: '/store/order/list/data',
    method: 'get',
    params
  })
}
/**
 * 订单 删除
 * @param params
 */
export function orderDeleteApi(params) {
  return request({
    url: '/store/order/delete',
    method: 'get',
    params
  })
}

/**
 * 订单 编辑
 * @param prams
 */
export function orderUpdateApi(data, params) {
  return request({
    url: '/store/order/update',
    method: 'post',
    data,
    params
  })
}

/**
 * 订单 记录
 * @param prams
 */
export function orderLogApi(params) {
  return request({
    url: '/store/order/status/list',
    method: 'get',
    params
  })
}

/**
 * 订单 详情
 * @param prams
 */
export function orderDetailApi(params) {
  return request({
    url: '/store/order/info',
    method: 'get',
    params
  })
}

/**
 * 订单 备注
 * @param prams
 */
export function orderMarkApi(params) {
  return request({
    url: '/store/order/mark',
    method: 'post',
    params
  })
}

/**
 * 订单 发货
 * @param prams
 */
export function orderSendApi(data) {
  return request({
    url: '/store/order/send',
    method: 'post',
    data
  })
}

/**
 * 订单 拒绝退款
 * @param prams
 */
export function orderRefuseApi(params) {
  return request({
    url: '/store/order/refund/refuse',
    method: 'get',
    params
  })
}

/**
 * 订单 立即退款
 * @param prams
 */
export function orderRefundApi(params) {
  return request({
    url: '/store/order/refund',
    method: 'get',
    params
  })
}

/**
 * 订单 核销订单
 * @param prams
 */
export function writeUpdateApi(vCode) {
  return request({
    url: `/store/order/writeUpdate/${vCode}`,
    method: 'get'
  })
}

/**
 * 订单 核销码查询待核销订单
 * @param prams
 */
export function writeConfirmApi(vCode) {
  return request({
    url: `/store/order/writeConfirm/${vCode}`,
    method: 'get'
  })
}

/**
 * 订单 统计 头部数据
 */
export function orderStatisticsApi() {
  return request({
    url: `/store/order/statistics`,
    method: 'get'
  })
}

/**
 * 核销订单 月列表数据
 */
export function statisticsDataApi(params) {
  return request({
    url: `/store/order/statisticsData`,
    method: 'get',
    params
  })
}

/**
 * 一键改价
 */
export function updatePriceApi(data) {
  return request({
    url: `/store/order/update/price`,
    method: 'post',
    data
  })
}

/**
 *订单统计详情
 */
export function orderTimeApi(params) {
  return request({
    url: `/store/order/time`,
    method: 'get',
    params
  })
}

/**
 *面单默认配置信息
 */
export function sheetInfoApi() {
  return request({
    url: `/store/order/sheet/info`,
    method: 'get'
  })
}

/**
 *面单默认配置信息
 */
export function getLogisticsInfoApi(params) {
  return request({
    url: `/store/order/getLogisticsInfo`,
    method: 'get',
    params
  })
}

/**
 *视频号物流公司
 */
export function companyGetListApi() {
  return request({
    url: `/system/pay/component/delivery/company/get/list`,
    method: 'get'
  })
}

/**
 *视频号物流公司
 */
export function videoSendApi(data) {
  return request({
    url: `/store/order/video/send`,
    method: 'post',
    data
  })
}

/**
 *打印小票
 */
 export function orderPrint(id) {
  return request({
    url: `/infra/yly/print/${id}`,
    method: 'get',
  })
}
