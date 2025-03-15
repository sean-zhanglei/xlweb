package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *  添加购物车参数

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="购物车")
public class CartRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "商品ID", required = true)
    @NotNull(message = "商品id不能为空")
    private Integer productId;

    @Schema(description = "商品属性 -- attr 对象的id")
    @NotBlank(message = "商品属性id不能为空")
    private String productAttrUnique;

    @Schema(description = "商品数量", required = true)
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量不能小于1")
    @Max(value = 99, message = "单个商品数量不能大于99")
    private Integer cartNum;
}
