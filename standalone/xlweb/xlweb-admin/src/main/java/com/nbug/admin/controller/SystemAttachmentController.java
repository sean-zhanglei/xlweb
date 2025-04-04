package com.nbug.admin.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.common.model.system.SystemAttachment;
import com.nbug.common.request.SystemAttachmentMoveRequest;
import com.nbug.common.request.SystemAttachmentRequest;
import com.nbug.service.service.SystemAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 附件管理表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/system/attachment")
@Api(tags = "附件管理") //配合swagger使用
public class SystemAttachmentController {

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    /**
     * 分页显示附件管理表
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:list')")
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<SystemAttachment>>  getList(
            @RequestParam @Validated Integer pid,
            @RequestParam(
                    value = "attType",
                    defaultValue = "png,jpeg,jpg,audio/mpeg,text/plain,video/mp4,gif",
                    required = false) String attType,
            @Validated PageParamRequest pageParamRequest) {
        CommonPage<SystemAttachment> systemAttachmentCommonPage =
                CommonPage.restPage(systemAttachmentService.getList(pid, attType, pageParamRequest));
        return CommonResult.success(systemAttachmentCommonPage);
    }

    /**
     * 新增附件管理表 TODO:未使用的话删除此接口
     * @param systemAttachmentRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:save')")
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated SystemAttachmentRequest systemAttachmentRequest) {
        if (systemAttachmentService.add(systemAttachmentRequest)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 删除附件管理表
     * @param ids String
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:delete')")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public CommonResult<String> delete(@PathVariable String ids) {
        if (systemAttachmentService.removeByIds(XlwebUtil.stringToArray(ids))) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 修改附件管理表
     * @param id integer id
     * @param systemAttachmentRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:update')")
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id,
                                       @RequestBody @Validated SystemAttachmentRequest systemAttachmentRequest) {
        systemAttachmentRequest.setAttId(id);
        if (systemAttachmentService.edit(systemAttachmentRequest)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 查询附件管理表信息
     * @param move SystemAttachmentMoveRequest
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:move')")
    @ApiOperation(value = "更改图片目录")
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public CommonResult<String> updateAttrId(@RequestBody @Validated SystemAttachmentMoveRequest move) {
        if (systemAttachmentService.updateAttrId(move)) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    /**
     * 附件详情
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:info')")
    @ApiOperation(value = "附件详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<SystemAttachment> info(@PathVariable Integer id) {
        return CommonResult.success(systemAttachmentService.getById(id));
   }
}



