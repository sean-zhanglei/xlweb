package com.nbug.admin.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.system.SystemGroupData;
import com.nbug.common.request.SystemGroupDataRequest;
import com.nbug.common.request.SystemGroupDataSearchRequest;
import com.nbug.service.service.SystemGroupDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 组合数据详情表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/group/data")
@Api(tags = "设置 -- 组合数据 -- 详情")
public class SystemGroupDataController {

    @Autowired
    private SystemGroupDataService systemGroupDataService;

    /**
     * 分页组合数据详情
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:group:data:list')")
    @ApiOperation(value = "分页组合数据详情")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemGroupData>>  getList(@Validated SystemGroupDataSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<SystemGroupData> systemGroupDataCommonPage = CommonPage.restPage(systemGroupDataService.getList(request, pageParamRequest));
        return CommonResult.success(systemGroupDataCommonPage);
    }

    /**
     * 新增组合数据详情
     * @param systemGroupDataRequest SystemFormCheckRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:group:data:save')")
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated SystemGroupDataRequest systemGroupDataRequest) {
        if (systemGroupDataService.create(systemGroupDataRequest)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除组合数据详情表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:group:data:delete')")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (systemGroupDataService.removeById(id)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 修改组合数据详情表
     * @param id integer id
     * @param request 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:group:data:update')")
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated SystemGroupDataRequest request) {
        if (systemGroupDataService.update(id, request)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 组合数据详情信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:group:data:info')")
    @ApiOperation(value = "组合数据详情信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemGroupData> info(@RequestParam(value = "id") Integer id) {
        SystemGroupData systemGroupData = systemGroupDataService.getById(id);
        return CommonResult.success(systemGroupData);
    }
}



