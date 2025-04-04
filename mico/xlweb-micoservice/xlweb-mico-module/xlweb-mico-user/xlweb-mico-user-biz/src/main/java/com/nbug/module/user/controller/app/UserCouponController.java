package com.nbug.module.user.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCouponReceiveRequest;
import com.nbug.mico.common.response.StoreCouponUserResponse;
import com.nbug.module.store.api.storeCouponUser.StoreCouponUserApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠卷控制器

 */
@Slf4j
@RestController
@RequestMapping("/user/coupon")
@Tag(name = "应用后台 - 营销 -- 优惠券")
public class UserCouponController {

    @Autowired
    private StoreCouponUserApi storeCouponUserApi;

    /**
     * 我的优惠券
     */
    @Operation(summary = "我的优惠券")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name ="type", description="类型，usable-可用，unusable-不可用", required = true),
            @Parameter(name="page", description="页码", required = true),
            @Parameter(name="limit", description="每页数量", required = true)
    })
    public CommonResult<CommonPage<StoreCouponUserResponse>> getList(@RequestParam(value = "type") String type,
                                                                     @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(storeCouponUserApi.getMyCouponList(type, pageParamRequest).getCheckedData());
    }

    /**
     * 领券
     * @param request UserCouponReceiveRequest 新增参数
     */
    @Operation(summary = "领券")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public CommonResult<String> receive(@RequestBody @Validated UserCouponReceiveRequest request) {
        storeCouponUserApi.receiveCoupon(request).getCheckedData();
        return CommonResult.success("success");
    }

}



