package com.nbug.module.user.controller.admin;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.AdminIntegralSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.UserIntegralRecordResponse;
import com.nbug.module.user.service.UserIntegralRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户积分管理控制器

 */
@Slf4j
@RestController
@RequestMapping("user/integral")
@Tag(name = "管理后台 - 用户积分管理")
public class UserIntegralController {

    @Autowired
    private UserIntegralRecordService integralRecordService;

    /**
     * 积分分页列表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:user:integral:list')")
    @Operation(summary = "积分分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<CommonPage<UserIntegralRecordResponse>> getList(@RequestBody @Validated AdminIntegralSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<UserIntegralRecordResponse> restPage = CommonPage.restPage(integralRecordService.findAdminList(request, pageParamRequest));
        return CommonResult.success(restPage);
    }


}
