package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 充值套餐响应对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="充值套餐响应对象")
public class UserRechargeItemResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "充值模板id")
    private Integer id;

    @Schema(description = "充值金额")
    private String price;

    @Schema(description = "赠送金额")
    private String giveMoney;

}
