package com.nbug.mico.common.model.order;

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
 * 订单购物详情表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_order_info")
@Schema(description="订单购物详情表")
public class StoreOrderInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "订单id")
    private Integer orderId;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "购买东西的详细信息")
    private String info;

    @Schema(description = "唯一id")
    @TableField(value = "`unique`")
    private String unique;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "规格属性id")
    private Integer attrValueId;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "sku")
    private String sku;

    @Schema(description = "单价")
    private BigDecimal price;

    @Schema(description = "购买数量")
    private Integer payNum;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "体积")
    private BigDecimal volume;

    @Schema(description = "获得积分")
    private Integer giveIntegral;

    @Schema(description = "是否评价")
    private Boolean isReply;

    @Schema(description = "是否单独分佣")
    private Boolean isSub;

    @Schema(description = "会员价")
    private BigDecimal vipPrice;

    @Schema(description = "商品类型:0-普通，1-秒杀，2-砍价，3-拼团，4-视频号")
    private Integer productType;
}
