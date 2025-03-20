

import request from '@/utils/request'


/**
 * @description 分销设置 -- 详情
 */
export function configApi() {
  return request({
    url: '/system/retail/spread/manage/get',
    method: 'get'
  })
}

/**
 * @description 分销设置 -- 表单提交
 */
export function configUpdateApi(data) {
  return request({
    url: '/system/retail/spread/manage/set',
    method: 'post',
    data
  })
}

/**
 * @description 分销员 -- 列表
 */
export function promoterListApi(params) {
  return request({
    url: '/system/retail/list',
    method: 'get',
    params
  })
}

/**
 * @description 推广人 -- 列表
 */
export function spreadListApi( params, data) {
  return request({
    url: '/system/retail/spread/userlist',
    method: 'post',
    params,
    data
  })
}

/**
 * @description 推广人订单 -- 列表
 */
export function spreadOrderListApi(params, data) {
  return request({
    url: '/system/retail/spread/orderlist',
    method: 'post',
    params,
    data
  })
}

/**
 * @description 推广人 -- 清除上级推广人
 */
export function spreadClearApi(id) {
  return request({
    url: `/system/retail/spread/clean/${id}`,
    method: 'get'
  })
}

/**
 * @description 分销统计
 */
export function spreadStatisticsApi(params) {
  return request({
    url: `/system/retail/statistics`,
    method: 'get',
    params
  })
}
