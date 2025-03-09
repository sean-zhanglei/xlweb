package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信退款返回对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信退款返回对象")
public class WxRefundResponseVo {
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

    @Schema(description = "调用接口提交的公众账号ID")
    @JsonProperty(value = "appid")
    private String appId;

    @Schema(description = "调用接口提交的商户号")
    @JsonProperty(value = "mch_id")
    private String mchId;

    @Schema(description = "微信返回的随机字符串")
    @JsonProperty(value = "nonce_str")
    private String nonceStr;

    @Schema(description = "微信返回的签名")
    private String sign;

    @Schema(description = "微信支付订单号")
    @JsonProperty(value = "transaction_id")
    private String transactionId;

    @Schema(description = "商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号", required = true)
    @JsonProperty(value = "out_trade_no")
    private String outTradeNo;

    @Schema(description = "商户退款单号,同一退款单号多次请求只退一笔。")
    @JsonProperty(value = "out_refund_no")
    private String outRefundNo;

    @Schema(description = "微信退款单号")
    @JsonProperty(value = "refund_id")
    private String refundId;

    @Schema(description = "退款总金额,单位为分,可以做部分退款")
    @JsonProperty(value = "refund_fee")
    private Integer refundFee;

    @Schema(description = "应结退款金额")
    @JsonProperty(value = "settlement_refund_fee")
    private Integer settlementRefundFee;

    @Schema(description = "标价金额")
    @JsonProperty(value = "total_fee")
    private Integer totalFee;

    @Schema(description = "应结订单金额")
    @JsonProperty(value = "settlement_total_fee")
    private Integer settlementTotalFee;

    @Schema(description = "标价币种，订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型")
    @JsonProperty(value = "fee_type")
    private String feeType;

    @Schema(description = "现金支付金额")
    @JsonProperty(value = "cash_fee")
    private Integer cashFee;

    @Schema(description = "现金支付币种，订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型")
    @JsonProperty(value = "cash_fee_type")
    private String cashFeeType;

    @Schema(description = "现金退款金额")
    @JsonProperty(value = "cash_refund_fee")
    private Integer cashRefundFee;

}
