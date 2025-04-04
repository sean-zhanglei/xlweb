package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建订单请求对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="创建订单请求对象")
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = -6133994384185333872L;

    @Schema(description = "预下单订单号")
    @NotBlank(message = "预下单订单号不能为空")
    private String preOrderNo;

    @Schema(description = "快递类型: 1-快递配送，2-到店自提")
    @NotNull(message = "快递类型不能为空")
    @Range(min = 1, max = 2, message = "未知的快递类型")
    private Integer shippingType;

    @Schema(description = "收货地址id")
    private Integer addressId;

    @Schema(description = "优惠券编号")
    private Integer couponId;

    @Schema(description = "支付类型:weixin-微信支付，yue-余额支付,alipay-支付宝支付")
    @NotBlank(message = "支付类型不能为空")
    private String payType;

    @Schema(description = "支付渠道:weixinh5-微信H5支付，public-公众号支付，routine-小程序支付，weixinAppIos-微信appios支付，weixinAppAndroid-微信app安卓支付,alipay-支付宝支付，appAliPay-App支付宝支付")
    @NotBlank(message = "支付渠道不能为空")
    private String payChannel;

    @Schema(description = "是否使用积分")
    @NotNull(message = "是否使用积分不能为空")
    private Boolean useIntegral;

    @Schema(description = "订单备注")
    private String mark;

    // 以下为到店自提参数

    @Schema(description = "自提点id")
    private Integer storeId;

    @Schema(description = "真实名称")
    private String realName;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "配送时间")
    private String deliveryTime;

    @Schema(description = "自提时间")
    private String pickupTime;
}
