package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 参数配置")
public interface AttachmentApi {

    String PREFIX = ApiConstants.PREFIX + "/config";

    @GetMapping(PREFIX + "/clearPrefix")
    @Operation(summary = "清除 cdn url， 在保存数据的时候使用")
    CommonResult<String> clearPrefix(String path);

}
