package com.nbug.module.system.api.systemMenu;

import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 自提地址")
public interface SystemStoreApi {

    String PREFIX = ApiConstants.PREFIX + "/systemStore";

    @GetMapping(PREFIX + "/getByCondition")
    @Operation(summary = "根据条件获取自提地址")
    @Parameter(name = "systemStore", description = "自提地址", required = true)
    public CommonResult<SystemStore> getByCondition(SystemStore systemStore);

    @GetMapping(PREFIX + "/getMapInId")
    @Operation(summary = "根据id获取自提地址集合")
    @Parameter(name = "storeIdList", description = "自提地址ID集合", required = true)
    public CommonResult<HashMap<Integer, SystemStore>> getMapInId(List<Integer> storeIdList);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取自提地址")
    @Parameter(name = "id", description = "自提地址ID", required = true)
    public CommonResult<SystemStore> getById(Integer id);
}
