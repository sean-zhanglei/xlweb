package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 充值响应对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="充值响应对象")
public class UserRechargeFrontResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "充值套餐列表")
    private List<UserRechargeItemResponse> rechargeQuota;

    @Schema(description = "注意事项")
    private List<String> rechargeAttention;
}
