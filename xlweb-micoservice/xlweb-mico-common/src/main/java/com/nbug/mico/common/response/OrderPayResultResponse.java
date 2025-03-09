package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.AliPayJsResultVo;
import com.nbug.mico.common.vo.WxPayJsResultVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单支付结果 Response

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单支付结果响应对象")
public class OrderPayResultResponse {

    private static final long serialVersionUID=1L;

    @Schema(description = "支付状态")
    private Boolean status;

    @Schema(description = "微信调起支付参数对象")
    private WxPayJsResultVo jsConfig;

    @Schema(description = "支付类型")
    private String payType;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单支付宝支付")
    private String alipayRequest;

    @Schema(description = "支付宝调起支付参数对象（app支付专用）")
    private AliPayJsResultVo aliPayConfig;
}
