package com.nbug.module.store.api.storeCouponUser;

import com.nbug.mico.common.model.coupon.StoreCouponUser;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCouponReceiveRequest;
import com.nbug.mico.common.response.StoreCouponUserResponse;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 优惠卷记录")
public interface StoreCouponUserApi {

    String PREFIX = ApiConstants.PREFIX + "/storeCouponUser";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获得优惠券")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<StoreCouponUser> getById(@RequestParam Integer id);

    @PostMapping(PREFIX + "/updateById")
    @Operation(summary = "更新优惠券")
    @Parameter(name = "storeCouponUser", description = "更新优惠券", required = true)
    public CommonResult<Boolean> updateById(@RequestBody StoreCouponUser storeCouponUser);

    @PostMapping(PREFIX + "/paySuccessGiveAway")
    @Operation(summary = "支付成功赠送优惠券")
    @Parameters({
            @Parameter(name = "couponId", description = "优惠券ID", required = true, example = "1024"),
            @Parameter(name = "userId", description = "用户ID", required = true, example = "1024")
    })
    public CommonResult<MyRecord> paySuccessGiveAway(@RequestParam Integer couponId,
                                                     @RequestParam Integer userId);

    @PostMapping(PREFIX + "/saveBatch")
    @Operation(summary = "批量保存优惠券")
    @Parameter(name = "storeCouponUsers", description = "优惠券", required = true)
    public CommonResult<Boolean> saveBatch(@RequestBody List<StoreCouponUser> storeCouponUsers);

    @GetMapping(PREFIX + "/getUseCount")
    @Operation(summary = "获取可用优惠券数量")
    @Parameter(name = "userId", description = "用户ID", required = true, example = "1024")
    public CommonResult<Integer> getUseCount(@RequestParam Integer userId);

    @GetMapping(PREFIX + "/findListByUid")
    @Operation(summary = "获取用户优惠券列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", required = true, example = "1024"),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<List<StoreCouponUser>> findListByUid(@RequestParam Integer userId,
                                                             @SpringQueryMap PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getMyCouponList")
    @Operation(summary = "获取我的优惠券")
    @Parameters({
            @Parameter(name = "type", description = "类型", required = true, example = "usable-可用，unusable-不可用"),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true),
    })
    public CommonResult<CommonPage<StoreCouponUserResponse>> getMyCouponList(@RequestParam String type,
                                                                             @SpringQueryMap PageParamRequest pageParamRequest);


    @PostMapping(PREFIX + "/receiveCoupon")
    @Operation(summary = "领取优惠券")
    @Parameter(name = "request", description = "用户领取优惠券请求对象", required = true)
    public CommonResult<Boolean> receiveCoupon(@RequestBody @Validated UserCouponReceiveRequest request);
}
