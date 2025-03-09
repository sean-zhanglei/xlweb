package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 充值

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="充值")
public class UserRechargeRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "充值金额")
//    @DecimalMin(value = "1", message = "充值金额不能小于1") todo 测试完后放开
    private BigDecimal price;

    @Schema(description = "选择金额组合数据id")
    @JsonProperty(value = "rechar_id")
    private Integer groupDataId;

    @Schema(description = "支付方式| weixin = 微信，alipay = 支付宝")
    private String payType = "weixin";

    @Schema(description = "来源 | public =  微信公众号, weixinh5 =微信H5支付, routine = 小程序，weixinAppIos-微信appios支付，weixinAppAndroid-微信app安卓支付,alipay-支付包支付，appAliPay-App支付宝支付")
    @JsonProperty(value = "from")
    private String fromType;

    @Schema(description = "客户端ip")
    @JsonIgnore
    private String clientIp;

    @Schema(description = "用户id")
    @JsonIgnore
    private Integer userId;

    @Schema(description = "赠送金额")
    @JsonIgnore
    private BigDecimal givePrice;
}
