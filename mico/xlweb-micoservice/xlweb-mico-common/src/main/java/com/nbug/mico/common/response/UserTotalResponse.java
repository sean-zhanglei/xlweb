package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户总数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户总数据对象")
public class UserTotalResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "累计用户数")
    private Integer userNum;

    @Schema(description = "累计充值人数")
    private Integer rechargePeopleNum;

    @Schema(description = "累计充值金额（佣金转余额也算）")
    private BigDecimal rechargeTotalAmount;

    @Schema(description = "累计消费金额")
    private BigDecimal consumptionAmount;

}
