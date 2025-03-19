package com.nbug.module.user.api.userDetail;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户详情")
public interface UserDetailApi {

    String PREFIX = ApiConstants.PREFIX + "/userDetail";

    @GetMapping(PREFIX + "/loadUserByUsername")
    @Operation(summary = "获取admin 用户信息")
    @Parameter(name = "username", description = "用户名称", required = true)
    public CommonResult<UserDetails> loadUserByUsername(@RequestParam String username);

}
