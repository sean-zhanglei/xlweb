package com.nbug.module.store.api.storeCoupon;

import com.nbug.mico.common.model.coupon.StoreCoupon;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 优惠券")
public interface StoreCouponApi {

    String PREFIX = ApiConstants.PREFIX + "/storeCoupon";

    @PostMapping(PREFIX + "/deduction")
    @Operation(summary = "优惠券扣减")
    @Parameters({
            @Parameter(name = "id", description = "优惠券Id", required = true),
            @Parameter(name = "num", description = "优惠券数量", required = true),
            @Parameter(name = "isLimited", description = "是否限时", required = true)
    })
    public CommonResult<Boolean> deduction(Integer id, Integer num, Boolean isLimited);

    @PostMapping(PREFIX + "/findRegisterList")
    @Operation(summary = "获取优惠券列表")
    public CommonResult<List<StoreCoupon>> findRegisterList();

}
