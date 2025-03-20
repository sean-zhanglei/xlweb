package com.nbug.module.user.controller.admin;

import com.nbug.mico.common.model.user.UserTag;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserTagRequest;
import com.nbug.module.user.service.UserTagService;
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

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 用户标签 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("user/tag")
@Tag(name = "管理后台 - 会员 -- 标签") //配合swagger使用
public class UserTagController {

    @Autowired
    private UserTagService userTagService;

    /**
     * 分页显示用户分标签
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:user:tag:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserTag>>  getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserTag> userTagCommonPage = CommonPage.restPage(userTagService.getList(pageParamRequest));
        return CommonResult.success(userTagCommonPage);
    }

    /**
     * 新增用户分标签
     * @param userTagRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:user:tag:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated UserTagRequest userTagRequest) {
        if (userTagService.create(userTagRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除用户分标签
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:user:tag:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (userTagService.delete(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改用户标签
     * @param id integer id
     * @param userTagRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:user:tag:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated UserTagRequest userTagRequest) {
        if (userTagService.updateTag(id, userTagRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询用户标签
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:user:tag:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<UserTag> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(userTagService.getById(id));
   }
}



