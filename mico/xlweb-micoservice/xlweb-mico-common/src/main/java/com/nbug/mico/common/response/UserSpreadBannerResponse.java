package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户地址表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户推广海报")
public class UserSpreadBannerResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "名称")
    private String title;

    @Schema(description = "背景图")
    private String pic;

}
