package com.nbug.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单九宫格数据

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="StoreOrderTopItemResponse对象", description="订单九宫格数据")
public class StoreOrderTopItemResponse implements Serializable {

    private static final long serialVersionUID = -7583407020447771557L;

    @ApiModelProperty(value = "订单数量")
    private Integer count;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "微信支付金额")
    private BigDecimal weChatAmount;

    @ApiModelProperty(value = "余额支付")
    private BigDecimal yueAmount;
}
