package com.nbug.module.user.api.userBill;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.FundsMonitorSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户订单")
public interface UserBillApi {

    String PREFIX = ApiConstants.PREFIX + "/bill";


    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存用户订单")
    @Parameter(name = "userBill", description = "用户订单信息", required = true)
    public CommonResult<Boolean> save(@RequestBody UserBill userBill);

    @PostMapping(PREFIX + "/saveRefundBill")
    @Operation(summary = "保存退款日志")
    @Parameters({
            @Parameter(name = "request", description = "退款订单", required = true),
            @Parameter(name = "user", description = "用户", required = true)
    })
    public CommonResult<Boolean> saveRefundBill(@RequestBody @Validated StoreOrderRefundRequest request,
                                                @RequestParam User user);

    @GetMapping(PREFIX + "/listMaps")
    @Operation(summary = "查询用户订单列表")
    @Parameters({
            @Parameter(name = "timeType", description = "时间类型", required = true),
            @Parameter(name = "startDate", description = "开始时间", required = true),
            @Parameter(name = "endDate", description = "结束时间", required = true)
    })
    public CommonResult<List<Map<String, Object>>> listMaps(@RequestParam String timeType,
                                                            @RequestParam Date startDate,
                                                            @RequestParam Date endDate);

    @PostMapping(PREFIX + "/saveBatch")
    @Operation(summary = "批量保存用户订单")
    @Parameter(name = "billList", description = "用户订单信息", required = true)
    public CommonResult<Boolean> saveBatch(@RequestBody List<UserBill> billList);

    @GetMapping(PREFIX + "/getFlowRecord")
    @Operation(summary = "获取账单记录列表")
    @Parameters({
            @Parameter(name = "type", description = "流水类型", required = true),
            @Parameter(name = "time", description = "时间", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<MyRecord> getFlowRecord(@RequestParam String type,
                                                @RequestParam String time,
                                                @SpringQueryMap PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getFlowList")
    @Operation(summary = "资金流水统计数据")
    @Parameters({
            @Parameter(name = "ids", description = "流水id列表", required = true),
            @Parameter(name = "keywords", description = "关键字"),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<MyRecord> getFlowList(@RequestParam List<Integer> ids,
                                              @RequestParam String keywords,
                                              @SpringQueryMap PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getBalanceBasic")
    @Operation(summary = "余额统计数量")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getBalanceBasic(@RequestParam String time);

    @GetMapping(PREFIX + "/getBalanceTrend")
    @Operation(summary = "余额统计折线图")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getBalanceTrend(@RequestParam String time);

    @GetMapping(PREFIX + "/getBalanceChannel")
    @Operation(summary = "余额来源分析")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getBalanceChannel(@RequestParam String time);

    @GetMapping(PREFIX + "/getBalanceType")
    @Operation(summary = "余额类型分析")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getBalanceType(@RequestParam String time);

    @PostMapping(PREFIX + "/getBalanceList")
    @Operation(summary = "余额记录")
    @Parameters({
            @Parameter(name = "type", description = "流水类型", required = true),
            @Parameter(name = "keywords", description = "关键字"),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<MyRecord> getBalanceList(@RequestBody @Validated FundsMonitorSearchRequest request,
                                                 @SpringQueryMap PageParamRequest pageParamRequest);
}
