package com.nbug.module.system.api.shippingTemplates;

import com.nbug.mico.common.model.express.ShippingTemplates;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 运费模板")
public interface ShippingTemplatesApi {

    String PREFIX = ApiConstants.PREFIX + "/shippingTemplates";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取运费模板")
    @Parameter(name = "id", description = "运费模板ID", required = true)
    public CommonResult<ShippingTemplates> getById(@RequestParam Integer id);
}
