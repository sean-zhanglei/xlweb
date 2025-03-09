package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单状态图片

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单状态图片")
public class SystemGroupDataOrderStatusPicResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单状态")
    private int orderStatus;

    @Schema(description = "图片地址")
    private String url;

}
