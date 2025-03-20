

import request from '@/utils/request'

export function groupDelete(pram) {
  const data = {
    id: pram.id
  }
  return request({
    url: '/system/group/delete',
    method: 'GET',
    params: data
  })
}

export function groupInfo(pram) {
  const data = {
    id: pram.id
  }
  return request({
    url: '/system/group/info',
    method: 'GET',
    params: data
  })
}

export function groupList(pram) {
  const data = {
    keywords: pram.keywords,
    page: pram.page,
    limit: pram.limit
  }
  return request({
    url: '/system/group/list',
    method: 'GET',
    params: data
  })
}

export function groupDataList(pram) {
  const data = {
    gid:pram.gid,
    keywords: pram.keywords,
    page: pram.page,
    limit: pram.limit
  }
  return request({
    url: '/system/group/data/list',
    method: 'GET',
    params: data
  })
}

export function groupSave(pram) {
  const data = {
    formId: pram.formId,
    info: pram.info,
    name: pram.name
  }
  return request({
    url: '/system/group/save',
    method: 'POST',
    params: data
  })
}

export function groupEdit(pram) {
  const data = {
    formId: pram.formId,
    info: pram.info,
    name: pram.name,
    id: pram.id
  }
  return request({
    url: '/system/group/update',
    method: 'POST',
    params: data
  })
}

/**
 * @description 页面设计 获取数据
 */
export function designListApi() {
  return request.get(`/system/page/layout/index`)
}

/**
 * @description 页面设计商品Tab 获取数据
 */
 export function goodDesignList(pram) {
  const data = {
    gid: pram.gid,
  }
  return request({
    url: '/system/group/data/list',
    method: 'GET',
    params: data
  })
}

/**
 * @description 页面设计 保存
 */
export function SaveDataApi(data, url) {
  return request({
    url: url,
    method: 'POST',
    data
  })
}

/**
 * @description 获取配置
 */
 export function getDataApi(data) {
  return request({
    url: '/system/page/layout/category/config',
    method: 'GET',
    data
  })
}

/**
 * @description 保存设置
 */
 export function themeSave(params) {
  return request({
    url: `/system/config/saveuniq`,
    method: 'post',
    params
  })
}

/**
 * @description 获取设置
 */
 export function getTheme(params) {
  return request({
    url: `/system/config/getuniq`,
    method: 'get',
    params
  })
}
