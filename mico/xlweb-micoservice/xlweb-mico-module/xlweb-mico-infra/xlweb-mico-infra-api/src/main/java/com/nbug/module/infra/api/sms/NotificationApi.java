package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 通知")
public interface NotificationApi {

    String PREFIX = ApiConstants.PREFIX + "/notification";

    @GetMapping(PREFIX + "/getByMark")
    @Operation(summary = "根据标识获取通知")
    @Parameters({
            @Parameter(name = "mark", description = "标识", required = true)
    })
    public CommonResult<SystemNotification> getByMark(String mark);
}
