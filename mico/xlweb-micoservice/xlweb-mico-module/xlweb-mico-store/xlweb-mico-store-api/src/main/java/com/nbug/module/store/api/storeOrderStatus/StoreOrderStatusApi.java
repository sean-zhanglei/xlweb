package com.nbug.module.store.api.storeOrderStatus;

import com.nbug.mico.common.model.order.StoreOrderStatus;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 订单操作记录")
public interface StoreOrderStatusApi {

    String PREFIX = ApiConstants.PREFIX + "/orderStatus";

    @PostMapping(PREFIX + "/createLog")
    @Operation(summary = "创建订单操作记录")
    @Parameters({
            @Parameter(name = "orderId", description = "订单Id", required = true),
            @Parameter(name = "type", description = "类型", required = true),
            @Parameter(name = "message", description = "消息", required = true)
    })
    public CommonResult<Boolean> createLog(@RequestParam Integer orderId,
                                           @RequestParam String type,
                                           @RequestParam String message);


    @GetMapping(PREFIX + "/getByEntity")
    @Operation(summary = "根据实体参数获取")
    @Parameter(name = "storeOrderStatus", description = "订单操作记录", required = true)
    public CommonResult<List<StoreOrderStatus>> getByEntity(@RequestParam StoreOrderStatus storeOrderStatus);


    @GetMapping(PREFIX + "/getLastByOrderId")
    @Operation(summary = "根据订单Id获取最后一个")
    @Parameter(name = "orderId", description = "订单Id", required = true)
    public CommonResult<StoreOrderStatus> getLastByOrderId(@RequestParam Integer orderId);

}
