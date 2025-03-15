package com.nbug.module.system.controller.admin;


import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemStoreRequest;
import com.nbug.mico.common.request.SystemStoreSearchRequest;
import com.nbug.module.system.service.SystemStoreService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 门店自提 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/store")
@Tag(name = "设置 -- 提货点 -- 提货点")
public class SystemStoreController {

    @Autowired
    private SystemStoreService systemStoreService;

    /**
     * 分页显示门店自提
     * @param request SystemStoreSearchRequest 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:store:list')")
    @Operation(summary = "门店自提分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemStore>>  getList(@Validated SystemStoreSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<SystemStore> expressCommonPage = CommonPage.restPage(systemStoreService.getList(request.getKeywords(), request.getStatus(), pageParamRequest));
        return CommonResult.success(expressCommonPage);
    }

    /**
     * 数量
     */
    @PreAuthorize("hasAuthority('admin:system:store:count')")
    @Operation(summary = "数量")
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public CommonResult<HashMap<String, Integer>>  getCount() {
        return CommonResult.success(systemStoreService.getCount());
    }

    /**
     * 新增门店自提
     * @param request SystemStoreRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:store:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated SystemStoreRequest request) {
        if (systemStoreService.create(request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }


    /**
     * 删除门店自提
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:store:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (systemStoreService.delete(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改门店自提
     * @param id integer id
     * @param request 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:store:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated SystemStoreRequest request) {
        if (systemStoreService.update(id, request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改门店显示状态
     * @param id integer id
     * @param status 状态
     */
    @PreAuthorize("hasAuthority('admin:system:store:update:status')")
    @Operation(summary = "修改门店显示状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.GET)
    public CommonResult<String> updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        if (systemStoreService.updateStatus(id, status)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 门店自提详情
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:store:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<SystemStore> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(systemStoreService.getInfo(id));
    }

    /**
     * 彻底删除
     */
    @PreAuthorize("hasAuthority('admin:system:store:completely:delete')")
    @Operation(summary = "彻底删除")
    @RequestMapping(value = "/completely/delete", method = RequestMethod.GET)
    public CommonResult<Object> completeLyDelete(@RequestParam(value = "id") Integer id) {
        if (systemStoreService.completeLyDelete(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 恢复
     */
    @PreAuthorize("hasAuthority('admin:system:store:recovery')")
    @Operation(summary = "提货点恢复")
    @RequestMapping(value = "/recovery", method = RequestMethod.GET)
    public CommonResult<Object> recovery(@RequestParam(value = "id") Integer id) {
        if (systemStoreService.recovery(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }
}



