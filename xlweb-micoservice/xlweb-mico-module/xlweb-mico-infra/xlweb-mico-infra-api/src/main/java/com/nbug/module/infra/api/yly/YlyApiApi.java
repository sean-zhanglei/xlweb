package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
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
    public CommonResult<Boolean> sendOrderEditPriceNotice(String phone, String orderNo, BigDecimal price, String msgTempKey);


    @PostMapping(PREFIX + "/sendOrderReceiptNotice")
    @Operation(summary = "发送用户确认收货管理员提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "realName", description = "收货人姓名", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderReceiptNotice( String phone, String orderNo, String realName, String msgTempKey);

    @PostMapping(PREFIX + "/sendOrderDeliverNotice")
    @Operation(summary = "发送订单发货提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "nickName", description = "用户昵称", required = true),
            @Parameter(name = "storeName", description = "店铺名称", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderDeliverNotice(String phone, String nickName, String storeName, String orderNo, String msgTempKey);

    @PostMapping(PREFIX + "/sendPaySuccess")
    @Operation(summary = "发送订单支付成功提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "payPrice", description = "支付金额", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendPaySuccess(String phone, String orderNo, BigDecimal payPrice, String msgTempKey);

    @PostMapping(PREFIX + "/sendPaySuccess")
    @Operation(summary = "发送订单支付成功提醒短信")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "orderNo", description = "订单编号", required = true),
            @Parameter(name = "payPrice", description = "支付金额", required = true),
            @Parameter(name = "msgTempKey", description = "短信模板编号", required = true)
    })
    public CommonResult<Boolean> sendOrderPaySuccessNotice( String phone, String orderNo, String realName, String msgTempKey);
}
