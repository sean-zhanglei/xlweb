package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.FundsMonitorSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userIntegralRecord.UserIntegralRecordApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 统计 -- 主页 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/statistic")
@Tag(name = "管理后台 - 统计 -- APIS")
public class StatisticsController {

    @Autowired
    private UserIntegralRecordApi userIntegralRecordApi;

    @Autowired
    private UserBillApi userBillApi;

    @Autowired
    private StoreOrderApi storeOrderApi;

    /**
     * 积分统计
     * '/integral/get_basic',
     * '/integral/get_trend',
     * '/integral/get_channel',
     * '/integral/get_type',
     * @return
     */

    /**
     * 年订单量趋势
     */
//    @PreAuthorize("hasAuthority('admin:statistics:integral:get_basic')")
    @Operation(summary = "积分统计顶部")
    @RequestMapping(value = "/integral/get_basic", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getIntegralBasic(@RequestParam @Validated String time) {
        return CommonResult.success(userIntegralRecordApi.getIntegralBasic(time).getCheckedData().getColumns());
    }

    //    @PreAuthorize("hasAuthority('admin:statistics:integral:get_basic')")
    @Operation(summary = "积分统计折线图")
    @RequestMapping(value = "/integral/get_trend", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getIntegralTrend(@RequestParam @Validated String time) {
        return CommonResult.success(userIntegralRecordApi.getIntegralTrend(time).getCheckedData().getColumns());
    }

    //    @PreAuthorize("hasAuthority('admin:statistics:integral:get_basic')")
    @Operation(summary = "积分来源分析")
    @RequestMapping(value = "/integral/get_channel", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getIntegralChannel(@RequestParam @Validated String time) {
        return CommonResult.success(userIntegralRecordApi.getIntegralChannel(time).getCheckedData().getColumns());
    }

    //    @PreAuthorize("hasAuthority('admin:statistics:integral:get_basic')")
    @Operation(summary = "积分消耗分析")
    @RequestMapping(value = "/integral/get_type", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getIntegralType(@RequestParam @Validated String time) {
        return CommonResult.success(userIntegralRecordApi.getIntegralType(time).getCheckedData().getColumns());
    }

    /**
     * 3、交易统计
     * /statistic/trade/top_trade?time=today
     * /statistic/trade/bottom_trade?data=2025%2F01%2F27-2025%2F02%2F25
     */

    @Operation(summary = "交易数据 今天")
    @RequestMapping(value = "/trade/top_trade", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getTradetop(@RequestParam @Validated String time) {
        return CommonResult.success(storeOrderApi.getTradetop(time).getCheckedData().getColumns());
    }

    @Operation(summary = "交易概括")
    @RequestMapping(value = "/trade/bottom_trade", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getTradeBottom(@RequestParam @Validated String data) {
        return CommonResult.success(storeOrderApi.getTradeBottom(data).getCheckedData().getColumns());
    }

    /**
     * 1、账单记录
     * /statistic/flow/get_record?type=day&time=&page=1&limit=15
     * /statistic/flow/get_list?type=0&time=&keywords=&page=1&limit=20&ids[]=5114&ids[]=5115&ids[]=5117
     *
     */
    @Operation(summary = "账单记录列表")
    @RequestMapping(value = "/flow/get_record", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getFlowRecord(@RequestParam @Validated String type,@RequestParam @Validated String time, @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(userBillApi.getFlowRecord(type, time, pageParamRequest).getCheckedData().getColumns());
    }

    @Operation(summary = "财务管理 -- 资金流水统计")
    @RequestMapping(value = "/flow/get_list", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getFlowList(@RequestParam @Validated List<Integer> ids,@RequestParam @Validated String keywords, @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(userBillApi.getFlowList(ids, keywords, pageParamRequest).getCheckedData().getColumns());
    }

    /**
     * 2、余额统计
     * /statistic/balance/get_basic?time=2025%2F01%2F27-2025%2F02%2F25
     * /statistic/balance/get_trend?time=2025%2F01%2F27-2025%2F02%2F25
     * /statistic/balance/get_channel?time=2025%2F01%2F27-2025%2F02%2F25
     * /statistic/balance/get_type?time=2025%2F01%2F27-2025%2F02%2F25
     * 2.1 余额记录
     * /balance/list?type=&time=&keywords=&page=1&limit=20
     * @param time
     * @return
     */
    @Operation(summary = "余额统计数量")
    @RequestMapping(value = "/balance/get_basic", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getBalanceBasic(@RequestParam @Validated String time) {
        return CommonResult.success(userBillApi.getBalanceBasic(time).getCheckedData().getColumns());
    }

    @Operation(summary = "余额统计折线图")
    @RequestMapping(value = "/balance/get_trend", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getBalanceTrend(@RequestParam @Validated String time) {
        return CommonResult.success(userBillApi.getBalanceTrend(time).getCheckedData().getColumns());
    }

    @Operation(summary = "余额来源分析")
    @RequestMapping(value = "/balance/get_channel", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getBalanceChannel(@RequestParam @Validated String time) {
        return CommonResult.success(userBillApi.getBalanceChannel(time).getCheckedData().getColumns());
    }

    @Operation(summary = "余额类型分析")
    @RequestMapping(value = "/balance/get_type", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getBalanceType(@RequestParam @Validated String time) {
        return CommonResult.success(userBillApi.getBalanceType(time).getCheckedData().getColumns());
    }

    @Operation(summary = "余额记录")
    @RequestMapping(value = "/balance/list", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getBalanceList(@RequestParam @Validated String type, @RequestParam @Validated String time, @RequestParam @Validated String keywords, @ModelAttribute PageParamRequest pageParamRequest) {
        FundsMonitorSearchRequest fundsMonitorSearchRequest = new FundsMonitorSearchRequest();
        fundsMonitorSearchRequest.setType(type);
        fundsMonitorSearchRequest.setKeywords(keywords);
        fundsMonitorSearchRequest.setDateLimit(time);
        return CommonResult.success(userBillApi.getBalanceList(fundsMonitorSearchRequest, pageParamRequest).getCheckedData().getColumns());
    }

    /**
     * 4、订单统计
     * /statistic/order/get_basic?time=2025%2F01%2F28-2025%2F02%2F26
     * /statistic/order/get_trend?time=2025%2F01%2F28-2025%2F02%2F26
     * /statistic/order/get_channel?time=2025%2F01%2F28-2025%2F02%2F26
     * /statistic/order/get_type?time=2025%2F01%2F28-2025%2F02%2F26
     */
    @Operation(summary = "订单统计数量")
    @RequestMapping(value = "/order/get_basic", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getOrderBasic(@RequestParam @Validated String time) {
        return CommonResult.success(storeOrderApi.getOrderBasic(time).getCheckedData().getColumns());
    }

    @Operation(summary = "订单统计折线图")
    @RequestMapping(value = "/order/get_trend", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getOrderTrend(@RequestParam @Validated String time) {
        return CommonResult.success(storeOrderApi.getOrderTrend(time).getCheckedData().getColumns());
    }

    @Operation(summary = "订单来源分析")
    @RequestMapping(value = "/order/get_channel", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getOrderChannel(@RequestParam @Validated String time) {
        return CommonResult.success(storeOrderApi.getOrderChannel(time).getCheckedData().getColumns());
    }

    @Operation(summary = "订单类型分析")
    @RequestMapping(value = "/order/get_type", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getOrderType(@RequestParam @Validated String time) {
        return CommonResult.success(storeOrderApi.getOrderType(time).getCheckedData().getColumns());
    }
}



