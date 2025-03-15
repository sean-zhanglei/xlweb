package com.nbug.module.system.api.logger;

import com.nbug.mico.common.model.logger.OperateLog;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.system.service.OperateLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OperateLogApiImpl implements OperateLogApi {

    @Resource
    private OperateLogService operateLogService;

    @Override
    public CommonResult<Boolean> createOperateLog(OperateLog createReqDTO) {
        operateLogService.createOperateLog(createReqDTO);
        return success(true);
    }

    @Override
    public CommonResult<PageResult<OperateLog>> getOperateLogPage(OperateLog pageReqDTO) {
        PageResult<OperateLog> operateLogPage = operateLogService.getOperateLogPage(pageReqDTO);
        return success(operateLogPage);
    }

}
