package com.nbug.module.system.api.logistic;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.LogisticsResultVo;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 快递轨迹")
public interface LogisticApi {

    String PREFIX = ApiConstants.PREFIX + "/logistics";

    @GetMapping(PREFIX + "/info")
    @Operation(summary = "获取快递轨迹信息")
    @Parameters({
            @Parameter(name = "expressNo", description = "快递单号", required = true),
            @Parameter(name = "type", description = "快递公司类型", required = true),
            @Parameter(name = "com", description = "快递公司编码", required = true),
            @Parameter(name = "phone", description = "手机号", required = true)
    })
    public CommonResult<LogisticsResultVo> info(@RequestParam String expressNo,
                                                @RequestParam String type,
                                                @RequestParam String com,
                                                @RequestParam String phone);
}
