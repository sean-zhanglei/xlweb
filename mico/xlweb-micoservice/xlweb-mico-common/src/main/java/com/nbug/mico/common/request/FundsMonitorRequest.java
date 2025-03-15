package com.nbug.mico.common.request;

import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 资金监控

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="资金监控")
public class FundsMonitorRequest implements Serializable {

    private static final long serialVersionUID = 3362714265772774491L;

    @Schema(description = "搜索关键字")
    private String keywords;

    @Schema(description = "添加时间")
    private String dateLimit;

    @Schema(description = "明细类型:recharge-充值支付，admin-后台操作，productRefund商品退款，payProduct购买商品")
    @StringContains(limitValues = {"recharge", "admin", "productRefund", "payProduct"}, message = "请选择正确的明细类型")
    private String title;

}
