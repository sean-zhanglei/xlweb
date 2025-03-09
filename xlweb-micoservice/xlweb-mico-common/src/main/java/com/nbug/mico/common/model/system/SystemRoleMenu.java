package com.nbug.mico.common.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色菜单关联表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_role_menu")
@Schema(description="角色菜单关联表")
public class SystemRoleMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "角色id")
    private Integer rid;

    @Schema(description = "权限id")
    private Integer menuId;


}
