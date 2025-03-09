package com.nbug.mico.common.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统菜单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_menu")
@Schema(description="系统菜单表")
public class SystemMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "icon")
    private String icon;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "类型，M-目录，C-菜单，A-按钮")
    private String menuType;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "显示状态")
    private Boolean isShow;

    @Schema(description = "是否删除")
    @JsonIgnore
    private Boolean isDelte;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    @JsonIgnore
    private Date updateTime;


}
