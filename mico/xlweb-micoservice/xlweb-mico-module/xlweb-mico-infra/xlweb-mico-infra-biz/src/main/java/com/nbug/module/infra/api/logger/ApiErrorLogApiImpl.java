package com.nbug.module.infra.api.logger;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.nbug.module.infra.service.logger.ApiErrorLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static com.nbug.mico.common.pojo.CommonResult.error;
import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public CommonResult<Boolean> createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> createApiErrorLogAsync(ApiErrorLogCreateReqDTO createDTO){
        try {
            apiErrorLogService.createApiErrorLogAsync(createDTO);
            return success(true);
        } catch (Exception e) {
            return error(INTERNAL_SERVER_ERROR);
        }
    }
}
