package com.nbug.module.user.api.userToken;

import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户Token")
public interface UserTokenApi {

    String PREFIX = ApiConstants.PREFIX + "/token";


    @GetMapping(PREFIX + "/getTokenByUserId")
    @Operation(summary = "获取UserTokenByUserId")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", required = true),
            @Parameter(name = "type", description = "用户类型", required = true)
    })
    CommonResult<UserToken> getTokenByUserId(@RequestParam Integer userId,
                                             @RequestParam int type);

}
