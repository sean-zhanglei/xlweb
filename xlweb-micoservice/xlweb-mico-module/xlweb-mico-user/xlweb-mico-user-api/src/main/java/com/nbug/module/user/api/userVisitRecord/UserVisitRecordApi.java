package com.nbug.module.user.api.userBill;

import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 用户订单")
public interface UserBillApi {

    String PREFIX = ApiConstants.PREFIX + "/bill";


    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存用户订单")
    @Parameter(name = "userBill", description = "用户订单信息", required = true)
    public CommonResult<Boolean> save(UserBill userBill);

}
