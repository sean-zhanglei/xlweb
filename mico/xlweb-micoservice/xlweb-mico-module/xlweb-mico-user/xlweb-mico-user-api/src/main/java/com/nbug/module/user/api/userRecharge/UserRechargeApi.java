package com.nbug.module.user.api.userRecharge;

import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户充值")
public interface UserRechargeApi {

    String PREFIX = ApiConstants.PREFIX + "/recharge";

    @GetMapping(PREFIX + "/getInfoByEntity")
    @Operation(summary = "根据对象查询充值订单")
    @Parameter(name = "userRecharge", description = "UserRecharge", required = true)
    public CommonResult<UserRecharge> getInfoByEntity(@RequestParam UserRecharge userRecharge);


    @PostMapping(PREFIX + "/updateById")
    @Operation(summary = "更新用户充值")
    @Parameter(name = "userRecharge", description = "用户充值信息", required = true)
    CommonResult<Boolean> updateById(@RequestParam UserRecharge userRecharge);

}
