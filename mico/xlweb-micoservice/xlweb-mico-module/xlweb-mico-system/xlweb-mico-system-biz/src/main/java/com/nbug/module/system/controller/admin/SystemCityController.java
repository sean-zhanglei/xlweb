package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.system.SystemCity;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.SystemCityRequest;
import com.nbug.mico.common.request.SystemCitySearchRequest;
import com.nbug.mico.common.vo.SystemCityTreeVo;
import com.nbug.module.system.service.SystemCityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 城市表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/city")
@Tag(name = "管理后台 - 城市管理")
public class SystemCityController {

    @Autowired
    private SystemCityService systemCityService;

    /**
     * 分页城市列表
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:system:city:list')")
    @Operation(summary = "分页城市列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<Object> getList(@Validated SystemCitySearchRequest request) {
        return CommonResult.success(systemCityService.getList(request));
    }

    /**
     * 修改城市
     * @param id 城市id
     * @param request 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:city:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @Validated SystemCityRequest request) {
        if (systemCityService.update(id, request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改状态
     * @param id 城市id
     * @param status 状态
     */
    @PreAuthorize("hasAuthority('admin:system:city:update:status')")
    @Operation(summary = "修改状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        if (systemCityService.updateStatus(id, status)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 城市详情
     * @param id 城市id
     */
    @PreAuthorize("hasAuthority('admin:system:city:info')")
    @Operation(summary = "城市详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemCity> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(systemCityService.getById(id));
    }

    /**
     * 获取tree结构的列表
     */
    @PreAuthorize("hasAuthority('admin:system:city:list:tree')")
    @Operation(summary = "获取tree结构的列表")
    @RequestMapping(value = "/list/tree", method = RequestMethod.GET)
    public CommonResult<List<SystemCityTreeVo>> getListTree() {
        return CommonResult.success(systemCityService.getListTree());
    }
}



