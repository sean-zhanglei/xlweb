package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单详情响应对象")
public class OrderInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单id")
    private Integer orderId;

    @Schema(description = "attrId")
    private Integer attrId;

    @Schema(description = "商品ID")
    private Integer productId;

//    @Schema(description = "购买东西的详细信息")
//    private StoreCartResponse info;

    @Schema(description = "商品数量")
    private Integer cartNum;

//    @Schema(description = "唯一id")
//    @TableField(value = "`unique`")
//    private String unique;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "商品名称")
    private String storeName;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "是否评价")
    private Integer isReply;

    @Schema(description = "规格属性值")
    private String sku;

    @Schema(description = "用户姓名")
    private String realName;
}
