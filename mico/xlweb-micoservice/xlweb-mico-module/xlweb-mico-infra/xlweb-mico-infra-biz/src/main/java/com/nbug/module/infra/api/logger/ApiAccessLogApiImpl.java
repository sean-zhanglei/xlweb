package com.nbug.module.infra.api.logger;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.nbug.module.infra.service.logger.ApiAccessLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static com.nbug.mico.common.pojo.CommonResult.error;
import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public CommonResult<Boolean> createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
        return success(true);
    }


    /**
     * 【异步】创建 API 访问日志
     *
     * @param createDTO 访问日志 DTO
     */
    @Override
    public CommonResult<Boolean> createApiAccessLogAsync(ApiAccessLogCreateReqDTO createDTO) {
        try {
            apiAccessLogService.createApiAccessLogAsync(createDTO);
            return success(true);
        } catch (Exception e) {
            return error(INTERNAL_SERVER_ERROR);
        }

    }
}
