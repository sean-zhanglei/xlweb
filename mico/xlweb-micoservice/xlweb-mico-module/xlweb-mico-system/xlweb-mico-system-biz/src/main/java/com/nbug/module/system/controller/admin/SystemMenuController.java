package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.system.SystemMenu;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.SystemMenuRequest;
import com.nbug.mico.common.request.SystemMenuSearchRequest;
import com.nbug.mico.common.vo.MenuCheckVo;
import com.nbug.module.system.service.SystemMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 系统菜单管理 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/menu")
@Tag(name = "管理后台 - 系统菜单管理")
public class SystemMenuController {

    @Autowired
    private SystemMenuService systemMenuService;

    /**
     * 菜单列表
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:system:menu:list')")
    @Operation(summary = "菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<SystemMenu>> getList(@Validated SystemMenuSearchRequest request) {
        return CommonResult.success(systemMenuService.getAdminList(request));
    }

    /**
     * 新增菜单
     * @param systemMenuRequest 新增菜单
     */
    @PreAuthorize("hasAuthority('admin:system:menu:add')")
    @Operation(summary = "新增菜单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<String> add(@RequestBody @Validated SystemMenuRequest systemMenuRequest) {
        if (systemMenuService.add(systemMenuRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除菜单
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:menu:delete')")
    @Operation(summary = "删除菜单")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult<String> delete(@PathVariable(value = "id") Integer id) {
        if (systemMenuService.deleteById(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("hasAuthority('admin:system:menu:update')")
    @Operation(summary = "修改菜单")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated SystemMenuRequest systemMenuRequest) {
        if (systemMenuService.edit(systemMenuRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 菜单详情
     */
    @PreAuthorize("hasAuthority('admin:system:menu:info')")
    @Operation(summary = "菜单详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<SystemMenu> info(@PathVariable(value = "id") Integer id) {
        return CommonResult.success(systemMenuService.getInfo(id));
   }

    /**
     * 修改菜单显示状态
     */
    @PreAuthorize("hasAuthority('admin:system:menu:show:status')")
    @Operation(summary = "修改菜单显示状态")
    @RequestMapping(value = "/updateShowStatus/{id}", method = RequestMethod.POST)
    public CommonResult<Object> updateShowStatus(@PathVariable(value = "id") Integer id) {
        if (systemMenuService.updateShowStatus(id)) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR,"修改失败");
    }

    /**
     * 菜单缓存树
     */
    @PreAuthorize("hasAuthority('admin:system:menu:cache:tree')")
    @Operation(summary = "菜单缓存树")
    @RequestMapping(value = "/cache/tree", method = RequestMethod.GET)
    public CommonResult<List<MenuCheckVo>> getCacheTree() {
        return CommonResult.success(systemMenuService.getCacheTree());
    }
}



