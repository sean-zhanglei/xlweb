package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 设置用户等级表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="等级更改显示状态请求")
public class SystemUserLevelUpdateShowRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "等级id")
    @NotNull(message = "等级id不能为空")
    private Integer id;

    @Schema(description = "是否显示 1=显示,0=隐藏")
    @NotNull(message = "是否显示不能为空")
    private Boolean isShow;

}
