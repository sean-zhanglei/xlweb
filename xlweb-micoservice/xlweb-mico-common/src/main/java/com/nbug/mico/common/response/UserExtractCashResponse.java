package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 提现用户信息响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="提现用户信息响应对象")
public class UserExtractCashResponse implements Serializable {
    public UserExtractCashResponse(){}
    public UserExtractCashResponse(String minPrice, BigDecimal commissionCount, BigDecimal brokenCommission, String brokenDay) {
        this.minPrice = minPrice;
        this.commissionCount = commissionCount;
        this.brokenCommission = brokenCommission;
        this.brokenDay = brokenDay;
    }

    private static final long serialVersionUID=1L;

//    @Schema(description = "提现银行")
//    private List<String> extractBank;

    @Schema(description = "提现最低金额")
    private String minPrice;

    @Schema(description = "可提现佣金")
    private BigDecimal commissionCount;

    @Schema(description = "冻结佣金")
    private BigDecimal brokenCommission;

    @Schema(description = "冻结天数")
    private String brokenDay;
}
