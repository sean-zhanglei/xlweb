package com.nbug.module.system.api.logger;

import com.nbug.mico.common.model.logger.LoginLog;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.LoginLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public CommonResult<Boolean> createLoginLog(LoginLog reqDTO) {
        loginLogService.createLoginLog(reqDTO);
        return success(true);
    }

}
