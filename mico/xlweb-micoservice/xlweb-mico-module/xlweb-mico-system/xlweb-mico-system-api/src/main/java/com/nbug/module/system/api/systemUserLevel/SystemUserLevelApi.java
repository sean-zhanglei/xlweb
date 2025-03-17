package com.nbug.module.system.api.systemUserLevel;

import com.nbug.mico.common.model.system.SystemUserLevel;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户等级")
public interface SystemUserLevelApi {

    String PREFIX = ApiConstants.PREFIX + "/systemUserLevel";

    @GetMapping(PREFIX + "/getByLevelId")
    @Operation(summary = "根据等级id获取等级信息")
    @Parameter(name = "levelId", description = "等级id", required = true)
    public CommonResult<SystemUserLevel> getByLevelId(@RequestParam Integer levelId);


    @GetMapping(PREFIX + "/getUsableList")
    @Operation(summary = "获取可用等级列表")
    public CommonResult<List<SystemUserLevel>> getUsableList();

    @GetMapping(PREFIX + "/getH5LevelList")
    @Operation(summary = "获取H5等级列表")
    public CommonResult<List<SystemUserLevel>> getH5LevelList();
}
