package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * 微信模板发送类

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信模板发送类")
public class ProgramTemplateMessageVo {
    @Schema(description = "OPENID", required = true)
    private String touser;

    @Schema(description = "模板ID", required = true)
    private String template_id;

    @Schema(description = "模板跳转链接小程序地址")
    private String page;

    @Schema(description = "发送内容", required = true)
    private HashMap<String, SendProgramTemplateMessageItemVo> data;

    @Schema(description = "跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版")
    private String miniprogram_state = "formal";

    @Schema(description = "进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN\n")
    private String lang = "zh_CN";
}
