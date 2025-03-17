package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.model.system.SystemAttachment;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SystemAttachmentMoveRequest;
import com.nbug.mico.common.request.SystemAttachmentRequest;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.infra.service.attachment.SystemAttachmentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 附件管理表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("infra/attachment")
@Tag(name = "管理后台 - 附件管理") //配合swagger使用
public class SystemAttachmentController {

    @Autowired
    private SystemAttachmentService systemAttachmentService;

    /**
     * 分页显示附件管理表
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:list')")
    @Operation(summary = "分页列表") //配合swagger使用
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
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated SystemAttachmentRequest systemAttachmentRequest) {
        if (systemAttachmentService.add(systemAttachmentRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除附件管理表
     * @param ids String
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    public CommonResult<String> delete(@PathVariable String ids) {
        if (systemAttachmentService.removeByIds(XlwebUtil.stringToArray(ids))) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改附件管理表
     * @param id integer id
     * @param systemAttachmentRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id,
                                       @RequestBody @Validated SystemAttachmentRequest systemAttachmentRequest) {
        systemAttachmentRequest.setAttId(id);
        if (systemAttachmentService.edit(systemAttachmentRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询附件管理表信息
     * @param move SystemAttachmentMoveRequest
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:move')")
    @Operation(summary = "更改图片目录")
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    public CommonResult<String> updateAttrId(@RequestBody @Validated SystemAttachmentMoveRequest move) {
        if (systemAttachmentService.updateAttrId(move)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 附件详情
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:system:attachment:info')")
    @Operation(summary = "附件详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<SystemAttachment> info(@PathVariable Integer id) {
        return CommonResult.success(systemAttachmentService.getById(id));
   }
}



