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
public class TemplateMessageVo {
    @Schema(description = "OPENID", required = true)
    private String touser;

    @Schema(description = "模板ID", required = true)
    private String template_id;

    @Schema(description = "模板跳转链接（海外帐号没有跳转能力）")
    private String url;

    @Schema(description = "发送内容")
    private HashMap<String, SendTemplateMessageItemVo> data;
}
