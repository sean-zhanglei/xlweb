package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户领取优惠券请求对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="用户领取优惠券请求对象")
public class UserCouponReceiveRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "优惠券id")
    @NotNull(message = "优惠券编号不能为空")
    private Integer couponId;
}
