package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * item对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="item对象")
public class SystemConfigFormItemVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "配置")
    private SystemConfigFormItemConfigVo __config__;

    @Schema(description = "")
    private String placeholder;

    @Schema(description = "")
    private String step;

    @Schema(description = "")
    private String stepStrictly;

    @Schema(description = "")
    private String controlsPosition;

    @Schema(description = "")
    private String disabled;

    @Schema(description = "")
    private String __vModel__;



}
