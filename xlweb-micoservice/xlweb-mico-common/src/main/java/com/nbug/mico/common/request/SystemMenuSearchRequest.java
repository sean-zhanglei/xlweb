package com.nbug.mico.common.request;

import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单搜索Request对象

 */
@Data
@Schema( description="菜单搜索Request对象")
public class SystemMenuSearchRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单类型:M-目录，C-菜单，A-按钮")
    @StringContains(limitValues = {"M","C","A"}, message = "未知的菜单类型")
    private String menuType;
}
