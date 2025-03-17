package com.nbug.module.infra.api.attachment;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 附件")
public interface AttachmentApi {

    String PREFIX = ApiConstants.PREFIX + "/attachment";

    @GetMapping(PREFIX + "/clearPrefix")
    @Operation(summary = "清除 cdn url， 在保存数据的时候使用")
    CommonResult<String> clearPrefix(@RequestParam String path);

    @GetMapping(PREFIX + "/prefixImage")
    @Operation(summary = "给图片加前缀")
    @Parameter(name = "path", description = "路径", required = true)
    public CommonResult<String> prefixImage(@RequestParam String path);
}
