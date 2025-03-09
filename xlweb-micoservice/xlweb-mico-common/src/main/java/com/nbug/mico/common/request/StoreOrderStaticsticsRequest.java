package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nbug.mico.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单统计详情request

 */
@Data
public class StoreOrderStaticsticsRequest {
    @Schema(description = "页码", example= Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

    @Schema(description = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;

    @Schema(description = "today,yesterday,lately7,lately30,month,year,/yyyy-MM-dd hh:mm:ss,yyyy-MM-dd hh:mm:ss/")
    private String dateLimit;

    @JsonIgnore
    private String startTime;

    @JsonIgnore
    private String endTime;
}
