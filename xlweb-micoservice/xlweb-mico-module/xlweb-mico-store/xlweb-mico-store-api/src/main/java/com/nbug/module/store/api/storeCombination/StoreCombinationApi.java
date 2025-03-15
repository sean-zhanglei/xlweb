package com.nbug.module.store.api.storeOrder;

import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 订单")
public interface StoreOrderApi {

    String PREFIX = ApiConstants.PREFIX + "/order";

    @GetMapping(PREFIX + "/getByOderId")
    @Operation(summary = "获取订单ById")
    @Parameter(name = "orderId", description = "订单Id", required = true)
    public CommonResult<StoreOrder> getByOderId(String orderId);

    @PostMapping(PREFIX + "/updateById")
    @Operation(summary = "更新订单ById")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> updateById(StoreOrder storeOrder);

}
