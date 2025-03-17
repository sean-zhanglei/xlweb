package com.nbug.module.user.api.userAddress;

import com.nbug.mico.common.model.user.UserAddress;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户地址")
public interface UserAddressApi {

    String PREFIX = ApiConstants.PREFIX + "/address";

    @GetMapping(PREFIX + "/getDefaultByUid")
    @Operation(summary = "获取默认地址")
    @Parameter(name = "uid", description = "用户ID", required = true)
    public CommonResult<UserAddress> getDefaultByUid(@RequestParam Integer uid);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取地址ById")
    @Parameter(name = "addressId", description = "地址Id", required = true)
    public CommonResult<UserAddress> getById(@RequestParam Integer addressId);

}
