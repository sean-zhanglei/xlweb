package com.nbug.module.system.api.shippingTemplatesFree;

import com.nbug.mico.common.model.express.ShippingTemplatesFree;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 免费运费模版")
public interface ShippingTemplatesFreeApi {

    String PREFIX = ApiConstants.PREFIX + "/shippingTemplatesFree";

    @GetMapping(PREFIX + "/getByTempIdAndCityId")
    @Operation(summary = "根据模板ID和城市ID获取免费运费模版")
    @Parameters({
            @Parameter(name = "tempId", description = "模板ID", required = true),
            @Parameter(name = "cityId", description = "城市ID", required = true)
    })
    public CommonResult<ShippingTemplatesFree> getByTempIdAndCityId(@RequestParam Integer tempId,
                                                                    @RequestParam Integer cityId);
}
