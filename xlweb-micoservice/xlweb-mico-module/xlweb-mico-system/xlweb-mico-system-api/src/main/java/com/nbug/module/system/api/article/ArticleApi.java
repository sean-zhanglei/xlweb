package com.nbug.module.system.api.admin;

import com.nbug.mico.common.model.system.SystemAdmin;
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
@Tag(name = "RPC 服务 - 管理员")
public interface AdminApi {

    String PREFIX = ApiConstants.PREFIX + "/admin";

    @GetMapping(PREFIX + "/getMapInId")
    @Operation(summary = "根据id获取管理员信息")
    @Parameter(name = "adminIdList", description = "管理员ID集合", required = true)
    public CommonResult<HashMap<Integer, SystemAdmin>> getMapInId(List<Integer> adminIdList);

    @GetMapping(PREFIX + "/findIsSmsList")
    @Operation(summary = "获取短信通知管理员列表")
    public CommonResult<List<SystemAdmin>> findIsSmsList();

    @GetMapping(PREFIX + "/selectUserByUserName")
    @Operation(summary = "根据用户名获取管理员信息")
    @Parameter(name = "username", description = "用户名", required = true)
    public CommonResult<SystemAdmin> selectUserByUserName(String username);
}
