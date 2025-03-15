package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 短信模板")
public interface SmsTemplateApi {

    String PREFIX = ApiConstants.PREFIX + "/smsTemplate";

    @GetMapping(PREFIX + "/getDetail")
    @Operation(summary = "获取短信模板")
    @Parameter(name = "id", description = "短信模板编号", required = true)
    public CommonResult<SmsTemplate> getDetail(Integer id);
}
