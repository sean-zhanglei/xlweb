package com.nbug.module.infra.service.wechat;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.wechat.WechatPayInfo;

/**
 *  WechatPayInfoService 接口

 */
public interface WechatPayInfoService extends IService<WechatPayInfo> {

    /**
     * 获取详情（商户订单号）
     * @param outTradeNo 商户订单号
     * @return WechatPayInfo
     */
    WechatPayInfo getByNo(String outTradeNo);
}
