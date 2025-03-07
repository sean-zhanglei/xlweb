package com.nbug.admin.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.user.UserLevel;
import com.nbug.service.service.UserLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户等级记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/user/level")
@Api(tags = "会员 -- 等级")
public class UserLevelController {

    @Autowired
    private UserLevelService userLevelService;

    /**
     * 分页显示用户等级记录表
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:user:level:list')")
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserLevel>>  getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserLevel> userLevelCommonPage = CommonPage.restPage(userLevelService.getList(pageParamRequest));
        return CommonResult.success(userLevelCommonPage);
    }
}



