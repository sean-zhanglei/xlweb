package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 短信")
public interface SmsApi {

    String PREFIX = ApiConstants.PREFIX + "/sms";

    @PostMapping(PREFIX + "/sendOrderEditPriceNotice")
    @Operation(summary = "发送订单改价提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "price", description = "修改后的价格", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderEditPriceNotice(@RequestParam String phone,
                                                          @RequestParam String orderNo,
                                                          @RequestParam BigDecimal price,
                                                          @RequestParam String msgTempKey);


    @PostMapping(PREFIX + "/sendOrderReceiptNotice")
    @Operation(summary = "发送用户确认收货管理员提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "realName", description = "收货人姓名", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderReceiptNotice(@RequestParam String phone,
                                                        @RequestParam String orderNo,
                                                        @RequestParam String realName,
                                                        @RequestParam String msgTempKey);

    @PostMapping(PREFIX + "/sendOrderDeliverNotice")
    @Operation(summary = "发送订单发货提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "nickName", description = "用户昵称", required = true),
            @Parameter(name = "storeName", description = "店铺名称", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderDeliverNotice(@RequestParam String phone,
                                                        @RequestParam String nickName,
                                                        @RequestParam String storeName,
                                                        @RequestParam String orderNo,
                                                        @RequestParam String msgTempKey);

    @PostMapping(PREFIX + "/sendPaySuccess")
    @Operation(summary = "发送支付成功短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "payPrice", description = "支付金额", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendPaySuccess(@RequestParam String phone,
                                                @RequestParam String orderNo,
                                                @RequestParam BigDecimal payPrice,
                                                @RequestParam String msgTempKey);

    @PostMapping(PREFIX + "/sendOrderPaySuccessNotice")
    @Operation(summary = "发送订单支付成功管理员提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "payPrice", description = "支付金额", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderPaySuccessNotice(@RequestParam String phone,
                                                           @RequestParam String orderNo,
                                                           @RequestParam String realName,
                                                           @RequestParam String msgTempKey);

    @PostMapping(PREFIX + "/sendCreateOrderNotice")
    @Operation(summary = "发送管理员下单短信提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "realName", description = "用户昵称", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendCreateOrderNotice(@RequestParam String phone,
                                                       @RequestParam String orderNo,
                                                       @RequestParam String realName,
                                                       @RequestParam String msgTempKey);

    @PostMapping(PREFIX + "/sendOrderRefundApplyNotice")
    @Operation(summary = "发送用户退款管理员提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "realName", description = "用户昵称", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderRefundApplyNotice(@RequestParam String phone,
                                                            @RequestParam String orderNo,
                                                            @RequestParam String realName,
                                                            @RequestParam String msgTempKey);


    @PostMapping(PREFIX + "/sendCommonCode")
    @Operation(summary = "发送公共验证码")
    @Parameter(name = "phone", description = "手机号", required = true)
    public CommonResult<Boolean> sendCommonCode(@RequestParam String phone);
}
