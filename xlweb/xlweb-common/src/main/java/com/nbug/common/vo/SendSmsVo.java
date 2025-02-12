package com.nbug.common.vo;

import lombok.Data;

/**
 * 短信发送api第三方参数实体类

 */
@Data
public class SendSmsVo {
    private String uid;
    private String token;

    // 待发送短信手机号
    private String mobile;

    // 模版Id 已废弃
    private Integer templateId;

    // 模版Key
    private String templateKey;

    // 发送参数 短信模版
    private String param;

    // 发送内容 短信参数
    private String content;

}
