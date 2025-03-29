import request from "@/utils/request.js";
import {
	toLogin,
	checkLogin
} from '../libs/login';

/**
 * 分享
 * @returns {*}
 */
export function getShare() {
  return request.get("user/index/share", {}, { noAuth: true });
}


/**
 * 获取图片base64
 * @retins {*}
 * */
export function imageBase64(image) {
  return request.post("qrcode/base64",image,{ noAuth: true },1);
}

/**
 * 自动复制口令功能
 * @returns {*}
 */
export function copyWords() {
  return request.get("copy_words", {}, { noAuth: true });
}

/**
 * 首页 获取客服地址
 * @returns {*}
 */
export function kefuConfig() {
  return request.get("config", {}, { noAuth: true });
}


/**
 * 苹果登录
 * @param {Object} data
 */
export function appleLogin(data) {
	return request.post("ios/login", data, { noAuth : true });
}


/**
 * 苹果绑定手机号
 * @param {Object} data
 */
export function iosBinding(data) {
	return request.post("ios/binding/phone", data, { noAuth : true });
}