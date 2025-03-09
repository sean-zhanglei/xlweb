package com.nbug.mico.common.model.wechat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_template_message")
@Schema(description="微信模板")
public class TemplateMessage implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "模板id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "0=订阅消息,1=微信模板消息")
    private Boolean type;

    @Schema(description = "模板编号")
    private String tempKey;

    @Schema(description = "模板名")
    @JsonProperty
    private String name;

    @Schema(description = "回复内容")
    private String content;

    @Schema(description = "模板ID")
    private String tempId;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
