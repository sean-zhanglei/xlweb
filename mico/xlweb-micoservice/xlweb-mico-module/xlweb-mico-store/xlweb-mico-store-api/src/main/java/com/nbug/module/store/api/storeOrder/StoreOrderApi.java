package com.nbug.module.store.api.storeOrder;

import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderSearchRequest;
import com.nbug.mico.common.request.SystemWriteOffOrderSearchRequest;
import com.nbug.mico.common.response.OrderBrokerageData;
import com.nbug.mico.common.response.StoreOrderDetailResponse;
import com.nbug.mico.common.response.SystemWriteOffOrderResponse;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
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


    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取订单ById")
    @Parameter(name = "id", description = "id", required = true)
    public CommonResult<StoreOrder> getById(Integer id);

    @GetMapping(PREFIX + "/getByEntityOne")
    @Operation(summary = "根据属性仅仅获取一条")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<StoreOrder> getByEntityOne(StoreOrder storeOrder);

    @PostMapping(PREFIX + "/updateBatchById")
    @Operation(summary = "批量更新订单ById")
    @Parameters({
            @Parameter(name = "storeOrders", description = "订单", required = true),
            @Parameter(name = "batchSize", description = "批量大小", required = true)
    })
    public CommonResult<Boolean> updateBatchById(List<StoreOrder> storeOrders, Integer batchSize);


    @GetMapping(PREFIX + "/getUserOrderList")
    @Operation(summary = "获取用户订单列表")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "status", description = "订单状态", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<List<StoreOrder>> getUserOrderList(Integer uid, Integer status, PageParamRequest pageParamRequest);


    @GetMapping(PREFIX + "/getOrderCountByUid")
    @Operation(summary = "获取用户订单数量")
    @Parameter(name = "uid", description = "用户id", required = true)
    public CommonResult<Integer> getOrderCountByUid(Integer uid);

    @GetMapping(PREFIX + "/getTopDataUtil")
    @Operation(summary = "获取订单统计数据")
    @Parameters({
            @Parameter(name = "status", description = "订单状态", required = true),
            @Parameter(name = "userId", description = "用户id", required = true)
    })
    public CommonResult<Integer> getTopDataUtil(Integer status, Integer userId);

    @GetMapping(PREFIX + "/getSumPayPriceByUid")
    @Operation(summary = "获取用户订单数量")
    @Parameter(name = "uid", description = "用户id", required = true)
    public CommonResult<BigDecimal> getSumPayPriceByUid(Integer uid);

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建订单")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<Boolean> create(StoreOrder storeOrder);

    @GetMapping(PREFIX + "/getUserCurrentDaySecKillOrders")
    @Operation(summary = "获取用户当前秒杀订单")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "seckillId", description = "秒杀id", required = true)
    })
    public CommonResult<List<StoreOrder>> getUserCurrentDaySecKillOrders( Integer uid, Integer seckillId);

    @GetMapping(PREFIX + "/getByBargainOrder")
    @Operation(summary = "获取用户当前砍价订单")
    @Parameters({
            @Parameter(name = "bargainId", description = "砍价id", required = true),
            @Parameter(name = "bargainUserId", description = "砍价用户id", required = true)
    })
    public CommonResult<StoreOrder> getByBargainOrder(Integer bargainId, Integer bargainUserId);

    @GetMapping(PREFIX + "/getUserCurrentBargainOrders")
    @Operation(summary = "获取用户当前砍价订单")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "bargainId", description = "砍价id", required = true)
    })
    public CommonResult<List<StoreOrder>> getUserCurrentBargainOrders( Integer uid, Integer bargainId);

    @GetMapping(PREFIX + "/getUserCurrentCombinationOrders")
    @Operation(summary = "获取用户当前拼团订单")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "combinationId", description = "拼团id", required = true)
    })
    public CommonResult<List<StoreOrder>> getUserCurrentCombinationOrders(Integer uid, Integer combinationId);


    @GetMapping(PREFIX + "/findIdAndUidListByReceipt")
    @Operation(summary = "获取所有收货订单id集合")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "combinationId", description = "拼团id", required = true)
    })
    public CommonResult<List<StoreOrder>> findIdAndUidListByReceipt();

    @PostMapping(PREFIX + "/updatePaid")
    @Operation(summary = "更新订单支付状态")
    @Parameter(name = "orderNo", description = "订单号", required = true)
    public CommonResult<Boolean> updatePaid(String orderNo);

    @GetMapping(PREFIX + "/getAdminList")
    @Operation(summary = "获取订单列表")
    @Parameters({
            @Parameter(name = "request", description = "查询条件", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<CommonPage<StoreOrderDetailResponse>> getAdminList(StoreOrderSearchRequest request, PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getInfoByEntity")
    @Operation(summary = "获取订单信息")
    @Parameter(name = "storeOrder", description = "订单", required = true)
    public CommonResult<StoreOrder> getInfoByEntity(StoreOrder storeOrder);

    @GetMapping(PREFIX + "/getSpreadOrderTotalPriceByOrderList")
    @Operation(summary = "获取推广订单总金额")
    @Parameter(name = "orderNoList", description = "订单号集合", required = true)
    public CommonResult<BigDecimal> getSpreadOrderTotalPriceByOrderList(List<String> orderNoList);

    @GetMapping(PREFIX + "/getBrokerageData")
    @Operation(summary = "获取佣金相关数据")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "spreadId", description = "推广人id", required = true)
    })
    public CommonResult<OrderBrokerageData> getBrokerageData(Integer uid, Integer spreadId);

    @GetMapping(PREFIX + "/getMapInOrderNo")
    @Operation(summary = "跟据订单号列表获取订单列表Map")
    @Parameter(name = "orderNoList", description = "订单号集合", required = true)
    public CommonResult<Map<String, StoreOrder>> getMapInOrderNo(List<String> orderNoList);

    @GetMapping(PREFIX + "/getSumPayPriceByUidAndDate")
    @Operation(summary = "获取用户消费金额(时间)")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true),
            @Parameter(name = "date", description = "时间", required = true)
    })
    public CommonResult<BigDecimal> getSumPayPriceByUidAndDate(Integer userId, String date);

    @GetMapping(PREFIX + "/getOrderCountByUidAndDate")
    @Operation(summary = "获取订单数量(时间)")
    @Parameters({
            @Parameter(name = "uid", description = "用户id", required = true),
            @Parameter(name = "date", description = "时间", required = true)
    })
    public CommonResult<Integer> getOrderCountByUidAndDate(Integer uid, String date);

    @GetMapping(PREFIX + "/findPaidListByUid")
    @Operation(summary = "获取用户已支付的订单列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<List<StoreOrder>> findPaidListByUid(Integer userId, PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getWriteOffList")
    @Operation(summary = "获取核销订单列表")
    @Parameters({
            @Parameter(name = "request", description = "查询条件", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<SystemWriteOffOrderResponse> getWriteOffList(SystemWriteOffOrderSearchRequest request, PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getOrderGroupByDate")
    @Operation(summary = "按开始结束时间分组订单")
    @Parameters({
            @Parameter(name = "dateLimit", description = "时间范围", required = true),
            @Parameter(name = "lefTime", description = "时间类型", required = true)
    })
    public CommonResult<List<StoreOrder>> getOrderGroupByDate(String dateLimit, int lefTime);


    @GetMapping(PREFIX + "/getPayOrderAmountByDate")
    @Operation(summary = "通过日期获取支付订单金额")
    @Parameter(name = "date", description = "时间", required = true)
    public CommonResult<BigDecimal> getPayOrderAmountByDate(String date);

    @GetMapping(PREFIX + "/getOrderNumByDate")
    @Operation(summary = "通过日期获取订单数量")
    @Parameter(name = "date", description = "时间", required = true)
    public CommonResult<Integer> getOrderNumByDate(String date);


    @GetMapping(PREFIX + "/getTradetop")
    @Operation(summary = "交易统计-顶部数据")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getTradetop(String time);

    @GetMapping(PREFIX + "/getTradeBottom")
    @Operation(summary = "交易统计-底部数据")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getTradeBottom(String time);

    @GetMapping(PREFIX + "/getOrderBasic")
    @Operation(summary = "交易统计-订单基础数据")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getOrderBasic(String time);

    @GetMapping(PREFIX + "/getOrderTrend")
    @Operation(summary = "交易统计-订单趋势")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getOrderTrend(String time);

    @GetMapping(PREFIX + "/getOrderChannel")
    @Operation(summary = "交易统计-订单渠道")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getOrderChannel(String time);

    @GetMapping(PREFIX + "/getOrderType")
    @Operation(summary = "交易统计-订单类型")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getOrderType(String time);
}
