package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品属性值表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品属性值表")
public class StoreProductAttrValueRequest implements Serializable {

    private static final long serialVersionUID=1L;
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "商品ID", example = "0")
    @Min(value = 0, message = "请选择商品")
    private Integer productId;

    @Schema(description = "商品属性索引值 (attr_value|attr_value[|....])")
    private String suk;

    @Schema(description = "属性对应的库存")
    private Integer stock;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "属性金额")
    private BigDecimal price;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "唯一值")
    private String unique;

    @Schema(description = "成本价")
    private BigDecimal cost;

    @Schema(description = "商品条码")
    private String barCode;

    @Schema(description = "原价")
    private BigDecimal otPrice;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "一级返佣")
    private BigDecimal brokerage;

    @Schema(description = "二级返佣")
    private BigDecimal brokerageTwo;

    @Schema(description = "活动类型 0=商品，1=秒杀，2=砍价，3=拼团")
    private Integer type;

    @Schema(description = "活动限购数量")
    private Integer quota;

    @Schema(description = "活动限购数量显示")
    private Integer quotaShow;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "attrValue字段，前端传递后用作sku字段")
    private String attrValue;

    @Schema(description = "是否选中-秒杀用")
    private Boolean checked;

    @Schema(description = "砍价商品最低价")
    private BigDecimal minPrice;
}
