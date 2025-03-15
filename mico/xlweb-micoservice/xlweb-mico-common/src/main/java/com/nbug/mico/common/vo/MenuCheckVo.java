package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单待选中Vo对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="菜单待选中Vo对象")
public class MenuCheckVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "菜单ID")
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "icon")
    private String icon;

    @Schema(description = "是否选中")
    private Boolean checked;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "子对象列表")
    private List<MenuCheckVo> childList;
}
