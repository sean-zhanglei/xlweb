package com.nbug.module.system.api.express;

import com.nbug.mico.common.model.express.Express;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 快递公司")
public interface ExpressApi {

    String PREFIX = ApiConstants.PREFIX + "/express";

    @GetMapping(PREFIX + "/getByName")
    @Operation(summary = "根据快递公司名称获取快递公司信息")
    @Parameter(name = "name", description = "快递公司名称", required = true)
    public CommonResult<Express> getByName(String name);

    @GetMapping(PREFIX + "/getByCode")
    @Operation(summary = "根据快递公司编码获取快递公司信息")
    @Parameter(name = "code", description = "快递公司编码", required = true)
    public CommonResult<Express> getByCode(String code);
}
