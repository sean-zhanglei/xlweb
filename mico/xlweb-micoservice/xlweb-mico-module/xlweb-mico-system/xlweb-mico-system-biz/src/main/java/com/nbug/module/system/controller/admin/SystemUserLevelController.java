package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.system.SystemUserLevel;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.SystemUserLevelRequest;
import com.nbug.mico.common.request.SystemUserLevelUpdateShowRequest;
import com.nbug.module.system.service.SystemUserLevelService;
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


/**
 * 设置用户等级表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/user/level")
@Tag(name = "管理后台 - 设置 -- 会员等级")
public class SystemUserLevelController {

    @Autowired
    private SystemUserLevelService systemUserLevelService;

    /**
     * 分页显示设置用户等级表
     */
    @PreAuthorize("hasAuthority('admin:system:user:level:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<SystemUserLevel>> getList() {
        return CommonResult.success(systemUserLevelService.getList());
    }

    /**
     * 新增等级
     */
    @PreAuthorize("hasAuthority('admin:system:user:level:save')")
    @Operation(summary = "新增等级")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<Object> save(@RequestBody @Validated SystemUserLevelRequest request) {
        systemUserLevelService.create(request);
        return CommonResult.success("success");
    }

    /**
     * 删除等级
     * @param id 等级id
     */
    @PreAuthorize("hasAuthority('admin:system:user:level:delete')")
    @Operation(summary = "删除等级")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult<Object> delete(@PathVariable(value = "id") Integer id) {
        systemUserLevelService.delete(id);
        return CommonResult.success("success");
    }

    /**
     * 更新等级
     */
    @PreAuthorize("hasAuthority('admin:system:user:level:update')")
    @Operation(summary = "更新等级")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult<Object> update(@PathVariable(value = "id") Integer id,
                                       @RequestBody @Validated SystemUserLevelRequest request) {
        systemUserLevelService.update(id, request);
        return CommonResult.success("success");
    }

    /**
     * 使用/禁用
     */
    @PreAuthorize("hasAuthority('admin:system:user:level:use')")
    @Operation(summary = "使用/禁用")
    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public CommonResult<Object> use(@RequestBody @Validated SystemUserLevelUpdateShowRequest request) {
        systemUserLevelService.updateShow(request);
        return CommonResult.success("success");
    }
}



