package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 计算订单价格请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="计算订单价格请求对象")
public class OrderComputedPriceRequest {

    @Schema(description = "预下单订单号")
    @NotBlank(message = "预下单订单号不能为空")
    private String preOrderNo;

    @Schema(description = "地址id")
    private Integer addressId;

    @Schema(description = "优惠券id")
    private Integer couponId;

    @Schema(description = "快递类型: 1-快递配送，2-到店自提")
    @NotNull(message = "快递类型不能为空")
    @Range(min = 1, max = 2, message = "未知的快递类型")
    private Integer shippingType;

    @Schema(description = "是否使用积分")
    @NotNull(message = "是否使用积分不能为空")
    private Boolean useIntegral;

}
