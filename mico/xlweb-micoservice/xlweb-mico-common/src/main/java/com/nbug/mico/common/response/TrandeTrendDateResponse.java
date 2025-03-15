package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易概览数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="交易概览数据对象")
public class TrandeTrendDateResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "营业额")
    private BigDecimal turnover;

    @Schema(description = "商品支付金额")
    private BigDecimal proPayAmount;

    @Schema(description = "充值金额")
    private BigDecimal rechargeAmount;

    @Schema(description = "支出金额")
    private BigDecimal payoutAmount;

}
