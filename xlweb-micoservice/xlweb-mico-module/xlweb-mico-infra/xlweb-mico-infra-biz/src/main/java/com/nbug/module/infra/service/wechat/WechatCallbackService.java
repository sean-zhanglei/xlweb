package com.nbug.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.model.wechat.WechatCallback;

/**
 * 微信小程序回调Service

 */
public interface WechatCallbackService extends IService<WechatCallback> {

    /**
     * 微信回调
     * @param request request
     * @return String
     */
    String callback(String request);
}
