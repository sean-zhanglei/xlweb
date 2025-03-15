package com.nbug.module.order.api;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.OrderRefundApplyRequest;
import com.nbug.module.order.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 订单")
public interface OrderApi {

    String PREFIX = ApiConstants.PREFIX + "/order";

    @PostMapping(PREFIX + "/refundApply")
    @Operation(summary = "订单退款申请")
    @Parameter(name = "request", description = "申请退款参数", required = true)
    public CommonResult<Boolean> refundApply(OrderRefundApplyRequest request);

    @PostMapping(PREFIX + "/refundApplyTask")
    @Operation(summary = "订单退款申请任务")
    @Parameter(name = "applyList", description = "申请退款参数", required = true)
    public CommonResult<Boolean> refundApplyTask(List<OrderRefundApplyRequest> applyList);
}
