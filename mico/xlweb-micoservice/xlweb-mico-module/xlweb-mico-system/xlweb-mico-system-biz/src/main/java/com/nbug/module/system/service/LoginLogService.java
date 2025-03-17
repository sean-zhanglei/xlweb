package com.nbug.module.system.service;


import com.nbug.mico.common.model.logger.LoginLog;
import com.nbug.mico.common.pojo.PageResult;

import javax.validation.Valid;

/**
 * 登录日志 Service 接口
 */
public interface LoginLogService {

    /**
     * 获得登录日志分页
     *
     * @param pageReqVO 分页条件
     * @return 登录日志分页
     */
    PageResult<LoginLog> getLoginLogPage(LoginLog pageReqVO);

    /**
     * 创建登录日志
     *
     * @param req 日志信息
     */
    void createLoginLog(@Valid LoginLog req);

}
