package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 视频订单发货对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="视频订单发货对象")
public class VideoOrderSendRequest {
    private static final long serialVersionUID=1L;

    @Schema(description = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "快递公司ID")
    @NotBlank(message = "快递公司ID不能为空")
    private String deliveryId;

    @Schema(description = "快递单号")
    @NotBlank(message = "快递单号不能为空")
    private String waybillId;
}
