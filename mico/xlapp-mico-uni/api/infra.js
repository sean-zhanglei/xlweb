import request from "@/utils/request.js";
import wechat from "@/libs/wechat.js";

/**
 * wechat
 */

/**
 * 获取微信公众号js配置
 * @returns {*}
 */
export function getWechatConfig() {
  return request.get("wechat/config",{ url: encodeURIComponent(wechat.signLink()) },{ noAuth: true });
}

/**
 * 获取微信sdk配置
 * @returns {*}
 */
export function wechatAuth(code, spread) {
	var reg=/^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 ，判断正整数用/^[1-9]+[0-9]*]*$/
	spread = reg.test(spread) ? spread : 0;
  return request.get(
    "wechat/authorize/login?code=" + code + "&spread_spid=" + spread, {},
    { noAuth: true }
  );
}


/**
 * 小程序用户登录
 * @param data object 小程序用户登录信息
 */
export function login(code,data) {
  return request.post("wechat/authorize/program/login?code="+code, data, { noAuth : true });
}

/**
 * 获取关注海报
 * @returns {*}
 */
export function follow() {
  return request.get("wechat/follow", {}, { noAuth: true });
}


/**
 * 微信（公众号，小程序）绑定手机号
 * @param {Object} data
 */
export function getUserPhone(data){
	return request.post('wechat/register/binding/phone',data,{noAuth : true});
}

/**
 * APP微信登录
 * @param {Object} data
 */
export function appAuth(data) {
	return request.post("wechat/authorize/app/login", data, { noAuth : true });
}


/**
 * 获取小程序直播列表
 */
export function getLiveList(page,limit) {
  return request.get('wechat/live', { page, limit}, { noAuth: true });
}


/**
 * 获取订阅消息id
 */
export function getTemlIds(data)
{
  return request.get('wechat/program/my/temp/list', data , { noAuth:true});
}

/**
 * ios
 */

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

/**
 * infra
 */

/**
 * 获取图片base64
 * @retins {*}
 * */
export function imageBase64(image) {
  return request.post("infra/qrcode/base64",image,{ noAuth: true },1);
}

/**
 * 首页 获取客服地址
 * @returns {*}
 */
export function kefuConfig() {
  return request.get("infra/config", {}, { noAuth: true });
}

/**
 * 获取小程序二维码
 */
export function getQrcode(data) {
  return request.post('infra/qrcode/get',data,{ noAuth: true });
}

/**
 * 获取登录授权login
 * 
*/
export function getLogo()
{
  return request.get('infra/wechat/getLogo', {}, { noAuth : true});
}


/**
 * 将字符串 转base64
 * @param object data
 */
export function qrcodeApi(data) {
	return request.post('infra/qrcode/str2base64', data, {}, 1);
}