package com.nbug.module.system.api.systemMenu;

import com.nbug.mico.common.model.system.SystemMenu;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 系统菜单")
public interface SystemMenuApi {

    String PREFIX = ApiConstants.PREFIX + "/systemMenu";

    @GetMapping(PREFIX + "/getAllPermissions")
    @Operation(summary = "获取所有权限")
    public CommonResult<List<SystemMenu>> getAllPermissions();


    @GetMapping(PREFIX + "/findPermissionByUserId")
    @Operation(summary = "根据用户id获取权限")
    @Parameter(name = "userId", description = "用户id", required = true)
    public CommonResult<List<SystemMenu>> findPermissionByUserId(Integer userId);
}
