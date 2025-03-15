package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 微信公众号js-sdk响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信公众号js-sdk响应对象对象")
public class WeChatJsSdkConfigResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "url")
    private String url;

    @Schema(description = "jsApiTicket")
    private String jsApiTicket;

    @Schema(description = "nonceStr")
    private String nonceStr;

    @Schema(description = "timestamp")
    private Long timestamp;

    @Schema(description = "signature")
    private String signature;

    @Schema(description = "jsApiList")
    private List<String> jsApiList;

    @Schema(description = "debug")
    private Boolean debug;

    @Schema(description = "appid")
    private String appId;
}
