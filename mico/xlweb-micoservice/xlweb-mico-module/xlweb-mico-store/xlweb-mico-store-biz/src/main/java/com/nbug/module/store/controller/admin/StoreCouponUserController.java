package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCouponUserRequest;
import com.nbug.mico.common.request.StoreCouponUserSearchRequest;
import com.nbug.mico.common.response.StoreCouponUserResponse;
import com.nbug.module.store.service.StoreCouponUserService;
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
 * 优惠券发放记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("store/coupon/user")
@Tag(name = "管理后台 - 营销 -- 优惠券 -- 领取记录")
public class StoreCouponUserController {

    @Autowired
    private StoreCouponUserService storeCouponUserService;

    /**
     * 分页显示优惠券发放记录表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:coupon:user:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCouponUserResponse>>  getList(@Validated StoreCouponUserSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<StoreCouponUserResponse> storeCouponUserCommonPage = CommonPage.restPage(storeCouponUserService.getList(request, pageParamRequest));
        return CommonResult.success(storeCouponUserCommonPage);
    }

    /**
     * 领券
     * @param storeCouponUserRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:coupon:user:receive')")
    @Operation(summary = "领券")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonResult<String> receive(@Validated StoreCouponUserRequest storeCouponUserRequest) {
        storeCouponUserService.receive(storeCouponUserRequest);
        return CommonResult.success("success");
    }
}



