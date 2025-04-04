

import request from '@/utils/request'

/**
 * 查询待生成代码列表
 * @param pram
 */
export function getCodegenList(pram) {
  const data = {
    page: pram.page,
    limit: pram.limit,
    tableName: pram.tableName
  }
  return request({
    url: '/codegen/list',
    method: 'GET',
    params: data
  })
}

