package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 系统左侧菜单对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="系统左侧菜单对象")
public class MenusResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "ID")
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

    @Schema(description = "子对象列表")
    private List<MenusResponse> childList;
}
