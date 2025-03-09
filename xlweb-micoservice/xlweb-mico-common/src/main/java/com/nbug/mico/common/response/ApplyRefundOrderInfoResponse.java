package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 申请退款订单响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="申请退款订单响应对象")
public class ApplyRefundOrderInfoResponse implements Serializable {

    private static final long serialVersionUID = 1387727608277207652L;

    @Schema(description = "订单id")
    private Integer id;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "支付状态")
    private Boolean paid;

    @Schema(description = "支付金额")
    private BigDecimal payPrice;

    @Schema(description = "订单商品总数")
    private Integer totalNum;

    @Schema(description = "订单详情对象列表")
    private List<OrderInfoResponse> orderInfoList;

}
