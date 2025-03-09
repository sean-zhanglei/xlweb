package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 微信调起支付参数对象

 */
@Data
@Schema(description="微信调起支付参数对象")
public class WxPayJsResultVo {

    @Schema(description = "微信分配的小程序ID")
    private String appId;

    @Schema(description = "随机字符串，不长于32位")
    private String nonceStr;

    @Schema(description = "统一下单接口返回的 prepay_id 参数值")
    private String packages;

    @Schema(description = "签名类型，默认为MD5，支持HMAC-SHA256和MD5。")
    private String signType;

    @Schema(description = "时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间")
    private String timeStamp;

    @Schema(description = "支付签名")
    private String paySign;

    @Schema(description = "H5支付跳转链接")
    private String mwebUrl;

    @Schema(description = "微信商户号")
    private String partnerid;

    @Schema(description = "拉起收银台的ticket")
    private String ticket;
}
