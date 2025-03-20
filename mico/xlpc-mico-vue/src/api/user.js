

import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/login/account',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/system/login/getAdminInfoByToken',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/system/login/logout',
    method: 'get'
  })
}

/**
 * 会员管理 列表
 * @param pram
 */
export function userListApi(params) {
  return request({
    url: `/user/list`,
    method: 'get',
    params
  })
}

/**
 * 会员管理 修改
 * @param pram
 */
export function userUpdateApi(params, data) {
  return request({
    url: `/user/update`,
    method: 'post',
    params,
    data
  })
}

/**
 * 会员管理等级 修改
 * @param pram
 */
 export function userLevelUpdateApi( data) {
  return request({
    url: `/user/update/level`,
    method: 'post',
    data
  })
}

/**
 * 会员管理 详情
 * @param pram
 */
export function userInfoApi(params) {
  return request({
    url: `/user/info`,
    method: 'get',
    params
  })
}

/**
 * 会员管理 账户详情
 * @param pram
 */
export function infobyconditionApi(params) {
  return request({
    url: `/user/infobycondition`,
    method: 'get',
    params
  })
}

/**
 * 会员管理 账户详情top数据
 * @param pram
 */
export function topdetailApi(params) {
  return request({
    url: `/user/topdetail`,
    method: 'get',
    params
  })
}

/**
 * 会员管理 批量设置分组
 * @param pram
 */
export function groupPiApi(params) {
  return request({
    url: `/user/group`,
    method: 'post',
    params
  })
}

/**
 * 会员管理 批量设置标签
 * @param pram
 */
export function tagPiApi(params) {
  return request({
    url: `/user/tag`,
    method: 'post',
    params
  })
}

/**
 * 会员管理 积分余额
 * @param pram
 */
export function foundsApi(params) {
  return request({
    url: `/user/operate/founds`,
    method: 'get',
    params
  })
}

/**
 * 会员管理 删除
 * @param pram
 */
export function userDeleteApi(params) {
  return request({
    url: `/user/delete`,
    method: 'get',
    params
  })
}

/**
 * 会员等级 列表
 * @param pram
 */
export function levelListApi() {
  return request({
    url: `/user/level/list`,
    method: 'get'
  })
}

/**
 * 会员等级 新增
 * @param pram
 */
export function levelSaveApi(data) {
  return request({
    url: `/user/level/save`,
    method: 'post',
    data
  })
}

/**
 * 会员等级 编辑
 *  @param pram
 */
 export function levelUpdateApi(params, data) {
  return request({
    url: `/user/level/update/${params}`,
    method: 'post',
    // params,
    data
  })
}

/**
 * 会员等级 详情
 * @param pram
 */
export function levelInfoApi(params) {
  return request({
    url: `/user/level/info`,
    method: 'get',
    params
  })
}

/**
 * 会员等级 删除
 * @param pram
 */
 export function levelDeleteApi(id) {
  return request({
    url: `/user/level/delete/${id}`,
    method: 'post'
  })
}

/**
 * 会员等级 是否显示
 * @param pram
 */
export function levelUseApi(data) {
  return request({
    url: `/user/level/use`,
    method: 'post',
    data
  })
}

/**
 * 会员标签 列表
 * @param pram
 */
export function tagListApi(params) {
  return request({
    url: `/user/tag/list`,
    method: 'get',
    params
  })
}

/**
 * 会员标签 新增
 * @param pram
 */
export function tagSaveApi(data) {
  return request({
    url: `/user/tag/save`,
    method: 'post',
    data
  })
}

/**
 * 会员标签 编辑
 * @param pram
 */
export function tagUpdateApi(params, data) {
  return request({
    url: `/user/tag/update`,
    method: 'post',
    params,
    data
  })
}

/**
 * 会员标签 详情
 * @param pram
 */
export function tagInfoApi(params) {
  return request({
    url: `/user/tag/info`,
    method: 'get',
    params
  })
}

/**
 * 会员标签 删除
 * @param pram
 */
export function tagDeleteApi(params) {
  return request({
    url: `/user/tag/delete`,
    method: 'get',
    params
  })
}

/**
 * 会员分组 列表
 * @param pram
 */
export function groupListApi(params) {
  return request({
    url: `/user/group/list`,
    method: 'get',
    params
  })
}

/**
 * 会员分组 新增
 * @param pram
 */
export function groupSaveApi(data) {
  return request({
    url: `/user/group/save`,
    method: 'post',
    data
  })
}

/**
 * 会员分组 编辑
 * @param pram
 */
export function groupUpdateApi(params, data) {
  return request({
    url: `/user/group/update`,
    method: 'post',
    params,
    data
  })
}

/**
 * 会员分组 详情
 * @param pram
 */
export function groupInfoApi(params) {
  return request({
    url: `/user/group/info`,
    method: 'get',
    params
  })
}

/**
 * 会员分组 删除
 * @param pram
 */
export function groupDeleteApi(params) {
  return request({
    url: `/user/group/delete`,
    method: 'get',
    params
  })
}

/**
 *获取登录页图片
 */
export function getLoginPicApi() {
  return request({
    url: `/system/login/getLoginPic`,
    method: 'get'
  })
}

/**
 * @description 验证码
 */
export function captchaApi() {
  return request({
    url: `/infra/validate/code/get`,
    method: 'get'
  })
}

/**
 * @description 修改上级推广人
 */
export function updateSpreadApi(data) {
  return request({
    url: `/user/update/spread`,
    method: 'post',
    data
  })
}

/**
 * @description 修改手机号
 */
export function updatePhoneApi(params) {
  return request({
    url: `/user/update/phone`,
    method: 'get',
    params
  })
}
