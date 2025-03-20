package com.nbug.module.user.controller.admin;

import com.nbug.mico.common.model.user.UserGroup;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserGroupRequest;
import com.nbug.module.user.service.UserGroupService;
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
 * 用户分组表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("user/group")
@Tag(name = "管理后台 - 会员 -- 分组")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    /**
     * 分页显示用户分组表
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:user:group:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserGroup>>  getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserGroup> userGroupCommonPage = CommonPage.restPage(userGroupService.getList(pageParamRequest));
        return CommonResult.success(userGroupCommonPage);
    }

    /**
     * 新增用户分组表
     * @param userGroupRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:user:group:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated UserGroupRequest userGroupRequest) {
        if (userGroupService.create(userGroupRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除用户分组表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:user:group:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (userGroupService.removeById(id)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改用户分组表
     * @param id integer id
     * @param userGroupRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:user:group:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated UserGroupRequest userGroupRequest) {
        if (userGroupService.edit(id, userGroupRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 查询用户分组表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:user:group:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<UserGroup> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(userGroupService.getById(id));
   }
}



