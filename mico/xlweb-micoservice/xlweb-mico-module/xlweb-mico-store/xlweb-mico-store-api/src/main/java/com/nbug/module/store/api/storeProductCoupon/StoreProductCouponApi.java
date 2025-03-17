package com.nbug.module.store.api.storeProductCoupon;

import com.nbug.mico.common.model.product.StoreProductCoupon;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 商品优惠券")
public interface StoreProductCouponApi {

    String PREFIX = ApiConstants.PREFIX + "/storeProductCoupon";

    @GetMapping(PREFIX + "/getListByProductId")
    @Operation(summary = "获取商品优惠券列表")
    @Parameter(name = "productId", description = "商品Id", required = true)
    public CommonResult<List<StoreProductCoupon>> getListByProductId(@RequestParam Integer productId);

}
