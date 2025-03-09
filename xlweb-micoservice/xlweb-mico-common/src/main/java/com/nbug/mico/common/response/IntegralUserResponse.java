package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户积分响应对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户积分响应对象")
public class IntegralUserResponse implements Serializable {

    private static final long serialVersionUID=1L;

//    @Schema(description = "用户id")
//    private Integer uid;
//
//    @Schema(description = "用户昵称")
//    private String nickname;
//
//    @Schema(description = "用户头像")
//    private String avatar;
//
//    @Schema(description = "用户余额")
//    private BigDecimal nowMoney;
//
    @Schema(description = "用户剩余积分")
    private Integer integral;
//
//    @Schema(description = "连续签到天数")
//    private Integer signNum;
//
//    @Schema(description = "是否为推广员")
//    private Boolean isPromoter;
//
//    @Schema(description = "用户购买次数")
//    private Integer payCount;
//
//    @Schema(description = "下级人数")
//    private Integer spreadCount;
//
//    @Schema(description = "累计签到次数")
//    private Integer sumSignDay;
//
//    @Schema(description = "今天是否签到")
//    private Boolean isDaySign;
//
//    @Schema(description = "昨天是否签到")
//    private Boolean isYesterdaySign;

    @Schema(description = "累计总积分")
    private Integer sumIntegral;

    @Schema(description = "累计抵扣积分")
    private Integer deductionIntegral;

//    @Schema(description = "今日获得累计积分")
//    private Integer nowIntegral;

    @Schema(description = "冻结的积分")
    private Integer frozenIntegral;

}
