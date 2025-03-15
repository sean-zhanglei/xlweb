package com.nbug.mico.common.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品属性值表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_attr_value")
@Schema(description="商品属性值表")
public class StoreProductAttrValue implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "attrId")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "商品ID")
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

    @TableField(value = "`unique`")
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

    @Schema(description = "产品属性值和属性名对应关系")
    private String attrValue;

    @Schema(description = "是否删除,0-否，1-是")
    private Boolean isDel;
}
