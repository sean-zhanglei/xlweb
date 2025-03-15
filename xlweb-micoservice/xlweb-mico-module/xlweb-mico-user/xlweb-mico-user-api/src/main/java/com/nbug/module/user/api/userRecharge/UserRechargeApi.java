package com.nbug.module.user.api.user;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 用户基本")
public interface UserApi {

    String PREFIX = ApiConstants.PREFIX + "/base";


    @GetMapping(PREFIX + "/getValidateCodeRedisKey")
    @Operation(summary = "检测手机验证码key")
    @Parameter(name = "phone", description = "检测手机验证码key", required = true)
    public CommonResult<String> getValidateCodeRedisKey(String phone);


    @GetMapping(PREFIX + "/updateIntegral")
    @Operation(summary = "更新用户积分")
    @Parameters({
            @Parameter(name = "user", description = "用户", required = true),
            @Parameter(name = "integral", description = "积分", required = true),
            @Parameter(name = "type", description = "类型", required = true)
    })

    public CommonResult<Boolean> updateIntegral(User user, Integer integral, String type);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取用户ById")
    @Parameter(name = "id", description = "id", required = true)
    public CommonResult<User> getById(String id);

}
