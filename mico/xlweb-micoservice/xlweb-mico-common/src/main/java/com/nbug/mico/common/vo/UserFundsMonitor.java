package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户充值对象类

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="佣金")
public class UserFundsMonitor implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "充值用户UID")
    private Integer uid;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "账户余额")
    private BigDecimal nowMoney;

    @Schema(description = "账户佣金")
    private BigDecimal brokerage;

    @Schema(description = "账户总佣金")
    private BigDecimal totalBrokerage;

    @Schema(description = "提现总金额")
    private BigDecimal totalExtract;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "推广员UID")
    private Integer spreadUid;

    @Schema(description = "推广员昵称")
    private String spreadName;
}
