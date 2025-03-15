package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 砍价商品ExeclVo对象

 */
@Data
public class BargainProductExcelVo {

    @Schema(description = "砍价活动名称")
    private String title;

    @Schema(description = "砍价活动简介")
    private String info;

    @Schema(description = "砍价金额")
    private String price;

    @Schema(description = "用户每次砍价的次数")
    private Integer bargainNum;

    @Schema(description = "砍价状态 0(到砍价时间不自动开启)  1(到砍价时间自动开启时间)")
    private String status;

    @Schema(description = "砍价开启时间")
    private String startTime;

    @Schema(description = "砍价结束时间")
    private String stopTime;

    @Schema(description = "销量")
    private Integer sales;

    @Schema(description = "库存剩余")
    private Integer quotaShow;

    @Schema(description = "反多少积分")
    private BigDecimal giveIntegral;

    @Schema(description = "添加时间")
    private String addTime;
}
