import request from "@/utils/request.js";
import {
	toLogin,
	checkLogin
} from '../libs/login';


/**
 * 自动复制口令功能  暂时无效
 * @returns {*}
 */
export function copyWords() {
  return request.get("copy_words", {}, { noAuth: true });
}


