package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单列表请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_order")
@Schema( description="订单列表请求对象")
public class StoreOrderSearchRequest implements Serializable {
    private static final long serialVersionUID=1L;

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "创建时间区间")
    private String dateLimit;

    @Schema(description = "订单状态（all 总数； 未支付 unPaid； 未发货 notShipped；待收货 spike；待评价 bargain；已完成 complete；待核销 toBeWrittenOff；退款中:refunding；已退款:refunded；已删除:deleted")
    @StringContains(limitValues = {"all","unPaid","notShipped","spike","bargain","complete","toBeWrittenOff","refunding","refunded","deleted"}, message = "未知的订单状态")
    private String status;

    @Schema(description = "订单类型：0普通订单，1-视频号订单, 2-全部订单")
    @NotNull(message = "订单类型不能为空")
    @Range(min = 0, max = 2, message = "未知的订单类型")
    private Integer type;
}
