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
public class TradingDataResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "营业额")
    private BigDecimal turnover;

    @Schema(description = "营业额环比")
    private String turnoverRatio;

    @Schema(description = "商品支付金额")
    private BigDecimal proPayAmount;

    @Schema(description = "商品支付金额环比")
    private String proPayAmountRatio;

    @Schema(description = "充值金额")
    private BigDecimal rechargeAmount;

    @Schema(description = "充值金额环比")
    private String rechargeAmountRatio;

    @Schema(description = "支出金额")
    private BigDecimal payoutAmount;

    @Schema(description = "支出金额环比")
    private String payoutAmountRatio;

    @Schema(description = "余额支付金额")
    private BigDecimal balanceAmount;

    @Schema(description = "余额支付金额月环比")
    private String balanceAmountRatio;

    @Schema(description = "支付佣金金额")
    private BigDecimal payoutBrokerageAmount;

    @Schema(description = "支付佣金金额环比")
    private String payoutBrokerageAmountRatio;

    @Schema(description = "商品退款金额")
    private BigDecimal proRefundAmount;

    @Schema(description = "商品退款金额环比")
    private String proRefundAmountRatio;

}
