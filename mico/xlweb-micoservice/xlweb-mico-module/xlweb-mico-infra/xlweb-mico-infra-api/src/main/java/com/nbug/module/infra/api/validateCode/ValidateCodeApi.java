package com.nbug.module.infra.api.validateCode;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.ValidateCode;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 验证码")
public interface ValidateCodeApi {

    String PREFIX = ApiConstants.PREFIX + "/validateCode";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获取验证码")
    public CommonResult<ValidateCode> get();

    @PostMapping(PREFIX + "/check")
    @Operation(summary = "校验验证码")
    @Parameters({
            @Parameter(name = "key", description = "验证码的 key", required = true),
            @Parameter(name = "code", description = "验证码的 code", required = true)
    })
    public CommonResult<Boolean> check(String key, String code);
}
