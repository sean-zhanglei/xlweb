package com.nbug.module.system.api.city;

import com.nbug.mico.common.model.system.SystemCity;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 城市")
public interface CityApi {

    String PREFIX = ApiConstants.PREFIX + "/city";

    @GetMapping(PREFIX + "/getCityByCityName")
    @Operation(summary = "根据城市名称获取城市信息")
    @Parameter(name = "cityName", description = "城市名称", required = true)
    public CommonResult<SystemCity> getCityByCityName(@RequestParam String cityName);

    @GetMapping(PREFIX + "/getCityByCityId")
    @Operation(summary = "根据城市ID获取城市信息")
    @Parameter(name = "cityId", description = "城市ID", required = true)
    public CommonResult<SystemCity> getCityByCityId(@RequestParam Integer cityId);
}
