

import request from '@/utils/request'

/**
 * 角色详情
 */
export function getRoleById(pram) {
  return request({
    url: `/system/role/info/${pram.roles}`,
    method: 'GET',
  })
}

/**
 * 菜单
 * @param pram
 */
export function menuListApi() {
  return request({
    url: '/system/login/getMenus',
    method: 'GET'
  })
}
