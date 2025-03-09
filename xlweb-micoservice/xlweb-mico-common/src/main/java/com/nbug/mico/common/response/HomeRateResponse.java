package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 主页统计数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="主页统计数据对象")
public class HomeRateResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "今日销售额")
    private Object sales;

    @Schema(description = "昨日销售额")
    private Object yesterdaySales;

    @Schema(description = "今日访问量")
    private Object pageviews;

    @Schema(description = "昨日访问量")
    private Object yesterdayPageviews;

    @Schema(description = "今日订单量")
    private Object orderNum;

    @Schema(description = "昨日订单量")
    private Object yesterdayOrderNum;

    @Schema(description = "今日新增用户")
    private Object newUserNum;

    @Schema(description = "昨日新增用户")
    private Object yesterdayNewUserNum;


}
