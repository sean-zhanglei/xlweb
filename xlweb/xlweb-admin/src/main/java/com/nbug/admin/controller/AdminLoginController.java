package com.nbug.admin.controller;

import com.nbug.common.request.SystemAdminLoginRequest;
import com.nbug.common.response.CommonResult;
import com.nbug.common.response.MenusResponse;
import com.nbug.common.response.SystemAdminResponse;
import com.nbug.common.response.SystemLoginResponse;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.admin.service.AdminLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 管理端登录服务

 */
@Slf4j
@RestController
@RequestMapping("api/admin")
@Api(tags = "管理端登录服务")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @ApiOperation(value="PC登录")
    @PostMapping(value = "/login", produces = "application/json")
    public CommonResult<SystemLoginResponse> SystemAdminLogin(@RequestBody @Validated SystemAdminLoginRequest systemAdminLoginRequest, HttpServletRequest request) {
        String ip = XlwebUtil.getClientIp(request);
        SystemLoginResponse systemAdminResponse = adminLoginService.login(systemAdminLoginRequest, ip);
        return CommonResult.success(systemAdminResponse, "login success");
    }

    @PreAuthorize("hasAuthority('admin:logout')")
    @ApiOperation(value="PC登出")
    @GetMapping(value = "/logout")
    public CommonResult<SystemAdminResponse> SystemAdminLogout() {
        adminLoginService.logout();
        return CommonResult.success("logout success");
    }

    @PreAuthorize("hasAuthority('admin:info')")
    @ApiOperation(value="获取用户详情")
    @GetMapping(value = "/getAdminInfoByToken")
    public CommonResult<SystemAdminResponse> getAdminInfo() {
        return CommonResult.success(adminLoginService.getInfoByToken());
    }

    /**
     * 获取登录页图片
     * @return Map<String, Object>
     */
    @ApiOperation(value = "获取登录页图片")
    @RequestMapping(value = "/getLoginPic", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getLoginPic() {
        return CommonResult.success(adminLoginService.getLoginPic());
    }

    /**
     * 获取管理员可访问目录
     */
    @PreAuthorize("hasAuthority('admin:login:menus')")
    @ApiOperation(value = "获取管理员可访问目录")
    @RequestMapping(value = "/getMenus", method = RequestMethod.GET)
    public CommonResult<List<MenusResponse>> getMenus() {
        return CommonResult.success(adminLoginService.getMenus());
    }

}
