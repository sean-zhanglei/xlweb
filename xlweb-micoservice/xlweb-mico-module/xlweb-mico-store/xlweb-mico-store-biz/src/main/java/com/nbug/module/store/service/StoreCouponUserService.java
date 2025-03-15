package com.nbug.module.store.service1;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.coupon.StoreCouponUser;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCouponUserRequest;
import com.nbug.mico.common.request.StoreCouponUserSearchRequest;
import com.nbug.mico.common.request.UserCouponReceiveRequest;
import com.nbug.mico.common.response.StoreCouponUserOrder;
import com.nbug.mico.common.response.StoreCouponUserResponse;
import com.nbug.mico.common.vo.MyRecord;

import java.util.HashMap;
import java.util.List;

/**
 * StoreCouponUserService 接口

 */
public interface StoreCouponUserService extends IService<StoreCouponUser> {

    /**
     * 优惠券发放记录
     * @param request 查询参数
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    PageInfo<StoreCouponUserResponse> getList(StoreCouponUserSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * PC领取优惠券
     * @param storeCouponUserRequest 优惠券参数
     * @return Boolean
     */
    Boolean receive(StoreCouponUserRequest storeCouponUserRequest);

    HashMap<Integer, StoreCouponUser> getMapByUserId(Integer userId);

    /**
     * 根据购物车id获取可用优惠券
     * @param preOrderNo 预下单订单号
     * @return 可用优惠券集合
     */
    List<StoreCouponUserOrder> getListByPreOrderNo(String preOrderNo);

    /**
     * 优惠券过期定时任务
     */
    void overdueTask();

    /**
     * 用户领取优惠券
     */
    Boolean receiveCoupon(UserCouponReceiveRequest request);

    /**
     * 支付成功赠送处理
     * @param couponId 优惠券编号
     * @param uid  用户uid
     * @return MyRecord
     */
    MyRecord paySuccessGiveAway(Integer couponId, Integer uid);

    /**
     * 根据uid获取列表
     * @param uid uid
     * @param pageParamRequest 分页参数
     * @return List<StoreCouponUser>
     */
    List<StoreCouponUser> findListByUid(Integer uid, PageParamRequest pageParamRequest);

    /**
     * 获取可用优惠券数量
     * @param uid 用户uid
     */
    Integer getUseCount(Integer uid);

    /**
     * 我的优惠券列表
     * @param type 类型，usable-可用，unusable-不可用
     * @param pageParamRequest 分页参数
     * @return CommonPage<StoreCouponUserResponse>
     */
    CommonPage<StoreCouponUserResponse> getMyCouponList(String type, PageParamRequest pageParamRequest);
}
