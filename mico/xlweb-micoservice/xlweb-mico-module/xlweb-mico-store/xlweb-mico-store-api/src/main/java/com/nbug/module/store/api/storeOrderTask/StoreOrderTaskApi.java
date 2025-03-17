package com.nbug.module.store.api.storeOrderTask;

import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 订单任务")
public interface StoreOrderTaskApi {

    String PREFIX = ApiConstants.PREFIX + "/storeOrderTask";


    @PostMapping(PREFIX + "/cancelByUser")
    @Operation(summary = "取消订单任务")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> cancelByUser(@RequestParam StoreOrder storeOrder);

    @PostMapping(PREFIX + "/refundOrder")
    @Operation(summary = "退款订单任务")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> refundOrder(@RequestParam StoreOrder storeOrder);

    @PostMapping(PREFIX + "/complete")
    @Operation(summary = "完成订单任务")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> complete(@RequestParam StoreOrder storeOrder);

    @PostMapping(PREFIX + "/orderReceiving")
    @Operation(summary = "收货订单任务")
    @Parameter(name = "orderId", description = "订单Id", required = true)
    public CommonResult<Boolean> orderReceiving(@RequestParam Integer orderId);

    @PostMapping(PREFIX + "/autoCancel")
    @Operation(summary = "自动取消订单任务")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> autoCancel(@RequestParam StoreOrder storeOrder);

}
