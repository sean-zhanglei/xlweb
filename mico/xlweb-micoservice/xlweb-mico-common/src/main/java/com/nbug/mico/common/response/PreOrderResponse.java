package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.OrderInfoVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 预下单响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="预下单响应对象")
public class PreOrderResponse implements Serializable {

    private static final long serialVersionUID = 7282892323898493847L;

    @Schema(description = "订单详情对象")
    private OrderInfoVo orderInfoVo;

    @Schema(description = "预下单订单号")
    private String preOrderNo;

    @Schema(description = "门店自提是否开启")
    private String storeSelfMention;

    @Schema(description = "余额支付 1 开启 2 关闭")
    private String yuePayStatus;

    @Schema(description = "微信支付 1 开启 0 关闭")
    private String payWeixinOpen;

    @Schema(description = "支付宝支付 1 开启 0 关闭")
    private String aliPayStatus;

}
