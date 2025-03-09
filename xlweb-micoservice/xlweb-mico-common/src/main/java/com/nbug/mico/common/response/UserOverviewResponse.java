package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户概览数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户概览数据对象")
public class UserOverviewResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "注册用户数")
    private Integer registerNum;

    @Schema(description = "注册用户数环比")
    private String registerNumRatio;

    @Schema(description = "活跃用户数")
    private Integer activeUserNum;

    @Schema(description = "活跃用户数环比")
    private String activeUserNumRatio;

    @Schema(description = "充值用户数")
    private Integer rechargeUserNum;

    @Schema(description = "充值用户数环比")
    private String rechargeUserNumRatio;

    @Schema(description = "浏览量")
    private Integer pageviews;

    @Schema(description = "下单用户数量")
    private Integer orderUserNum;

    @Schema(description = "成交用户数量")
    private Integer orderPayUserNum;

    @Schema(description = "成交金额")
    private BigDecimal payOrderAmount;

    @Schema(description = "客单价")
    private BigDecimal customerPrice;

}
