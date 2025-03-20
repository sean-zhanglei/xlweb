

import request from '@/utils/request'


/**
 * @description 电子面单模板
 */
export function exportTempApi(params) {
  return request({
    url: '/system/express/template',
    method: 'get',
    params
  })
}

/**
 * @description 全部物流公司
 */
export function expressAllApi(params) {
  return request({
    url: '/system/express/all',
    method: 'get',
    params
  })
}
