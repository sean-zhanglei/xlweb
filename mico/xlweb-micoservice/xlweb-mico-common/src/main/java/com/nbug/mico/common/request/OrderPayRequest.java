package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 支付订单参数
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="订单支付")
public class OrderPayRequest {

    @Schema(description = "订单id")
//    @NotNull(message = "订单id不能为空")
    private String uni;

    @Schema(description = "订单编号")
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "支付类型：weixin-微信支付，yue-余额支付，offline-线下支付，alipay-支付包支付")
    @NotNull(message = "支付类型不能为空")
    private String payType;

    @Schema(description = "支付渠道:weixinh5-微信H5支付，public-公众号支付，routine-小程序支付，weixinAppIos-微信appios支付，weixinAppAndroid-微信app安卓支付,alipay-支付包支付，appAliPay-App支付宝支付")
    @NotNull(message = "支付渠道不能为空")
    private String payChannel;

    @Schema(description = "支付平台")
    private String from;

    @Schema(description = "下单时小程序的场景值")
    private Integer scene;
}
