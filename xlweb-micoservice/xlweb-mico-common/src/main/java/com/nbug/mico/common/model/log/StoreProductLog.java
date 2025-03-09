package com.nbug.mico.common.model.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品统计

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product_log")
@Schema(description="商品统计")
public class StoreProductLog implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品统计表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "类型visit,cart,order,pay,collect,refund")
    private String type;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "用户ID")
    private Integer uid;

    @Schema(description = "是否浏览")
    private Boolean visitNum;

    @Schema(description = "加入购物车数量")
    private Integer cartNum;

    @Schema(description = "下单数量")
    private Integer orderNum;

    @Schema(description = "支付数量")
    private Integer payNum;

    @Schema(description = "支付金额")
    private BigDecimal payPrice;

    @Schema(description = "商品成本价")
    private BigDecimal costPrice;

    @Schema(description = "支付用户ID")
    private Integer payUid;

    @Schema(description = "退款数量")
    private Integer refundNum;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;

    @Schema(description = "收藏")
    private Boolean collectNum;

    @Schema(description = "添加时间")
    private Long addTime;


}
