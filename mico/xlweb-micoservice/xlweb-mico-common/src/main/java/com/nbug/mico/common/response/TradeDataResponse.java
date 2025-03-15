package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易统计数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="交易统计数据对象")
public class TradeDataResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "今日订单数量")
    private Integer todayOrderNum;

    @Schema(description = "昨日订单数量")
    private Integer yesterdayOrderNum;

    @Schema(description = "昨日交易数量日环比")
    private String yesterdayOrderNumRatio;

    @Schema(description = "昨日交易金额")
    private BigDecimal yesterdayOrderSales;

    @Schema(description = "昨日交易金额日环比")
    private String yesterdayOrderSalesRatio;

    @Schema(description = "本月交易数量")
    private Integer monthOrderNum;

    @Schema(description = "本月交易数量月环比")
    private String monthOrderNumRatio;

    @Schema(description = "本月交易金额")
    private BigDecimal monthOrderSales;

    @Schema(description = "本月交易金额月环比")
    private String monthOrderSalesRatio;

}
