package com.nbug.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信小程序订阅消息Vo对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RoutineMyTemplateVo对象", description="微信小程序订阅消息Vo对象")
public class RoutineMyTemplateVo {

    @ApiModelProperty(value = "模板ID")
    private String priTmplId;

    @ApiModelProperty(value = "模板标题")
    private String title;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板示例")
    private String example;

    @ApiModelProperty(value = "类型")
    private String type;
}
