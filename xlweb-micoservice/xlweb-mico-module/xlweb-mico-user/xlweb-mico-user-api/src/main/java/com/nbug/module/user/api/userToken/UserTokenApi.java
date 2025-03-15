package com.nbug.module.user.api.user;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 用户基本")
public interface UserApi {

    String PREFIX = ApiConstants.PREFIX + "/base";


    @GetMapping(PREFIX + "/get-validate-code-redis-key")
    @Operation(summary = "检测手机验证码key")
    @Parameter(name = "phone", description = "检测手机验证码key", required = true)
    CommonResult<String> getValidateCodeRedisKey(@RequestParam("phone") String phone);

}
