package com.nbug.module.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.TaskConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.request.StoreOrderStaticsticsRequest;
import com.nbug.mico.common.response.StoreOrderVerificationConfirmResponse;
import com.nbug.mico.common.response.StoreStaffDetail;
import com.nbug.mico.common.response.StoreStaffTopDetail;
import com.nbug.mico.common.utils.SecurityUtil;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.LoginUserVo;
import com.nbug.mico.common.vo.dateLimitUtilVo;
import com.nbug.module.order.api.OrderApi;
import com.nbug.module.store.dal.StoreOrderDao;
import com.nbug.module.store.service.StoreOrderInfoService;
import com.nbug.module.store.service.StoreOrderService;
import com.nbug.module.store.service.StoreOrderVerification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * StoreOrderVerificationImpl 接口实现 核销订单

 */
@Service
public class StoreOrderVerificationImpl implements StoreOrderVerification {

    @Resource
    private StoreOrderDao dao;

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private StoreOrderInfoService storeOrderInfoService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取订单核销数据
     */
    @Override
    public StoreStaffTopDetail getOrderVerificationData() {
        StoreStaffTopDetail storeStaffTopDetail = new StoreStaffTopDetail();
        //订单支付没有退款 数量-
        LambdaQueryWrapper<StoreOrder> lqwOrderCount = Wrappers.lambdaQuery();
        lqwOrderCount.eq(StoreOrder::getIsDel,false).eq(StoreOrder::getPaid,true).eq(StoreOrder::getRefundStatus,0);
        storeStaffTopDetail.setOrderCount(Math.toIntExact(dao.selectCount(lqwOrderCount)));
        //订单支付没有退款 支付总金额
        LambdaQueryWrapper<StoreOrder> lqwSumPrice = Wrappers.lambdaQuery();
        lqwSumPrice.eq(StoreOrder::getIsDel,false).eq(StoreOrder::getPaid,true).eq(StoreOrder::getRefundStatus,0);
        List<StoreOrder> storeOrdersSumPrice = dao.selectList(lqwSumPrice);
        Double sumPrice = storeOrdersSumPrice.stream().mapToDouble(e->e.getPayPrice().doubleValue()).sum();
        storeStaffTopDetail.setSumPrice(BigDecimal.valueOf(sumPrice).setScale(2,BigDecimal.ROUND_HALF_UP));
        //订单待支付 数量
        LambdaQueryWrapper<StoreOrder> lqwUnPaidCount = Wrappers.lambdaQuery();
        statusApiByWhere(0);
        storeStaffTopDetail.setUnpaidCount(Math.toIntExact(dao.selectCount(lqwUnPaidCount)));
        //订单待发货 数量
        LambdaQueryWrapper<StoreOrder> lqwUnShippedCount = Wrappers.lambdaQuery();
        statusApiByWhere(1);
        storeStaffTopDetail.setUnshippedCount(Math.toIntExact(dao.selectCount(lqwUnShippedCount)));
        //订单待收货 数量
        LambdaQueryWrapper<StoreOrder> lqwReceivedCount = Wrappers.lambdaQuery();
        statusApiByWhere(2);
        storeStaffTopDetail.setReceivedCount(Math.toIntExact(dao.selectCount(lqwReceivedCount)));
        // 订单待核销数量
        LambdaQueryWrapper<StoreOrder> verificationCount = Wrappers.lambdaQuery();
        statusApiByWhere(3);
        storeStaffTopDetail.setVerificationCount(Math.toIntExact(dao.selectCount(verificationCount)));
        //订单已完成 数量
        LambdaQueryWrapper<StoreOrder> lqwCompleteCount = Wrappers.lambdaQuery();
        statusApiByWhere(4);
        storeStaffTopDetail.setCompleteCount(Math.toIntExact(dao.selectCount(lqwCompleteCount)));
        //订单退款 数量
        LambdaQueryWrapper<StoreOrder> lqwRefundCount = Wrappers.lambdaQuery();
        statusApiByWhere(-3);
        storeStaffTopDetail.setRefundCount(Math.toIntExact(dao.selectCount(lqwRefundCount)));

        // 获取今日，昨日，本月，订单金额
        String dayStart = DateUtil.nowDateTime(Constants.DATE_FORMAT_START);
        String dayEnd = DateUtil.nowDateTime(Constants.DATE_FORMAT_END);
        String yesterdayStart = DateUtil.addDay(dayStart,-1,Constants.DATE_FORMAT_START);
        String yesterdayEnd = DateUtil.addDay(dayEnd,-1,Constants.DATE_FORMAT_END);
        String monthStart = DateUtil.nowDateTime(Constants.DATE_FORMAT_MONTH_START);
        String monthEnd = DateUtil.getMonthEndDay();

        // 今日订单数量
        LambdaQueryWrapper<StoreOrder> lqwTodayCount = Wrappers.lambdaQuery();
        lqwTodayCount.eq(StoreOrder::getIsDel,false).between(StoreOrder::getPayTime,dayStart,dayEnd)
                .eq(StoreOrder::getPaid,1).eq(StoreOrder::getRefundStatus,0);
        List<StoreOrder> storeOrdersTodayCount = dao.selectList(lqwTodayCount);
        if(null == storeOrdersTodayCount) storeOrdersTodayCount = new ArrayList<>();
        storeStaffTopDetail.setTodayCount(storeOrdersTodayCount.size());

        // 今日成交额
        double todayPrice = storeOrdersTodayCount.stream().mapToDouble(e->e.getPayPrice().doubleValue()).sum();
        storeStaffTopDetail.setTodayPrice(BigDecimal.valueOf(todayPrice).setScale(2,BigDecimal.ROUND_HALF_UP));

        // 昨日订单数
        LambdaQueryWrapper<StoreOrder> lqwPro = Wrappers.lambdaQuery();
        lqwPro.eq(StoreOrder::getIsDel,false).between(StoreOrder::getCreateTime,yesterdayStart,yesterdayEnd)
                .eq(StoreOrder::getPaid, true).eq(StoreOrder::getRefundStatus,0);
        List<StoreOrder> storeOrdersPro = dao.selectList(lqwPro);
        if(null == storeOrdersPro) storeOrdersPro = new ArrayList<>();
        storeStaffTopDetail.setProCount(storeOrdersPro.size());

        //  昨日成交额
        double proPrice = storeOrdersPro.stream().mapToDouble(e->e.getPayPrice().doubleValue()).sum();
        storeStaffTopDetail.setProPrice(BigDecimal.valueOf(proPrice).setScale(2,BigDecimal.ROUND_HALF_UP));

        // 本月成交订单数量
        LambdaQueryWrapper<StoreOrder> lqwMonth = Wrappers.lambdaQuery();
        lqwMonth.eq(StoreOrder::getIsDel,false).between(StoreOrder::getPayTime,monthStart, monthEnd)
                .eq(StoreOrder::getPaid,true).eq(StoreOrder::getRefundStatus,0);
        List<StoreOrder> storeOrdersMonth = dao.selectList(lqwMonth);
        if(null == storeOrdersMonth) storeOrdersMonth = new ArrayList<>();
        storeStaffTopDetail.setMonthCount(storeOrdersMonth.size());

        // 本月成交额
        double monthTotalPrice = storeOrdersMonth.stream().mapToDouble(e -> e.getPayPrice().doubleValue()).sum();
        storeStaffTopDetail.setMonthPrice(BigDecimal.valueOf(monthTotalPrice).setScale(2,BigDecimal.ROUND_HALF_UP));

        return storeStaffTopDetail;
    }

    /**
     * h5 订单查询 where status 封装
     * @param status 状态
     */
    public void statusApiByWhere(Integer status){
        LambdaQueryWrapper<StoreOrder> queryWrapper = Wrappers.lambdaQuery();
        switch (status){
            case Constants.ORDER_STATUS_H5_UNPAID: // 未支付
                queryWrapper.eq(StoreOrder::getPaid, false);
                queryWrapper.eq(StoreOrder::getStatus, 0);
                queryWrapper.eq(StoreOrder::getRefundStatus, 0);
                queryWrapper.eq(StoreOrder::getType, 0);
                break;
            case Constants.ORDER_STATUS_H5_NOT_SHIPPED: // 待发货
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.eq(StoreOrder::getStatus, 0);
                queryWrapper.eq(StoreOrder::getRefundStatus, 0);
//                queryWrapper.eq(StoreOrder::getShippingType, 1);
                break;
            case Constants.ORDER_STATUS_H5_SPIKE: // 待收货
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.eq(StoreOrder::getStatus, 1);
                queryWrapper.eq(StoreOrder::getRefundStatus, 0);
                break;
            case Constants.ORDER_STATUS_H5_JUDGE: //  已支付 已收货 待评价
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.eq(StoreOrder::getStatus, 2);
                queryWrapper.eq(StoreOrder::getRefundStatus, 0);
                break;
            case Constants.ORDER_STATUS_H5_COMPLETE: // 已完成
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.eq(StoreOrder::getStatus, 3);
                queryWrapper.eq(StoreOrder::getRefundStatus, 0);
                break;
            case Constants.ORDER_STATUS_H5_REFUNDING: // 退款中
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.in(StoreOrder::getRefundStatus, 1, 3);
                break;
            case Constants.ORDER_STATUS_H5_REFUNDED: // 已退款
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.eq(StoreOrder::getRefundStatus, 2);
                break;
            case Constants.ORDER_STATUS_H5_REFUND: // 包含已退款和退款中
                queryWrapper.eq(StoreOrder::getPaid, true);
                queryWrapper.in(StoreOrder::getRefundStatus, 1,2,3);
                break;
        }
        queryWrapper.eq(StoreOrder::getIsDel, false);
        queryWrapper.eq(StoreOrder::getIsSystemDel, false);
    }

    /**
     * 核销月详情
     * @return 月详情
     */
    @Override
    public List<StoreStaffDetail> getOrderVerificationDetail(StoreOrderStaticsticsRequest request) {
        request.setPage((request.getPage() - 1) * request.getLimit());
        dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
        request.setStartTime(dateLimit.getStartTime());
        request.setEndTime(dateLimit.getEndTime());
        return dao.getOrderVerificationDetail(request);
    }


    /**
     * 根据核销码核销订单(相当于收货)
     *
     * @param vCode 核销码
     * @return 核销结果
     */
    @Override
    public boolean verificationOrderByCode(String vCode) {
        StoreOrderVerificationConfirmResponse existOrder = getVerificationOrderByCode(vCode);
        // 判断当前用户是否有权限核销
        LoginUserVo loginUserVo = SecurityUtil.getLoginUserVo();
        SystemAdmin currentAdmin = loginUserVo.getUser();
        // 添加核销人员后执行核销操作
        StoreOrder storeOrder = new StoreOrder();
        BeanUtils.copyProperties(existOrder,storeOrder);
        storeOrder.setStatus(Constants.ORDER_STATUS_INT_BARGAIN);
        storeOrder.setClerkId(currentAdmin.getId());
        boolean saveStatus = dao.updateById(storeOrder) > 0;

        // 小程序订阅消息发送
        if(saveStatus){
            //后续操作放入redis
            redisUtil.lPush(TaskConstants.ORDER_TASK_REDIS_KEY_AFTER_TAKE_BY_USER, storeOrder.getId());
        }
        return saveStatus;
    }

    /**
     * 根据核销码查询待核销订单
     *
     * @param vCode 核销码
     * @return 待核销订单详情
     */
    @Override
    public StoreOrderVerificationConfirmResponse getVerificationOrderByCode(String vCode) {
        StoreOrderVerificationConfirmResponse response = new StoreOrderVerificationConfirmResponse();
        StoreOrder storeOrderPram = new StoreOrder().setVerifyCode(vCode).setPaid(true).setRefundStatus(0);
        StoreOrder existOrder = storeOrderService.getByEntityOne(storeOrderPram);
        if(null == existOrder) throw new XlwebException(Constants.RESULT_VERIFICATION_ORDER_NOT_FUND.replace("${vCode}",vCode));
        if(existOrder.getStatus() > 0) throw new XlwebException(Constants.RESULT_VERIFICATION_ORDER_VED.replace("${vCode}",vCode));
        BeanUtils.copyProperties(existOrder, response);
        response.setStoreOrderInfoVos(storeOrderInfoService.getOrderListByOrderId(existOrder.getId()));
        return response;
    }
}
