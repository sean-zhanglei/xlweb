package com.nbug.module.store.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.NotifyConstants;
import com.nbug.mico.common.constants.PayConstants;
import com.nbug.mico.common.constants.UserConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.model.express.Express;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.model.system.SystemAdmin;
import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreDateRangeSqlPram;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.request.StoreOrderSearchRequest;
import com.nbug.mico.common.request.StoreOrderSendRequest;
import com.nbug.mico.common.request.StoreOrderUpdatePriceRequest;
import com.nbug.mico.common.request.SystemWriteOffOrderSearchRequest;
import com.nbug.mico.common.response.OrderBrokerageData;
import com.nbug.mico.common.response.StoreOrderCountItemResponse;
import com.nbug.mico.common.response.StoreOrderDetailResponse;
import com.nbug.mico.common.response.StoreOrderInfoResponse;
import com.nbug.mico.common.response.StoreOrderItemResponse;
import com.nbug.mico.common.response.StoreOrderStatisticsResponse;
import com.nbug.mico.common.response.StoreOrderTopItemResponse;
import com.nbug.mico.common.response.SystemWriteOffOrderResponse;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.utils.validation.ValidateFormUtil;
import com.nbug.mico.common.vo.ExpressSheetVo;
import com.nbug.mico.common.vo.LogisticsResultVo;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.mico.common.vo.dateLimitUtilVo;
import com.nbug.module.infra.api.sms.NotificationApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.sms.SmsTemplateApi;
import com.nbug.module.infra.api.sms.TemplateMessageApi;
import com.nbug.module.store.dal.StoreOrderDao;
import com.nbug.module.store.service.StoreOrderInfoService;
import com.nbug.module.store.service.StoreOrderRefundService;
import com.nbug.module.store.service.StoreOrderService;
import com.nbug.module.store.service.StoreOrderStatusService;
import com.nbug.module.store.service.StorePinkService;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.express.ExpressApi;
import com.nbug.module.system.api.logistic.LogisticApi;
import com.nbug.module.system.api.systemStore.SystemStoreApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * StoreOrderServiceImpl 接口实现

 */
@Service
public class StoreOrderServiceImpl extends ServiceImpl<StoreOrderDao, StoreOrder> implements StoreOrderService {

    private static final Logger logger = LoggerFactory.getLogger(StoreOrderServiceImpl.class);

    @Resource
    private StoreOrderDao dao;

    @Autowired
    private StoreOrderInfoService storeOrderInfoService;

    @Autowired
    private StoreOrderInfoService StoreOrderInfoService;

    @Autowired
    private StoreOrderStatusService storeOrderStatusService;

    @Autowired
    private StoreOrderRefundService storeOrderRefundService;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private StorePinkService storePinkService;

    @Autowired
    private AdminApi adminApi;

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private NotificationApi notificationApi;

    @Autowired
    private SmsTemplateApi smsTemplateApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserBillApi userBillApi;

    @Autowired
    private UserTokenApi userTokenApi;

    @Autowired
    private UserBrokerageRecordApi userBrokerageRecordApi;


    @Autowired
    private SmsApi smsApi;

    @Autowired
    private SystemStoreApi systemStoreApi;

    @Autowired
    private ExpressApi expressApi;

    @Autowired
    private TemplateMessageApi templateMessageApi;

    @Autowired
    private LogisticApi logisticApi;

    /**
    * 列表
    * @param request 请求参数
    * @param pageParamRequest 分页类参数
    * @return CommonPage<StoreOrderDetailResponse>
    */
    @Override
    public CommonPage<StoreOrderDetailResponse> getAdminList(StoreOrderSearchRequest request, PageParamRequest pageParamRequest) {
        Page<Object> startPage = PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "order_id", "uid", "real_name", "pay_price", "pay_type", "create_time", "status", "refund_status"
                , "refund_reason_wap_img", "refund_reason_wap_explain", "refund_reason_wap", "refund_reason", "refund_reason_time"
                , "is_del", "combination_id", "pink_id", "seckill_id", "bargain_id", "verify_code", "remark", "paid", "is_system_del", "shipping_type", "type", "is_alter_price");
        if (StrUtil.isNotBlank(request.getOrderNo())) {
            queryWrapper.eq("order_id", request.getOrderNo());
        }
        getRequestTimeWhere(queryWrapper, request);
        getStatusWhere(queryWrapper, request.getStatus());
        if (!request.getType().equals(2)) {
            queryWrapper.eq("type", request.getType());
        }
        queryWrapper.orderByDesc("id");
        List<StoreOrder> orderList = dao.selectList(queryWrapper);
        List<StoreOrderDetailResponse> detailResponseList = new ArrayList<>();
        if (CollUtil.isNotEmpty(orderList)) {
            detailResponseList = formatOrder1(orderList);
        }
        return CommonPage.restPage(CommonPage.copyPageInfo(startPage, detailResponseList));
    }


    /**
     * H5订单列表
     * @param uid 用户uid
     * @param status 评价等级|0=未支付,1=待发货,2=待收货,3=待评价,4=已完成,-3=售后/退款
     * @param pageParamRequest 分页参数
     * @return 订单结果列表
     */
    @Override
    public List<StoreOrder> getUserOrderList(Integer uid, Integer status, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        statusApiByWhere(lqw, status);
        lqw.eq(StoreOrder::getUid, uid);
        lqw.orderByDesc(StoreOrder::getId);
        return dao.selectList(lqw);
    }

    /**
     * 创建订单
     * @param storeOrder 订单参数
     * @return 结果标识
     */
    @Override
    public boolean create(StoreOrder storeOrder) {
        return dao.insert(storeOrder) > 0;
    }

    /**
     * 订单基本查询一条
     * @param storeOrder 参数
     * @return 查询结果
     */
    @Override
    public StoreOrder getByEntityOne(StoreOrder storeOrder) {
        LambdaQueryWrapper<StoreOrder> lqw = new LambdaQueryWrapper<>();
        lqw.setEntity(storeOrder);
        return dao.selectOne(lqw);
    }

    /**
     * 核销列表
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<StoreOrder>
     */
    @Override
    public SystemWriteOffOrderResponse getWriteOffList(SystemWriteOffOrderSearchRequest request, PageParamRequest pageParamRequest) {
        LambdaQueryWrapper<StoreOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        String where = " is_del = 0 and shipping_type = 2";
        //时间
        if (!StringUtils.isBlank(request.getDateLimit())) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(request.getDateLimit());
            where += " and (create_time between '" + dateLimit.getStartTime() + "' and '" + dateLimit.getEndTime() + "' )";
        }

        if (!StringUtils.isBlank(request.getKeywords())) {
            where += " and (real_name like '%"+ request.getKeywords() +"%' or user_phone = '"+ request.getKeywords() +"' or order_id = '" + request.getKeywords() + "' or id = '" + request.getKeywords() + "' )";
        }

        if (request.getStoreId() != null && request.getStoreId() > 0) {
            where += " and store_id = " + request.getStoreId();
        }

        SystemWriteOffOrderResponse systemWriteOffOrderResponse = new SystemWriteOffOrderResponse();
        BigDecimal totalPrice = dao.getTotalPrice(where);
        if (ObjectUtil.isNull(totalPrice)) {
            totalPrice = BigDecimal.ZERO;
        }
        systemWriteOffOrderResponse.setOrderTotalPrice(totalPrice);   //订单总金额

        BigDecimal refundPrice = dao.getRefundPrice(where);
        if (ObjectUtil.isNull(refundPrice)) {
            refundPrice = BigDecimal.ZERO;
        }
        systemWriteOffOrderResponse.setRefundTotalPrice(refundPrice); //退款总金额
        systemWriteOffOrderResponse.setRefundTotal(dao.getRefundTotal(where));  //退款总单数

        Page<StoreOrder> storeOrderPage = PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());

        lambdaQueryWrapper.apply(where);
        lambdaQueryWrapper.orderByDesc(StoreOrder::getId);
        List<StoreOrder> storeOrderList = dao.selectList(lambdaQueryWrapper);

        if (storeOrderList.size() < 1) {
            systemWriteOffOrderResponse.setList(CommonPage.restPage(new PageInfo<>()));
            return systemWriteOffOrderResponse;
        }

        List<StoreOrderItemResponse> storeOrderItemResponseArrayList = formatOrder(storeOrderList);

        systemWriteOffOrderResponse.setTotal(storeOrderPage.getTotal()); //总单数
        systemWriteOffOrderResponse.setList(CommonPage.restPage(CommonPage.copyPageInfo(storeOrderPage, storeOrderItemResponseArrayList)));

        return systemWriteOffOrderResponse;
    }

    /**
     * 格式化订单信息，对外输出一致
     * @param orderList List<StoreOrder> 订单列表
     * @return List<StoreOrderItemResponse>
     */
    private List<StoreOrderDetailResponse> formatOrder1(List<StoreOrder> orderList) {
        List<StoreOrderDetailResponse> detailResponseList  = new ArrayList<>();
        if (CollUtil.isEmpty(orderList)) {
            return detailResponseList;
        }

        //订单id集合
        List<Integer> orderIdList = orderList.stream().map(StoreOrder::getId).distinct().collect(Collectors.toList());

        //获取订单详情map
        HashMap<Integer, List<StoreOrderInfoOldVo>> orderInfoList = StoreOrderInfoService.getMapInId(orderIdList);
//
//        //根据用户获取信息
//        List<Integer> userIdList = orderList.stream().map(StoreOrder::getUid).distinct().collect(Collectors.toList());
//        //订单用户信息
//        HashMap<Integer, User> userList = userApi.getMapListInUid(userIdList);

        for (StoreOrder storeOrder : orderList) {
            StoreOrderDetailResponse storeOrderItemResponse = new StoreOrderDetailResponse();
            BeanUtils.copyProperties(storeOrder, storeOrderItemResponse);

            storeOrderItemResponse.setProductList(orderInfoList.get(storeOrder.getId()));

            //订单状态
            storeOrderItemResponse.setStatusStr(getStatus(storeOrder));
            storeOrderItemResponse.setStatus(storeOrder.getStatus());
            //支付方式
            storeOrderItemResponse.setPayTypeStr(getPayType(storeOrder.getPayType()));

            // 添加订单类型信息
            storeOrderItemResponse.setOrderType(getOrderTypeStr(storeOrder));
            detailResponseList.add(storeOrderItemResponse);
        }
        return detailResponseList;
    }

    /**
     * 获取订单类型（前端展示）
     * @param storeOrder 订单
     * @return String
     */
    private String getOrderTypeStr(StoreOrder storeOrder) {
        String orderTypeFormat = "[{}订单]{}";
        String orderType = StrUtil.format(orderTypeFormat, "普通", "");
        // 核销
        if (StrUtil.isNotBlank(storeOrder.getVerifyCode())) {
            orderType = StrUtil.format(orderTypeFormat, "核销", "");
        }
        // 秒杀
        if (ObjectUtil.isNotNull(storeOrder.getSeckillId()) && storeOrder.getSeckillId() > 0) {
            orderType = StrUtil.format(orderTypeFormat, "秒杀", "");
        }
        // 砍价
        if (ObjectUtil.isNotNull(storeOrder.getBargainId()) && storeOrder.getBargainId() > 0) {
            orderType = StrUtil.format(orderTypeFormat, "砍价", "");
        }
        // 拼团
        if (ObjectUtil.isNotNull(storeOrder.getCombinationId()) && storeOrder.getCombinationId() > 0) {
            StorePink storePink = storePinkService.getById(storeOrder.getPinkId());
            if (ObjectUtil.isNotNull(storePink)) {
                String pinkstatus = "";
                if (storePink.getStatus() == 2) {
                    pinkstatus = "已完成";
                } else if (storePink.getStatus() == 3) {
                    pinkstatus = "未完成";
                } else {
                    pinkstatus = "正在进行中";
                }
                orderType = StrUtil.format(orderTypeFormat, "拼团", pinkstatus);
            }
        }
        if (storeOrder.getType().equals(1)) {// 视频订单
            orderType = StrUtil.format(orderTypeFormat, "视频号", "");
        }
        return orderType;
    }

    /**
     * 格式化订单信息，对外输出一致
     * @param storeOrderList List<StoreOrder> 订单列表
     * @author Mr.Zhang
     * @since 2020-05-28
     * @return List<StoreOrderItemResponse>
     */
    private List<StoreOrderItemResponse> formatOrder(List<StoreOrder> storeOrderList) {
        List<StoreOrderItemResponse> storeOrderItemResponseArrayList  = new ArrayList<>();
        if (null == storeOrderList || storeOrderList.size() < 1) {
            return storeOrderItemResponseArrayList;
        }
        //门店id
        List<Integer> storeIdList = storeOrderList.stream().map(StoreOrder::getStoreId).distinct().collect(Collectors.toList());
        //店员id / 核销员id
        List<Integer> clerkIdList = storeOrderList.stream().map(StoreOrder::getClerkId).distinct().collect(Collectors.toList());

        //订单id集合
        List<Integer> orderIdList = storeOrderList.stream().map(StoreOrder::getId).distinct().collect(Collectors.toList());

        //获取门店map
        HashMap<Integer, SystemStore> systemStoreList = systemStoreApi.getMapInId(storeIdList).getCheckedData();
        //获取店员map
//        HashMap<Integer, SystemStoreStaff> systemStoreStaffList = systemStoreStaffService.getMapInId(clerkIdList);
        HashMap<Integer, SystemAdmin> systemStoreStaffList = adminApi.getMapInId(clerkIdList).getCheckedData();
        //获取订单详情map
        HashMap<Integer, List<StoreOrderInfoOldVo>> orderInfoList = StoreOrderInfoService.getMapInId(orderIdList);

        //根据用户获取信息
        List<Integer> userIdList = storeOrderList.stream().map(StoreOrder::getUid).distinct().collect(Collectors.toList());
        //订单用户信息
        HashMap<Integer, User> userList = userApi.getMapListInUid(userIdList).getCheckedData();

        //获取推广人id集合
        List<Integer> spreadPeopleUidList = new ArrayList<>();
        for(Map.Entry<Integer, User> entry : userList.entrySet()) {
            spreadPeopleUidList.add(entry.getValue().getSpreadUid());
        }

        //推广信息
        HashMap<Integer, User> mapListInUid = new HashMap<>();
        if (userIdList.size() > 0 && spreadPeopleUidList.size() > 0) {
            //推广人信息
            mapListInUid = userApi.getMapListInUid(spreadPeopleUidList).getCheckedData();
        }

        for (StoreOrder storeOrder : storeOrderList) {
            StoreOrderItemResponse storeOrderItemResponse = new StoreOrderItemResponse();
            BeanUtils.copyProperties(storeOrder, storeOrderItemResponse);
            String storeName = "";
            if (systemStoreList.containsKey(storeOrder.getStoreId())) {
                storeName = systemStoreList.get(storeOrder.getStoreId()).getName();
            }
            storeOrderItemResponse.setStoreName(storeName);

            // 添加核销人信息
            String clerkName = "";
            if (systemStoreStaffList.containsKey(storeOrder.getClerkId())) {
                clerkName = systemStoreStaffList.get(storeOrder.getClerkId()).getRealName();
            }
            storeOrderItemResponse.setProductList(orderInfoList.get(storeOrder.getId()));
            storeOrderItemResponse.setTotalNum(storeOrder.getTotalNum());

            //订单状态
            storeOrderItemResponse.setStatusStr(getStatus(storeOrder));
            storeOrderItemResponse.setStatus(storeOrder.getStatus());
            //支付方式
            storeOrderItemResponse.setPayTypeStr(getPayType(storeOrder.getPayType()));

            //推广人信息
            if (!userList.isEmpty()  && null != userList.get(storeOrder.getUid()) && mapListInUid.containsKey(userList.get(storeOrder.getUid()).getSpreadUid())) {
                storeOrderItemResponse.getSpreadInfo().setId(mapListInUid.get(userList.get(storeOrder.getUid()).getSpreadUid()).getUid());
                storeOrderItemResponse.getSpreadInfo().setName(mapListInUid.get(userList.get(storeOrder.getUid()).getSpreadUid()).getNickname());
            }
            storeOrderItemResponse.setRefundStatus(storeOrder.getRefundStatus());

            storeOrderItemResponse.setClerkName(clerkName);

            // 添加订单类型信息
            String orderTypeFormat = "[{}订单]{}";
            String orderType = "";
            // 核销
            if (StrUtil.isNotBlank(storeOrder.getVerifyCode())) {
                orderType = StrUtil.format(orderTypeFormat, "核销", "");
            }
            // 秒杀
            if (ObjectUtil.isNotNull(storeOrder.getSeckillId()) && storeOrder.getSeckillId() > 0) {
                orderType = StrUtil.format(orderTypeFormat, "秒杀", "");
            }
            // 砍价
            if (ObjectUtil.isNotNull(storeOrder.getBargainId()) && storeOrder.getBargainId() > 0) {
                orderType = StrUtil.format(orderTypeFormat, "砍价", "");
            }
            // 拼团
            if (ObjectUtil.isNotNull(storeOrder.getPinkId()) && storeOrder.getPinkId() > 0) {
                StorePink storePink = storePinkService.getById(storeOrder.getPinkId());
                if (ObjectUtil.isNotNull(storePink)) {
                    String pinkstatus = "";
                    if (storePink.getStatus() == 2) {
                        pinkstatus = "已完成";
                    } else if (storePink.getStatus() == 3) {
                        pinkstatus = "未完成";
                    } else {
                        pinkstatus = "正在进行中";
                    }
                    orderType = StrUtil.format(orderTypeFormat, "拼团", pinkstatus);
                }
            }
            if (StrUtil.isBlank(orderType)) {
                orderType = StrUtil.format(orderTypeFormat, "普通", "");
            }
            storeOrderItemResponse.setOrderType(orderType);
            storeOrderItemResponseArrayList.add(storeOrderItemResponse);
        }
        return storeOrderItemResponseArrayList;
    }

    /**
     * 累计消费
     * @param userId Integer 用户id
     * @author Mr.Zhang
     * @since 2020-06-10
     * @return UserBalanceResponse
     */
    @Override
    public BigDecimal getSumBigDecimal(Integer userId, String date) {
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(pay_price) as pay_price").
                eq("paid", 1).
                eq("is_del", 0);
        if (null != userId) {
            queryWrapper.eq("uid", userId);
        }
        if (null != date) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            queryWrapper.between("create_time", dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        StoreOrder storeOrder = dao.selectOne(queryWrapper);
        if (null == storeOrder || null == storeOrder.getPayPrice()) {
            return BigDecimal.ZERO;
        }
        return storeOrder.getPayPrice();
    }

    /**
     * 按开始结束时间分组订单
     * @param date String 时间范围
     * @param lefTime int 截取创建时间长度
     * @author Mr.Zhang
     * @since 2020-05-16
     * @return HashMap<String, Object>
     */
    public List<StoreOrder> getOrderGroupByDate(String date, int lefTime) {
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(pay_price) as pay_price", "left(create_time, "+lefTime+") as orderId", "count(id) as id");
        if (StringUtils.isNotBlank(date)) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            queryWrapper.between("create_time", dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        queryWrapper.groupBy("orderId").orderByAsc("orderId");
        return dao.selectList(queryWrapper);
    }

    /** 退款
     * @param request StoreOrderRefundRequest 退款参数
     * @return boolean
     * 这里只处理订单状态
     * 余额支付需要把余额给用户加回去
     * 其余处理放入redis中处理
     */
    @Override
    public boolean refund(StoreOrderRefundRequest request) {
        StoreOrder storeOrder = getInfoException(request.getOrderNo());
        if (!storeOrder.getPaid()) {
            throw new XlwebException("未支付无法退款");
        }
        if (storeOrder.getRefundPrice().add(request.getAmount()).compareTo(storeOrder.getPayPrice()) > 0) {
            throw new XlwebException("退款金额大于支付金额，请修改退款金额");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            if (storeOrder.getPayPrice().compareTo(BigDecimal.ZERO) != 0) {
                throw new XlwebException("退款金额不能为0，请修改退款金额");
            }
        }
        request.setOrderId(storeOrder.getId());
        //用户
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();

        //退款
        if (storeOrder.getPayType().equals(Constants.PAY_TYPE_WE_CHAT) && request.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            try {
                storeOrderRefundService.refund(request, storeOrder);
            } catch (Exception e) {
                e.printStackTrace();
                throw new XlwebException("微信申请退款失败！");
            }
        }

        //修改订单退款状态
        storeOrder.setRefundStatus(3);
        storeOrder.setRefundPrice(request.getAmount());

        Boolean execute = transactionTemplate.execute(e -> {
            updateById(storeOrder);
            if (storeOrder.getPayType().equals(Constants.PAY_TYPE_YUE)) {
                //新增日志
                request.setOrderId(storeOrder.getId());
                userBillApi.saveRefundBill(request, user);

                // 更新用户金额
                userApi.operationNowMoney(user.getUid(), request.getAmount(), user.getNowMoney(), "add");

                // 退款task
                redisUtil.lPush(Constants.ORDER_TASK_REDIS_KEY_AFTER_REFUND_BY_USER, storeOrder.getId());
            }
            if (storeOrder.getPayType().equals(Constants.PAY_TYPE_WE_CHAT) && request.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                //新增日志
                userBillApi.saveRefundBill(request, user);

                // 退款task
                redisUtil.lPush(Constants.ORDER_TASK_REDIS_KEY_AFTER_REFUND_BY_USER, storeOrder.getId());
            }
            return Boolean.TRUE;
        });
        if (!execute) {
            storeOrderStatusService.saveRefund(storeOrder.getId(), request.getAmount(), "失败");
            throw new XlwebException("订单更新失败");
        }

        // 发送消息通知
        return execute;
    }

    /**
     * 订单详情（PC）
     * @param orderNo 订单编号
     * @return StoreOrderInfoResponse
     */
    @Override
    public StoreOrderInfoResponse info(String orderNo) {
        StoreOrder storeOrder = getInfoException(orderNo);
        if (storeOrder.getIsSystemDel()) {
            throw new XlwebException("未找到对应订单信息");
        }
        StoreOrderInfoResponse storeOrderInfoResponse = new StoreOrderInfoResponse();
        BeanUtils.copyProperties(storeOrder, storeOrderInfoResponse);
        List<StoreOrderInfoOldVo> orderInfos = StoreOrderInfoService.getOrderListByOrderId(storeOrder.getId());
        storeOrderInfoResponse.setOrderInfo(orderInfos);
        storeOrderInfoResponse.setPayTypeStr(getPayType(storeOrder.getPayType()));
        storeOrderInfoResponse.setStatusStr(getStatus(storeOrder));
        if (ObjectUtil.isNotNull(storeOrder.getStoreId()) && storeOrder.getStoreId() > 0) {
            SystemStore systemStorePram = new SystemStore();
            systemStorePram.setId(storeOrder.getStoreId());
            storeOrderInfoResponse.setSystemStore(systemStoreApi.getByCondition(systemStorePram).getCheckedData());
        }

        //用户信息
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        storeOrderInfoResponse.setNikeName(user.getNickname());
        storeOrderInfoResponse.setPhone(user.getPhone());

        UserBrokerageRecord brokerageRecord = userBrokerageRecordApi.getByLinkIdAndLinkType(orderNo, "order").getCheckedData();
        if (ObjectUtil.isNotNull(brokerageRecord)) {
            User spread = userApi.getById(brokerageRecord.getUid()).getCheckedData();
            storeOrderInfoResponse.setSpreadName(spread.getNickname());
        }

        storeOrderInfoResponse.setProTotalPrice(storeOrder.getTotalPrice().subtract(storeOrder.getTotalPostage()));
        return storeOrderInfoResponse;
    }

    /** 发送货物
     * @param request StoreOrderSendRequest 发货参数
     * @author Mr.Zhang
     * @since 2020-06-10
     * @return boolean
     */
    @Override
    public Boolean send(StoreOrderSendRequest request) {
        //订单信息
        StoreOrder storeOrder = getInfoException(request.getOrderNo());
        if (storeOrder.getIsDel()) throw new XlwebException("订单已删除,不能发货!");
        if (storeOrder.getStatus() > 0) throw new XlwebException("订单已发货请勿重复操作!");
        request.setId(storeOrder.getId());
        switch (request.getType()) {
            case "1":// 发货
                express(request, storeOrder);
                break;
            case "2":// 送货
                delivery(request, storeOrder);
                break;
            case "3":// 虚拟
                virtual(request, storeOrder);
                break;
            default:
                throw new XlwebException("类型错误");
        }
        return true;
    }

    /**
     * 订单备注
     * @param orderNo 订单编号
     * @param mark 备注
     * @return Boolean
     */
    @Override
    public Boolean mark(String orderNo, String mark) {
        StoreOrder storeOrder = getInfoException(orderNo);
        storeOrder.setRemark(mark);
        return updateById(storeOrder);
    }

    /**
     * 拒绝退款
     * @param orderNo 订单编号
     * @param reason String 原因
     * @return Boolean
     */
    @Override
    public Boolean refundRefuse(String orderNo, String reason) {
        if (StrUtil.isBlank(reason)) {
            throw new XlwebException("请填写拒绝退款原因");
        }
        StoreOrder storeOrder = getInfoException(orderNo);
        storeOrder.setRefundReason(reason);
        storeOrder.setRefundStatus(0);

        User user = userApi.getById(storeOrder.getUid()).getCheckedData();

        Boolean execute = transactionTemplate.execute(e -> {
            updateById(storeOrder);
            storeOrderStatusService.createLog(storeOrder.getId(), Constants.ORDER_LOG_REFUND_REFUSE, Constants.ORDER_LOG_MESSAGE_REFUND_REFUSE.replace("{reason}", reason));
            return Boolean.TRUE;
        });
        if (execute) {
            // 如果是拼团订单要将拼团状态改回去
            if (ObjectUtil.isNotNull(storeOrder) && storeOrder.getPinkId() > 0) {
                StorePink storePink = storePinkService.getById(storeOrder.getPinkId());
                if (storePink.getStatus().equals(3)) {
                    storePink.setStatus(1);
                    storePinkService.updateById(storePink);
                }
            }
        }
        return execute;
    }

    /**
     * 查询单条
     * @param storeOrder StoreOrder 订单参数
     * @author Mr.Zhang
     * @since 2020-05-28
     * @return StoreOrder
     */
    @Override
    public StoreOrder getInfoByEntity(StoreOrder storeOrder) {
        LambdaQueryWrapper<StoreOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.setEntity(storeOrder);
        return dao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 获取订单快递信息
     * @param orderNo 订单编号
     * @return LogisticsResultVo
     */
    @Override
    public LogisticsResultVo getLogisticsInfo(String orderNo) {
        StoreOrder info = getInfoException(orderNo);
        if (info.getType().equals(1)) {// 视频号订单
            Express express = expressApi.getByName(info.getDeliveryName()).getCheckedData();
            if (ObjectUtil.isNotNull(express)) {
                info.setDeliveryCode(express.getCode());
            } else {
                info.setDeliveryCode("");
            }
        }
        return logisticApi.info(info.getDeliveryId(), null, Optional.ofNullable(info.getDeliveryCode()).orElse(""), info.getUserPhone()).getCheckedData();
    }

    /**
     * 订单 top 查询参数
     * @param status 状态参数
     * @return 订单查询结果
     */
    @Override
    public Integer getTopDataUtil(Integer status, Integer userId) {
        LambdaQueryWrapper<StoreOrder> lqw = new LambdaQueryWrapper<>();
        statusApiByWhere(lqw, status);
        lqw.eq(StoreOrder::getUid,userId);
        return Math.toIntExact(dao.selectCount(lqw));
    }

    /**
     * h5 订单查询 where status 封装
     * @param status 状态
     */
    public void statusApiByWhere(LambdaQueryWrapper<StoreOrder> queryWrapper, Integer status){
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
     * 改价
     * @param orderNo 订单编号
     * @param price 修改后的价格
     * @param oldPrice 原支付金额
     */
    private Boolean orderEditPrice(String orderNo, BigDecimal price, BigDecimal oldPrice) {
        LambdaUpdateWrapper<StoreOrder> luw = new LambdaUpdateWrapper<>();
        luw.set(StoreOrder::getPayPrice, price);
        luw.set(StoreOrder::getBeforePayPrice, oldPrice);
        luw.set(StoreOrder::getIsAlterPrice, 1);
        luw.eq(StoreOrder::getOrderId, orderNo);
        luw.eq(StoreOrder::getPaid, false);
        return update(luw);
    }

    /**
     * 根据时间参数统计订单销售额
     *
     * @param dateLimit 时间区间
     * @param type 类型
     * @return 统计订单信息
     */
    @Override
    public StoreOrderStatisticsResponse orderStatisticsByTime(String dateLimit, Integer type) {
        StoreOrderStatisticsResponse response = new StoreOrderStatisticsResponse();
        // 根据开始时间和结束时间获取时间差 再根据时间差获取上一个时间段 查询当前和上一个时间段的数据 进行比较且返回
        dateLimitUtilVo dateRange = DateUtil.getDateLimit(dateLimit);
        String dateStartD = dateRange.getStartTime();
        String dateEndD = dateRange.getEndTime();
        int days = DateUtil.daysBetween(
                DateUtil.strToDate(dateStartD,Constants.DATE_FORMAT_DATE),
                DateUtil.strToDate(dateEndD,Constants.DATE_FORMAT_DATE)
        );
        // 同时间区间的上一个时间起点
        String perDateStart = DateUtil.addDay(
                DateUtil.strToDate(dateStartD,Constants.DATE_FORMAT_DATE), -days, Constants.DATE_FORMAT_START);
        // 当前时间区间
        String dateStart = DateUtil.addDay(
                DateUtil.strToDate(dateStartD,Constants.DATE_FORMAT_DATE),0,Constants.DATE_FORMAT_START);
        String dateEnd = DateUtil.addDay(
                DateUtil.strToDate(dateEndD,Constants.DATE_FORMAT_DATE),0,Constants.DATE_FORMAT_END);

        // 上一个时间段查询
        List<StoreOrder> orderPerList = getOrderPayedByDateLimit(perDateStart,dateStart);

        // 当前时间段
        List<StoreOrder> orderCurrentList = getOrderPayedByDateLimit(dateStart, dateEnd);
        double increasePrice = 0;
        if (type == 1) {
            double perSumPrice = orderPerList.stream().mapToDouble(e -> e.getPayPrice().doubleValue()).sum();
            double currentSumPrice = orderCurrentList.stream().mapToDouble(e -> e.getPayPrice().doubleValue()).sum();

            response.setChart(dao.getOrderStatisticsPriceDetail(new StoreDateRangeSqlPram(dateStart,dateEnd)));
            response.setTime(BigDecimal.valueOf(currentSumPrice).setScale(2,BigDecimal.ROUND_HALF_UP));
            // 当前营业额和上一个同比营业额增长区间
            increasePrice = currentSumPrice - perSumPrice;
            if (increasePrice <= 0) response.setGrowthRate(0);
            else if (perSumPrice == 0) response.setGrowthRate((int) increasePrice * 100);
            else response.setGrowthRate((int)((increasePrice * perSumPrice) * 100));
        }else if (type ==2) {
            response.setChart(dao.getOrderStatisticsOrderCountDetail(new StoreDateRangeSqlPram(dateStart,dateEnd)));
            response.setTime(BigDecimal.valueOf(orderCurrentList.size()));
            increasePrice = orderCurrentList.size() - orderPerList.size();
            if (increasePrice <= 0) response.setGrowthRate(0);
            else if (orderPerList.size() == 0) response.setGrowthRate((int) increasePrice);
            else response.setGrowthRate((int)((increasePrice / orderPerList.size()) * 100));
        }
        response.setIncreaseTime(increasePrice+"");
        response.setIncreaseTimeStatus(increasePrice >= 0 ? 1:2);
        return response;
    }

    /**
     * 获取用户当天的秒杀数量
     *
     * @param uid 用户uid
     * @param seckillId 秒杀商品id
     * @return 用户当天的秒杀商品订单数量
     */
    @Override
    public List<StoreOrder> getUserCurrentDaySecKillOrders(Integer uid, Integer seckillId) {
        String dayStart = DateUtil.nowDateTime(Constants.DATE_FORMAT_START);
        String dayEnd = DateUtil.nowDateTime(Constants.DATE_FORMAT_END);
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getUid, uid);
        lqw.eq(StoreOrder::getSeckillId, seckillId);
        lqw.between(StoreOrder::getCreateTime, dayStart, dayEnd);
        lqw.eq(StoreOrder::getIsDel, false);
        return dao.selectList(lqw);
    }

    /**
     * 获取用户当前的砍价订单
     * @param uid    用户uid
     * @return  用户当前的砍价订单
     */
    @Override
    public List<StoreOrder> getUserCurrentBargainOrders(Integer uid, Integer bargainId) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getUid, uid);
        lqw.eq(StoreOrder::getBargainId, bargainId);
        lqw.eq(StoreOrder::getIsDel, false);
        return dao.selectList(lqw);
    }

    /**
     * 获取用户当前的拼团订单
     * @param uid    用户uid
     * @return  用户当前的拼团订单
     */
    @Override
    public List<StoreOrder> getUserCurrentCombinationOrders(Integer uid, Integer combinationId) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getUid, uid);
        lqw.eq(StoreOrder::getCombinationId, combinationId);
        lqw.eq(StoreOrder::getIsDel, false);
        return dao.selectList(lqw);
    }

    @Override
    public StoreOrder getByOderId(String orderId) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getOrderId, orderId);
        return dao.selectOne(lqw);
    }

    /**
     * 获取面单默认配置信息
     * @return ExpressSheetVo
     */
    @Override
    public ExpressSheetVo getDeliveryInfo() {
        return configApi.getDeliveryInfo().getCheckedData();
    }

    /**
     * 更新支付结果
     * @param orderNo 订单编号
     * @return Boolean
     */
    @Override
    public Boolean updatePaid(String orderNo) {
        LambdaUpdateWrapper<StoreOrder> lqw = new LambdaUpdateWrapper<>();
        lqw.set(StoreOrder::getPaid, true);
        lqw.set(StoreOrder::getPayTime, DateUtil.nowDateTime());
        lqw.eq(StoreOrder::getOrderId, orderNo);
        lqw.eq(StoreOrder::getPaid,false);
        return update(lqw);
    }

    /**
     * 跟据订单号列表获取订单列表Map
     * @param orderNoList 订单号列表
     * @return Map
     */
    @Override
    public Map<String, StoreOrder> getMapInOrderNo(List<String> orderNoList) {
        Map<String, StoreOrder> map = new HashMap<>();
        LambdaUpdateWrapper<StoreOrder> lqw = new LambdaUpdateWrapper<>();
        lqw.in(StoreOrder::getOrderId, orderNoList);
        List<StoreOrder> orderList = dao.selectList(lqw);
        orderList.forEach(order -> {
            map.put(order.getOrderId(), order);
        });
        return map;
    }

    /**
     * 获取推广订单总金额
     * @param orderNoList 订单编号列表
     * @return BigDecimal
     */
    @Override
    public BigDecimal getSpreadOrderTotalPriceByOrderList(List<String> orderNoList) {
        LambdaQueryWrapper<StoreOrder> lqw = new LambdaQueryWrapper<>();
        lqw.select(StoreOrder::getPayPrice);
        lqw.in(StoreOrder::getOrderId, orderNoList);
        List<StoreOrder> orderList = dao.selectList(lqw);
        return orderList.stream().map(StoreOrder::getPayPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取所有收货订单id集合
     * @return List<StoreOrder>
     */
    @Override
    public List<StoreOrder> findIdAndUidListByReceipt() {
        LambdaQueryWrapper<StoreOrder> lqw = new LambdaQueryWrapper<>();
        lqw.select(StoreOrder::getId, StoreOrder::getUid);
        lqw.eq(StoreOrder::getStatus, 2);
        lqw.eq(StoreOrder::getRefundStatus, 0);
        lqw.eq(StoreOrder::getIsDel, false);
        List<StoreOrder> orderList = dao.selectList(lqw);
        if (CollUtil.isEmpty(orderList)) {
            return CollUtil.newArrayList();
        }
        return orderList;
    }

    /**
     *
     * @param userId 用户uid
     * @param pageParamRequest 分页参数
     * @return List
     */
    @Override
    public List<StoreOrder> findPaidListByUid(Integer userId, PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<StoreOrder> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StoreOrder::getUid, userId);
        lqw.eq(StoreOrder::getPaid, true);
        lqw.eq(StoreOrder::getIsDel, false);
        lqw.lt(StoreOrder::getRefundStatus, 2);
        lqw.orderByDesc(StoreOrder::getId);
        return dao.selectList(lqw);
    }

    /**
     * 订单改价
     * @param request 改价请求对象
     * @return 改价结果
     */
    @Override
    public Boolean updatePrice(StoreOrderUpdatePriceRequest request) {
        StoreOrder existOrder = getInfoException(request.getOrderNo());
        // 订单已支付
        if (existOrder.getPaid()) {
            throw new XlwebException(StrUtil.format("订单号为 {} 的订单已支付", existOrder.getOrderId()));
        }
        if (existOrder.getIsAlterPrice()) {
            throw new XlwebException("系统只支持一次改价");
        }
        // 修改价格和原来价格相同
        if (existOrder.getPayPrice().compareTo(request.getPayPrice()) ==0) {
            throw new XlwebException(StrUtil.format("修改价格不能和原支付价格相同 原价 {} 修改价 {}", existOrder.getPayPrice(), request.getPayPrice()));
        }
        String oldPrice = existOrder.getPayPrice()+"";

        Boolean execute = transactionTemplate.execute(e -> {
            // 修改订单价格
            orderEditPrice(existOrder.getOrderId(), request.getPayPrice(), existOrder.getPayPrice());
            // 订单修改状态操作
            storeOrderStatusService.createLog(existOrder.getId(), Constants.ORDER_LOG_EDIT,
                    Constants.RESULT_ORDER_EDIT_PRICE_LOGS.replace("${orderPrice}", oldPrice)
                            .replace("${price}", request.getPayPrice() + ""));
            return Boolean.TRUE;
        });
        if (!execute) {
            throw new XlwebException(Constants.RESULT_ORDER_EDIT_PRICE_SUCCESS
                    .replace("${orderNo}", existOrder.getOrderId()).replace("${price}", request.getPayPrice()+""));
        }
        // 发送改价短信提醒
        SystemNotification notification = notificationApi.getByMark(NotifyConstants.MODIFY_ORDER_PRICE_MARK).getCheckedData();
        if (notification.getIsSms().equals(1)) {
            User user = userApi.getById(existOrder.getUid()).getCheckedData();
            if (StrUtil.isNotBlank(user.getPhone())) {
                SmsTemplate smsTemplate = smsTemplateApi.getDetail(notification.getSmsId()).getCheckedData();
                // 发送改价短信提醒
                smsApi.sendOrderEditPriceNotice(user.getPhone(), existOrder.getOrderId(), request.getPayPrice(), smsTemplate.getTempKey());
            }
        }

        return execute;
    }

    /**
     * 获取订单总数量
     * @param uid 用户uid
     * @return Integer
     */
    @Override
    public Integer getOrderCountByUid(Integer uid) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getPaid, true);
        lqw.eq(StoreOrder::getIsDel, false);
        lqw.eq(StoreOrder::getUid, uid);
        lqw.lt(StoreOrder::getRefundStatus, 2);
        return Math.toIntExact(dao.selectCount(lqw));
    }

    /**
     * 获取用户总消费金额
     * @param userId 用户uid
     * @return BigDecimal
     */
    @Override
    public BigDecimal getSumPayPriceByUid(Integer userId) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.select(StoreOrder::getPayPrice);
        lqw.eq(StoreOrder::getPaid, true);
        lqw.eq(StoreOrder::getIsDel, false);
        lqw.eq(StoreOrder::getUid, userId);
        lqw.lt(StoreOrder::getRefundStatus, 2);
        List<StoreOrder> orderList = dao.selectList(lqw);
        return orderList.stream().map(StoreOrder::getPayPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取订单数量(时间)
     * @param uid 用户uid
     * @return Integer
     */
    @Override
    public Integer getOrderCountByUidAndDate(Integer uid, String date) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getPaid, true);
        lqw.eq(StoreOrder::getIsDel, false);
        lqw.eq(StoreOrder::getUid, uid);
        lqw.lt(StoreOrder::getRefundStatus, 2);
        if (StrUtil.isNotBlank(date)) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            lqw.between(StoreOrder::getCreateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        return Math.toIntExact(dao.selectCount(lqw));
    }

    /**
     * 获取用户消费金额(时间)
     * @param userId 用户uid
     * @return BigDecimal
     */
    @Override
    public BigDecimal getSumPayPriceByUidAndDate(Integer userId, String date) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.select(StoreOrder::getPayPrice);
        lqw.eq(StoreOrder::getPaid, true);
        lqw.eq(StoreOrder::getIsDel, false);
        lqw.eq(StoreOrder::getUid, userId);
        lqw.lt(StoreOrder::getRefundStatus, 2);
        if (StrUtil.isNotBlank(date)) {
            dateLimitUtilVo dateLimit = DateUtil.getDateLimit(date);
            lqw.between(StoreOrder::getCreateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }
        List<StoreOrder> orderList = dao.selectList(lqw);
        return orderList.stream().map(StoreOrder::getPayPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取砍价订单
     * @param bargainId 砍价商品id
     * @param bargainUserId 用户砍价活动id
     * @return StoreOrder
     */
    @Override
    public StoreOrder getByBargainOrder(Integer bargainId, Integer bargainUserId) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getBargainId, bargainId);
        lqw.eq(StoreOrder::getBargainUserId, bargainUserId);
        lqw.orderByDesc(StoreOrder::getId);
        lqw.last(" limit 1");
        return dao.selectOne(lqw);
    }

    /**
     * 获取订单状态数量
     * @return StoreOrderCountItemResponse
     */
    @Override
    public StoreOrderCountItemResponse getOrderStatusNum(String dateLimit, Integer type) {
        StoreOrderCountItemResponse response = new StoreOrderCountItemResponse();
        if (type.equals(2)) {
            type = null;
        }
        // 全部订单
        response.setAll(getCount(dateLimit, Constants.ORDER_STATUS_ALL, type));
        // 未支付订单
        response.setUnPaid(getCount(dateLimit, Constants.ORDER_STATUS_UNPAID, type));
        // 未发货订单
        response.setNotShipped(getCount(dateLimit, Constants.ORDER_STATUS_NOT_SHIPPED, type));
        // 待收货订单
        response.setSpike(getCount(dateLimit, Constants.ORDER_STATUS_SPIKE, type));
        // 待评价订单
        response.setBargain(getCount(dateLimit, Constants.ORDER_STATUS_BARGAIN, type));
        // 交易完成订单
        response.setComplete(getCount(dateLimit, Constants.ORDER_STATUS_COMPLETE, type));
        // 待核销订单
        response.setToBeWrittenOff(getCount(dateLimit, Constants.ORDER_STATUS_TOBE_WRITTEN_OFF, type));
        // 退款中订单
        response.setRefunding(getCount(dateLimit, Constants.ORDER_STATUS_REFUNDING, type));
        // 已退款订单
        response.setRefunded(getCount(dateLimit, Constants.ORDER_STATUS_REFUNDED, type));
        // 已删除订单
        response.setDeleted(getCount(dateLimit, Constants.ORDER_STATUS_DELETED, type));
        return response;
    }

    /**
     * 获取订单统计数据
     * @param dateLimit 时间端
     * @return StoreOrderTopItemResponse
     */
    @Override
    public StoreOrderTopItemResponse getOrderData(String dateLimit) {
        StoreOrderTopItemResponse itemResponse = new StoreOrderTopItemResponse();
        // 订单数量
        itemResponse.setCount(getCount(dateLimit, Constants.ORDER_STATUS_ALL));
        // 订单金额
        itemResponse.setAmount(getAmount(dateLimit, ""));
        // 微信支付金额
        itemResponse.setWeChatAmount(getAmount(dateLimit, Constants.PAY_TYPE_WE_CHAT));
        // 余额支付金额
        itemResponse.setYueAmount(getAmount(dateLimit, Constants.PAY_TYPE_YUE));
        return itemResponse;
    }

    /**
     * 订单删除
     * @param orderNo 订单编号
     * @return Boolean
     */
    @Override
    public Boolean delete(String orderNo) {
        StoreOrder storeOrder = getInfoException(orderNo);
        if (!storeOrder.getIsDel()) {
            throw new XlwebException("您选择的的订单存在用户未删除的订单，无法删除用户未删除的订单！");
        }
        if (storeOrder.getIsSystemDel()) {
            throw new XlwebException("此订单已经被删除了!");
        }
        storeOrder.setIsSystemDel(true);
        return updateById(storeOrder);
    }

    /**
     * 通过日期获取商品交易件数
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    @Override
    public Integer getOrderProductNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(total_num), 0) as total_num");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getTotalNum();
    }

    /**
     * 通过日期获取商品交易成功件数
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    @Override
    public Integer getOrderSuccessProductNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(total_num), 0) as total_num");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getTotalNum();
    }

    /**
     * 通过日期获取订单数量
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    @Override
    public Integer getOrderNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return Math.toIntExact(dao.selectCount(wrapper));
    }

    /**
     * 通过日期获取支付订单数量
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    @Override
    public Integer getPayOrderNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return Math.toIntExact(dao.selectCount(wrapper));
    }

    /**
     * 通过日期获取支付订单金额
     * @param date 日期，yyyy-MM-dd格式
     * @return BigDecimal
     */
    @Override
    public BigDecimal getPayOrderAmountByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getPayPrice();
    }

    /**
     * 通过日期获取支付订单金额
     * @param startDate 日期
     * @param endDate 日期
     * @return BigDecimal
     */
    @Override
    public BigDecimal getPayOrderAmountByPeriod(String startDate, String endDate) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') between {0} and {1}", startDate, endDate);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getPayPrice();
    }

    /**
     * 通过日期获取余额支付订单支付金额
     * @param date 日期，yyyy-MM-dd格式
     * @return BigDecimal
     */
    @Override
    public BigDecimal getYuePayOrderAmountByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
        wrapper.eq("paid", 1);
        wrapper.eq("pay_type", "yue");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getPayPrice();
    }

    /**
     * 获取累计消费金额
     * @return BigDecimal
     */
    @Override
    public BigDecimal getTotalPrice() {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
        wrapper.eq("paid", 1);
        StoreOrder storeOrder = dao.selectOne(wrapper);
        return storeOrder.getPayPrice();
    }

    /**
     * 根据日期获取下单用户数量
     * @param date 日期
     * @return Integer
     */
    @Override
    public Integer getOrderUserNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        wrapper.groupBy("uid");
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return 0;
        }
        return orderList.size();
    }

    /**
     * 根据日期获取下单用户数量
     * @param startDate 日期
     * @param endDate 日期
     * @return Integer
     */
    @Override
    public Integer getOrderUserNumByPeriod(String startDate, String endDate) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.apply("date_format(create_time, '%Y-%m-%d') between {0} and {1}", startDate, endDate);
        wrapper.groupBy("uid");
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return 0;
        }
        return orderList.size();
    }

    /**
     * 根据日期获取成交用户数量
     * @param date 日期
     * @return Integer
     */
    @Override
    public Integer getOrderPayUserNumByDate(String date) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        wrapper.groupBy("uid");
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return 0;
        }
        return orderList.size();
    }

    /**
     * 根据日期获取成交用户数量
     * @param startDate 日期
     * @param endDate 日期
     * @return Integer
     */
    @Override
    public Integer getOrderPayUserNumByPeriod(String startDate, String endDate) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("paid", 1);
        wrapper.apply("date_format(create_time, '%Y-%m-%d') between {0} and {1}", startDate, endDate);
        wrapper.groupBy("uid");
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return 0;
        }
        return orderList.size();
    }

    /**
     * 根据用户uid列表获取成交用户数量
     * @param uidList 用户列表
     * @return Integer
     */
    @Override
    public Integer getOrderPayUserNumByUidList(List<Integer> uidList) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        wrapper.eq("paid", 1);
        wrapper.in("uid", uidList);
        wrapper.groupBy("uid");
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return 0;
        }
        return orderList.size();
    }

    /**
     * 根据用户uid列表获取支付金额
     * @param uidList 用户列表
     * @return BigDecimal
     */
    @Override
    public BigDecimal getPayOrderAmountByUidList(List<Integer> uidList) {
        QueryWrapper<StoreOrder> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(sum(pay_price), 0.00) as pay_price");
//        wrapper.select("ifnull(if(sum(pay_price) = 0.00, 0, sum(pay_price)), 0) as pay_price");
        wrapper.eq("paid", 1);
        wrapper.in("uid", uidList);
        List<StoreOrder> orderList = dao.selectList(wrapper);
        if (CollUtil.isEmpty(orderList)) {
            return BigDecimal.ZERO;
        }
        return orderList.stream().map(StoreOrder::getPayPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取待发货订单数量
     * @return Integer
     */
    @Override
    public Integer getNotShippingNum() {
        return getCount("", Constants.ORDER_STATUS_NOT_SHIPPED);
    }

    /**
     * 获取退款中订单数量
     */
    @Override
    public Integer getRefundingNum() {
        return getCount("", Constants.ORDER_STATUS_REFUNDING);
    }

    /**
     * 获取待核销订单数量
     */
    @Override
    public Integer getNotWriteOffNum() {
        return getCount("", Constants.ORDER_STATUS_TOBE_WRITTEN_OFF);
    }

    /**
     * 获取佣金相关数据
     * @param uid 用户uid
     * @param spreadId 推广人uid
     */
    @Override
    public OrderBrokerageData getBrokerageData(Integer uid, Integer spreadId) {
        return dao.getBrokerageData(uid, spreadId);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////// 以下为自定义方法

    /**
     * 根据时间参数获取有效订单
     * @return 有效订单列表
     */
    private List<StoreOrder> getOrderPayedByDateLimit(String startTime, String endTime) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getIsDel, false).eq(StoreOrder::getPaid, true).eq(StoreOrder::getRefundStatus,0)
                .between(StoreOrder::getCreateTime, startTime, endTime);
     return dao.selectList(lqw);
    }

    private StoreOrder getInfoException(String orderNo) {
        LambdaQueryWrapper<StoreOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StoreOrder::getOrderId, orderNo);
        StoreOrder storeOrder = dao.selectOne(lqw);
        if (ObjectUtil.isNull(storeOrder)) {
            throw new XlwebException("没有找到订单信息");
        }
        return storeOrder;
    }

    /** 快递
     * @param request StoreOrderSendRequest 发货参数
     * @param storeOrder StoreOrder 订单信息
     */
    private void express(StoreOrderSendRequest request, StoreOrder storeOrder) {
        // 校验快递发货参数
        validateExpressSend(request);
        //快递公司信息
        Express express = expressApi.getByCode(request.getExpressCode()).getCheckedData();
        if (request.getExpressRecordType().equals("1")) { // 正常发货
            deliverGoods(request, storeOrder);
        }
        if (request.getExpressRecordType().equals("2")) { // 电子面单
            request.setExpressName(express.getName());
            expressDump(request, storeOrder, express);
        }

        storeOrder.setDeliveryCode(express.getCode());
        storeOrder.setDeliveryName(express.getName());
        storeOrder.setStatus(1);
        storeOrder.setDeliveryType("express");

        String message = Constants.ORDER_LOG_MESSAGE_EXPRESS.replace("{deliveryName}", express.getName()).replace("{deliveryCode}", storeOrder.getDeliveryId());

        Boolean execute = transactionTemplate.execute(i -> {
            updateById(storeOrder);
            //订单记录增加
            storeOrderStatusService.createLog(request.getId(), Constants.ORDER_LOG_EXPRESS, message);
            return Boolean.TRUE;
        });

        if (!execute) throw new XlwebException("快递发货失败！");

        sendGoodsNotify(storeOrder);
    }


    /**
     * 发货通知
     * @param storeOrder 订单
     */
    private void sendGoodsNotify(StoreOrder storeOrder) {
        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        SystemNotification notification = notificationApi.getByMark(NotifyConstants.DELIVER_GOODS_MARK).getCheckedData();
        if (notification.getIsSms().equals(1)) {
            // 发货短信提醒
            if (StrUtil.isNotBlank(user.getPhone())) {
                SmsTemplate smsTemplate = smsTemplateApi.getDetail(notification.getSmsId()).getCheckedData();
                String proName = "";
                List<StoreOrderInfoOldVo> voList = storeOrderInfoService.getOrderListByOrderId(storeOrder.getId());
                proName = voList.get(0).getInfo().getProductName();
                if (voList.size() > 1) {
                    proName = proName.concat("等");
                }
                smsApi.sendOrderDeliverNotice(user.getPhone(), user.getNickname(), proName, storeOrder.getOrderId(), smsTemplate.getTempKey());
            }
        }

        // 发送消息通知
        pushMessageOrder(storeOrder, user, notification);
    }

    /**
     * 发送消息通知
     * 根据用户类型发送
     * 公众号模板消息
     * 小程序订阅消息
     */
    private void pushMessageOrder(StoreOrder storeOrder, User user, SystemNotification notification) {
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
            temMap.put(Constants.WE_CHAT_TEMP_KEY_FIRST, "订单发货提醒");
            temMap.put("keyword1", storeOrder.getOrderId());
            temMap.put("keyword2", cn.hutool.core.date.DateUtil.now());
            temMap.put("keyword3", storeOrder.getDeliveryName());
            temMap.put("keyword4", storeOrder.getDeliveryId());
            temMap.put(Constants.WE_CHAT_TEMP_KEY_END, "欢迎再次购买！");
            templateMessageApi.pushTemplateMessage(notification.getWechatId(), temMap, userToken.getToken());
            return;
        } else if (notification.getIsRoutine().equals(1)) {
            // 小程序发送订阅消息
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_ROUTINE).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            // 组装数据
            // 注释部分为丰享汇小程序
//        temMap.put("character_string1", storeOrder.getOrderId());
//        temMap.put("name3", storeOrder.getDeliveryName());
//        temMap.put("character_string4", storeOrder.getDeliveryId());
//        temMap.put("thing7", "您的订单已发货");
            // 放开部分为一码秦川小程序
            temMap.put("character_string1", storeOrder.getOrderId());
            temMap.put("name6", storeOrder.getDeliveryName());
            temMap.put("character_string7", storeOrder.getDeliveryId());
            temMap.put("thing11", "您的订单已发货");
            templateMessageApi.pushMiniTemplateMessage(notification.getRoutineId(), temMap, userToken.getToken());
        }
    }

    /**
     * 电子面单
     * @param request
     * @param storeOrder
     * @param express
     */
    private void expressDump(StoreOrderSendRequest request, StoreOrder storeOrder, Express express) {
        String configExportOpen = configApi.getValueByKeyException("config_export_open").getCheckedData();
        if (!configExportOpen.equals("1")) {// 电子面单未开启
            throw new XlwebException("请先开启电子面单");
        }
        MyRecord record = new MyRecord();
        record.set("com", express.getCode());// 快递公司编码
        record.set("to_name", storeOrder.getRealName());// 收件人
        record.set("to_tel", storeOrder.getUserPhone());// 收件人电话
        record.set("to_addr", storeOrder.getUserAddress());// 收件人详细地址
        record.set("from_name", request.getToName());// 寄件人
        record.set("from_tel", request.getToTel());// 寄件人电话
        record.set("from_addr", request.getToAddr());// 寄件人详细地址
        record.set("temp_id", request.getExpressTempId());// 电子面单模板ID
        String siid = configApi.getValueByKeyException("config_export_siid").getCheckedData();
        record.set("siid", siid);// 云打印机编号
        record.set("count", storeOrder.getTotalNum());// 商品数量

        //获取购买商品名称
        List<Integer> orderIdList = new ArrayList<>();
        orderIdList.add(storeOrder.getId());
        HashMap<Integer, List<StoreOrderInfoOldVo>> orderInfoMap = StoreOrderInfoService.getMapInId(orderIdList);
        if (orderInfoMap.isEmpty() || !orderInfoMap.containsKey(storeOrder.getId())) {
            throw new XlwebException("没有找到购买的商品信息");
        }
        List<String> productNameList = new ArrayList<>();
        for (StoreOrderInfoOldVo storeOrderInfoVo : orderInfoMap.get(storeOrder.getId())) {
            productNameList.add(storeOrderInfoVo.getInfo().getProductName());
        }

        record.set("cargo", String.join(",", productNameList));// 物品名称
        if (express.getPartnerId()) {
            record.set("partner_id", express.getAccount());// 电子面单月结账号(部分快递公司必选)
        }
        if (express.getPartnerKey()) {
            record.set("partner_key", express.getPassword());// 电子面单密码(部分快递公司必选)
        }
        if (express.getNet()) {
            record.set("net", express.getNetName());// 收件网点名称(部分快递公司必选)
        }

        MyRecord myRecord = expressDump(record);
        storeOrder.setDeliveryId(myRecord.getStr("kuaidinum"));
    }

    /**
     * 电子面单
     * @param record 电子面单参数
     * @return
     */
    public MyRecord expressDump(MyRecord record) {
        // TODO 后续对接电子面单厂家
        MyRecord myRecord = new MyRecord();
        JSONObject jsonObject = new JSONObject();
        return myRecord.setColums(jsonObject);
    }

    /**
     * 正常发货
     */
    private void deliverGoods(StoreOrderSendRequest request, StoreOrder storeOrder) {
        storeOrder.setDeliveryId(request.getExpressNumber());
    }

    /**
     * 校验快递发货参数
     */
    private void validateExpressSend(StoreOrderSendRequest request) {
        if (request.getExpressRecordType().equals("1")) {
            if (StrUtil.isBlank(request.getExpressNumber())) throw new XlwebException("请填写快递单号");
            return;
        }
        if (StrUtil.isBlank(request.getExpressCode())) throw new XlwebException("请选择快递公司");
        if (StrUtil.isBlank(request.getExpressRecordType())) throw new XlwebException("请选择发货记录类型");
        if (StrUtil.isBlank(request.getExpressTempId())) throw new XlwebException("请选择电子面单");
        if (StrUtil.isBlank(request.getToName())) throw new XlwebException("请填写寄件人姓名");
        if (StrUtil.isBlank(request.getToTel())) throw new XlwebException("请填写寄件人电话");
        if (StrUtil.isBlank(request.getToAddr())) throw new XlwebException("请填写寄件人地址");
    }

    /** 送货上门
     * @param request StoreOrderSendRequest 发货参数
     * @param storeOrder StoreOrder 订单信息
     * @author Mr.Zhang
     * @since 2020-06-10
     */
    private void delivery(StoreOrderSendRequest request, StoreOrder storeOrder) {
        if (StrUtil.isBlank(request.getDeliveryName())) throw new XlwebException("请输入送货人姓名");
        if (StrUtil.isBlank(request.getDeliveryTel())) throw new XlwebException("请输入送货人电话号码");
        ValidateFormUtil.isPhone(request.getDeliveryTel(), "送货人联系方式");

        //送货信息
        storeOrder.setDeliveryName(request.getDeliveryName());
        storeOrder.setDeliveryId(request.getDeliveryTel());
        storeOrder.setStatus(1);
        storeOrder.setDeliveryType("send");

        //获取购买商品名称
        List<Integer> orderIdList = new ArrayList<>();
        orderIdList.add(storeOrder.getId());
        HashMap<Integer, List<StoreOrderInfoOldVo>> orderInfoMap = StoreOrderInfoService.getMapInId(orderIdList);
        if (orderInfoMap.isEmpty() || !orderInfoMap.containsKey(storeOrder.getId())) {
            throw new XlwebException("没有找到购买的商品信息");
        }
        List<String> productNameList = new ArrayList<>();
        for (StoreOrderInfoOldVo storeOrderInfoVo : orderInfoMap.get(storeOrder.getId())) {
            productNameList.add(storeOrderInfoVo.getInfo().getProductName());
        }

        String message = Constants.ORDER_LOG_MESSAGE_DELIVERY.replace("{deliveryName}", request.getDeliveryName()).replace("{deliveryCode}", request.getDeliveryTel());

        Boolean execute = transactionTemplate.execute(i -> {
            // 更新订单
            updateById(storeOrder);
            // 订单记录增加
            storeOrderStatusService.createLog(request.getId(), Constants.ORDER_LOG_DELIVERY, message);
            return Boolean.TRUE;
        });
        if (!execute) throw new XlwebException("订单更新送货失败");

        User user = userApi.getById(storeOrder.getUid()).getCheckedData();
        // 发送消息通知
        pushMessageDeliveryOrder(storeOrder, user, request, productNameList);
    }

    /**
     * 发送消息通知
     * 根据用户类型发送
     * 公众号模板消息
     * 小程序订阅消息
     */
    private void pushMessageDeliveryOrder(StoreOrder storeOrder, User user, StoreOrderSendRequest request, List<String> productNameList) {
        if (storeOrder.getIsChannel().equals(2)) {
            return;
        }
        if (!storeOrder.getPayType().equals(Constants.PAY_TYPE_WE_CHAT)) {
            return;
        }
        SystemNotification notification = notificationApi.getByMark(NotifyConstants.FULFILLMENT_ORDER_MARK).getCheckedData();
        UserToken userToken;
        HashMap<String, String> map = new HashMap<>();
        String proName = "";
        if (CollUtil.isNotEmpty(productNameList)) {
            proName = StringUtils.join(productNameList, "|");
        }
        // 公众号
        if (storeOrder.getIsChannel().equals(Constants.ORDER_PAY_CHANNEL_PUBLIC) && notification.getIsWechat().equals(1)) {
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_WECHAT).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }
            map.put(Constants.WE_CHAT_TEMP_KEY_FIRST, "订单配送提醒");
            map.put("keyword1", storeOrder.getOrderId());
            map.put("keyword2", DateUtil.dateToStr(storeOrder.getCreateTime(), Constants.DATE_FORMAT));
            map.put("keyword3", storeOrder.getUserAddress());
            map.put("keyword4", request.getDeliveryName());
            map.put("keyword5", request.getDeliveryTel());
            map.put(Constants.WE_CHAT_TEMP_KEY_END, "欢迎再次购买！");
            // 发送微信模板消息
            templateMessageApi.pushTemplateMessage(notification.getWechatId(), map, userToken.getToken());
        } else if (notification.getIsRoutine().equals(1)) {
            // 小程序发送订阅消息
            userToken = userTokenApi.getTokenByUserId(user.getUid(), UserConstants.USER_TOKEN_TYPE_ROUTINE).getCheckedData();
            if (ObjectUtil.isNull(userToken)) {
                return ;
            }

            if (proName.length() > 20) {
                proName = proName.substring(0, 15) + "***";
            }
//        map.put("thing8", proName);
//        map.put("character_string1", storeOrder.getOrderId());
//        map.put("name4", request.getDeliveryName());
//        map.put("phone_number10", request.getDeliveryTel());
            map.put("thing8", proName);
            map.put("character_string1", storeOrder.getOrderId());
            map.put("name4", request.getDeliveryName());
            map.put("phone_number10", request.getDeliveryTel());
            templateMessageApi.pushMiniTemplateMessage(notification.getRoutineId(), map, userToken.getToken());
        }
    }

    /** 虚拟
     * @param request StoreOrderSendRequest 发货参数
     * @param storeOrder StoreOrder 订单信息
     * @author Mr.Zhang
     * @since 2020-06-10
     */
    private void virtual(StoreOrderSendRequest request, StoreOrder storeOrder) {
        //快递信息
        storeOrder.setDeliveryType("fictitious");
        storeOrder.setStatus(1);

        Boolean execute = transactionTemplate.execute(i -> {
            updateById(storeOrder);
            //订单记录增加
            storeOrderStatusService.createLog(request.getId(), Constants.ORDER_LOG_DELIVERY_VI, "虚拟物品发货");
            return Boolean.TRUE;
        });
        if (!execute) throw new XlwebException("虚拟物品发货失败");
    }

    /**
     * 获取订单总数
     * @param dateLimit 时间端
     * @param status String 状态
     * @return Integer
     */
    private Integer getCount(String dateLimit, String status) {
        //总数只计算时间
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dateLimit)) {
            dateLimitUtilVo dateLimitUtilVo = DateUtil.getDateLimit(dateLimit);
            queryWrapper.between("create_time", dateLimitUtilVo.getStartTime(), dateLimitUtilVo.getEndTime());
        }
        getStatusWhereNew(queryWrapper, status);
        return Math.toIntExact(dao.selectCount(queryWrapper));
    }

    /**
     * 获取订单总数
     * @param dateLimit 时间端
     * @param status String 状态
     * @return Integer
     */
    private Integer getCount(String dateLimit, String status, Integer type) {
        //总数只计算时间
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(dateLimit)) {
            dateLimitUtilVo dateLimitUtilVo = DateUtil.getDateLimit(dateLimit);
            queryWrapper.between("create_time", dateLimitUtilVo.getStartTime(), dateLimitUtilVo.getEndTime());
        }
        getStatusWhereNew(queryWrapper, status);
        if (ObjectUtil.isNotNull(type)) {
            queryWrapper.eq("type", type);
        }
        return Math.toIntExact(dao.selectCount(queryWrapper));
    }

    /**
     * 获取订单金额
     * @param dateLimit 时间端
     * @param type  支付类型
     * @return Integer
     */
    private BigDecimal getAmount(String dateLimit, String type) {
        QueryWrapper<StoreOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(pay_price) as pay_price");
        if (StringUtils.isNotBlank(type)) {
            queryWrapper.eq("pay_type", type);
        }
        queryWrapper.isNotNull("pay_time");
        queryWrapper.eq("paid", 1);
        if (StringUtils.isNotBlank(dateLimit)) {
            dateLimitUtilVo dateLimitUtilVo = DateUtil.getDateLimit(dateLimit);
            queryWrapper.between("create_time", dateLimitUtilVo.getStartTime(), dateLimitUtilVo.getEndTime());
        }
        StoreOrder storeOrder = dao.selectOne(queryWrapper);
        if (ObjectUtil.isNull(storeOrder)) {
            return BigDecimal.ZERO;
        }
        return storeOrder.getPayPrice();
    }

    /**
     * 获取request的where条件
     * @param queryWrapper QueryWrapper<StoreOrder> 表达式
     * @param request StoreOrderSearchRequest 请求参数
     */
    private void getRequestTimeWhere(QueryWrapper<StoreOrder> queryWrapper, StoreOrderSearchRequest request) {
        if (StringUtils.isNotBlank(request.getDateLimit())) {
            dateLimitUtilVo dateLimitUtilVo = DateUtil.getDateLimit(request.getDateLimit());
            queryWrapper.between("create_time", dateLimitUtilVo.getStartTime(), dateLimitUtilVo.getEndTime());
        }
    }

    /**
     * 根据订单状态获取where条件
     * @param queryWrapper QueryWrapper<StoreOrder> 表达式
     * @param status String 类型
     */
    private void getStatusWhereNew(QueryWrapper<StoreOrder> queryWrapper, String status) {
        if (StrUtil.isBlank(status)) {
            return;
        }
        switch (status) {
            case Constants.ORDER_STATUS_ALL: //全部
                break;
            case Constants.ORDER_STATUS_UNPAID: //未支付
                queryWrapper.eq("paid", 0);//支付状态
                queryWrapper.eq("status", 0); //订单状态
                queryWrapper.eq("is_del", 0);//删除状态
                break;
            case Constants.ORDER_STATUS_NOT_SHIPPED: //未发货
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 0);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("shipping_type", 1);//配送方式
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_SPIKE: //待收货
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 1);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_BARGAIN: //待评价
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 2);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_COMPLETE: //交易完成
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 3);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_TOBE_WRITTEN_OFF: //待核销
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 0);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("shipping_type", 2);//配送方式
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_REFUNDING: //退款中
                queryWrapper.eq("paid", 1);
                queryWrapper.in("refund_status", 1,3);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_REFUNDED: //已退款
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("refund_status", 2);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_DELETED: //已删除
                queryWrapper.eq("is_del", 1);
                break;
            default:
                queryWrapper.eq("paid", 1);
                queryWrapper.ne("refund_status", 2);
                break;
        }
        queryWrapper.eq("is_system_del", 0);
    }

    /**
     * 根据订单状态获取where条件
     * @param queryWrapper QueryWrapper<StoreOrder> 表达式
     * @param status String 类型
     */
    private void getStatusWhere(QueryWrapper<StoreOrder> queryWrapper, String status) {
        if (StrUtil.isBlank(status)) {
            return;
        }
        switch (status) {
            case Constants.ORDER_STATUS_UNPAID: //未支付
                queryWrapper.eq("paid", 0);//支付状态
                queryWrapper.eq("status", 0); //订单状态
                queryWrapper.eq("is_del", 0);//删除状态
                break;
            case Constants.ORDER_STATUS_NOT_SHIPPED: //未发货
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 0);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("shipping_type", 1);//配送方式
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_SPIKE: //待收货
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 1);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_BARGAIN: //待评价
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 2);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_COMPLETE: //交易完成
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 3);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_TOBE_WRITTEN_OFF: //待核销
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("status", 0);
                queryWrapper.eq("refund_status", 0);
                queryWrapper.eq("shipping_type", 2);//配送方式
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_REFUNDING: //退款中
                queryWrapper.eq("paid", 1);
                queryWrapper.in("refund_status", 1,3);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_REFUNDED: //已退款
                queryWrapper.eq("paid", 1);
                queryWrapper.eq("refund_status", 2);
                queryWrapper.eq("is_del", 0);
                break;
            case Constants.ORDER_STATUS_DELETED: //已删除
                queryWrapper.eq("is_del", 1);
                break;
            default:
                queryWrapper.eq("paid", 1);
                queryWrapper.ne("refund_status", 2);
                break;
        }
        queryWrapper.eq("is_system_del", 0);
    }

    /**
     * 获取订单状态
     * @param storeOrder StoreOrder 订单信息
     * @author Mr.Zhang
     * @since 2020-06-12
     */
    private Map<String, String> getStatus(StoreOrder storeOrder) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "");
        map.put("value", "");
        if (null == storeOrder) {
            return map;
        }
        // 未支付
        if (!storeOrder.getPaid()
                && storeOrder.getStatus() == 0
                && storeOrder.getRefundStatus() == 0
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_UNPAID);
            map.put("value", Constants.ORDER_STATUS_STR_UNPAID);
            return map;
        }
        // 未发货
        if (storeOrder.getPaid()
                && storeOrder.getStatus() == 0
                && storeOrder.getRefundStatus() == 0
                && storeOrder.getShippingType() == 1
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_NOT_SHIPPED);
            map.put("value", Constants.ORDER_STATUS_STR_NOT_SHIPPED);
            return map;
        }
        // 待收货
        if (storeOrder.getPaid()
                && storeOrder.getStatus() == 1
                && storeOrder.getRefundStatus() == 0
                && storeOrder.getShippingType() == 1
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_SPIKE);
            map.put("value", Constants.ORDER_STATUS_STR_SPIKE);
            return map;
        }
        // 待评价
        if (storeOrder.getPaid()
                && storeOrder.getStatus() == 2
                && storeOrder.getRefundStatus() == 0
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_BARGAIN);
            map.put("value", Constants.ORDER_STATUS_STR_BARGAIN);
            return map;
        }
        // 交易完成
        if (storeOrder.getPaid()
                && storeOrder.getStatus() == 3
                && storeOrder.getRefundStatus() == 0
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_COMPLETE);
            map.put("value", Constants.ORDER_STATUS_STR_COMPLETE);
            return map;
        }
        // 待核销
        if (storeOrder.getPaid()
                && storeOrder.getStatus() == 0
                && storeOrder.getRefundStatus() == 0
                && storeOrder.getShippingType() == 2
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_TOBE_WRITTEN_OFF);
            map.put("value", Constants.ORDER_STATUS_STR_TOBE_WRITTEN_OFF);
            return map;
        }

        //申请退款
        if (storeOrder.getPaid()
                && storeOrder.getRefundStatus() == 1
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_APPLY_REFUNDING);
            map.put("value", Constants.ORDER_STATUS_STR_APPLY_REFUNDING);
            return map;
        }

        //退款中
        if (storeOrder.getPaid()
                && storeOrder.getRefundStatus() == 3
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_REFUNDING);
            map.put("value", Constants.ORDER_STATUS_STR_REFUNDING);
            return map;
        }

        //已退款
        if (storeOrder.getPaid()
                && storeOrder.getRefundStatus() == 2
                && !storeOrder.getIsDel()
                && !storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_REFUNDED);
            map.put("value", Constants.ORDER_STATUS_STR_REFUNDED);
        }

        //已删除
        if (storeOrder.getIsDel() || storeOrder.getIsSystemDel()) {
            map.put("key", Constants.ORDER_STATUS_DELETED);
            map.put("value", Constants.ORDER_STATUS_STR_DELETED);
        }

        return map;
    }
    /**
     * 获取支付文字
     * @param payType String 支付方式
     */
    private String getPayType(String payType) {
        switch (payType) {
            case Constants.PAY_TYPE_WE_CHAT:
                return Constants.PAY_TYPE_STR_WE_CHAT;
            case Constants.PAY_TYPE_YUE:
                return Constants.PAY_TYPE_STR_YUE;
            case Constants.PAY_TYPE_ALI_PAY:
                return Constants.PAY_TYPE_STR_ALI_PAY;
            default:
                return Constants.PAY_TYPE_STR_OTHER;
        }
    }

    @Override
    public MyRecord getOrderBasic(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

            // 当前总订单销量
            QueryWrapper<StoreOrder> queryStoreOrderAllWrapper = new QueryWrapper<>();
            queryStoreOrderAllWrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
            queryStoreOrderAllWrapper.eq("paid", 1);
            queryStoreOrderAllWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            BigDecimal payPrice = dao.selectOne(queryStoreOrderAllWrapper).getPayPrice();

            // 当前订单总数
            QueryWrapper<StoreOrder> queryPayCountWrapper = new QueryWrapper<>();
            queryPayCountWrapper.select("id");
            queryPayCountWrapper.eq("paid", 1);
            queryPayCountWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));

            int payCount = Math.toIntExact(dao.selectCount(queryPayCountWrapper));

            // 当前退款金额
            QueryWrapper<StoreOrder> queryRefundPriceWrapper = new QueryWrapper<>();
            queryRefundPriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
            queryRefundPriceWrapper.eq("paid", 1);
            queryRefundPriceWrapper.ne("refund_status", 0);
            queryRefundPriceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            BigDecimal refundPrice = dao.selectOne(queryRefundPriceWrapper).getPayPrice();

            // 当前退款订单总数
            QueryWrapper<StoreOrder> queryRefundCountWrapper = new QueryWrapper<>();
            queryRefundCountWrapper.select("id");
            queryRefundCountWrapper.eq("paid", 1);
            queryRefundCountWrapper.ne("refund_status", 0);
            queryRefundCountWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
            int refundCount = Math.toIntExact(dao.selectCount(queryRefundCountWrapper));

            MyRecord myRecord = new MyRecord();
            myRecord.set("pay_price", payPrice);
            myRecord.set("pay_count", payCount);
            myRecord.set("refund_price", refundPrice);
            myRecord.set("refund_count", refundCount);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取订单统计数量数据失败", ex);
            throw new XlwebException("获取订单统计数量数据失败");
        }
    }

    @Override
    public MyRecord getOrderTrend(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();

            long dayCount = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
            MyRecord record;
            if (dayCount == 1) {
                record = trend(times, 0);
            } else if (dayCount > 1 && dayCount <= 31) {
                record = trend(times, 1);
            } else if (dayCount > 31 && dayCount <= 92) {
                record = trend(times, 3);
            } else {
                record = trend(times, 30);
            }
            return record;
        } catch (Exception ex) {
            logger.error("获取订单统计折线图数据失败", ex);
            throw new XlwebException("获取订单统计折线图数据失败");
        }
    }

    public MyRecord trend(String[] time, int num) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(time[0]);
        Date endDate = sdf.parse(time[1]);

        List<String> xAxis = new ArrayList<>();
        String timeType = "%Y-%m";
        if (num == 0) {
            xAxis = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
            timeType = "%H";
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);

            while (!cal.getTime().after(endDate)) {
                if (num == 30) {
                    xAxis.add(new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
                    cal.add(Calendar.MONTH, 1);
                    timeType = "%Y-%m";
                } else {
                    xAxis.add(new SimpleDateFormat("MM-dd").format(cal.getTime()));
                    cal.add(Calendar.DAY_OF_MONTH, num);
                    timeType = "%m-%d";
                }
            }
        }

        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

        QueryWrapper<StoreOrder> queryStoreOrderAllWrapper = new QueryWrapper<>();
        queryStoreOrderAllWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryStoreOrderAllWrapper.eq("paid", 1);
        queryStoreOrderAllWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryStoreOrderAllWrapper.groupBy("days");
        List<Map<String, Object>> payPrice = dao.selectMaps(queryStoreOrderAllWrapper);

        QueryWrapper<StoreOrder> queryStoreOrderCountAllWrapper = new QueryWrapper<>();
        queryStoreOrderCountAllWrapper.select("count(id) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryStoreOrderCountAllWrapper.eq("paid", 1);
        queryStoreOrderCountAllWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryStoreOrderCountAllWrapper.groupBy("days");
        List<Map<String, Object>> payPriceCount = dao.selectMaps(queryStoreOrderCountAllWrapper);

        QueryWrapper<StoreOrder> queryRefundPriceWrapper = new QueryWrapper<>();
        queryRefundPriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryRefundPriceWrapper.eq("paid", 1);
        queryRefundPriceWrapper.ne("refund_status", 0);
        queryRefundPriceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryRefundPriceWrapper.groupBy("days");
        List<Map<String, Object>> refundPrice = dao.selectMaps(queryRefundPriceWrapper);

        QueryWrapper<StoreOrder> queryRefundPriceCountWrapper = new QueryWrapper<>();
        queryRefundPriceCountWrapper.select("count(id) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
        queryRefundPriceCountWrapper.eq("paid", 1);
        queryRefundPriceCountWrapper.ne("refund_status", 0);
        queryRefundPriceCountWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryRefundPriceCountWrapper.groupBy("days");
        List<Map<String, Object>> refundPriceCount = dao.selectMaps(queryRefundPriceCountWrapper);

        Map<String, Float> payPriceMap = payPrice.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("pay_price")).toPlainString())
                ));

        Map<String, Integer> payPriceCountMap = payPriceCount.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Integer.valueOf(String.valueOf(item.get("num")))
                ));

        Map<String, Float> refundPriceMap = refundPrice.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Float.valueOf(((BigDecimal)item.get("pay_price")).toPlainString())
                ));

        Map<String, Integer> refundCountMap = refundPriceCount.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> Integer.valueOf(String.valueOf(item.get("num")))
                ));

        Map<String, List<Object>> data = new HashMap<>();
        data.put("订单金额", new ArrayList<>());
        data.put("订单量", new ArrayList<>());

        data.put("退款金额", new ArrayList<>());
        data.put("退款订单量", new ArrayList<>());


        for (String item : xAxis) {
            data.get("订单金额").add(payPriceMap.getOrDefault(item, 0.0f));
            data.get("订单量").add(payPriceCountMap.getOrDefault(item, 0));
            data.get("退款金额").add(refundPriceMap.getOrDefault(item, 0.0f));
            data.get("退款订单量").add(refundCountMap.getOrDefault(item, 0));
        }

        List<Map<String, Object>> series = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : data.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", entry.getKey());
            item.put("data", entry.getValue());
            item.put("type", "line");
            series.add(item);
        }
        MyRecord myRecord = new MyRecord();
        myRecord.set("xAxis", xAxis);
        myRecord.set("series", series);

        return myRecord;
    }

    @Override
    public MyRecord getOrderChannel(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();

            List<String> bingXdata = Arrays.asList("公众号", "小程序", "H5", "APP", "支付宝支付", "余额");
            List<String> color = Arrays.asList("#64a1f4", "#3edeb5", "#70869f", "#fc7d6a", "#fc7d6c", "#70869d");

            // 支付渠道"支付渠道(0-微信公众号,1-微信小程序,2-H5,3-余额,4-微信AppIos,5-微信AppIos安卓,6-支付宝支付，7-支付宝app支付)"
            List<String> data = Arrays.asList("0", "1", "2", "3","4", "5");
            List<Map<String, Object>> bingData = new ArrayList<>();

            for (int key = 0; key < data.size(); key++) {
                String item = data.get(key);

                // 增加的积分
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<StoreOrder> queryStoreOrderWrapper = new QueryWrapper<>();
                queryStoreOrderWrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
                queryStoreOrderWrapper.eq("paid", "1");
                queryStoreOrderWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));

                switch (item) {
                    case "0":// 公众号
                        queryStoreOrderWrapper.eq("pay_type", PayConstants.PAY_TYPE_WE_CHAT);
                        queryStoreOrderWrapper.in("is_channel", Collections.singletonList("0"));
                        break;
                    case "1":// 小程序
                        queryStoreOrderWrapper.eq("pay_type", PayConstants.PAY_TYPE_WE_CHAT);
                        queryStoreOrderWrapper.in("is_channel", Collections.singletonList("1"));
                        break;
                    case "2":// H5
                        queryStoreOrderWrapper.eq("pay_type", PayConstants.PAY_TYPE_WE_CHAT);
                        queryStoreOrderWrapper.in("is_channel", Collections.singletonList("2"));
                        break;
                    case "3":// APP
                        queryStoreOrderWrapper.eq("pay_type", PayConstants.PAY_TYPE_WE_CHAT).in("is_channel", Arrays.asList("4","5"))
                                .or()
                                .eq("pay_type", PayConstants.PAY_TYPE_ALI_PAY).in("is_channel", Collections.singletonList("7"));
                        break;
                    case "4":// 支付宝支付
                        queryStoreOrderWrapper.eq("pay_type", PayConstants.PAY_TYPE_ALI_PAY);
                        queryStoreOrderWrapper.in("is_channel", Collections.singletonList("6"));
                        break;
                    default: // 余额
                        queryStoreOrderWrapper.in("is_channel", Collections.singletonList("3"));
                }

                BigDecimal addValue = dao.selectOne(queryStoreOrderWrapper).getPayPrice();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            BigDecimal count = bingData.stream().map(item -> (BigDecimal) item.get("value")).reduce(BigDecimal.ZERO, BigDecimal::add);

            for (Map<String, Object> item : bingData) {
                BigDecimal value = (BigDecimal) item.get("value");
                double percent = count.intValue() != 0 ?
                        value
                                .divide(count, 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }

            list.sort(Comparator.comparing(item -> (BigDecimal)item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取订单来源分析数据失败", ex);
            throw new XlwebException("获取订单来源分析数据失败");
        }
    }

    @Override
    public MyRecord getOrderType(String time) {
        try {
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();
            List<String> color = Arrays.asList("#64a1f4", "#3edeb5", "#3edeb5", "#70869f", "#ffc653", "#ffc653");
            List<String> bingXdata = Arrays.asList("普通订单", "核销订单", "秒杀订单", "砍价订单", "拼团订单", "视频订单");
            List<String> data = Arrays.asList("1", "2", "3", "4", "5", "6");

            List<Map<String, Object>> bingData = new ArrayList<>();
            for (int key = 0; key < data.size(); key++) {
                String orderType = data.get(key);
                // 订单类型分析
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
                SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
                QueryWrapper<StoreOrder> queryStoreOrderWrapper = new QueryWrapper<>();
                queryStoreOrderWrapper.select("IFNULL(sum(pay_price), 0) as pay_price");
                queryStoreOrderWrapper.eq("paid", "1");
                queryStoreOrderWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
                switch (orderType) {
                    case "2":// 核销订单
                        queryStoreOrderWrapper.isNotNull("verify_code");
                        break;
                    case "3":// 秒杀订单
                        queryStoreOrderWrapper.isNotNull("seckill_id");
                        queryStoreOrderWrapper.gt("seckill_id", 0);
                        break;
                    case "4":// 砍价订单
                        queryStoreOrderWrapper.isNotNull("bargain_id");
                        queryStoreOrderWrapper.gt("bargain_id", 0);
                        break;
                    case "5":// 拼团订单
                        queryStoreOrderWrapper.isNotNull("combination_id");
                        queryStoreOrderWrapper.gt("combination_id", 0);
                    case "6":// 视频订单
                        queryStoreOrderWrapper.eq("type", 1);
                        break;
                    default: // 普通订单
                        queryStoreOrderWrapper.isNull("verify_code");
                        queryStoreOrderWrapper.eq("seckill_id", 0);
                        queryStoreOrderWrapper.eq("bargain_id", 0);
                        queryStoreOrderWrapper.eq("combination_id", 0);

                        queryStoreOrderWrapper.ne("type", 1);
                }

                BigDecimal addValue = dao.selectOne(queryStoreOrderWrapper).getPayPrice();

                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", bingXdata.get(key));
                recordData.put("value", addValue);
                Map<String, Object> itemStyle = new HashMap<>();
                itemStyle.put("color", color.get(key));
                recordData.put("itemStyle", itemStyle);
                bingData.add(recordData);
            }

            List<Map<String, Object>> list = new ArrayList<>();
            BigDecimal count = bingData.stream().map(item -> (BigDecimal) item.get("value")).reduce(BigDecimal.ZERO, BigDecimal::add);

            for (Map<String, Object> item : bingData) {
                BigDecimal value = (BigDecimal) item.get("value");
                double percent = count.intValue() != 0 ?
                        value
                                .divide(count, 4, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100))
                                .setScale(1, BigDecimal.ROUND_HALF_UP)
                                .doubleValue() : 0;
                Map<String, Object> recordData = new HashMap<>();
                recordData.put("name", item.get("name"));
                recordData.put("value", value);
                recordData.put("percent", percent);
                list.add(recordData);
            }
            list.sort(Comparator.comparing(item -> (BigDecimal)item.get("value")));

            MyRecord myRecord = new MyRecord();
            myRecord.set("bing_xdata", bingXdata);
            myRecord.set("bing_data", bingData);
            myRecord.set("list", list);

            return myRecord;
        } catch (Exception ex) {
            logger.error("获取订单类型分析数据失败", ex);
            throw new XlwebException("获取订单类型分析数据失败");
        }
    }

    @Override
    public MyRecord getTradetop(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            // 1、计算左侧
            Map<String, Object> left = new HashMap<>();
            left.put("name", "当日订单金额");
            List<String> xAxis = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
            String timeType = "%H";
            // "x": ["00","24"]
            left.put("x", xAxis);
            // 查询当日数据
            QueryWrapper<StoreOrder> queryCurDateWrapper = new QueryWrapper<>();
            queryCurDateWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurDateWrapper.eq("paid", 1);
            queryCurDateWrapper.eq("refund_status", 0);
            Date curData = new Date();
            queryCurDateWrapper.between("update_time", sdfFormat.format(curData), sdfEndFormat.format(curData));
            queryCurDateWrapper.groupBy("days");
            List<Map<String, Object>> curDatePrice = dao.selectMaps(queryCurDateWrapper);
            Map<String, BigDecimal> curDatePriceMap = curDatePrice.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> (BigDecimal)item.get("pay_price"))
                    );
            // 查询昨日数据
            QueryWrapper<StoreOrder> queryCurPreDateWrapper = new QueryWrapper<>();
            queryCurPreDateWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurPreDateWrapper.eq("paid", 1);
            queryCurPreDateWrapper.eq("refund_status", 0);
            Calendar curPreCal = Calendar.getInstance();
            curPreCal.setTime(curData);
            curPreCal.add(Calendar.DAY_OF_MONTH, -1);
            Date curPreData = curPreCal.getTime();
            queryCurPreDateWrapper.between("update_time", sdfFormat.format(curPreData), sdfEndFormat.format(curPreData));
            queryCurPreDateWrapper.groupBy("days");
            List<Map<String, Object>> curPreDatePrice = dao.selectMaps(queryCurPreDateWrapper);
            Map<String, BigDecimal> curPreDatePriceMap = curPreDatePrice.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> (BigDecimal)item.get("pay_price"))
                    );
            // 解析数据
            BigDecimal defaultValue = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            List<Map<String,Object>> curSeries = new ArrayList<>();
            // 计算今天总金额
            Map<String,Object> curSer = new HashMap<>();
            BigDecimal curSerCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            List<BigDecimal> curSerValues  = new ArrayList<>();

            for (String x : xAxis) {
                if (Objects.nonNull(curDatePriceMap.get(x))) {
                    curSerValues.add(curDatePriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
                    curSerCount = curSerCount.add(curDatePriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
                } else {
                    curSerValues.add(defaultValue);
                }
            }
            curSer.put("money", curSerCount);
            curSer.put("value", curSerValues);

            // 计算昨天总金额
            Map<String,Object> curPreSer = new HashMap<>();
            BigDecimal curPreSerCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            List<BigDecimal> curPreSerValues  = new ArrayList<>();
            for (String x : xAxis) {
                if (Objects.nonNull(curPreDatePriceMap.get(x))) {
                    curPreSerValues.add(curPreDatePriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
                    curPreSerCount = curPreSerCount.add(curPreDatePriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
                } else {
                    curPreSerValues.add(defaultValue);
                }
            }
            curPreSer.put("money", curPreSerCount);
            curPreSer.put("value", curPreSerValues);

            curSeries.add(curSer);
            curSeries.add(curPreSer);
            left.put("series", curSeries);

            ///2、计算右侧
            Map<String, Object> right = new HashMap<>();
            Map<String, Object> today = new HashMap<>();
            today.put("x", xAxis); // "x": ["00","24"]
            /**
             * "series": [{
             * 	"name": "今日订单数",
             * 	"now_money": 18,
             * 	"last_money": 7,
             * 	"rate": "157.00",
             * 	"value": [0, 0]
             * }, {
             * 	"name": "今日支付人数",
             * 	"now_money": 10,
             * 	"last_money": 5,
             * 	"rate": "100.00",
             * 	"value": [0, 0]
             * }]
             */
            // 查询当日昨日订单数据
            QueryWrapper<StoreOrder> queryCurDateOrderCountWrapper = new QueryWrapper<>();
            queryCurDateOrderCountWrapper.select("count(id) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurDateOrderCountWrapper.eq("paid", 1);
            queryCurDateOrderCountWrapper.eq("refund_status", 0);
            queryCurDateOrderCountWrapper.between("update_time", sdfFormat.format(curData), sdfEndFormat.format(curData));
            queryCurDateOrderCountWrapper.groupBy("days");
            List<Map<String, Object>> curDateOrderCountList = dao.selectMaps(queryCurDateOrderCountWrapper);
            Map<String, Integer> curDateOrderCountMap = curDateOrderCountList.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> ((Long)item.get("num")).intValue())
                    );



            // 查询当日昨日支付人数数据
            QueryWrapper<StoreOrder> queryCurDatePayCountWrapper = new QueryWrapper<>();
            queryCurDatePayCountWrapper.select("count(distinct uid) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurDatePayCountWrapper.eq("paid", 1);
            queryCurDatePayCountWrapper.eq("refund_status", 0);
            queryCurDatePayCountWrapper.between("update_time", sdfFormat.format(curData), sdfEndFormat.format(curData));
            queryCurDatePayCountWrapper.groupBy("days");
            List<Map<String, Object>> curDatePayCountList = dao.selectMaps(queryCurDatePayCountWrapper);
            Map<String, Integer> curDatePayCountMap = curDatePayCountList.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> ((Long)item.get("num")).intValue())
                    );

            // 计算当日昨日数据
            QueryWrapper<StoreOrder> queryCurPreDateOrderCountWrapper = new QueryWrapper<>();
            queryCurPreDateOrderCountWrapper.select("id");
            queryCurPreDateOrderCountWrapper.eq("paid", 1);
            queryCurPreDateOrderCountWrapper.eq("refund_status", 0);
            queryCurPreDateOrderCountWrapper.between("update_time", sdfFormat.format(curPreData), sdfEndFormat.format(curPreData));
            int curPreDateOrderCount = Math.toIntExact(dao.selectCount(queryCurPreDateOrderCountWrapper));

            QueryWrapper<StoreOrder> queryCurPreDatePayCountWrapper = new QueryWrapper<>();
            queryCurPreDatePayCountWrapper.select("distinct uid");
            queryCurPreDatePayCountWrapper.eq("paid", 1);
            queryCurPreDatePayCountWrapper.eq("refund_status", 0);
            queryCurPreDatePayCountWrapper.between("update_time", sdfFormat.format(curPreData), sdfEndFormat.format(curPreData));
            int curPreDatePayCount = Math.toIntExact(dao.selectCount(queryCurPreDatePayCountWrapper));

            int curDateOrderCount = 0;
            List<Integer> curDateOrderSerValues  = new ArrayList<>();
            for (String x : xAxis) {
                if (Objects.nonNull(curDateOrderCountMap.get(x))) {
                    curDateOrderSerValues.add(curDateOrderCountMap.get(x));
                    curDateOrderCount = curDateOrderCount + curDateOrderCountMap.get(x);
                } else {
                    curDateOrderSerValues.add(0);
                }
            }

            int curDatePayCount = 0;
            List<Integer> curDatePaySerValues  = new ArrayList<>();
            for (String x : xAxis) {
                if (Objects.nonNull(curDatePayCountMap.get(x))) {
                    curDatePaySerValues.add(curDatePayCountMap.get(x));
                    curDatePayCount = curDatePayCount + curDatePayCountMap.get(x);
                } else {
                    curDatePaySerValues.add(0);
                }
            }

            // 使用 DecimalFormat 保留两位小数
            DecimalFormat df = new DecimalFormat("0.00");
            List<Map<String, Object>> toDaySeries = new ArrayList<>();
            Map<String, Object> toDayOrder = new HashMap<>();
            toDayOrder.put("name", "今日订单数");
            toDayOrder.put("now_money", curDateOrderCount);
            toDayOrder.put("last_money", curPreDateOrderCount);
            // 计算环比
            double curPreDateOrderGrowthRate = (((double)(curDateOrderCount - curPreDateOrderCount)) / (curPreDateOrderCount == 0 ? 1 : curPreDateOrderCount)) * 100;
            toDayOrder.put("rate", df.format(curPreDateOrderGrowthRate));
            toDayOrder.put("value", curDateOrderSerValues);

            Map<String, Object> toDayPay = new HashMap<>();
            toDayPay.put("name", "今日支付人数");
            toDayPay.put("now_money", curDatePayCount);
            toDayPay.put("last_money", curPreDatePayCount);
            // 计算环比
            double curPreDatePayGrowthRate = (((double)(curDatePayCount - curPreDatePayCount)) / (curPreDatePayCount == 0 ? 1 : curPreDatePayCount)) * 100;
            toDayPay.put("rate", df.format(curPreDatePayGrowthRate));
            toDayPay.put("value", curDatePaySerValues);

            toDaySeries.add(toDayOrder);
            toDaySeries.add(toDayPay);
            today.put("series", toDaySeries);
            right.put("today", today);
            /**
             * "month": [{
             * 	"name": "本月订单数",
             * 	"now_money": 153,
             * 	"last_money": 172,
             * 	"rate": "-11.00",
             * 	"value": {
             * 		"2025-02-01": 0,
             * 		"2025-02-28": 0
             *        }
             * }, {
             * 	"name": "本月支付人数",
             * 	"now_money": 93,
             * 	"last_money": 90,
             * 	"rate": "3.00",
             * 	"value": {
             * 		"2025-02-01": 0,
             * 		"2025-02-28": 0
             *    }
             * }]
             */
            List<Map<String, Object>> month = new ArrayList<>();
            timeType = "%Y-%m-%d";
            // 查询当月订单数据
            QueryWrapper<StoreOrder> queryCurMonthOrderCountWrapper = new QueryWrapper<>();
            queryCurMonthOrderCountWrapper.select("count(id) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurMonthOrderCountWrapper.eq("paid", 1);
            queryCurMonthOrderCountWrapper.eq("refund_status", 0);
            Date curMonthStart = sdf.parse(DateUtil.nowDateTime(Constants.DATE_FORMAT_MONTH_START));
            Date curMonthEnd = sdf.parse(DateUtil.getMonthEndDay());
            queryCurMonthOrderCountWrapper.between("update_time", sdfFormat.format(curMonthStart), sdfEndFormat.format(curMonthEnd));
            queryCurMonthOrderCountWrapper.groupBy("days");
            List<Map<String, Object>> curMonthOrderCountList = dao.selectMaps(queryCurMonthOrderCountWrapper);
            Map<String, Integer> curMonthOrderCountMap = curMonthOrderCountList.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> ((Long)item.get("num")).intValue())
                    );

            // 查询上月支付人数数据
            QueryWrapper<StoreOrder> queryCurMonthPayCountWrapper = new QueryWrapper<>();
            queryCurMonthPayCountWrapper.select("count(distinct uid) as num, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryCurMonthPayCountWrapper.eq("paid", 1);
            queryCurMonthPayCountWrapper.eq("refund_status", 0);
            Date preMonthStart = sdf.parse(DateUtil.getLastMonthStartDay());
            Date preMonthEnd = sdf.parse(DateUtil.getLastMonthEndDay());
            queryCurMonthPayCountWrapper.between("update_time", sdfFormat.format(preMonthStart), sdfEndFormat.format(preMonthEnd));
            queryCurMonthPayCountWrapper.groupBy("days");
            List<Map<String, Object>> curMonthPayCountList = dao.selectMaps(queryCurMonthPayCountWrapper);
            Map<String, Integer> curMonthPayCountMap = curMonthPayCountList.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> ((Long)item.get("num")).intValue())
                    );

            // 计算当月上月数据
            QueryWrapper<StoreOrder> queryCurPreMonthOrderCountWrapper = new QueryWrapper<>();
            queryCurPreMonthOrderCountWrapper.select("id");
            queryCurPreMonthOrderCountWrapper.eq("paid", 1);
            queryCurPreMonthOrderCountWrapper.eq("refund_status", 0);
            queryCurPreMonthOrderCountWrapper.between("update_time", sdfFormat.format(preMonthStart), sdfEndFormat.format(preMonthEnd));
            int curPreMonthOrderCount = Math.toIntExact(dao.selectCount(queryCurPreMonthOrderCountWrapper));

            QueryWrapper<StoreOrder> queryCurPreMonthPayCountWrapper = new QueryWrapper<>();
            queryCurPreMonthPayCountWrapper.select("distinct uid");
            queryCurPreMonthPayCountWrapper.eq("paid", 1);
            queryCurPreMonthPayCountWrapper.eq("refund_status", 0);
            queryCurPreMonthPayCountWrapper.between("update_time", sdfFormat.format(preMonthStart), sdfEndFormat.format(preMonthEnd));
            int curPreMonthPayCount = Math.toIntExact(dao.selectCount(queryCurPreMonthPayCountWrapper));

            int curMonthOrderCount = 0;
            List<String> curMonthSerValues  = new ArrayList<>();
            Calendar curCalMonthOrder = Calendar.getInstance();
            curCalMonthOrder.setTime(curMonthStart);
            while (!curCalMonthOrder.getTime().after(curMonthEnd)) {
                curMonthSerValues.add(new SimpleDateFormat("yyyy-MM-dd").format(curCalMonthOrder.getTime()));
                curCalMonthOrder.add(Calendar.DAY_OF_MONTH, 1);
            }
            for (String x : curMonthSerValues) {
                if (Objects.nonNull(curMonthOrderCountMap.get(x))) {
                    curMonthOrderCount = curMonthOrderCount + curMonthOrderCountMap.get(x);
                }
            }

            int curMonthPayCount = 0;
            for (String x : curMonthSerValues) {
                if (Objects.nonNull(curMonthPayCountMap.get(x))) {
                    curMonthPayCount = curMonthPayCount + curMonthPayCountMap.get(x);
                }
            }

            // 使用 DecimalFormat 保留两位小数
            Map<String, Object> toMonthOrder = new HashMap<>();
            toMonthOrder.put("name", "本月订单数");
            toMonthOrder.put("now_money", curMonthOrderCount);
            toMonthOrder.put("last_money", curPreMonthOrderCount);
            // 计算环比
            double curPreMonthOrderGrowthRate = (((double)(curMonthOrderCount - curPreMonthOrderCount)) / (curPreMonthOrderCount == 0 ? 1 : curPreMonthOrderCount)) * 100;
            toMonthOrder.put("rate", df.format(curPreMonthOrderGrowthRate));
            toMonthOrder.put("value", curMonthSerValues);

            Map<String, Object> toMonthPay = new HashMap<>();
            toMonthPay.put("name", "本月支付人数");
            toMonthPay.put("now_money", curMonthPayCount);
            toMonthPay.put("last_money", curPreMonthPayCount);
            // 计算环比
            double curPreMonthPayGrowthRate = (((double)(curMonthPayCount - curPreMonthPayCount)) / (curPreMonthPayCount == 0 ? 1 : curPreMonthPayCount)) * 100;
            toMonthPay.put("rate", df.format(curPreMonthPayGrowthRate));
            toMonthPay.put("value", curMonthSerValues);

            month.add(toMonthOrder);
            month.add(toMonthPay);
            right.put("month", month);

            MyRecord myRecord = new MyRecord();
            myRecord.set("left", left);
            myRecord.set("right", right);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取今天交易数据失败", ex);
            throw new XlwebException("获取今天交易数据失败");
        }
    }

    @Override
    public MyRecord getTradeBottom(String time) {
        try {
            List<Map<String, Object>> series = new ArrayList<>();
            String[] times = time.split("-");
            if (times.length != 2) {
                throw new IllegalArgumentException("请选择时间");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date startDate = sdf.parse(times[0]);
            // 结束日减去1
            Date endDate = sdf.parse(times[1]);
            Calendar cal =  Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            endDate = cal.getTime();
            // 生成xAxis
            List<String> xAxis = xAixs(startDate, endDate);
            String timeType = timeType(startDate, endDate);

            // 1、余额支付金额 "type": 0, "desc": "用户下单时使用余额实际支付的金额"
            QueryWrapper<StoreOrder> queryYuePriceWrapper = new QueryWrapper<>();
            queryYuePriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryYuePriceWrapper.eq("paid", 1);
            queryYuePriceWrapper.eq("pay_type", PayConstants.PAY_TYPE_YUE);
            series.add(bottomOrderPayItem("余额支付金额", "用户下单时使用余额实际支付的金额", 0, xAxis, timeType, startDate, endDate, queryYuePriceWrapper));

            //2、商品退款金额 "type": 0, "desc": "用户成功退款的商品金额"
            QueryWrapper<StoreOrder> queryRefundPriceWrapper = new QueryWrapper<>();
            queryRefundPriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryRefundPriceWrapper.eq("paid", 1);
            queryRefundPriceWrapper.eq("refund_status", 2);
            series.add(bottomOrderPayItem("商品退款金额", "用户成功退款的商品金额", 0, xAxis, timeType, startDate, endDate, queryRefundPriceWrapper));

            //3、支出金额 "type": 1, "desc": "余额支付金额、商品退款成功金额"
            QueryWrapper<StoreOrder> queryOutPriceWrapper = new QueryWrapper<>();
            queryOutPriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryOutPriceWrapper.eq("paid", 1);
            queryOutPriceWrapper.eq("refund_status", 2).or().eq("pay_type", PayConstants.PAY_TYPE_YUE);
            series.add(bottomOrderPayItem("支出金额", "余额支付金额、商品退款成功金额", 1, xAxis, timeType, startDate, endDate, queryOutPriceWrapper));

            //4、充值金额 "type": 1, "desc": "选定条件下，用户成功充值的金额，用户充值、后台充值"
            // 先查询环比数据
            Map<String, Date> preDates = preDates(timeType, startDate, endDate);
            Date preStartDate = preDates.get("preStartDate");
            Date preEndDate = preDates.get("preEndDate");

            List<Map<String, Object>> preYueIncome = userBillApi.listMaps(timeType, preStartDate, preEndDate).getCheckedData();
            Map<String, BigDecimal> preYueIncomeMap = preYueIncome.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> BigDecimal.valueOf((Double)item.get("number")))
                    );

            // 在查询当前数据

            List<Map<String, Object>> curYueIncome = userBillApi.listMaps(timeType, startDate, endDate).getCheckedData();
            Map<String, BigDecimal> curYueIncomeMap = curYueIncome.stream()
                    .collect(Collectors.toMap(
                            item -> (String) item.get("days"),
                            item -> BigDecimal.valueOf((Double) item.get("number")))
                    );
            // 解析数据
            BigDecimal curYueIncomeCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            BigDecimal preYueIncomeCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
            List<BigDecimal> curYueIncomeValues  = new ArrayList<>();
            List<String> xAxisPre =  xAixs(preStartDate, preEndDate);
            for (String preX : xAxisPre) {
                if (Objects.nonNull(preYueIncomeMap.get(preX))) {
                    preYueIncomeCount = preYueIncomeCount.add(preYueIncomeMap.get(preX).setScale(2, RoundingMode.HALF_UP));
                }
            }
            for (String x : xAxis) {
                if (Objects.nonNull(curYueIncomeMap.get(x))) {
                    curYueIncomeValues.add(curYueIncomeMap.get(x).setScale(2, RoundingMode.HALF_UP));
                    curYueIncomeCount = curYueIncomeCount.add(curYueIncomeMap.get(x).setScale(2, RoundingMode.HALF_UP));
                } else {
                    curYueIncomeValues.add(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP));
                }
            }
            Map<String, Object> curYueIncomeItem = new HashMap<>();
            curYueIncomeItem.put("name", "充值金额");
            curYueIncomeItem.put("desc", "选定条件下，用户成功充值的金额，用户充值、后台充值");
            curYueIncomeItem.put("money", curYueIncomeCount);
            curYueIncomeItem.put("pre_money", preYueIncomeCount);
            curYueIncomeItem.put("type", 1);
            curYueIncomeItem.put("rate", curYueIncomeCount.subtract(preYueIncomeCount).divide(preYueIncomeCount.intValue() == 0 ? new BigDecimal(1) : preYueIncomeCount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            curYueIncomeItem.put("value", curYueIncomeValues);
            series.add(curYueIncomeItem);

            //5、商品支付金额 "type": 1, "desc": "选定条件下，用户购买商品的实际支付金额，包括微信支付、余额支付、支付宝支付"
            QueryWrapper<StoreOrder> queryPayPriceWrapper = new QueryWrapper<>();
            queryPayPriceWrapper.select("IFNULL(sum(pay_price), 0) as pay_price, DATE_FORMAT(update_time, '"+ timeType +"') as days");
            queryPayPriceWrapper.eq("paid", 1);
            Map<String, Object> payItem = bottomOrderPayItem("商品支付金额", "选定条件下，用户购买商品的实际支付金额，包括微信支付、余额支付、支付宝支付", 1, xAxis, timeType, startDate, endDate, queryPayPriceWrapper);
            BigDecimal payPriceMoney =  (BigDecimal)payItem.get("money");
            BigDecimal payPreMoney =  (BigDecimal)payItem.get("pre_money");
            List<BigDecimal> payPriceValues = (List<BigDecimal>)payItem.get("value");
            series.add(payItem);

            //6、营业额 "type": 1, "desc": "商品支付金额、充值金额"
            List<BigDecimal> curYYEValues  = new ArrayList<>();
            BigDecimal curYYECount = curYueIncomeCount.add(payPriceMoney);
            BigDecimal preYYECount = preYueIncomeCount.add(payPreMoney);
            for(int i = 0 ; i < xAxis.size(); i++) {
                curYYEValues.add(i, payPriceValues.get(i).add(curYueIncomeValues.get(i)));
            }
            Map<String, Object> curYYEItem = new HashMap<>();
            curYYEItem.put("name", "营业额");
            curYYEItem.put("desc", "商品支付金额、充值金额");
            curYYEItem.put("money", curYueIncomeCount.add(payPriceMoney));
            curYYEItem.put("type", 1);
            curYYEItem.put("rate", curYYECount.subtract(preYYECount).divide(preYYECount.intValue() == 0 ? new BigDecimal(1) : preYYECount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
            curYYEItem.put("value", curYYEValues);
            series.add(curYYEItem);

            MyRecord myRecord = new MyRecord();
            myRecord.set("x", xAxis);
            myRecord.set("series", series);
            return myRecord;
        } catch (Exception ex) {
            logger.error("获取交易概括数据失败", ex);
            throw new XlwebException("获取交易概括数据失败");
        }
    }

    String timeType(Date startDate, Date endDate) {
        long dayCount = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        int num;
        if (dayCount == 1) {
            num = 0;
        } else if (dayCount > 1 && dayCount <= 31) {
            num = 1;
        } else if (dayCount > 31 && dayCount <= 92) {
            num = 3;
        } else {
            num = 30;
        }

        String timeType = "%Y-%m";
        if (num == 0) {
            timeType = "%H";
        } else if(num == 30) {
            timeType = "%Y-%m";
        } else {
            timeType = "%Y-%m-%d";
        }
        return timeType;
    }

    List<String> xAixs(Date startDate, Date endDate) {
        List<String> xAxis = new ArrayList<>();
        // 生成xAxis
        long dayCount = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000) + 1;
        int num;
        if (dayCount == 1) {
            num = 0;
        } else if (dayCount > 1 && dayCount <= 31) {
            num = 1;
        } else if (dayCount > 31 && dayCount <= 92) {
            num = 3;
        } else {
            num = 30;
        }

        if (num == 0) {
            xAxis = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            while (!cal.getTime().after(endDate)) {
                if (num == 30) {
                    xAxis.add(new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
                    cal.add(Calendar.MONTH, 1);
                } else {
                    xAxis.add(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
                    cal.add(Calendar.DAY_OF_MONTH, num);
                }
            }
        }
        return xAxis;
    }

    Map<String, Date> preDates(String timeType, Date startDate, Date endDate) {
        Date preStartDate = startDate;
        Date preEndDate = endDate;
        Calendar preStartCal = Calendar.getInstance();
        preStartCal.setTime(startDate);
        Calendar preEndCal = Calendar.getInstance();
        preEndCal.setTime(endDate);
        if ("%H".equals(timeType)) {
            // "%H"
            // 前一天
            preStartCal.add(Calendar.DAY_OF_MONTH, -1);
            preStartDate = preStartCal.getTime();
            preEndCal.add(Calendar.DAY_OF_MONTH, -1);
            preEndDate = preEndCal.getTime();
        } else if ("%Y-%m".equals(timeType)) {
            // "%Y-%m"
            // 去年
            preStartCal.add(Calendar.YEAR, -1);
            preStartDate = preStartCal.getTime();
            preEndCal.add(Calendar.YEAR, -1);
            preEndDate = preEndCal.getTime();
        } else {
            // "%Y-%m-%d"
            // 上一月
            preStartCal.add(Calendar.MONTH, -1);
            preStartDate = preStartCal.getTime();
            preEndCal.add(Calendar.MONTH, -1);
            preEndDate = preEndCal.getTime();
        }
        Map<String, Date> result = new HashMap<>();
        result.put("preStartDate", preStartDate);
        result.put("preEndDate", preEndDate);
        return result;
    }

    /**
     * 合并两个 QueryWrapper，使用 AND 逻辑连接
     * @param source 第一个 QueryWrapper
     * @param target 第二个 QueryWrapper
     * @return 合并后的 QueryWrapper
     */
    public static <T> QueryWrapper<T> mergeQueryWrappers(QueryWrapper<T> source, QueryWrapper<T> target) {
        // 合并条件，使用 AND 逻辑连接
        target.and(wrapper -> wrapper.apply(source.getSqlSegment()));

        return target;
    }

    Map<String,Object> bottomOrderPayItem(String name, String desc, Integer type, List<String> xAxis, String timeType, Date startDate, Date endDate, QueryWrapper<StoreOrder> queryPriceWrapper) {

        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdfEndFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        BigDecimal defaultValue = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        // 先查询环比数据
        Map<String, Date> preDates = preDates(timeType, startDate, endDate);
        Date preStartDate = preDates.get("preStartDate");
        Date preEndDate = preDates.get("preEndDate");

        QueryWrapper<StoreOrder> queryPrePriceWrapper = new QueryWrapper<>();
        queryPrePriceWrapper.select(queryPriceWrapper.getSqlSelect());
        queryPrePriceWrapper.between("update_time", sdfFormat.format(preStartDate), sdfEndFormat.format(preEndDate));
        queryPrePriceWrapper = mergeQueryWrappers(queryPriceWrapper, queryPrePriceWrapper);
        queryPrePriceWrapper.groupBy("days");
        List<Map<String, Object>> prePrice = dao.selectMaps(queryPrePriceWrapper);
        Map<String, BigDecimal> prePriceMap = prePrice.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> (BigDecimal)item.get("pay_price"))
                );

        // 再查询当前数据
        queryPriceWrapper.between("update_time", sdfFormat.format(startDate), sdfEndFormat.format(endDate));
        queryPrePriceWrapper.groupBy("days");
        List<Map<String, Object>> curPrice = dao.selectMaps(queryPriceWrapper);
        Map<String, BigDecimal> curPriceMap = curPrice.stream()
                .collect(Collectors.toMap(
                        item -> (String) item.get("days"),
                        item -> (BigDecimal)item.get("pay_price"))
                );
        // 解析数据
        BigDecimal curPriceCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        BigDecimal prePriceCount = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        List<BigDecimal> curPriceValues  = new ArrayList<>();
        List<String> xAxisPre = xAixs(preStartDate, preEndDate);
        for (String preX : xAxisPre) {
            if (Objects.nonNull(prePriceMap.get(preX))) {
                prePriceCount = prePriceCount.add(prePriceMap.get(preX).setScale(2, RoundingMode.HALF_UP));
            }
        }

        for (String x : xAxis) {
            if (Objects.nonNull(curPriceMap.get(x))) {
                curPriceValues.add(curPriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
                curPriceCount = curPriceCount.add(curPriceMap.get(x).setScale(2, RoundingMode.HALF_UP));
            } else {
                curPriceValues.add(defaultValue);
            }
        }
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("desc", desc);
        item.put("money", curPriceCount);
        item.put("pre_money", prePriceCount);
        item.put("type", type);
        item.put("rate", curPriceCount.subtract(prePriceCount).divide(prePriceCount.intValue() == 0 ? new BigDecimal(1) : prePriceCount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
        item.put("value", curPriceValues);

        return item;
    }

}

