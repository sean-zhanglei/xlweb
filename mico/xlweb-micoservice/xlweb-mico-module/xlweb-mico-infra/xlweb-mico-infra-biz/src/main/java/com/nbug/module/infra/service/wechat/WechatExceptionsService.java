package com.nbug.module.infra.service.wechat;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.wechat.WechatExceptions;

/**
 *  微信异常服务类

 */
public interface WechatExceptionsService extends IService<WechatExceptions> {

    /**
     * 删除历史日志
     */
    void autoDeleteLog();
}
