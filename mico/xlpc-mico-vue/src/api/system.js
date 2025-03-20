

import request from '@/utils/request'
/**
 * @description 附件分类 -- 所有分类
 */
export function formatLstApi(data) {
  return request.get({
    url: '/system/product/save',
    method: 'POST',
    data
  })
}
/**
 * @description 附件分类 -- 添加分类
 */
export function attachmentCreateApi() {
  return request.get(`infra/attachment/category/create/form`)
}
/**
 * @description 附件分类 -- 编辑分类
 */
export function attachmentUpdateApi(id) {
  return request.get(`infra/attachment/category/update/form/${id}`)
}
/**
 * @description 附件分类 -- 删除分类
 */
export function attachmentDeleteApi(id) {
  return request.delete(`infra/attachment/category/delete/${id}`)
}
/**
 * @description 图片列表
 */
export function attachmentListApi(data) {
  return request.get(`infra/attachment/lst`, data)
}
/**
 * @description 图片列表 -- 删除
 */
export function picDeleteApi(id) {
  return request.delete(`infra/attachment/delete`, id)
}
/**
 * @description 图片列表 -- 修改附件分类
 */
export function categoryApi(ids, attachment_category_id) {
  return request.post(`infra/attachment/category`, { ids, attachment_category_id })
}
