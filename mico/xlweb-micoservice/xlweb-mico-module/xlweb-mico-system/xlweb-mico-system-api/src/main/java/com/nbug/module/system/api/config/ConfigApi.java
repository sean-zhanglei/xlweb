package com.nbug.module.system.api.config;

import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.ExpressSheetVo;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 参数配置")
public interface ConfigApi {

    String PREFIX = ApiConstants.PREFIX + "/config";

    @GetMapping(PREFIX + "/getValueByKeyException")
    @Operation(summary = "根据 name 获取 value 找不到抛异常")
    @Parameter(name = "name", description = "name", required = true)
    CommonResult<String> getValueByKeyException(String name);

    @GetMapping(PREFIX + "/getValueByKey")
    @Operation(summary = "根据menu name 获取 value")
    @Parameter(name = "key", description = "key", required = true)
    CommonResult<String> getValueByKey(String key);

    @GetMapping(PREFIX + "/getDeliveryInfo")
    @Operation(summary = "获取面单默认配置信息")
    public CommonResult<ExpressSheetVo> getDeliveryInfo();

    @GetMapping(PREFIX + "/getValuesByKes")
    @Operation(summary = "同时获取多个配置")
    @Parameter(name = "keys", description = "keys", required = true)
    public CommonResult<List<String>> getValuesByKes(List<String> keys);

    @GetMapping(PREFIX + "/info")
    @Operation(summary = "根据formId查询数据")
    @Parameter(name = "formId", description = "formId", required = true)
    public CommonResult<HashMap<String, String>> info(Integer formId);

    @GetMapping(PREFIX + "/getColorConfig")
    @Operation(summary = "获取颜色配置")
    public CommonResult<SystemConfig> getColorConfig();
}
