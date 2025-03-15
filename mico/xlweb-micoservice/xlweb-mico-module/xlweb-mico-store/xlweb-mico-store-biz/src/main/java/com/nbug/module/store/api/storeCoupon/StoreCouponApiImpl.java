package com.nbug.module.store.api.storeCoupon;

import com.nbug.mico.common.model.coupon.StoreCoupon;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreCouponService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreCouponApiImpl implements StoreCouponApi {

    @Resource
    private StoreCouponService storeCouponService;

    /**
     * 扣减数量
     * @param id 优惠券id
     * @param num 数量
     * @param isLimited 是否限量
     */
    @Override
    public CommonResult<Boolean> deduction(Integer id, Integer num, Boolean isLimited) {
        return success(storeCouponService.deduction(id, num, isLimited));
    }


    @Override
    public CommonResult<List<StoreCoupon>> findRegisterList() {
        return success(storeCouponService.findRegisterList());
    }
}
