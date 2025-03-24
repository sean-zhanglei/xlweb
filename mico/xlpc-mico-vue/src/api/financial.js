

import request from '@/utils/request'

/**
 * 提现申请 列表
 * @param pram
 */
export function applyListApi(params) {
  return request({
    url: '/user/finance/apply/list',
    method: 'get',
    params
  })
}

/**
 * 提现申请 金额
 * @param pram
 */
export function applyBalanceApi(params) {
  return request({
    url: '/user/finance/apply/balance',
    method: 'post',
    params
  })
}

/**
 * 提现申请 修改
 * @param pram
 */
export function applyUpdateApi(params) {
  return request({
    url: '/user/finance/apply/update',
    method: 'post',
    params
  })
}

/**
 * 提现申请 审核
 * @param pram
 */
export function applyStatusApi(params, data) {
  return request({
    url: '/user/finance/apply/apply',
    method: 'post',
    params,
    data
  })
}

/**
 * 充值 列表
 * @param pram
 */
export function topUpLogListApi(params) {
  return request({
    url: '/user/recharge/list',
    method: 'get',
    params
  })
}

/**
 * 充值 金额
 * @param pram
 */
export function balanceApi() {
  return request({
    url: '/user/recharge/balance',
    method: 'post'
  })
}

/**
 * 充值 删除
 * @param pram
 */
export function topUpLogDeleteApi(params) {
  return request({
    url: '/user/recharge/delete',
    method: 'get',
    params
  })
}

/**
 * 充值 退款
 * @param pram
 */
export function refundApi(data) {
  return request({
    url: '/user/recharge/refund',
    method: 'post',
    data
  })
}

/**
 * 资金监控 列表
 * @param pram
 */
export function monitorListApi(params) {
  return request({
    url: '/user/finance/founds/monitor/list',
    method: 'get',
    params
  })
}

/**
 * 资金监控 明细类型
 * @param pram
 */
export function monitorListOptionApi() {
  return request({
    url: `/user/finance/founds/monitor/list/option`,
    method: 'get'
  })
}

/**
 * 佣金记录 列表
 * @param pram
 */
export function brokerageListApi(params) {
  return request({
    url: '/user/finance/founds/monitor/brokerage/record',
    method: 'get',
    params
  })
}

/**
 * @description 财务管理 -- 余额列表
 * @param {Number} param data {Number} 请求参数data
 */
export function getBalanceList(data) {
  return request({
    url: `/system/statistic/balance/list`,
    method: 'get',
    params: data,
  });
}
