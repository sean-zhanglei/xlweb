package com.nbug.module.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.BrokerageRecordConstants;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.ExperienceRecordConstants;
import com.nbug.mico.common.constants.IntegralRecordConstants;
import com.nbug.mico.common.constants.NotifyConstants;
import com.nbug.mico.common.constants.PayConstants;
import com.nbug.mico.common.constants.SysConfigConstants;
import com.nbug.mico.common.constants.TaskConstants;
import com.nbug.mico.common.constants.UserConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.model.coupon.StoreCouponUser;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.order.StoreOrderInfo;
import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.model.product.StoreProductAttrValue;
import com.nbug.mico.common.model.product.StoreProductCoupon;
import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.model.user.UserIntegralRecord;
import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.request.OrderPayRequest;
import com.nbug.mico.common.response.OrderPayResultResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.utils.wx.WxPayUtil;
import com.nbug.mico.common.vo.AttachVo;
import com.nbug.mico.common.vo.CreateOrderH5SceneInfoDetailVo;
import com.nbug.mico.common.vo.CreateOrderH5SceneInfoVo;
import com.nbug.mico.common.vo.CreateOrderRequestVo;
import com.nbug.mico.common.vo.CreateOrderResponseVo;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.mico.common.vo.WxPayJsResultVo;
import com.nbug.module.infra.api.sms.NotificationApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.sms.SmsTemplateApi;
import com.nbug.module.infra.api.sms.TemplateMessageApi;
import com.nbug.module.infra.api.wechat.WechatNewApi;
import com.nbug.module.infra.api.yly.YlyApiApi;
import com.nbug.module.order.service.OrderPayService;
import com.nbug.module.store.api.storeCombination.StoreCombinationApi;
import com.nbug.module.store.api.storeCoupon.StoreCouponApi;
import com.nbug.module.store.api.storeCouponUser.StoreCouponUserApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeOrderInfo.StoreOrderInfoApi;
import com.nbug.module.store.api.storeOrderStatus.StoreOrderStatusApi;
import com.nbug.module.store.api.storePink.StorePinkApi;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.store.api.storeProductAttrValue.StoreProductAttrValueApi;
import com.nbug.module.store.api.storeProductCoupon.StoreProductCouponApi;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userExperience.UserExperienceApi;
import com.nbug.module.user.api.userIntegralRecord.UserIntegralRecordApi;
import com.nbug.module.user.api.userLevel.UserLevelApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * OrderPayService 实现类

 */
@Data
@Service
public class OrderPayServiceImpl implements OrderPayService {
    private static final Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private StoreOrderStatusApi storeOrderStatusApi;

    @Autowired
    private StoreOrderInfoApi storeOrderInfoApi;

    @Autowired
    private StoreProductCouponApi storeProductCouponApi;

    @Autowired
    private StoreCouponUserApi storeCouponUserApi;

    @Autowired
    private StoreProductApi storeProductApi;

    @Autowired
    private StoreCombinationApi storeCombinationApi;

    @Autowired
    private StorePinkApi storePinkApi;

    @Autowired
    private StoreCouponApi storeCouponApi;

    @Autowired
    private StoreProductAttrValueApi storeProductAttrValueApi;


    @Autowired
    private UserBillApi userBillApi;

    @Lazy
    @Autowired
    private SmsApi smsApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private AdminApi adminApi;

    @Autowired
    private TemplateMessageApi templateMessageApi;

    //订单类
    private StoreOrder order;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private UserTokenApi userTokenApi;

    @Autowired
    private UserLevelApi userLevelApi;

    @Autowired
    private UserBrokerageRecordApi userBrokerageRecordApi;

    @Autowired
    private UserIntegralRecordApi userIntegralRecordApi;

    @Autowired
    private UserExperienceApi userExperienceApi;

    @Autowired
    private WechatNewApi wechatNewApi;

    @Autowired
    private YlyApiApi ylyApiApi;

    @Autowired
    private NotificationApi notificationApi;

    @Autowired
    private SmsTemplateApi smsTemplateApi;

    /**
     * 支付成功处理
     * @param storeOrder 订单
     */
    @GlobalTransactional(timeoutMills = 300000, name = "spring-seata-tx-paySuccess", rollbackFor = Exception.class)
    @Override
    public Boolean paySuccess(StoreOrder storeOrder) {

        User user = userApi.getById(storeOrder.getUid()).getCheckedData();

        List<UserBill> billList = CollUtil.newArrayList();
        List<UserIntegralRecord> integralList = CollUtil.newArrayList();

        // 订单支付记录
        UserBill userBill = userBillInit(storeOrder, user);
        billList.add(userBill);

        // 积分抵扣记录
        if (storeOrder.getUseIntegral() > 0) {
            UserIntegralRecord integralRecordSub = integralRecordSubInit(storeOrder, user);
            integralList.add(integralRecordSub);
        }

        // 经验处理：1.经验添加，2.等级计算
        Integer experience;
        experience = storeOrder.getPayPrice().setScale(0, BigDecimal.ROUND_DOWN).intValue();
        user.setExperience(user.getExperience() + experience);
        // 经验添加记录
        UserExperienceRecord experienceRecord = experienceRecordInit(storeOrder, user.getExperience(), experience);


        // 积分处理：1.下单赠送积分，2.商品赠送积分
        int integral;
        // 下单赠送积分
        //赠送积分比例
        String integralStr = configApi.getValueByKey(Constants.CONFIG_KEY_INTEGRAL_RATE_ORDER_GIVE).getCheckedData();
        if (StrUtil.isNotBlank(integralStr) && storeOrder.getPayPrice().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal integralBig = new BigDecimal(integralStr);
            integral = integralBig.multiply(storeOrder.getPayPrice()).setScale(0, BigDecimal.ROUND_DOWN).intValue();
            if (integral > 0) {
                // 生成积分记录
                UserIntegralRecord integralRecord = integralRecordInit(storeOrder, user.getIntegral(), integral, "order");
                integralList.add(integralRecord);
            }
        }

        // 商品赠送积分
        // 查询订单详情
        // 获取商品额外赠送积分
        List<StoreOrderInfo> orderInfoList = storeOrderInfoApi.getListByOrderNo(storeOrder.getOrderId()).getCheckedData();
        if (orderInfoList.get(0).getProductType().equals(0)) {
            List<Integer> productIds = orderInfoList.stream().map(StoreOrderInfo::getProductId).collect(Collectors.toList());
            if (productIds.size() > 0) {
                List<StoreProduct> products = storeProductApi.getListInIds(productIds).getCheckedData();
                int sumIntegral = products.stream().mapToInt(StoreProduct::getGiveIntegral).sum();
                if (sumIntegral > 0) {
                    // 生成积分记录
                    UserIntegralRecord integralRecord = integralRecordInit(storeOrder, user.getIntegral(), sumIntegral, "product");
                    integralList.add(integralRecord);
                }
            }
        }

        // 更新用户下单数量
        user.setPayCount(user.getPayCount() + 1);

        /**
         * 计算佣金，生成佣金记录
         */
        List<UserBrokerageRecord> recordList = assignCommission(storeOrder);

        // 分销员逻辑
        if (!user.getIsPromoter()) {
            String funcStatus = configApi.getValueByKey(SysConfigConstants.CONFIG_KEY_BROKERAGE_FUNC_STATUS).getCheckedData();
            if ("1".equals(funcStatus)) {
                String broQuota = configApi.getValueByKey(SysConfigConstants.CONFIG_KEY_STORE_BROKERAGE_QUOTA).getCheckedData();
                if (!"-1".equals(broQuota) && storeOrder.getPayPrice().compareTo(new BigDecimal(broQuota)) >= 0) {// -1 不成为分销员
                    user.setIsPromoter(true);
                    user.setPromoterTime(cn.hutool.core.date.DateUtil.date());
                }
            }
        }

        // 状态变化
        try {
            //订单日志
            storeOrderStatusApi.createLog(storeOrder.getId(), Constants.ORDER_LOG_PAY_SUCCESS, Constants.ORDER_LOG_MESSAGE_PAY_SUCCESS).getCheckedData();

            // 用户信息变更
            userApi.updateById(user).getCheckedData();

            //资金变动
            userBillApi.saveBatch(billList).getCheckedData();

            // 积分记录
            userIntegralRecordApi.saveBatch(integralList).getCheckedData();

            // 经验记录
            userExperienceApi.save(experienceRecord).getCheckedData();

            //经验升级
            userLevelApi.upLevel(user).getCheckedData();

            // 佣金记录
            if (CollUtil.isNotEmpty(recordList)) {
                recordList.forEach(temp -> {
                    temp.setLinkId(storeOrder.getOrderId());
                });
                userBrokerageRecordApi.saveBatch(recordList).getCheckedData();
            }

            // 如果是拼团订单进行拼团后置处理
            if (storeOrder.getCombinationId() > 0) {
                pinkProcessing(storeOrder);
            }

            try {
                SystemNotification payNotification = notificationApi.getByMark(NotifyConstants.PAY_SUCCESS_MARK).getCheckedData();
                // 发送短信
                if (StrUtil.isNotBlank(user.getPhone()) && payNotification.getIsSms().equals(1)) {
                    SmsTemplate smsTemplate = smsTemplateApi.getDetail(payNotification.getSmsId()).getCheckedData();
                    smsApi.sendPaySuccess(user.getPhone(), storeOrder.getOrderId(), storeOrder.getPayPrice(), smsTemplate.getTempKey()).getCheckedData();
                }

                // 发送用户支付成功管理员提醒短信
                SystemNotification payAdminNotification = notificationApi.getByMark(NotifyConstants.PAY_SUCCESS_ADMIN_MARK).getCheckedData();
                if (payAdminNotification.getIsSms().equals(1)) {
                    // 查询可已发送短信的管理员
                    List<SystemAdmin> systemAdminList = adminApi.findIsSmsList().getCheckedData();
                    if (CollUtil.isNotEmpty(systemAdminList)) {
                        SmsTemplate smsTemplate = smsTemplateApi.getDetail(payAdminNotification.getSmsId()).getCheckedData();
                        // 发送短信
                        systemAdminList.forEach(admin -> {
                            smsApi.sendOrderPaySuccessNotice(admin.getPhone(), storeOrder.getOrderId(), admin.getRealName(), smsTemplate.getTempKey()).getCheckedData();
                        });
                    }
                }

                if (payNotification.getIsWechat().equals(1) || payNotification.getIsRoutine().equals(1)) {
                    //下发模板通知
                    pushMessageOrder(storeOrder, user, payNotification);
                }

                // 购买成功后根据配置送优惠券
                autoSendCoupons(storeOrder);

                // 根据配置 打印小票
                ylyApiApi.YlyPrint(storeOrder.getOrderId(),true).getCheckedData();

            } catch (Exception e) {
                logger.error("短信、模板通知、优惠券或打印小票异常", e);
            }

            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error("支付成功后续任务执行失败，即将重试", e);
            return Boolean.FALSE;
        }
    }

    // 支持成功拼团后置处理
    private Boolean pinkProcessing(StoreOrder storeOrder) {
        // 判断拼团是否成功
        StorePink storePink = storePinkApi.getById(storeOrder.getPinkId()).getCheckedData();
        if (storePink.getKId() <= 0) {
            return true;
        }

        List<StorePink> pinkList = storePinkApi.getListByCidAndKid(storePink.getCid(), storePink.getKId()).getCheckedData();
        StorePink tempPink = storePinkApi.getById(storePink.getKId()).getCheckedData();
        pinkList.add(tempPink);
        if (pinkList.size() < storePink.getPeople()) {// 还未拼团成功
            return true;
        }
        // 1.修改拼团状态
        // 2.给所有拼团人员发送拼团成功通知
        pinkList.forEach(e -> {
            e.setStatus(2);
        });
        boolean update = storePinkApi.updateBatchById(pinkList).getCheckedData();
        if (!update) {
            logger.error("拼团订单支付成功后更新拼团状态失败,orderNo = " + storeOrder.getOrderId());
            return false;
        }
        SystemNotification notification = notificationApi.getByMark(NotifyConstants.GROUP_SUCCESS_MARK).getCheckedData();
        if (notification.getIsWechat().equals(1) || notification.getIsRoutine().equals(1)) {
            pinkList.forEach(i -> {
                StoreOrder order = storeOrderApi.getByOderId(i.getOrderId()).getCheckedData();
                StoreCombination storeCombination = storeCombinationApi.getById(i.getCid()).getCheckedData();
                User tempUser = userApi.getById(i.getUid()).getCheckedData();
                // 发送微信模板消息
                MyRecord record = new MyRecord();
                record.set("orderNo", order.getOrderId());
                record.set("proName", storeCombination.getTitle());
                record.set("payType", order.getPayType());
                record.set("isChannel", order.getIsChannel());
                pushMessagePink(record, tempUser, notification);
            });
        }
        return true;
    }

    /**
     * 发送拼团成功通知
     * @param record 信息参数
     * @param user 用户
     */
    private void pushMessagePink(MyRecord record, User user, SystemNotification notification) {
        if (!record.getStr("payType").equals(Constants.PAY_TYPE_WE_CHAT)) {
            return ;
        }
        if (record.getInt("isChannel").equals(2)) {
            return ;
        }

        UserToken userToken;
        HashMap<String, String> temMap = new HashMap<>();
        // 公众号
        if (record.getInt("isChannel").equals(Constants.ORDER_PAY_CHANNEL_PUBLIC) && notification.getIsWechat().equals(1)) {
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_WECHAT).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 发送微信模板消息
            temMap.put(Constants.WE_CHAT_TEMP_KEY_FIRST, "恭喜您拼团成功！我们将尽快为您发货。");
            temMap.put("keyword1", record.getStr("orderNo"));
            temMap.put("keyword2", record.getStr("proName"));
            temMap.put(Constants.WE_CHAT_TEMP_KEY_END, "感谢你的使用！");
            templateMessageApi.pushTemplateMessage(notification.getWechatId(), temMap, userToken.getToken());
        } else if (notification.getIsRoutine().equals(1)) {
            // 小程序发送订阅消息
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_ROUTINE).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 组装数据
//        temMap.put("character_string1",  record.getStr("orderNo"));
//        temMap.put("thing2", record.getStr("proName"));
//        temMap.put("thing5", "恭喜您拼团成功！我们将尽快为您发货。");
            temMap.put("character_string10",  record.getStr("orderNo"));
            temMap.put("thing7", record.getStr("proName"));
            temMap.put("thing9", "恭喜您拼团成功！我们将尽快为您发货。");
            templateMessageApi.pushMiniTemplateMessage(notification.getRoutineId(), temMap, userToken.getToken());
        }

    }

    /**
     * 分配佣金
     * @param storeOrder 订单
     * @return List<UserBrokerageRecord>
     */
    private List<UserBrokerageRecord> assignCommission(StoreOrder storeOrder) {
        // 检测商城是否开启分销功能
        String isOpen = configApi.getValueByKey(Constants.CONFIG_KEY_STORE_BROKERAGE_IS_OPEN).getCheckedData();
        if(StrUtil.isBlank(isOpen) || "0".equals(isOpen)){
            return CollUtil.newArrayList();
        }
        // 营销产品不参与
        if(storeOrder.getCombinationId() > 0 || storeOrder.getSeckillId() > 0 || storeOrder.getBargainId() > 0){
            return CollUtil.newArrayList();
        }
        // 查找订单所属人信息
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        // 当前用户不存在 没有上级 或者 当用用户上级时自己  直接返回
        if(null == user.getSpreadUid() || user.getSpreadUid() < 1 || user.getSpreadUid().equals(storeOrder.getUid())){
            return CollUtil.newArrayList();
        }
        // 获取参与分佣的人（两级）
        List<MyRecord> spreadRecordList = getSpreadRecordList(user.getSpreadUid());
        if (CollUtil.isEmpty(spreadRecordList)) {
            return CollUtil.newArrayList();
        }
        // 获取佣金冻结期
        String fronzenTime = configApi.getValueByKey(Constants.CONFIG_KEY_STORE_BROKERAGE_EXTRACT_TIME).getCheckedData();

        // 生成佣金记录
        List<UserBrokerageRecord> brokerageRecordList = spreadRecordList.stream().map(record -> {
            BigDecimal brokerage = calculateCommission(record, storeOrder.getId());
            UserBrokerageRecord brokerageRecord = new UserBrokerageRecord();
            brokerageRecord.setUid(record.getInt("spreadUid"));
            brokerageRecord.setLinkType(BrokerageRecordConstants.BROKERAGE_RECORD_LINK_TYPE_ORDER);
            brokerageRecord.setType(BrokerageRecordConstants.BROKERAGE_RECORD_TYPE_ADD);
            brokerageRecord.setTitle(BrokerageRecordConstants.BROKERAGE_RECORD_TITLE_ORDER);
            brokerageRecord.setPrice(brokerage);
            brokerageRecord.setMark(StrUtil.format("获得推广佣金，分佣{}", brokerage));
            brokerageRecord.setStatus(BrokerageRecordConstants.BROKERAGE_RECORD_STATUS_CREATE);
            brokerageRecord.setFrozenTime(Integer.valueOf(Optional.ofNullable(fronzenTime).orElse("0")));
            brokerageRecord.setCreateTime(DateUtil.nowDateTime());
            brokerageRecord.setBrokerageLevel(record.getInt("index"));
            return brokerageRecord;
        }).collect(Collectors.toList());

        return brokerageRecordList;
    }

    /**
     * 计算佣金
     * @param record index-分销级数，spreadUid-分销人
     * @param orderId 订单id
     * @return BigDecimal
     */
    private BigDecimal calculateCommission(MyRecord record, Integer orderId) {
        BigDecimal brokeragePrice = BigDecimal.ZERO;
        // 查询订单详情
        List<StoreOrderInfoOldVo> orderInfoVoList = storeOrderInfoApi.getOrderListByOrderId(orderId).getCheckedData();
        if (CollUtil.isEmpty(orderInfoVoList)) {
            return brokeragePrice;
        }
        BigDecimal totalBrokerPrice = BigDecimal.ZERO;
        //查询对应等级的分销比例
        Integer index = record.getInt("index");
        String key = "";
        if (index == 1) {
            key = Constants.CONFIG_KEY_STORE_BROKERAGE_RATE_ONE;
        }
        if (index == 2) {
            key = Constants.CONFIG_KEY_STORE_BROKERAGE_RATE_TWO;
        }
        String rate = configApi.getValueByKey(key).getCheckedData();
        if(StringUtils.isBlank(rate)){
            rate = "1";
        }
        //佣金比例整数存储， 例如80， 所以计算的时候要除以 10*10
        BigDecimal rateBigDecimal = brokeragePrice;
        if(StringUtils.isNotBlank(rate)){
            rateBigDecimal = new BigDecimal(rate).divide(BigDecimal.TEN.multiply(BigDecimal.TEN));
        }

        for (StoreOrderInfoOldVo orderInfoVo : orderInfoVoList) {
            // 先看商品是否有固定分佣
            StoreProductAttrValue attrValue = storeProductAttrValueApi.getById(orderInfoVo.getInfo().getAttrValueId()).getCheckedData();
            if (orderInfoVo.getInfo().getIsSub()) {// 有固定分佣
                if(index == 1){
                    brokeragePrice = Optional.ofNullable(attrValue.getBrokerage()).orElse(BigDecimal.ZERO);
                }
                if(index == 2){
                    brokeragePrice = Optional.ofNullable(attrValue.getBrokerageTwo()).orElse(BigDecimal.ZERO);
                }
            } else {// 系统分佣
                if(!rateBigDecimal.equals(BigDecimal.ZERO)){
                    // 商品没有分销金额, 并且有设置对应等级的分佣比例
                    // 舍入模式向零舍入。
                    if (ObjectUtil.isNotNull(orderInfoVo.getInfo().getVipPrice())) {
                        brokeragePrice = orderInfoVo.getInfo().getVipPrice().multiply(rateBigDecimal).setScale(2, BigDecimal.ROUND_DOWN);
                    } else {
                        brokeragePrice = orderInfoVo.getInfo().getPrice().multiply(rateBigDecimal).setScale(2, BigDecimal.ROUND_DOWN);
                    }
                } else {
                    brokeragePrice = BigDecimal.ZERO;
                }
            }
            // 同规格商品可能有多件
            if (brokeragePrice.compareTo(BigDecimal.ZERO) > 0 && orderInfoVo.getInfo().getPayNum() > 1) {
                brokeragePrice = brokeragePrice.multiply(new BigDecimal(orderInfoVo.getInfo().getPayNum()));
            }
            totalBrokerPrice = totalBrokerPrice.add(brokeragePrice);
        }

        return totalBrokerPrice;
    }

    /**
     * 获取参与分佣人员（两级）
     * @param spreadUid 一级分佣人Uid
     * @return List<MyRecord>
     */
    private List<MyRecord> getSpreadRecordList(Integer spreadUid) {
        List<MyRecord> recordList = CollUtil.newArrayList();

        // 第一级
        User spreadUser = userApi.getById(spreadUid).getCheckedData();
        if (ObjectUtil.isNull(spreadUser)) {
            return recordList;
        }
        // 判断分销模式
        String model = configApi.getValueByKey(Constants.CONFIG_KEY_STORE_BROKERAGE_MODEL).getCheckedData();
        if (StrUtil.isNotBlank(model) && "1".equals(model) && !spreadUser.getIsPromoter()) {
            // 指定分销模式下：不是推广员不参与分销
            return recordList;
        }
        MyRecord firstRecord = new MyRecord();
        firstRecord.set("index", 1);
        firstRecord.set("spreadUid", spreadUid);
        recordList.add(firstRecord);

        // 第二级
        User spreadSpreadUser = userApi.getById(spreadUser.getSpreadUid()).getCheckedData();
        if (ObjectUtil.isNull(spreadSpreadUser)) {
            return recordList;
        }
        if (StrUtil.isNotBlank(model) && "1".equals(model) && !spreadSpreadUser.getIsPromoter()) {
            // 指定分销模式下：不是推广员不参与分销
            return recordList;
        }
        MyRecord secondRecord = new MyRecord();
        secondRecord.set("index", 2);
        secondRecord.set("spreadUid", spreadSpreadUser.getUid());
        recordList.add(secondRecord);
        return recordList;
    }

    /**
     * 余额支付
     * @param storeOrder 订单
     * @return Boolean Boolean
     */
    private Boolean yuePay(StoreOrder storeOrder) {

        // 用户余额扣除
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        if (ObjectUtil.isNull(user)) throw new XlwebException("用户不存在");
        if (user.getNowMoney().compareTo(storeOrder.getPayPrice()) < 0) {
            throw new XlwebException("用户余额不足");
        }
        if (user.getIntegral() < storeOrder.getUseIntegral()) {
            throw new XlwebException("用户积分不足");
        }
        storeOrder.setPaid(true);
        storeOrder.setPayTime(DateUtil.nowDateTime());
        // 状态变化
        try {
            // 订单修改
            storeOrderApi.updateById(storeOrder).getCheckedData();
            // 这里只扣除金额，账单记录在task中处理
            userApi.updateNowMoney(user, storeOrder.getPayPrice(), "sub").getCheckedData();
            // 扣除积分
            if (storeOrder.getUseIntegral() > 0) {
                userApi.updateIntegral(user, storeOrder.getUseIntegral(), "sub").getCheckedData();
            }
            // 添加支付成功redis队列
            redisUtil.lPush(TaskConstants.ORDER_TASK_PAY_SUCCESS_AFTER, storeOrder.getOrderId());

            // 处理拼团
            if (storeOrder.getCombinationId() > 0) {
                // 判断拼团团长是否存在
                StorePink headPink = new StorePink();
                Integer pinkId = storeOrder.getPinkId();
                if (pinkId > 0) {
                    headPink = storePinkApi.getById(pinkId).getCheckedData();
                    if (ObjectUtil.isNull(headPink) || headPink.getIsRefund().equals(true) || headPink.getStatus() == 3) {
                        pinkId = 0;
                    }
                }
                StoreCombination storeCombination = storeCombinationApi.getById(storeOrder.getCombinationId()).getCheckedData();
                // 如果拼团人数已满，重新开团
                if (pinkId > 0) {
                    Integer count = storePinkApi.getCountByKid(pinkId).getCheckedData();
                    if (count >= storeCombination.getPeople()) {
                        pinkId = 0;
                    }
                }
                // 生成拼团表数据
                StorePink storePink = new StorePink();
                storePink.setUid(user.getUid());
                storePink.setAvatar(user.getAvatar());
                storePink.setNickname(user.getNickname());
                storePink.setOrderId(storeOrder.getOrderId());
                storePink.setOrderIdKey(storeOrder.getId());
                storePink.setTotalNum(storeOrder.getTotalNum());
                storePink.setTotalPrice(storeOrder.getTotalPrice());
                storePink.setCid(storeCombination.getId());
                storePink.setPid(storeCombination.getProductId());
                storePink.setPeople(storeCombination.getPeople());
                storePink.setPrice(storeCombination.getPrice());
                Integer effectiveTime = storeCombination.getEffectiveTime();// 有效小时数
                DateTime dateTime = cn.hutool.core.date.DateUtil.date();
                storePink.setAddTime(dateTime.getTime());
                if (pinkId > 0) {
                    storePink.setStopTime(headPink.getStopTime());
                } else {
                    DateTime hourTime = cn.hutool.core.date.DateUtil.offsetHour(dateTime, effectiveTime);
                    long stopTime =  hourTime.getTime();
                    if (stopTime > storeCombination.getStopTime()) {
                        stopTime = storeCombination.getStopTime();
                    }
                    storePink.setStopTime(stopTime);
                }
                storePink.setKId(pinkId);
                storePink.setIsTpl(false);
                storePink.setIsRefund(false);
                storePink.setStatus(1);
                storePinkApi.save(storePink).getCheckedData();
                // 如果是开团，需要更新订单数据
                storeOrder.setPinkId(storePink.getId());
                storeOrderApi.updateById(storeOrder).getCheckedData();
            }

            return Boolean.TRUE;
        } catch (Exception e) {
            throw new XlwebException("余额支付订单失败");
        }
    }

    /**
     * 订单支付
     * @param orderPayRequest   支付参数
     * @param ip                ip
     * @return OrderPayResultResponse
     * 1.微信支付拉起微信预支付，返回前端调用微信支付参数，在之后需要调用微信支付查询接口
     * 2.余额支付，更改对应信息后，加入支付成功处理task
     */
    @GlobalTransactional(timeoutMills = 300000, name = "spring-seata-tx-payment", rollbackFor = Exception.class)
    @Override
    public OrderPayResultResponse payment(OrderPayRequest orderPayRequest, String ip) {
        StoreOrder storeOrder = storeOrderApi.getByOderId(orderPayRequest.getOrderNo()).getCheckedData();
        if (ObjectUtil.isNull(storeOrder)) {
            throw new XlwebException("订单不存在");
        }
        if (storeOrder.getIsDel()) {
            throw new XlwebException("订单已被删除");
        }
        if (storeOrder.getPaid()) {
            throw new XlwebException("订单已支付");
        }
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        if (ObjectUtil.isNull(user)) throw new XlwebException("用户不存在");

        // 判断订单是否还是之前的支付类型
        if (!storeOrder.getPayType().equals(orderPayRequest.getPayType())) {
            // 根据支付类型进行校验,更换支付类型
            storeOrder.setPayType(orderPayRequest.getPayType());
            // 余额支付
            if (orderPayRequest.getPayType().equals(PayConstants.PAY_TYPE_YUE)) {
                if (user.getNowMoney().compareTo(storeOrder.getPayPrice()) < 0) {
                    throw new XlwebException("用户余额不足");
                }
                storeOrder.setIsChannel(3);
            }
            if (orderPayRequest.getPayType().equals(PayConstants.PAY_TYPE_WE_CHAT)) {
                switch (orderPayRequest.getPayChannel()){
                    case PayConstants.PAY_CHANNEL_WE_CHAT_H5:// H5
                        storeOrder.setIsChannel(2);
                        break;
                    case PayConstants.PAY_CHANNEL_WE_CHAT_PUBLIC:// 公众号
                        storeOrder.setIsChannel(0);
                        break;
                    case PayConstants.PAY_CHANNEL_WE_CHAT_PROGRAM:// 小程序
                        storeOrder.setIsChannel(1);
                        break;
                }
            }

            boolean changePayType = storeOrderApi.updateById(storeOrder).getCheckedData();
            if (!changePayType) {
                throw new XlwebException("变更订单支付类型失败!");
            }
        }

        if (user.getIntegral() < storeOrder.getUseIntegral()) {
            throw new XlwebException("用户积分不足");
        }

        OrderPayResultResponse response = new OrderPayResultResponse();
        response.setOrderNo(storeOrder.getOrderId());
        response.setPayType(storeOrder.getPayType());
        // 0元付
        if (storeOrder.getPayPrice().compareTo(BigDecimal.ZERO) <= 0) {
            Boolean aBoolean = yuePay(storeOrder);
            response.setPayType(PayConstants.PAY_TYPE_YUE);
            response.setStatus(aBoolean);
            return response;
        }

        // 微信支付，调用微信预下单，返回拉起微信支付需要的信息
        if (storeOrder.getPayType().equals(PayConstants.PAY_TYPE_WE_CHAT)) {
            // 预下单
            Map<String, String> unifiedorder = unifiedorder(storeOrder, ip);
            response.setStatus(true);
            WxPayJsResultVo vo = new WxPayJsResultVo();
            vo.setAppId(unifiedorder.get("appId"));
            vo.setNonceStr(unifiedorder.get("nonceStr"));
            vo.setPackages(unifiedorder.get("package"));
            vo.setSignType(unifiedorder.get("signType"));
            vo.setTimeStamp(unifiedorder.get("timeStamp"));
            vo.setPaySign(unifiedorder.get("paySign"));
            if (storeOrder.getIsChannel() == 2) {
                vo.setMwebUrl(unifiedorder.get("mweb_url"));
                response.setPayType(PayConstants.PAY_CHANNEL_WE_CHAT_H5);
            }
            if (storeOrder.getIsChannel() == 4 || storeOrder.getIsChannel() == 5) {
                vo.setPartnerid(unifiedorder.get("partnerid"));
            }
            // 更新商户订单号
            storeOrder.setOutTradeNo(unifiedorder.get("outTradeNo"));
            storeOrderApi.updateById(storeOrder);
            response.setJsConfig(vo);
            return response;
        }
        // 余额支付
        if (storeOrder.getPayType().equals(PayConstants.PAY_TYPE_YUE)) {
            Boolean yueBoolean = yuePay(storeOrder);
            response.setStatus(yueBoolean);
            return response;
        }
        if (storeOrder.getPayType().equals(PayConstants.PAY_TYPE_OFFLINE)) {
            throw new XlwebException("暂时不支持线下支付");
        }
        response.setStatus(false);
        return response;
    }

    /**
     * 预下单
     * @param storeOrder 订单
     * @param ip ip
     * @return 预下单返回对象
     */
    private Map<String, String> unifiedorder(StoreOrder storeOrder, String ip) {
        // 获取用户openId
        // 根据订单支付类型来判断获取公众号openId还是小程序openId
        UserToken userToken = new UserToken();
        if (storeOrder.getIsChannel() == 0) {// 公众号
            userToken = userTokenApi.getTokenByUserId(storeOrder.getUid(), 1).getCheckedData();
        }
        if (storeOrder.getIsChannel() == 1) {// 小程序
            userToken = userTokenApi.getTokenByUserId(storeOrder.getUid(), 2).getCheckedData();
        }
        if (storeOrder.getIsChannel() == 2) {// H5
            userToken.setToken("");
        }
        if (ObjectUtil.isNull(userToken)) {
            throw new XlwebException("该用户没有openId");
        }

        // 获取appid、mch_id
        // 微信签名key
        String appId = "";
        String mchId = "";
        String signKey = "";
        if (storeOrder.getIsChannel() == 0) {// 公众号
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
        }
        if (storeOrder.getIsChannel() == 1) {// 小程序
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY).getCheckedData();
        }
        if (storeOrder.getIsChannel() == 2) {// H5,使用公众号的
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
        }
        // 获取微信预下单对象
        CreateOrderRequestVo unifiedorderVo = getUnifiedorderVo(storeOrder, userToken.getToken(), ip, appId, mchId, signKey);
        // 预下单（统一下单）
        CreateOrderResponseVo responseVo = wechatNewApi.payUnifiedorder(unifiedorderVo).getCheckedData();
        // 组装前端预下单参数
        Map<String, String> map = new HashMap<>();
        map.put("appId", unifiedorderVo.getAppid());
        map.put("nonceStr", unifiedorderVo.getAppid());
        map.put("package", "prepay_id=".concat(responseVo.getPrepayId()));
        map.put("signType", unifiedorderVo.getSign_type());
        Long currentTimestamp = WxPayUtil.getCurrentTimestamp();
        map.put("timeStamp", Long.toString(currentTimestamp));
        String paySign = WxPayUtil.getSign(map, signKey);
        map.put("paySign", paySign);
        map.put("prepayId", responseVo.getPrepayId());
        map.put("prepayTime", DateUtil.nowDateTimeStr());
        map.put("outTradeNo", unifiedorderVo.getOut_trade_no());
        if (storeOrder.getIsChannel() == 2) {
            map.put("mweb_url", responseVo.getMWebUrl());
        }
        return map;
    }

    /**
     * 获取微信预下单对象
     * @return 微信预下单对象
     */
    private CreateOrderRequestVo getUnifiedorderVo(StoreOrder storeOrder, String openid, String ip, String appId, String mchId, String signKey) {

        // 获取域名
        String domain = configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_URL).getCheckedData();
        String apiDomain = configApi.getValueByKeyException(Constants.CONFIG_KEY_API_URL).getCheckedData();

        AttachVo attachVo = new AttachVo(Constants.SERVICE_PAY_TYPE_ORDER, storeOrder.getUid());
        CreateOrderRequestVo vo = new CreateOrderRequestVo();

        vo.setAppid(appId);
        vo.setMch_id(mchId);
        vo.setNonce_str(WxPayUtil.getNonceStr());
        vo.setSign_type(PayConstants.WX_PAY_SIGN_TYPE_MD5);
        String siteName = configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_NAME).getCheckedData();
        // 因商品名称在微信侧超长更换为网站名称
        vo.setBody(siteName);
        vo.setAttach(JSONObject.toJSONString(attachVo));
        vo.setOut_trade_no(XlwebUtil.getOrderNo("wxNo"));
        // 订单中使用的是BigDecimal,这里要转为Integer类型
        vo.setTotal_fee(storeOrder.getPayPrice().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue());
        vo.setSpbill_create_ip(ip);
        vo.setNotify_url(apiDomain + PayConstants.WX_PAY_NOTIFY_API_URI);
        vo.setTrade_type(PayConstants.WX_PAY_TRADE_TYPE_JS);
        vo.setOpenid(openid);
        if (storeOrder.getIsChannel() == 2){// H5
            vo.setTrade_type(PayConstants.WX_PAY_TRADE_TYPE_H5);
            vo.setOpenid(null);
        }
        CreateOrderH5SceneInfoVo createOrderH5SceneInfoVo = new CreateOrderH5SceneInfoVo(
                new CreateOrderH5SceneInfoDetailVo(
                        domain,
                        configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_NAME).getCheckedData()
                )
        );
        vo.setScene_info(JSONObject.toJSONString(createOrderH5SceneInfoVo));
        String sign = WxPayUtil.getSign(vo, signKey);
        vo.setSign(sign);
        return vo;
    }

    private UserIntegralRecord integralRecordSubInit(StoreOrder storeOrder, User user) {
        UserIntegralRecord integralRecord = new UserIntegralRecord();
        integralRecord.setUid(storeOrder.getUid());
        integralRecord.setLinkId(storeOrder.getOrderId());
        integralRecord.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
        integralRecord.setType(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_SUB);
        integralRecord.setTitle(IntegralRecordConstants.BROKERAGE_RECORD_TITLE_ORDER);
        integralRecord.setIntegral(storeOrder.getUseIntegral());
        integralRecord.setBalance(user.getIntegral());
        integralRecord.setMark(StrUtil.format("订单支付抵扣{}积分购买商品", storeOrder.getUseIntegral()));
        integralRecord.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
        return integralRecord;
    }

    private UserBill userBillInit(StoreOrder order, User user) {
        UserBill userBill = new UserBill();
        userBill.setPm(0);
        userBill.setUid(order.getUid());
        userBill.setLinkId(order.getId().toString());
        userBill.setTitle("购买商品");
        userBill.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userBill.setType(Constants.USER_BILL_TYPE_PAY_ORDER);
        userBill.setNumber(order.getPayPrice());
        userBill.setBalance(user.getNowMoney());
        userBill.setMark("支付" + order.getPayPrice() + "元购买商品");
        return userBill;
    }

    /**
     * 经验添加记录
     */
    private UserExperienceRecord experienceRecordInit(StoreOrder storeOrder, Integer balance, Integer experience) {
        UserExperienceRecord record = new UserExperienceRecord();
        record.setUid(storeOrder.getUid());
        record.setLinkId(storeOrder.getOrderId());
        record.setLinkType(ExperienceRecordConstants.EXPERIENCE_RECORD_LINK_TYPE_ORDER);
        record.setType(ExperienceRecordConstants.EXPERIENCE_RECORD_TYPE_ADD);
        record.setTitle(ExperienceRecordConstants.EXPERIENCE_RECORD_TITLE_ORDER);
        record.setExperience(experience);
        record.setBalance(balance);
        record.setMark("用户付款成功增加" + experience + "经验");
        record.setCreateTime(cn.hutool.core.date.DateUtil.date());
        return record;
    }

    /**
     * 积分添加记录
     * @return UserIntegralRecord
     */
    private UserIntegralRecord integralRecordInit(StoreOrder storeOrder, Integer balance, Integer integral, String type) {
        UserIntegralRecord integralRecord = new UserIntegralRecord();
        integralRecord.setUid(storeOrder.getUid());
        integralRecord.setLinkId(storeOrder.getOrderId());
        integralRecord.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
        integralRecord.setType(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
        integralRecord.setTitle(IntegralRecordConstants.BROKERAGE_RECORD_TITLE_ORDER);
        integralRecord.setIntegral(integral);
        integralRecord.setBalance(balance);
        if ("order".equals(type)){
            integralRecord.setMark(StrUtil.format("用户付款成功,订单增加{}积分", integral));
        }
        if ("product".equals(type)) {
            integralRecord.setMark(StrUtil.format("用户付款成功,商品增加{}积分", integral));
        }
        integralRecord.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_CREATE);
        // 获取积分冻结期
        String fronzenTime = configApi.getValueByKey(Constants.CONFIG_KEY_STORE_INTEGRAL_EXTRACT_TIME).getCheckedData();
        integralRecord.setFrozenTime(Integer.valueOf(Optional.ofNullable(fronzenTime).orElse("0")));
        integralRecord.setCreateTime(DateUtil.nowDateTime());
        return integralRecord;
    }

    /**
     * 发送消息通知
     * 根据用户类型发送
     * 公众号模板消息
     * 小程序订阅消息
     */
    private void pushMessageOrder(StoreOrder storeOrder, User user, SystemNotification payNotification) {
        if (storeOrder.getIsChannel().equals(2)) {// H5
            return;
        }
        UserToken userToken;
        HashMap<String, String> temMap = new HashMap<>();
        if (!storeOrder.getPayType().equals(Constants.PAY_TYPE_WE_CHAT)) {
            return;
        }
        // 公众号
        if (storeOrder.getIsChannel().equals(Constants.ORDER_PAY_CHANNEL_PUBLIC) && payNotification.getIsWechat().equals(1)) {
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_WECHAT).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 发送微信模板消息
            temMap.put(Constants.WE_CHAT_TEMP_KEY_FIRST, "您的订单已支付成功！");
            temMap.put("keyword1", storeOrder.getPayPrice().toString());
            temMap.put("keyword2", storeOrder.getOrderId());
            temMap.put(Constants.WE_CHAT_TEMP_KEY_END, "欢迎下次再来！");
            templateMessageApi.pushTemplateMessage(payNotification.getWechatId(), temMap, userToken.getToken()).getCheckedData();
            return;
        }
        if (storeOrder.getIsChannel().equals(Constants.ORDER_PAY_CHANNEL_PROGRAM) && payNotification.getIsRoutine().equals(1)) {
            // 小程序发送订阅消息
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_ROUTINE).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 组装数据
//            temMap.put("character_string1", storeOrder.getOrderId());
//            temMap.put("amount2", storeOrder.getPayPrice().toString() + "元");
//            temMap.put("thing7", "您的订单已支付成功");
            temMap.put("character_string3", storeOrder.getOrderId());
            temMap.put("amount9", storeOrder.getPayPrice().toString() + "元");
            temMap.put("thing6", "您的订单已支付成功");
            templateMessageApi.pushMiniTemplateMessage(payNotification.getRoutineId(), temMap, userToken.getToken()).getCheckedData();
        }
    }

    /**
     * 商品购买后根据配置送券
     */
    private void autoSendCoupons(StoreOrder storeOrder){
        // 根据订单详情获取商品信息
        List<StoreOrderInfoOldVo> orders = storeOrderInfoApi.getOrderListByOrderId(storeOrder.getId()).getCheckedData();
        if(null == orders){
            return;
        }
        List<StoreCouponUser> couponUserList = CollUtil.newArrayList();
        Map<Integer, Boolean> couponMap = new HashMap<>();
        for (StoreOrderInfoOldVo order : orders) {
            List<StoreProductCoupon> couponsForGiveUser = storeProductCouponApi.getListByProductId(order.getProductId()).getCheckedData();
            for (int i = 0; i < couponsForGiveUser.size();) {
                StoreProductCoupon storeProductCoupon = couponsForGiveUser.get(i);
                MyRecord record = storeCouponUserApi.paySuccessGiveAway(storeProductCoupon.getIssueCouponId(), storeOrder.getUid()).getCheckedData();
                if ("fail".equals(record.getStr("status"))) {
                    logger.error(StrUtil.format("支付成功领取优惠券失败，失败原因：{}", record.getStr("errMsg")));
                    couponsForGiveUser.remove(i);
                    continue;
                }

                StoreCouponUser storeCouponUser = record.get("storeCouponUser");
                couponUserList.add(storeCouponUser);
                couponMap.put(storeCouponUser.getCouponId(), record.getBoolean("isLimited"));
                i++;
            }
        }

        // 状态变化
        if (CollUtil.isNotEmpty(couponUserList)) {
            try {
                storeCouponUserApi.saveBatch(couponUserList).getCheckedData();
                couponUserList.forEach(i -> storeCouponApi.deduction(i.getCouponId(), 1, couponMap.get(i.getCouponId())).getCheckedData());
            } catch (Exception e) {
                logger.error(StrUtil.format("支付成功领取优惠券，更新数据库失败，订单编号：{}", storeOrder.getOrderId()));
            }
        }

    }
}
