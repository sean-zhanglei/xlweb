package com.nbug.module.user.api.userBill;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 用户订单")
public interface UserBillApi {

    String PREFIX = ApiConstants.PREFIX + "/bill";


    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存用户订单")
    @Parameter(name = "userBill", description = "用户订单信息", required = true)
    public CommonResult<Boolean> save(UserBill userBill);

    @PostMapping(PREFIX + "/saveRefundBill")
    @Operation(summary = "保存退款日志")
    @Parameters({
            @Parameter(name = "request", description = "退款订单", required = true),
            @Parameter(name = "user", description = "用户", required = true)
    })
    public CommonResult<Boolean> saveRefundBill(StoreOrderRefundRequest request, User user);

    @GetMapping(PREFIX + "/listMaps")
    @Operation(summary = "查询用户订单列表")
    @Parameters({
            @Parameter(name = "timeType", description = "时间类型", required = true),
            @Parameter(name = "startDate", description = "开始时间", required = true),
            @Parameter(name = "endDate", description = "结束时间", required = true)
    })
    public CommonResult<List<Map<String, Object>>> listMaps(String timeType, Date startDate, Date endDate);

}
