package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.model.coupon.StoreCoupon;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.SearchAndPageRequest;
import com.nbug.mico.common.request.StoreCouponRequest;
import com.nbug.mico.common.request.StoreCouponSearchRequest;
import com.nbug.mico.common.response.StoreCouponInfoResponse;
import com.nbug.module.store.service.StoreCouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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


/**
 * 优惠券表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("store/coupon")
@Tag(name = "管理后台 - 营销 -- 优惠券")
public class StoreCouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    /**
     * 分页显示优惠券表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:coupon:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCoupon>>  getList(@Validated StoreCouponSearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<StoreCoupon> storeCouponCommonPage = CommonPage.restPage(storeCouponService.getList(request, pageParamRequest));
        return CommonResult.success(storeCouponCommonPage);
    }

    /**
     * 保存优惠券表
     * @param request StoreCouponRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:coupon:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreCouponRequest request) {
        if (storeCouponService.create(request)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 是否有效
     * @param id integer id
     */
    @PreAuthorize("hasAuthority('admin:coupon:update:status')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam Integer id, @RequestParam Boolean status) {
        if (storeCouponService.updateStatus(id, status)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 详情
     * @param id integer id
     */
    @PreAuthorize("hasAuthority('admin:coupon:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @Parameter(name="id", description="优惠券ID", required = true)
    public CommonResult<StoreCouponInfoResponse> info(@RequestParam Integer id) {
        return CommonResult.success(storeCouponService.info(id));
    }

    /**
     * 发送优惠券列表
     * @param searchAndPageRequest 搜索分页参数
     */
    @PreAuthorize("hasAuthority('admin:coupon:send:list')")
    @Operation(summary = "发送优惠券列表")
    @RequestMapping(value = "/send/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreCoupon>>  getSendList(@Validated SearchAndPageRequest searchAndPageRequest) {
        CommonPage<StoreCoupon> storeCouponCommonPage = CommonPage.restPage(storeCouponService.getSendList(searchAndPageRequest));
        return CommonResult.success(storeCouponCommonPage);
    }

    /**
     * 删除优惠券
     * @param id 优惠券id
     */
    @PreAuthorize("hasAuthority('admin:coupon:delete')")
    @Operation(summary = "删除优惠券")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public CommonResult<String> delete(@RequestParam Integer id) {
        if (storeCouponService.delete(id)) {
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR,"删除失败");
        }
    }
}



