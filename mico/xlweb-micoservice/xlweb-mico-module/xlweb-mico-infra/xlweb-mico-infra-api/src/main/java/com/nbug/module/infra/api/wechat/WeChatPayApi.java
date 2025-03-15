package com.nbug.module.infra.api.wechat;

import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 微信支付")
public interface WeChatPayApi {

    String PREFIX = ApiConstants.PREFIX + "/weChatPay";

    @PostMapping(PREFIX + "/unifiedRecharge")
    @Operation(summary = "微信充值统一下单接口")
    @Parameters({
            @Parameter(name = "userRecharge", description = "充值参数", required = true),
            @Parameter(name = "clientIp", description = "客户端IP", required = true)
    })
    public CommonResult<Map<String, String>> unifiedRecharge(UserRecharge userRecharge, String clientIp);


    @PostMapping(PREFIX + "/queryPayResult")
    @Operation(summary = "查询支付结果")
    @Parameter(name = "orderNo", description = "订单号", required = true)
    public CommonResult<Boolean> queryPayResult(String orderNo);

}