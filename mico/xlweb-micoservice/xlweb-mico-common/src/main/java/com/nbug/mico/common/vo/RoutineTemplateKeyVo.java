package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信小程序订阅消息Vo对象(从微信获取的)

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信小程序订阅消息KeyVo对象(从微信获取的)")
public class RoutineTemplateKeyVo {

    @Schema(description = "关键词 id，选用模板时需要")
    private Integer kid;

    @Schema(description = "关键词内容")
    private String name;

    @Schema(description = "关键词内容对应的示例")
    private String example;

    @Schema(description = "参数类型")
    private String rule;

}
