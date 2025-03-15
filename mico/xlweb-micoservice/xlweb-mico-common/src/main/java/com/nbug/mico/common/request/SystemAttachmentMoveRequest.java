package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统附件移动Request对象

 */
@Data
public class SystemAttachmentMoveRequest {


    @Schema(description = "父级id")
    @NotNull(message = "父级id不能为空")
    private Integer pid;

    @Schema(description = "附件id")
    @NotBlank(message = "请选择附件")
    private String attrId;
}
