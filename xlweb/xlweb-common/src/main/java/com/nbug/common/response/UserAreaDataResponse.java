package com.nbug.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户区域数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserAreaDataResponse对象", description="用户区域数据对象")
public class UserAreaDataResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @ApiModelProperty(value = "区域（省份+其他）")
    private String area;

    @ApiModelProperty(value = "累计用户数量")
    private Integer userNum;

    @ApiModelProperty(value = "成交用户数量")
    private Integer payUserNum;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payAmount;

}
