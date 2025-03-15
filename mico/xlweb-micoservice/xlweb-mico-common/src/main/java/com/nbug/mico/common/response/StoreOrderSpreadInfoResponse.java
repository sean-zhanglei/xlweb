package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="推广人信息")
public class StoreOrderSpreadInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "推广人id")
    private Integer id = 0;

    @Schema(description = "推广人姓名")
    private String name = "";


}
