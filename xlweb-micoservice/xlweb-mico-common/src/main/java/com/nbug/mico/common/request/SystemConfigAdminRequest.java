package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统配置请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="系统配置请求对象")
public class SystemConfigAdminRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "配置id")
    @NotNull(message = "配置id不能为空")
    private Integer id;

    @Schema(description = "字段名称")
    @NotBlank(message = "字段名称不能为空")
    private String name;

    @Schema(description = "字段提示文字")
    @NotBlank(message = "字段提示文字不能为空")
    private String title;

    @Schema(description = "表单id")
    @NotNull(message = "表单id不能为空")
    private Integer formId;

    @Schema(description = "值")
    @NotBlank(message = "值不能为空")
    private String value;

    @Schema(description = "是否隐藏")
    @NotNull(message = "状态不能为空")
    private Boolean status;
}
