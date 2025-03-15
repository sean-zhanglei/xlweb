package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单操作记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="订单操作记录公共查询对象")
public class StoreOrderStatusSearchRequest {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单编号")
    private String orderNo;
}
