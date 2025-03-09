package com.nbug.mico.common.response;

import com.nbug.mico.common.page.CommonPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="核销订单")
public class SystemWriteOffOrderResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单总数量")
    private Long total = 0L;

    @Schema(description = "订单总金额")
    private BigDecimal orderTotalPrice;

    @Schema(description = "退款总金额")
    private BigDecimal refundTotalPrice;

    @Schema(description = "退款总单数")
    private Integer refundTotal = 0;

    @Schema(description = "订单列表")
    private CommonPage<StoreOrderItemResponse> list;
}
