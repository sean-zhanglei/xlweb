package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单状态数量

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="订单状态数量")
public class StoreOrderCountItemResponse implements Serializable {

    private static final long serialVersionUID = -8605913636959651047L;

    @Schema(description = "总数")
    private Integer all;

    @Schema(description = "未支付")
    private Integer unPaid;

    @Schema(description = "未发货")
    private Integer notShipped;

    @Schema(description = "待收货")
    private Integer spike;

    @Schema(description = "待评价")
    private Integer bargain;

    @Schema(description = "交易完成")
    private Integer complete;

    @Schema(description = "待核销")
    private Integer toBeWrittenOff;

    @Schema(description = "退款中")
    private Integer refunding;

    @Schema(description = "已退款")
    private Integer refunded;

    @Schema(description = "0 未退款 1 申请中 2 已退款")
    private Integer refundStatus;

    @Schema(description = "已删除")
    private Integer deleted;
}
