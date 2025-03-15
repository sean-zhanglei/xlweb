package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单数量响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单数量响应对象")
public class OrderDataResponse implements Serializable {

    private static final long serialVersionUID = 1387727608277207652L;

    @Schema(description = "已完成订单数量")
    private Integer completeCount;

    @Schema(description = "待核销订单数量")
    private Integer evaluatedCount;

//    @Schema(description = "用户昵称")
//    private Integer verificationCount;

    @Schema(description = "支付订单总数")
    private Integer orderCount;

    @Schema(description = "待收货订单数量")
    private Integer receivedCount;

    @Schema(description = "退款订单数量")
    private Integer refundCount;

    @Schema(description = "总消费钱数")
    private BigDecimal sumPrice;

    @Schema(description = "未支付订单数量")
    private Integer unPaidCount;

    @Schema(description = "待发货订单数量")
    private Integer unShippedCount;
}
