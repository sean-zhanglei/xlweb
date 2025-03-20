

import request from '@/utils/request'

export function getFormConfigInfo(pram) {
  const data = {
    id: pram.id
  }
  return request({
    url: '/system/form/temp/info',
    method: 'GET',
    params: data
  })
}

export function getFormConfigList(pram) {
  const data = {
    keywords: pram.keywords,
    page: pram.page,
    limit: pram.limit
  }
  return request({
    url: '/system/form/temp/list',
    method: 'GET',
    params: data
  })
}

export function getFormConfigSave(pram) {
  const data = {
    content: pram.content,
    info: pram.info,
    name: pram.name
  }
  return request({
    url: '/system/form/temp/save',
    method: 'POST',
    data: data
  })
}

export function getFormConfigEdit(pram) {
  const params = { id: pram.id }
  const data = {
    content: pram.content,
    info: pram.info,
    name: pram.name
  }
  return request({
    url: '/system/form/temp/update',
    method: 'POST',
    params: params,
    data: data
  })
}

/**
 * 系统通知列表
 * @param pram
 */
export function notificationListApi(pram) {
  const data = {
    sendType: pram.sendType
    //发送类型（1：通知会员，2：通知平台）
  }
  return request({
    url: '/infra/notification/list',
    method: 'GET',
    params: data
  })
}

/**
 * 小程序订阅模板开关
 * @param pram
 */
 export function notificationRoutine(id) {
  return request({
    url: `/infra/notification/routine/switch/${id}`,
    method: 'post',
  })
}

/**
 * 公众号模板开关
 * @param pram
 */
 export function notificationWechat(id) {
  return request({
    url: `/infra/notification/wechat/switch/${id}`,
    method: 'post',
  })
}

/**
 * 发送短信开关
 * @param pram
 */
 export function notificationSms(id) {
  return request({
    url: `/infra/notification/sms/switch/${id}`,
    method: 'post',
  })
}

/**
 * 通知详情
 * @param pram
 */
 export function notificationDetail(param) {
   let data = {
    detailType:param.type,
    id:param.id
   };
  return request({
    url: `/infra/notification/detail`,
    method: 'get',
    params:data
  })
}

/**
 * 修改通知
 * @param pram
 */
 export function notificationUpdate(param) {
  let data = {
   detailType:param.type,
   id:param.id,
   status:param.status,
   tempId:param.tempId
  };
 return request({
   url: `/infra/notification/update`,
   method: 'post',
   data
 })
}
