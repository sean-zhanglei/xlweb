package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 推广佣金明细

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="推广佣金明细")
public class UserCommissionResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "昨天的佣金")
    private BigDecimal lastDayCount = BigDecimal.ZERO;

    @Schema(description = "累计提现金额")
    private BigDecimal extractCount = BigDecimal.ZERO;

    @Schema(description = "当前佣金")
    private BigDecimal commissionCount = BigDecimal.ZERO;
}
