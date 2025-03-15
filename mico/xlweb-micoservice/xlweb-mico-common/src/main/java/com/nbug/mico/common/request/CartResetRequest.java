package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车重选Request对象类

 */
@Data
public class CartResetRequest {

    @Schema(description = "购物车id")
    @NotNull(message = "id 不能为空")
    private Long id;

    @Schema(description = "购物车数量")
    @NotNull(message = "num 不能为空")
    private Integer num;

    @Schema(description = "商品id")
    @NotNull(message = "productId 不能为空")
    private Integer productId;

    @Schema(description = "AttrValue Id")
    @NotNull(message = "unique 不能为空")
    private Integer unique;
}
