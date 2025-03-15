package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 首页商品对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="首页商品对象")
public class IndexProductResponse {


    @Schema(description = "商品id")
    private Integer id;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "市场价")
    private BigDecimal otPrice;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "虚拟销量")
    private Integer ficti;

    @Schema(description = "单位名")
    private String unitName;

    @Schema(description = "活动显示排序0=默认，1=秒杀，2=砍价，3=拼团")
    private String activity;

    @Schema(description = "为移动端特定参数")
    private ProductActivityItemResponse activityH5;

    @Schema(description = "购物车商品数量")
    private Integer cartNum;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "展示图")
    private String flatPattern;
}
