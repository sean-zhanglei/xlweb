package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 统一下单返回对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="统一下单返回对象")
public class CreateOrderResponseVo {
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

    @Schema(description = "调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，,H5支付固定传MWEB")
    @JsonProperty(value = "trade_type")
    private String tradeType;

    @Schema(description = "微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时,针对H5支付此参数无特殊用途")
    @JsonProperty(value = "prepay_id")
    private String prepayId;

    @Schema(description = "mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟")
    @JsonProperty(value = "mweb_url")
    private String mWebUrl;

    @Schema(description = "该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题")
    @JsonProperty(value = "scene_info")
    private String extra;

    private Object transJsConfig;
}
