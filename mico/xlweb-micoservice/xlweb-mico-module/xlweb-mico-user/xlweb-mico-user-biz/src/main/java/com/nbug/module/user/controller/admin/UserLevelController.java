package com.nbug.module.user.controller.admin;

import com.nbug.mico.common.model.user.UserLevel;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.module.user.service.UserLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "管理后台 - 会员 -- 等级")
public class UserLevelController {

    @Autowired
    private UserLevelService userLevelService;

    /**
     * 分页显示用户等级记录表
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:user:level:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserLevel>>  getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserLevel> userLevelCommonPage = CommonPage.restPage(userLevelService.getList(pageParamRequest));
        return CommonResult.success(userLevelCommonPage);
    }
}



