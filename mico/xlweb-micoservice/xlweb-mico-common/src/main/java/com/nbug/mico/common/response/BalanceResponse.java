package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户地址表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="提现金额")
public class BalanceResponse implements Serializable {

    public BalanceResponse() {}
    public BalanceResponse(BigDecimal withdrawn, BigDecimal unDrawn, BigDecimal commissionTotal, BigDecimal toBeWithdrawn) {
        this.withdrawn = withdrawn;
        this.unDrawn = unDrawn;
        this.commissionTotal = commissionTotal;
        ToBeWithdrawn = toBeWithdrawn;
    }
    private static final long serialVersionUID=1L;

    @Schema(description = "已提现")
    private BigDecimal withdrawn;

    @Schema(description = "未提现")
    private BigDecimal unDrawn;

    @Schema(description = "佣金总金额")
    private BigDecimal commissionTotal;

    @Schema(description = "待提现")
    private BigDecimal ToBeWithdrawn;

}
