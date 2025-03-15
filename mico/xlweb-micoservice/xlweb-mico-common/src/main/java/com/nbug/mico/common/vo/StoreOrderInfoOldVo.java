package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单购物详情表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单购物详情表")
public class StoreOrderInfoOldVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "订单id")
    private Integer orderId;

    @Schema(description = "商品ID")
    private Integer productId;

    @Schema(description = "购买东西的详细信息")
    private OrderInfoDetailVo info;

    @Schema(description = "唯一id")
    @TableField(value = "`unique`")
    private String unique;



}
