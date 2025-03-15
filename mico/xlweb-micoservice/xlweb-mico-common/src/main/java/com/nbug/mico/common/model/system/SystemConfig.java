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
 * 配置表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_config")
@Schema(description="配置表")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "配置id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "字段名称")
    private String name;

    @Schema(description = "字段提示文字")
    private String title;

    @Schema(description = "表单id")
    private Integer formId;

    @Schema(description = "值")
    private String value;

    @Schema(description = "是否隐藏")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
