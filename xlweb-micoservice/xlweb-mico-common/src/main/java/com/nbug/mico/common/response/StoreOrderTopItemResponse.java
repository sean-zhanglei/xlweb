package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单九宫格数据

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单九宫格数据")
public class StoreOrderTopItemResponse implements Serializable {

    private static final long serialVersionUID = -7583407020447771557L;

    @Schema(description = "订单数量")
    private Integer count;

    @Schema(description = "订单金额")
    private BigDecimal amount;

    @Schema(description = "微信支付金额")
    private BigDecimal weChatAmount;

    @Schema(description = "余额支付")
    private BigDecimal yueAmount;
}
