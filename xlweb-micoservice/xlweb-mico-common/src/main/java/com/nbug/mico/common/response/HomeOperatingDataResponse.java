package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 首页经营数据响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="首页经营数据响应对象")
public class HomeOperatingDataResponse implements Serializable {

    private static final long serialVersionUID = -1486435421582495511L;

    @Schema(description = "待发货订单数量")
    private Integer notShippingOrderNum;

    @Schema(description = "退款中订单数量")
    private Integer refundingOrderNum;

    @Schema(description = "待核销订单数量")
    private Integer notWriteOffOrderNum;

    @Schema(description = "库存预警商品数量")
    private Integer vigilanceInventoryNum;

    @Schema(description = "上架商品数量")
    private Integer onSaleProductNum;

    @Schema(description = "仓库中商品数量")
    private Integer notSaleProductNum;

    @Schema(description = "提现申请待审核数量")
    private Integer notAuditNum;

    @Schema(description = "用户充值总金额")
    private BigDecimal totalRechargeAmount;

}
