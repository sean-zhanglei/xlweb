package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组件商品列表搜索Request对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="组件商品列表搜索Request对象")
public class ComponentProductSearchRequest implements Serializable {

    private static final long serialVersionUID = -2196197495866986580L;

    @Schema(description = "商品ID")
    private Integer proId;

    @Schema(description = "模糊搜索内容，目前只支持商品名称")
    private String search;
}
