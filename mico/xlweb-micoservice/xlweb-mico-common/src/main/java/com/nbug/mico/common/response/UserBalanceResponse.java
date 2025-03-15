package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户资金统计

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户资金统计")
public class UserBalanceResponse implements Serializable {
    public UserBalanceResponse(){}
    public UserBalanceResponse(BigDecimal nowMoney, BigDecimal recharge, BigDecimal orderStatusSum) {
        this.nowMoney = nowMoney;
        this.recharge = recharge;
        this.orderStatusSum = orderStatusSum;
    }

    private static final long serialVersionUID=1L;


    @Schema(description = "当前总资金")
    private BigDecimal nowMoney;

    @Schema(description = "累计充值")
    private BigDecimal recharge;

    @Schema(description = "累计消费")
    private BigDecimal orderStatusSum;

}
