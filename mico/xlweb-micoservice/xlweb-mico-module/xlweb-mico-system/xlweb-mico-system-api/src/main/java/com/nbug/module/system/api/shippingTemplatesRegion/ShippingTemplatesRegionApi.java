package com.nbug.module.system.api.shippingTemplatesRegion;

import com.nbug.mico.common.model.express.ShippingTemplatesRegion;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 运费模版区域")
public interface ShippingTemplatesRegionApi {

    String PREFIX = ApiConstants.PREFIX + "/shippingTemplatesRegion";

    @GetMapping(PREFIX + "/getByTempIdAndCityId")
    @Operation(summary = "根据运费模版ID和城市ID获取运费模版区域")
    @Parameter(name = "tempId", description = "运费模版ID", required = true)
    public CommonResult<ShippingTemplatesRegion> getByTempIdAndCityId(@RequestParam Integer tempId,
                                                                      @RequestParam Integer cityId);
}
