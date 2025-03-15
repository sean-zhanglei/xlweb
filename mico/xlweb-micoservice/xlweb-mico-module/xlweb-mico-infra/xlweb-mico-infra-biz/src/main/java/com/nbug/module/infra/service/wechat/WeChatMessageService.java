package com.nbug.module.infra.service.wechat;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户中心 服务类

 */
public interface WeChatMessageService {
    String init(HttpServletRequest request);
}
