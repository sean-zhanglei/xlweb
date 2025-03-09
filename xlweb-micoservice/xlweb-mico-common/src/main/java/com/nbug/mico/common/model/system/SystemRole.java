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
 * 身份管理表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_role")
@Schema(description="身份管理表")
public class SystemRole implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "身份管理id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "身份管理名称")
    private String roleName;

    @Schema(description = "身份管理权限(menus_id)")
    private String rules;

    private Integer level;

    @Schema(description = "状态：0-关闭，1-正常")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

}
