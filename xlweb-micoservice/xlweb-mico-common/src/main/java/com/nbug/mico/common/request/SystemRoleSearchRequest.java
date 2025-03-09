package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 身份管理搜索Request对象

 */
@Data
public class SystemRoleSearchRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "身份管理名称")
    private String roleName;

    @Schema(description = "状态")
    private Boolean status;
}
