package com.nbug.module.system.service.impl;

import com.nbug.mico.common.model.logger.LoginLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.system.dal.LoginLogDao;
import com.nbug.module.system.service.LoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 登录日志 Service 实现
 */
@Service
@Validated
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    public PageResult<LoginLog> getLoginLogPage(LoginLog pageReqVO) {
        return loginLogDao.selectPage(pageReqVO);
    }

    @Override
    public void createLoginLog(LoginLog reqDTO) {
        loginLogDao.insert(reqDTO);
    }

}
