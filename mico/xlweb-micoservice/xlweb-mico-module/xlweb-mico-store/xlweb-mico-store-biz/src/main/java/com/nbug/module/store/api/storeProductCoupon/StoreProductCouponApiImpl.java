package com.nbug.module.store.api.storeProductCoupon;

import com.nbug.mico.common.model.product.StoreProductCoupon;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreProductCouponService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreProductCouponApiImpl implements StoreProductCouponApi {

    @Resource
    private StoreProductCouponService storeProductCouponService;

    /**
     * 根据商品id获取已关联优惠券信息
     * @param productId 商品id
     * @return 已关联优惠券
     */
    @Override
    public CommonResult<List<StoreProductCoupon>> getListByProductId(Integer productId) {
        return success(storeProductCouponService.getListByProductId(productId));
    }

}
