package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 微信调起支付参数对象
 
 */
@Data
@Schema(description="支付宝调起支付参数对象")
public class AliPayJsResultVo {

    @Schema(description = "支付宝id")
    private String appId;

    @Schema(description = "接口名称")
    private String method;

    @Schema(description = "仅支持JSON")
    private String format = "JSON";

    @Schema(description = "编码格式")
    private String charset;

    @Schema(description = "签名算法类型：RSA,RSA2")
    private String signType;

    @Schema(description = "支付签名")
    private String sign;

    @Schema(description = "发送请求时间，格式'yyyy-MM-dd HH:mm:ss'")
    private String timestamp;

    @Schema(description = "调用的接口版本")
    private String version = "1.0";

    @Schema(description = "异步回调地址")
    private String notifyUrl;

    @Schema(description = "业务请求参数集合")
    private String bizContent;
}
