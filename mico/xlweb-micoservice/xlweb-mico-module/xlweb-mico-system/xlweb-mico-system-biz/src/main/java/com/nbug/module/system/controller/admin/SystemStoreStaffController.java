package com.nbug.module.system.controller.admin;


import com.nbug.mico.common.model.system.SystemStoreStaff;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemStoreStaffRequest;
import com.nbug.mico.common.response.SystemStoreStaffResponse;
import com.nbug.module.system.service.SystemStoreStaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 门店店员表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/store/staff")
@Tag(name = "管理后台 - 设置 -- 提货点 -- 核销员")
public class SystemStoreStaffController {

    @Autowired
    private SystemStoreStaffService systemStoreStaffService;

    /**
     * 分页显示门店核销员列表
     * @param storeId 门店id
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:staff:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemStoreStaffResponse>>  getList(@RequestParam(name = "storeId", required = false, defaultValue = "0") Integer storeId,
                                                                       @ModelAttribute PageParamRequest pageParamRequest) {
        CommonPage<SystemStoreStaffResponse> systemStoreStaffCommonPage =
                CommonPage.restPage(systemStoreStaffService.getList(storeId, pageParamRequest));
        return CommonResult.success(systemStoreStaffCommonPage);
    }

    /**
     * 新增门店店员表
     * @param systemStoreStaffRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:staff:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @ModelAttribute SystemStoreStaffRequest systemStoreStaffRequest) {
        if (systemStoreStaffService.saveUnique(systemStoreStaffRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除门店店员表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:staff:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (systemStoreStaffService.removeById(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改门店店员表
     * @param id integer id
     * @param systemStoreStaffRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:staff:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @ModelAttribute SystemStoreStaffRequest systemStoreStaffRequest) {
        if (systemStoreStaffService.edit(id, systemStoreStaffRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改门店店员表
     * @param id integer id
     * @param status 状态
     */
    @PreAuthorize("hasAuthority('admin:system:staff:update:status')")
    @Operation(summary = "修改状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.GET)
    public CommonResult<String> updateStatus(@RequestParam Integer id, @RequestParam Integer status) {
        if (systemStoreStaffService.updateStatus(id, status)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询门店店员表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:staff:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemStoreStaff> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(systemStoreStaffService.getById(id));
   }
}



