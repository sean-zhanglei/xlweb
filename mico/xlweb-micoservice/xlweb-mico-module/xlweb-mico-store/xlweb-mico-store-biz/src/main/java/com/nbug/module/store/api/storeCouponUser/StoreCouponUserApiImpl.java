package com.nbug.module.store.api.storeCouponUser;

import com.nbug.mico.common.model.coupon.StoreCouponUser;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCouponReceiveRequest;
import com.nbug.mico.common.response.StoreCouponUserResponse;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.store.service.StoreCouponUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreCouponUserApiImpl implements StoreCouponUserApi {

    @Resource
    private StoreCouponUserService storeCouponUserService;

    @Override
    public CommonResult<StoreCouponUser> getById(Integer id) {
        return success(storeCouponUserService.getById(id));
    }

    @Override
    public CommonResult<Boolean> updateById(StoreCouponUser storeCouponUser) {
        return success(storeCouponUserService.updateById(storeCouponUser));
    }

    /**
     * 支付成功赠送处理
     * @param couponId 优惠券编号
     * @param userId  用户uid
     * @return MyRecord
     */
    @Override
    public CommonResult<MyRecord> paySuccessGiveAway(Integer couponId, Integer userId) {
        return success(storeCouponUserService.paySuccessGiveAway(couponId, userId));
    }

    @Override
    public CommonResult<Boolean> saveBatch(List<StoreCouponUser> storeCouponUsers) {
        return success(storeCouponUserService.saveBatch(storeCouponUsers));
    }

    /**
     * 获取可用优惠券数量
     * @param userId 用户uid
     */
    @Override
    public CommonResult<Integer> getUseCount(Integer userId) {
        return success(storeCouponUserService.getUseCount(userId));
    }

    /**
     * 根据uid获取列表
     * * @param userId userId
     * @param pageParamRequest 分页参数
     * @return List<StoreCouponUser>
     */
    @Override
    public CommonResult<List<StoreCouponUser>> findListByUid(Integer userId, PageParamRequest pageParamRequest) {
        return success(storeCouponUserService.findListByUid(userId, pageParamRequest));
    }

    /**
     * 我的优惠券列表
     * @param type 类型，usable-可用，unusable-不可用
     * @param pageParamRequest 分页参数
     * @return CommonPage<StoreCouponUserResponse>
     */
    @Override
    public CommonResult<CommonPage<StoreCouponUserResponse>> getMyCouponList(String type, PageParamRequest pageParamRequest) {
        return success(storeCouponUserService.getMyCouponList(type, pageParamRequest));
    }

    /**
     * 用户领取优惠券
     */
    @Override
    public CommonResult<Boolean> receiveCoupon(UserCouponReceiveRequest request) {
        return success(storeCouponUserService.receiveCoupon(request));
    }
}
