package com.nbug.module.infra.api.yly;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 易联云")
public interface YlyApiApi {

    String PREFIX = ApiConstants.PREFIX + "/yly";

    @PostMapping(PREFIX + "/YlyPrint")
    @Operation(summary = "打印订单")
    @Parameters({
            @Parameter(name = "ordId", description = "订单编号", required = true),
            @Parameter(name = "isAuto", description = "是否自动打印", required = true)
    })
    public CommonResult<String> YlyPrint(String ordId, boolean isAuto);
}
