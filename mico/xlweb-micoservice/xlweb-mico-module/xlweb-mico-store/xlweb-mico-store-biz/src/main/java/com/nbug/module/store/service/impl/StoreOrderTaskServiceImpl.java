package com.nbug.module.store.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.nbug.mico.common.constants.BrokerageRecordConstants;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.CouponConstants;
import com.nbug.mico.common.constants.ExperienceRecordConstants;
import com.nbug.mico.common.constants.IntegralRecordConstants;
import com.nbug.mico.common.constants.NotifyConstants;
import com.nbug.mico.common.constants.UserConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.bargain.StoreBargain;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.model.coupon.StoreCouponUser;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.order.StoreOrderInfo;
import com.nbug.mico.common.model.product.StoreProductAttrValue;
import com.nbug.mico.common.model.seckill.StoreSeckill;
import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.model.user.UserIntegralRecord;
import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.infra.api.sms.NotificationApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.sms.SmsTemplateApi;
import com.nbug.module.infra.api.sms.TemplateMessageApi;
import com.nbug.module.order.api.OrderApi;
import com.nbug.module.store.service.StoreBargainService;
import com.nbug.module.store.service.StoreCombinationService;
import com.nbug.module.store.service.StoreCouponUserService;
import com.nbug.module.store.service.StoreOrderInfoService;
import com.nbug.module.store.service.StoreOrderService;
import com.nbug.module.store.service.StoreOrderStatusService;
import com.nbug.module.store.service.StoreOrderTaskService;
import com.nbug.module.store.service.StorePinkService;
import com.nbug.module.store.service.StoreProductAttrValueService;
import com.nbug.module.store.service.StoreProductService;
import com.nbug.module.store.service.StoreSeckillService;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userExperience.UserExperienceApi;
import com.nbug.module.user.api.userIntegralRecord.UserIntegralRecordApi;
import com.nbug.module.user.api.userLevel.UserLevelApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StoreOrderTaskService实现类

 */
@Service
public class StoreOrderTaskServiceImpl implements StoreOrderTaskService {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(StoreOrderTaskServiceImpl.class);

    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private StoreOrderInfoService storeOrderInfoService;

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private StoreOrderStatusService storeOrderStatusService;

    @Autowired
    private UserApi userApi;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private StoreSeckillService storeSeckillService;

    @Autowired
    private StoreBargainService storeBargainService;

    @Autowired
    private StoreCombinationService storeCombinationService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBrokerageRecordApi userBrokerageRecordApi;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private AdminApi adminApi;

    @Autowired
    private UserTokenApi userTokenApi;

    @Autowired
    private TemplateMessageApi templateMessageApi;

    @Autowired
    private OrderApi orderApi;

    @Autowired
    private StorePinkService storePinkService;

    @Autowired
    private UserIntegralRecordApi userIntegralRecordApi;

    @Autowired
    private UserLevelApi userLevelApi;

    @Autowired
    private StoreProductAttrValueService attrValueService;

    @Autowired
    private StoreCouponUserService couponUserService;

    @Autowired
    private UserExperienceApi userExperienceApi;

    @Autowired
    private NotificationApi notificationApi;

    @Autowired
    private SmsTemplateApi smsTemplateApi;

    /**
     * 用户取消订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, XlwebException.class})
    public Boolean cancelByUser(StoreOrder storeOrder) {
        try{
            /*
            * 1、修改订单状态 （用户操作的时候已处理）
            * 2、写订单日志
            * 3、回滚库存
            * 4、回滚优惠券
            * 5、回滚积分
            * */

            //写订单日志
            storeOrderStatusService.createLog(storeOrder.getId(), "cancel_order", "取消订单");
            // 退优惠券
            if (storeOrder.getCouponId() > 0 ) {
                StoreCouponUser couponUser = couponUserService.getById(storeOrder.getCouponId());
                couponUser.setStatus(CouponConstants.STORE_COUPON_USER_STATUS_USABLE);
                couponUserService.updateById(couponUser);
            }
            return rollbackStock(storeOrder);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 完成订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class, XlwebException.class})
    public Boolean complete(StoreOrder storeOrder) {
        /*
         * 1、修改订单状态 （用户操作的时候已处理）
         * 2、写订单日志
         * */
        try{
            storeOrderStatusService.createLog(storeOrder.getId(), "check_order_over", "用户评价");
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 回滚库存
     * @param storeOrder 订单信息
     */
    private Boolean rollbackStock(StoreOrder storeOrder) {
        try{
            // 查找出商品详情
            List<StoreOrderInfo> orderInfoList = storeOrderInfoService.getListByOrderNo(storeOrder.getOrderId());
            if(null == orderInfoList || orderInfoList.size() < 1){
                return true;
            }

            // 兼容处理秒杀数据退款
            // 秒杀商品回滚库存和销量
            if(null != storeOrder.getSeckillId() && storeOrder.getSeckillId() > 0){
                // 秒杀只会有一个商品
                StoreOrderInfo orderInfo = orderInfoList.get(0);
                StoreSeckill storeSeckill = storeSeckillService.getByIdException(storeOrder.getSeckillId());
                storeSeckillService.operationStock(storeOrder.getSeckillId(), orderInfo.getPayNum(), "add");
                attrValueService.operationStock(orderInfo.getAttrValueId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_SECKILL);
                storeProductService.operationStock(storeSeckill.getProductId(), orderInfo.getPayNum(), "add");
                List<StoreProductAttrValue> attrValueList = attrValueService.getListByProductIdAndType(storeSeckill.getProductId(), Constants.PRODUCT_TYPE_NORMAL);
                attrValueList.forEach(e -> {
                    if (e.getSuk().equals(orderInfo.getSku())) {
                        attrValueService.operationStock(e.getId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_NORMAL);
                    }
                });
            } else if (ObjectUtil.isNotNull(storeOrder.getBargainId()) && storeOrder.getBargainId() > 0) { // 砍价商品回滚销量库存
                StoreOrderInfo orderInfo = orderInfoList.get(0);
                StoreBargain storeBargain = storeBargainService.getByIdException(storeOrder.getBargainId());
                storeBargainService.operationStock(storeBargain.getId(), orderInfo.getPayNum(), "add");
                attrValueService.operationStock(orderInfo.getAttrValueId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_BARGAIN);
                storeProductService.operationStock(storeBargain.getProductId(), orderInfo.getPayNum(), "add");
                List<StoreProductAttrValue> attrValueList = attrValueService.getListByProductIdAndType(storeBargain.getProductId(), Constants.PRODUCT_TYPE_NORMAL);
                attrValueList.forEach(e -> {
                    if (e.getSuk().equals(orderInfo.getSku())) {
                        attrValueService.operationStock(e.getId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_NORMAL);
                    }
                });
            } else if (ObjectUtil.isNotNull(storeOrder.getCombinationId()) && storeOrder.getCombinationId() > 0) { // 拼团商品回滚销量库存
                StoreOrderInfo orderInfo = orderInfoList.get(0);
                StoreCombination storeCombination = storeCombinationService.getByIdException(storeOrder.getCombinationId());
                storeCombinationService.operationStock(storeCombination.getId(), orderInfo.getPayNum(), "add");
                attrValueService.operationStock(orderInfo.getAttrValueId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_PINGTUAN);
                storeProductService.operationStock(storeCombination.getProductId(), orderInfo.getPayNum(), "add");
                List<StoreProductAttrValue> attrValueList = attrValueService.getListByProductIdAndType(storeCombination.getProductId(), Constants.PRODUCT_TYPE_NORMAL);
                attrValueList.forEach(e -> {
                    if (e.getSuk().equals(orderInfo.getSku())) {
                        attrValueService.operationStock(e.getId(), orderInfo.getPayNum(), "add", Constants.PRODUCT_TYPE_NORMAL);
                    }
                });
            } else { // 正常商品回滚销量库存
                for (StoreOrderInfo orderInfoVo : orderInfoList) {
                    storeProductService.operationStock(orderInfoVo.getProductId(), orderInfoVo.getPayNum(), "add");
                    attrValueService.operationStock(orderInfoVo.getAttrValueId(), orderInfoVo.getPayNum(), "add", Constants.PRODUCT_TYPE_NORMAL);
                }
            }
        }catch (Exception e){
//            throw new XlwebException(e.getMessage());
            logger.error("回滚库存失败，error = " + e.getMessage());
            return true;
        }
        return true;
    }

    /**
     * 订单退款处理
     * 退款得时候根据userBill 来进行回滚
     */
    @Override
    public Boolean refundOrder(StoreOrder storeOrder) {
        /**
         * 1、写订单日志
         * 2、回滚消耗积分
         * 3、回滚获得积分
         * 4、回滚冻结期佣金
         * 5、回滚经验
         * 6、回滚库存
         * 7、发送通知
         * 实际上2-5就是user数据的处理+userBill的记录
         */
        // 获取用户对象
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        if (ObjectUtil.isNull(user)) {
            logger.error("订单退款处理，对应的用户不存在,storeOrder===>" + storeOrder);
            return Boolean.FALSE;
        }

        // 回滚经验
        UserExperienceRecord userExperienceRecord = userExperienceApi.getByOrderNoAndUid(storeOrder.getOrderId(), storeOrder.getUid()).getCheckedData();
        user.setExperience(user.getExperience() - userExperienceRecord.getExperience());

        UserExperienceRecord experienceRecord = new UserExperienceRecord();
        BeanUtils.copyProperties(userExperienceRecord, experienceRecord);
        experienceRecord.setId(null);
        experienceRecord.setTitle(ExperienceRecordConstants.EXPERIENCE_RECORD_TITLE_REFUND);
        experienceRecord.setType(ExperienceRecordConstants.EXPERIENCE_RECORD_TYPE_SUB);
        experienceRecord.setBalance(user.getExperience());
        experienceRecord.setMark(StrUtil.format("订单退款，扣除{}赠送经验", userExperienceRecord.getExperience()));
        experienceRecord.setCreateTime(cn.hutool.core.date.DateUtil.date());

        // 回滚积分
        List<UserIntegralRecord> integralRecordList = userIntegralRecordApi.findListByOrderIdAndUid(storeOrder.getOrderId(), storeOrder.getUid()).getCheckedData();
        integralRecordList.forEach(record -> {
            if (record.getType().equals(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_SUB)) {// 订单抵扣部分
                user.setIntegral(user.getIntegral() + record.getIntegral());
                record.setId(null);
                record.setTitle(IntegralRecordConstants.BROKERAGE_RECORD_TITLE_REFUND);
                record.setType(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
                record.setBalance(user.getIntegral());
                record.setMark(StrUtil.format("订单退款，返还支付扣除得{}积分", record.getIntegral()));
                record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
                record.setUpdateTime(cn.hutool.core.date.DateUtil.date());
            } else if (record.getType().equals(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD)) {// 冻结积分部分
                record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_INVALIDATION);
                record.setUpdateTime(cn.hutool.core.date.DateUtil.date());
            }
        });
        List<UserIntegralRecord> addIntegralList = integralRecordList.stream().filter(e -> ObjectUtil.isNull(e.getId())).collect(Collectors.toList());
        List<UserIntegralRecord> updateIntegralList = integralRecordList.stream().filter(e -> ObjectUtil.isNotNull(e.getId())).collect(Collectors.toList());

        StoreOrder tempOrder = new StoreOrder();
        tempOrder.setId(storeOrder.getId());
        tempOrder.setRefundStatus(2);
        // 佣金处理：只处理冻结期佣金
        // 查询佣金记录
        List<UserBrokerageRecord> brokerageRecordList = CollUtil.newArrayList();
        List<UserBrokerageRecord> recordList = userBrokerageRecordApi.findListByLinkIdAndLinkType(storeOrder.getOrderId(), BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_ORDER).getCheckedData();
        if (CollUtil.isNotEmpty(recordList)) {
            recordList.forEach(r -> {
                //创建、冻结期佣金置为失效状态
                if (r.getStatus() < BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_COMPLETE) {
                    r.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_INVALIDATION);
                    brokerageRecordList.add(r);
                }
            });
        }

        Boolean execute = transactionTemplate.execute(e -> {
            //写订单日志
            storeOrderStatusService.saveRefund(storeOrder.getId(), storeOrder.getRefundPrice(), "成功");

            // 更新用户数据
            userApi.updateById(user);

            // 积分部分
            if (CollUtil.isNotEmpty(addIntegralList)) {
                userIntegralRecordApi.saveBatch(addIntegralList);
            }
            if (CollUtil.isNotEmpty(updateIntegralList)) {
                userIntegralRecordApi.updateBatchById(updateIntegralList);
            }

            // 佣金处理
            if (CollUtil.isNotEmpty(brokerageRecordList)) {
                userBrokerageRecordApi.updateBatchById(brokerageRecordList);
            }

            // 经验处理
            userExperienceApi.save(experienceRecord);
            userLevelApi.downLevel(user);

            // 回滚库存
            rollbackStock(storeOrder);

            storeOrderService.updateById(tempOrder);

            // 拼团状态处理
            if (storeOrder.getCombinationId() > 0) {
                StorePink storePink = storePinkService.getByOrderId(storeOrder.getOrderId());
                storePink.setStatus(3);
                storePink.setIsRefund(true);
                storePinkService.updateById(storePink);
            }

            // 退优惠券
            if (storeOrder.getCouponId() > 0 ) {
                StoreCouponUser couponUser = couponUserService.getById(storeOrder.getCouponId());
                couponUser.setStatus(CouponConstants.STORE_COUPON_USER_STATUS_USABLE);
                couponUserService.updateById(couponUser);
            }
            return Boolean.TRUE;
        });
        return execute;
    }

    /**
     * 超时未支付系统自动取消
     */
    @Override
    public Boolean autoCancel(StoreOrder storeOrder) {
        // 判断订单是否支付
        if (storeOrder.getPaid()) {
            return Boolean.TRUE;
        }
        if (storeOrder.getIsDel() || storeOrder.getIsSystemDel()) {
            return Boolean.TRUE;
        }
        // 获取过期时间
        String cancelStr;
        DateTime cancelTime;
        if (storeOrder.getType().equals(1)) {
            cancelStr = "3";
            cancelTime = cn.hutool.core.date.DateUtil.offset(storeOrder.getCreateTime(), DateField.MINUTE, Integer.parseInt(cancelStr));
        } else {
            if (storeOrder.getBargainId() > 0 || storeOrder.getSeckillId() > 0 || storeOrder.getCombinationId() > 0) {
                cancelStr = configApi.getValueByKey("order_activity_time").getCheckedData();
            } else {
                cancelStr = configApi.getValueByKey("order_cancel_time").getCheckedData();
            }
            if (StrUtil.isBlank(cancelStr)) {
                cancelStr = "1";
            }
            cancelTime = cn.hutool.core.date.DateUtil.offset(storeOrder.getCreateTime(), DateField.HOUR_OF_DAY, Integer.parseInt(cancelStr));
        }
        long between = cn.hutool.core.date.DateUtil.between(cancelTime, cn.hutool.core.date.DateUtil.date(), DateUnit.SECOND, false);
        if (between < 0) {// 未到过期时间继续循环
            return Boolean.FALSE;
        }
        storeOrder.setIsDel(true).setIsSystemDel(true);
        Boolean execute = transactionTemplate.execute(e -> {
            storeOrderService.updateById(storeOrder);
            //写订单日志
            storeOrderStatusService.createLog(storeOrder.getId(), "cancel", "到期未支付系统自动取消");
            // 退优惠券
            if (storeOrder.getCouponId() > 0 ) {
                StoreCouponUser couponUser = couponUserService.getById(storeOrder.getCouponId());
                couponUser.setStatus(CouponConstants.STORE_COUPON_USER_STATUS_USABLE);
                couponUserService.updateById(couponUser);
            }
            return Boolean.TRUE;
        });
        if (execute) {
            // 回滚库存
            rollbackStock(storeOrder);
        }
        return execute;
    }

    /**
     * 订单收货task处理
     * @param orderId 订单id
     * @return Boolean
     * 1.写订单日志
     * 2.分佣-佣金进入冻结期
     */
    @Override
    public Boolean orderReceiving(Integer orderId) {
        StoreOrder storeOrder = storeOrderService.getById(orderId);
        if (ObjectUtil.isNull(storeOrder)) {
            throw new XlwebException(StrUtil.format("订单收货task处理，未找到订单，id={}", orderId));
        }
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();

        // 获取佣金记录
        List<UserBrokerageRecord> recordList = userBrokerageRecordApi.findListByLinkIdAndLinkType(storeOrder.getOrderId(), BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_ORDER).getCheckedData();
        logger.info("收货处理佣金条数：" + recordList.size());
        for (UserBrokerageRecord record : recordList) {
            if (!record.getStatus().equals(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_CREATE)) {
                throw new XlwebException(StrUtil.format("订单收货task处理，订单佣金记录不是创建状态，id={}", orderId));
            }
            // 佣金进入冻结期
            record.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_FROZEN);
            // 计算解冻时间
            Long thawTime = cn.hutool.core.date.DateUtil.current();
            if (record.getFrozenTime() > 0) {
                DateTime dateTime = cn.hutool.core.date.DateUtil.offsetDay(new Date(), record.getFrozenTime());
                thawTime = dateTime.getTime();
            }
            record.setThawTime(thawTime);
        }

        // 获取积分记录
        List<UserIntegralRecord> integralRecordList = userIntegralRecordApi.findListByOrderIdAndUid(storeOrder.getOrderId(), storeOrder.getUid()).getCheckedData();
        logger.info("收货处理积分条数：" + integralRecordList.size());
        List<UserIntegralRecord> userIntegralRecordList = integralRecordList.stream().filter(e -> e.getType().equals(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD)).collect(Collectors.toList());
        for (UserIntegralRecord record : userIntegralRecordList) {
            if (!record.getStatus().equals(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_CREATE)) {
                throw new XlwebException(StrUtil.format("订单收货task处理，订单积分记录不是创建状态，id={}", orderId));
            }
            // 佣金进入冻结期
            record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_FROZEN);
            // 计算解冻时间
            Long thawTime = cn.hutool.core.date.DateUtil.current();
            if (record.getFrozenTime() > 0) {
                DateTime dateTime = cn.hutool.core.date.DateUtil.offsetDay(new Date(), record.getFrozenTime());
                thawTime = dateTime.getTime();
            }
            record.setThawTime(thawTime);
        }

        Boolean execute = transactionTemplate.execute(e -> {
            // 日志
            storeOrderStatusService.createLog(storeOrder.getId(), "user_take_delivery", Constants.ORDER_STATUS_STR_TAKE);
            // 分佣-佣金进入冻结期
            if (CollUtil.isNotEmpty(recordList)) {
                userBrokerageRecordApi.updateBatchById(recordList);
            }
            // 积分进入冻结期
            if (CollUtil.isNotEmpty(userIntegralRecordList)) {
                userIntegralRecordApi.updateBatchById(userIntegralRecordList);
            }
            return Boolean.TRUE;
        });
        if (execute) {
            // 发送用户确认收货管理员提醒短信
            SystemNotification notification = notificationApi.getByMark(NotifyConstants.RECEIPT_GOODS_ADMIN_MARK).getCheckedData();
            if (notification.getIsSms().equals(1)) {
                // 查询可已发送短信的管理员
                List<SystemAdmin> systemAdminList = adminApi.findIsSmsList().getCheckedData();
                if (CollUtil.isNotEmpty(systemAdminList)) {
                    SmsTemplate smsTemplate = smsTemplateApi.getDetail(notification.getSmsId()).getCheckedData();
                    // 发送短信
                    systemAdminList.forEach(admin -> {
                        smsApi.sendOrderReceiptNotice(admin.getPhone(), storeOrder.getOrderId(), admin.getRealName(), smsTemplate.getTempKey());
                    });
                }
            }

            // 发送消息通知
            pushMessageOrder(storeOrder, user);
        }

        return execute;
    }

    /**
     * 发送消息通知
     * 根据用户类型发送
     * 公众号模板消息
     * 小程序订阅消息
     */
    private void pushMessageOrder(StoreOrder storeOrder, User user) {
        SystemNotification notification = notificationApi.getByMark(NotifyConstants.RECEIPT_GOODS_MARK).getCheckedData();
        if (storeOrder.getIsChannel().equals(2)) {
            return;
        }
        if (!storeOrder.getPayType().equals(Constants.PAY_TYPE_WE_CHAT)) {
            return;
        }
        UserToken userToken;
        HashMap<String, String> temMap = new HashMap<>();
        // 公众号
        if (storeOrder.getIsChannel().equals(Constants.ORDER_PAY_CHANNEL_PUBLIC) && notification.getIsWechat().equals(1)) {
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_WECHAT).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 发送微信模板消息
            temMap.put(Constants.WE_CHAT_TEMP_KEY_FIRST, "您购买的商品已确认收货！");
            temMap.put("keyword1", storeOrder.getOrderId());
            temMap.put("keyword2", "已收货");
            temMap.put("keyword3", DateUtil.nowDateTimeStr());
            temMap.put("keyword4", "详情请进入订单查看");
            temMap.put(Constants.WE_CHAT_TEMP_KEY_END, "感谢你的使用。");
            templateMessageApi.pushTemplateMessage(notification.getWechatId(), temMap, userToken.getToken());
        } else if (notification.getIsRoutine().equals(1)) {
            // 小程序发送订阅消息
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_ROUTINE).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 组装数据
            // 获取商品名称
            String storeNameAndCarNumString = orderApi.getStoreNameAndCarNumString(storeOrder.getId()).getCheckedData();
            if (StrUtil.isBlank(storeNameAndCarNumString)) {
                return ;
            }
            if (storeNameAndCarNumString.length() > 20) {
                storeNameAndCarNumString = storeNameAndCarNumString.substring(0, 15) + "***";
            }
//        temMap.put("character_string6", storeOrder.getOrderId());
//        temMap.put("phrase4", "已收货");
//        temMap.put("time7", DateUtil.nowDateTimeStr());
//        temMap.put("thing1", storeNameAndCarNumString);
//        temMap.put("thing5", "您购买的商品已确认收货！");
            temMap.put("character_string6", storeOrder.getOrderId());
            temMap.put("date5", DateUtil.nowDateTimeStr());
            temMap.put("thing2", storeNameAndCarNumString);
            templateMessageApi.pushMiniTemplateMessage(notification.getRoutineId(), temMap, userToken.getToken());
        }
    }
}
