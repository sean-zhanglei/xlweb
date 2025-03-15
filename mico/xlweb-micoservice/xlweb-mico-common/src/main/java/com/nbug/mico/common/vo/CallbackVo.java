package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信回调对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信回调")
public class CallbackVo {
    @Schema(description = "调用接口提交的公众账号ID")
    @JsonProperty(value = "appid")
    private String appId;

    @Schema(description = "调用接口提交的商户号")
    @JsonProperty(value = "mch_id")
    private String mchId;

    @Schema(description = "调用接口提交的终端设备号")
    @JsonProperty(value = "device_info")
    private String deviceInfo;

    @Schema(description = "微信返回的随机字符串")
    @JsonProperty(value = "nonce_str")
    private String nonceStr;

    @Schema(description = "微信返回的签名")
    private String sign;

    @Schema(description = "SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断")
    @JsonProperty(value = "return_code")
    private String returnCode;

    @Schema(description = "当return_code为FAIL时返回信息为错误原因 ，例如 签名失败 参数格式校验错误")
    @JsonProperty(value = "return_msg")
    private String returnMsg;

    @Schema(description = "SUCCESS/FAIL 业务结果")
    @JsonProperty(value = "result_code")
    private String resultCode;

    @Schema(description = "详细参见错误列表")
    @JsonProperty(value = "err_code")
    private String errCode;

    @Schema(description = "错误返回的信息描述")
    @JsonProperty(value = "err_code_des")
    private String errCodeDes;

    @Schema(description = "用户在商户appid下的唯一标识")
    private String openid;

    @Schema(description = "微信支付订单号")
    @JsonProperty(value = "transaction_id")
    private String transactionId;

    @Schema(description = "微信支付订单号")
    @JsonProperty(value = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "商家数据包，原样返回")
    private String attach;

    @Schema(description = "支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则")
    @JsonProperty(value = "time_end")
    private String timeEnd;

    @Schema(description = "用户是否关注公众账号，Y-关注，N-未关注")
    @JsonProperty(value = "is_subscribe")
    private String isSubscribe;

    @Schema(description = "银行类型")
    @JsonProperty(value = "bank_type")
    private String bankType;

    @Schema(description = "现金支付金额")
    @JsonProperty(value = "cash_fee")
    private Integer cashFee;

    @Schema(description = "总代金券金额")
    @JsonProperty(value = "coupon_fee")
    private Integer couponFee;

}
