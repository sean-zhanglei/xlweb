package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信公众号私有模板消息Vo对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信公众号私有模板消息Vo对象")
public class PublicMyTemplateVo {

    @Schema(description = "模板ID")
    private String template_id;

    @Schema(description = "模板ID")
    private String title;

    @Schema(description = "一级行业")
    private String primary_industry;

    @Schema(description = "二级行业")
    private String deputy_industry;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "模板示例")
    private String example;
}
