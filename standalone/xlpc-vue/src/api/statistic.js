import request from '@/utils/request'

/**
 * @description 交易数据 今天
 * @param {Object} param data {Object} 传值参数
 */
export function statisticTopTradeApi(params) {
  return request({
    url: '/admin/statistic/trade/top_trade',
    method: 'get',
    params,
  });
}

/**
 * @description 交易概括
 * @param {Object} param data {Object} 传值参数
 */
export function statisticBottomTradeApi(params) {
  return request({
    url: '/admin/statistic/trade/bottom_trade',
    method: 'get',
    params,
  });
}

/**
 * @description 订单统计数量
 * @param {Object} param data {Object} 传值参数
 */
export function getBasic(params) {
  return request({
    url: '/admin/statistic/order/get_basic',
    method: 'get',
    params,
  });
}

/**
 * @description 订单统计折线图
 * @param {Object} param data {Object} 传值参数
 */
export function getTrend(params) {
  return request({
    url: '/admin/statistic/order/get_trend',
    method: 'get',
    params,
  });
}
/**
 * @description 订单来源分析
 * @param {Object} param data {Object} 传值参数
 */
export function getChannel(params) {
  return request({
    url: '/admin/statistic/order/get_channel',
    method: 'get',
    params,
  });
}
/**
 * @description 订单类型分析
 * @param {Object} param data {Object} 传值参数
 */
export function getType(params) {
  return request({
    url: '/admin/statistic/order/get_type',
    method: 'get',
    params,
  });
}

/**
 * @description 账单记录列表
 * @param {Object} param data {Object} 传值参数
 */
export function getRecord(params) {
  return request({
    url: '/admin/statistic/flow/get_record',
    method: 'get',
    params,
  });
}

/**
 * @description 财务管理 -- 资金流水统计
 * @param {Number} param data {Number} 请求参数data
 */
export function getFlowList(params) {
  return request({
    url: `/admin/statistic/flow/get_list`,
    method: 'get',
    params,
  });
}

/**
 * @description 余额统计数量
 * @param {Object} param data {Object} 传值参数
 */
export function getBalanceBasic(params) {
  return request({
    url: '/admin/statistic/balance/get_basic',
    method: 'get',
    params,
  });
}

/**
 * @description 余额统计折线图
 * @param {Object} param data {Object} 传值参数
 */
export function getBalanceTrend(params) {
  return request({
    url: '/admin/statistic/balance/get_trend',
    method: 'get',
    params,
  });
}
/**
 * @description 余额来源分析
 * @param {Object} param data {Object} 传值参数
 */
export function getBalanceChannel(params) {
  return request({
    url: '/admin/statistic/balance/get_channel',
    method: 'get',
    params,
  });
}
/**
 * @description 余额类型分析
 * @param {Object} param data {Object} 传值参数
 */
export function getBalanceType(params) {
  return request({
    url: '/admin/statistic/balance/get_type',
    method: 'get',
    params,
  });
}

/**
 * 积分统计顶部
 * @param {com} data
 */
export function getPointBasic(data) {
  return request({
    url: '/admin/statistic/integral/get_basic',
    method: 'get',
    params: data,
  });
}

/**
 * 积分统计 折线图
 * @param {com} data
 */
export function getPointTrend(data) {
  return request({
    url: '/admin/statistic/integral/get_trend',
    method: 'get',
    params: data,
  });
}

/**
 * @description 积分来源分析
 * @param {Object} param data {Object} 传值参数
 */
export function getPointChannel(params) {
  return request({
    url: '/admin/statistic/integral/get_channel',
    method: 'get',
    params,
  });
}
/**
 * @description 积分消耗分析
 * @param {Object} param data {Object} 传值参数
 */
export function getPointType(params) {
  return request({
    url: '/admin/statistic/integral/get_type',
    method: 'get',
    params,
  });
}
