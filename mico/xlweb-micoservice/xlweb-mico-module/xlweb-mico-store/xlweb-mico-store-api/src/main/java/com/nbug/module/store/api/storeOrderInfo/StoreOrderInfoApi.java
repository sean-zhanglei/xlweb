package com.nbug.module.store.api.storeOrderInfo;

import com.nbug.mico.common.model.order.StoreOrderInfo;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.mico.common.vo.StoreOrderInfoVo;
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
@Tag(name = "RPC 服务 - 订单详情")
public interface StoreOrderInfoApi {

    String PREFIX = ApiConstants.PREFIX + "/order";

    @GetMapping(PREFIX + "/getOrderListByOrderId")
    @Operation(summary = "获取订单详情")
    @Parameter(name = "orderId", description = "订单Id", required = true)
    public CommonResult<List<StoreOrderInfoOldVo>> getOrderListByOrderId(@RequestParam Integer orderId);


    @GetMapping(PREFIX + "/getListByOrderNo")
    @Operation(summary = "获取订单详情-订单编号")
    @Parameter(name = "orderNo", description = "订单号", required = true)
    public CommonResult<List<StoreOrderInfo>> getListByOrderNo(@RequestParam String orderNo);


    @GetMapping(PREFIX + "/getVoListByOrderId")
    @Operation(summary = "获取订单详情vo列表")
    @Parameter(name = "orderId", description = "订单Id", required = true)
    public CommonResult<List<StoreOrderInfoVo>> getVoListByOrderId(@RequestParam Integer orderId);

    @GetMapping(PREFIX + "/getByUniAndOrderId")
    @Operation(summary = "通过订单编号和规格号查询")
    @Parameters({
            @Parameter(name = "uni", description = "唯一标识", required = true),
            @Parameter(name = "orderId", description = "订单Id", required = true)
    })
    public CommonResult<StoreOrderInfo> getByUniAndOrderId(@RequestParam String uni,
                                                           @RequestParam Integer orderId);

    @PostMapping(PREFIX + "/saveOrderInfos")
    @Operation(summary = "保存订单详情")
    @Parameter(name = "storeOrderInfos", description = "订单详情列表", required = true)
    public CommonResult<Boolean> saveOrderInfos(@RequestParam List<StoreOrderInfo> storeOrderInfos);

}
