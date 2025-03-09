package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * StoreOrderStatisticsResponse

 */
@Data
public class StoreOrderStatisticsResponse {
    @Schema(description = "订单数图标数据")
    private List<StoreOrderStatisticsChartItemResponse> chart; // 订单数图标数据

    @Schema(description = "时间区间增长率")
    private Integer growthRate; // 时间区间增长率

    @Schema(description = "同比")
    private String increaseTime;

    @Schema(description = "同比上个时间区间增长营业额 1=增长，2=减少")
    private Integer increaseTimeStatus; // 同比上个时间区间增长营业额 1=增长，2=减少

    @Schema(description = "时间区间订单数")
    private BigDecimal time;
}
