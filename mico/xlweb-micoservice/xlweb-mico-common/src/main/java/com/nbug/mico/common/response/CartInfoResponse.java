package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="购物车详情响应对象")
public class CartInfoResponse implements Serializable {

    private static final long serialVersionUID = 3558884699193209193L;

    @Schema(description = "购物车表ID")
    private Long id;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "商品属性")
    private String productAttrUnique;

    @Schema(description = "商品数量")
    private Integer cartNum;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品规格id")
    private Integer attrId;

    @Schema(description = "商品属性索引值 (attr_value|attr_value[|....])")
    private String suk;

    @Schema(description = "sku价格")
    private BigDecimal price;

    @Schema(description = "商品是否有效")
    private Boolean attrStatus;

    @Schema(description = "sku库存")
    private Integer stock;

    @Schema(description = "sku会员价格")
    private BigDecimal vipPrice;
}
