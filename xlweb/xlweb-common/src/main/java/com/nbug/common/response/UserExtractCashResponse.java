package com.nbug.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提现用户信息响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserExtractCashResponse对象", description="提现用户信息响应对象")
public class UserExtractCashResponse implements Serializable {
    public UserExtractCashResponse(){}
    public UserExtractCashResponse(String minPrice, BigDecimal commissionCount, BigDecimal brokenCommission, String brokenDay) {
        this.minPrice = minPrice;
        this.commissionCount = commissionCount;
        this.brokenCommission = brokenCommission;
        this.brokenDay = brokenDay;
    }

    private static final long serialVersionUID=1L;

//    @ApiModelProperty(value = "提现银行")
//    private List<String> extractBank;

    @ApiModelProperty(value = "提现最低金额")
    private String minPrice;

    @ApiModelProperty(value = "可提现佣金")
    private BigDecimal commissionCount;

    @ApiModelProperty(value = "冻结佣金")
    private BigDecimal brokenCommission;

    @ApiModelProperty(value = "冻结天数")
    private String brokenDay;
}
