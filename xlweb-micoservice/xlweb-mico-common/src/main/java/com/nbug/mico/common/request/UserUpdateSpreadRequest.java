package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新推广人请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="更新推广人请求对象")
public class UserUpdateSpreadRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户编号")
    @NotNull(message = "请选择用户")
    private Integer userId;

    @Schema(description = "用户头像")
    @NotBlank(message = "请选择用户头像")
    private String image;

    @Schema(description = "推广人编号")
    @NotNull(message = "请选择推广人")
    private Integer spreadUid;
}
