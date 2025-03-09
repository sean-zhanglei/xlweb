package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 小程序我的模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="小程序我的模板")
public class WechatProgramMyTempSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "模版标题")
    private String title;

    @Schema(description = "我的模板id")
    private String tempId;

    @Schema(description = "状态 0，禁用，1启用")
    private Boolean status;

    @Schema(description = "应用场景")
    private String type;

    private Integer id;
}
