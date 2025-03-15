package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 后台管理员表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="后台管理员请求对象")
public class SystemAdminRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "后台管理员姓名")
    private String realName;

    @Schema(description = "后台管理员权限(menus_id)")
    private String roles;

    @Schema(description = "后台管理员状态 1有效0无效")
    private Boolean status;
}
