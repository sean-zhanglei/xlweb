package com.nbug.module.user.api.userExtract;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.UserExtractResponse;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 提现记录")
public interface UserExtractApi {

    String PREFIX = ApiConstants.PREFIX + "/userExtract";

    @GetMapping(PREFIX + "/getUserExtractByUserId")
    @Operation(summary = "获取用户提现记录")
    @Parameter(name = "userId", description = "用户id", required = true)
    public CommonResult<UserExtractResponse> getUserExtractByUserId(Integer userId);
}
