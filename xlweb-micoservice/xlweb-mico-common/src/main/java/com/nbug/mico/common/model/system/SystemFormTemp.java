package com.nbug.mico.common.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 表单模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_form_temp")
@Schema(description="表单模板")
public class SystemFormTemp implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "表单模板id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "表单名称")
    private String name;

    @Schema(description = "表单简介")
    private String info;

    @Schema(description = "表单内容")
    private String content;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;


}
