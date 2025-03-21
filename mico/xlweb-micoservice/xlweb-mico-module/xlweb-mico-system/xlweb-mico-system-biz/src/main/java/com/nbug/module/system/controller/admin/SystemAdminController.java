package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemAdminAddRequest;
import com.nbug.mico.common.request.SystemAdminRequest;
import com.nbug.mico.common.request.SystemAdminUpdateRequest;
import com.nbug.mico.common.response.SystemAdminResponse;
import com.nbug.module.system.service.SystemAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 后台管理员表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/admin")
@Tag(name = "管理后台 - 后台用户服务")
public class SystemAdminController {

    @Autowired
    private SystemAdminService systemAdminService;

    /**
     * 分页显示后台管理员表
     * @param systemAdminRequest 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:admin:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<SystemAdminResponse>> getList(@Validated SystemAdminRequest systemAdminRequest, PageParamRequest pageParamRequest) {
        CommonPage<SystemAdminResponse> systemAdminCommonPage = CommonPage.restPage(systemAdminService.getList(systemAdminRequest, pageParamRequest));
        return CommonResult.success(systemAdminCommonPage);
    }

    /**
     * 新增后台管理员
     * @param systemAdminAddRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:admin:save')")
    @Operation(summary = "新增后台管理员")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody SystemAdminAddRequest systemAdminAddRequest) {
        systemAdminService.saveAdmin(systemAdminAddRequest);
        return CommonResult.success("添加管理员成功");
    }

    /**
     * 删除后台管理员表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:admin:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        systemAdminService.removeById(id);
        return CommonResult.success("success");
    }

    /**
     * 修改后台管理员表
     * @param systemAdminRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:admin:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody SystemAdminUpdateRequest systemAdminRequest) {
        systemAdminService.updateAdmin(systemAdminRequest);
        return CommonResult.success("success");
    }

    /**
     * 后台管理员详情
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:admin:info')")
    @Operation(summary = "后台管理员详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemAdmin> info(@RequestParam(value = "id") @Valid Integer id) {
        return CommonResult.success(systemAdminService.getDetail(id));
    }

    /**
     * 修改后台管理员状态
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:admin:update:status')")
    @Operation(summary = "修改后台管理员状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
    public CommonResult<Object> updateStatus(@RequestParam(value = "id") @Valid Integer id,
                                             @RequestParam(value = "status") @Valid Boolean status) {
        systemAdminService.updateStatus(id, status);
        return CommonResult.success("修改成功");
    }

    /**
     * 修改后台管理员是否接收状态
     */
    @PreAuthorize("hasAuthority('admin:system:admin:update:sms')")
    @Operation(summary = "修改后台管理员是否接收状态")
    @RequestMapping(value = "/update/isSms", method = RequestMethod.GET)
    public CommonResult<Object> updateIsSms(@RequestParam(value = "id") @Valid Integer id) {
        systemAdminService.updateIsSms(id);
        return CommonResult.success("修改成功");
    }
}



