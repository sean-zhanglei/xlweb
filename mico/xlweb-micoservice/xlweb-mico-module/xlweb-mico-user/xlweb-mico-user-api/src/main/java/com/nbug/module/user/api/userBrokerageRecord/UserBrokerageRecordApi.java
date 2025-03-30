package com.nbug.module.user.api.userBrokerageRecord;

import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户佣金")
public interface UserBrokerageRecordApi {

    String PREFIX = ApiConstants.PREFIX + "/brokerage";

    @GetMapping(PREFIX + "/findListByLinkIdAndLinkType")
    @Operation(summary = "根据linkId和linkType查询用户佣金记录")
    @Parameters({
            @Parameter(name = "linkId", description = "关联id", required = true),
            @Parameter(name = "linkType", description = "关联类型", required = true)
    })
    public CommonResult<List<UserBrokerageRecord>> findListByLinkIdAndLinkType(@RequestParam String linkId,
                                                                               @RequestParam String linkType);

    @GetMapping(PREFIX + "/getByLinkIdAndLinkType")
    @Operation(summary = "根据linkId和linkType查询用户佣金记录")
    @Parameters({
            @Parameter(name = "linkId", description = "关联id", required = true),
            @Parameter(name = "linkType", description = "关联类型", required = true)
    })
    public CommonResult<UserBrokerageRecord> getByLinkIdAndLinkType(@RequestParam String linkId,
                                                                    @RequestParam String linkType);

    @PostMapping(PREFIX + "/updateBatchById")
    @Operation(summary = "批量更新用户佣金记录")
    @Parameter(name = "userBrokerageRecords", description = "用户佣金记录信息", required = true)
    public CommonResult<Boolean> updateBatchById(@RequestBody List<UserBrokerageRecord> userBrokerageRecords);

    @PostMapping(PREFIX + "/saveBatch")
    @Operation(summary = "批量保存用户佣金记录")
    @Parameter(name = "userBrokerageRecords", description = "用户佣金记录信息", required = true)
    public CommonResult<Boolean> saveBatch(@RequestBody List<UserBrokerageRecord> userBrokerageRecords);


    @GetMapping(PREFIX + "/getSpreadListByUid")
    @Operation(summary = "根据uid查询用户佣金记录")
    @Parameter(name = "userId", description = "用户id", required = true)
    public CommonResult<List<UserBrokerageRecord>> getSpreadListByUid(@RequestParam Integer userId);

    @GetMapping(PREFIX + "/getFreezePrice")
    @Operation(summary = "获取冻结期佣金")
    @Parameter(name = "userId", description = "用户id", required = true)
    public CommonResult<BigDecimal> getFreezePrice(@RequestParam Integer userId);
}
