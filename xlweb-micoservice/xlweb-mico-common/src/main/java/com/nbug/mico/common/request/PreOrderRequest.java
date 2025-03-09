package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 预下单请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="预下单请求对象")
public class PreOrderRequest {

    @Schema(description = "预下单类型（“shoppingCart”：购物车下单，“buyNow”：立即购买，”again“： 再次购买，”video“: 视频号商品下单）")
    @NotBlank(message = "预下单类型不能为空")
    private String preOrderType;

    @Schema(description = "订单详情列表")
    private List<PreOrderDetailRequest> orderDetails;

}
