package com.nbug.module.user.api.userIntegralRecord;

import com.nbug.mico.common.model.user.UserIntegralRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.user.enums.ApiConstants;
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
@Tag(name = "RPC 服务 - 用户积分记录")
public interface UserIntegralRecordApi {

    String PREFIX = ApiConstants.PREFIX + "/intergral";



    @GetMapping(PREFIX + "/findListByOrderIdAndUid")
    @Operation(summary = "根据订单编号、uid获取记录列表")
    @Parameters({
            @Parameter(name = "orderNo", description = "订单号", required = true),
            @Parameter(name = "userId", description = "用户id", required = true)
    })
    public CommonResult<List<UserIntegralRecord>> findListByOrderIdAndUid(@RequestParam String orderNo,
                                                                          @RequestParam Integer userId);

    @PostMapping(PREFIX + "/saveBatch")
    @Operation(summary = "保存用户积分记录")
    @Parameter(name = "userIntegralRecords", description = "用户积分记录", required = true)
    public CommonResult<Boolean> saveBatch(@RequestParam List<UserIntegralRecord> userIntegralRecords);

    @PostMapping(PREFIX + "/updateBatchById")
    @Operation(summary = "批量更新用户积分记录")
    @Parameter(name = "userIntegralRecords", description = "用户积分记录", required = true)
    public CommonResult<Boolean> updateBatchById(@RequestParam List<UserIntegralRecord> userIntegralRecords);

    @GetMapping(PREFIX + "/getIntegralBasic")
    @Operation(summary = "积分统计顶部")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getIntegralBasic(@RequestParam String time);

    @GetMapping(PREFIX + "/getIntegralTrend")
    @Operation(summary = "积分统计折线图")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getIntegralTrend(@RequestParam String time);

    @GetMapping(PREFIX + "/getIntegralChannel")
    @Operation(summary = "积分来源分析")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getIntegralChannel(@RequestParam String time);

    @GetMapping(PREFIX + "/getIntegralType")
    @Operation(summary = "积分消耗分析")
    @Parameter(name = "time", description = "时间", required = true)
    public CommonResult<MyRecord> getIntegralType(@RequestParam String time);

}
