package com.nbug.module.order.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.TaskConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.order.StoreOrderStatus;
import com.nbug.mico.common.model.product.StoreProductReply;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.module.order.service.OrderPayService;
import com.nbug.module.order.service.OrderTaskService;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeOrderInfo.StoreOrderInfoApi;
import com.nbug.module.store.api.storeOrderStatus.StoreOrderStatusApi;
import com.nbug.module.store.api.storeOrderTask.StoreOrderTaskApi;
import com.nbug.module.store.api.storeProductReply.StoreProductReplyApi;
import com.nbug.module.user.api.user.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * StoreOrderServiceImpl 接口实现

 */
@Service
public class OrderTaskServiceImpl implements OrderTaskService {
    //日志
    private static final Logger logger = LoggerFactory.getLogger(OrderTaskServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StoreOrderTaskApi storeOrderTaskApi;

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private StoreOrderStatusApi storeOrderStatusApi;

    @Autowired
    private StoreOrderInfoApi storeOrderInfoApi;

    @Autowired
    private StoreProductReplyApi storeProductReplyApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OrderPayService orderPayService;

    /**
     * 用户取消订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    public void cancelByUser() {
        String redisKey = Constants.ORDER_TASK_REDIS_KEY_AFTER_CANCEL_BY_USER;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.cancelByUser | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object data = redisUtil.getRightPop(redisKey, 10L);
            if (null == data) {
                continue;
            }
            try {
//                StoreOrder storeOrder = getJavaBeanStoreOrder(data);
                StoreOrder storeOrder = storeOrderApi.getById(Integer.valueOf(data.toString())).getCheckedData();
                boolean result = storeOrderTaskApi.cancelByUser(storeOrder).getCheckedData();
                if (!result) {
                    redisUtil.lPush(redisKey, data);
                }
            } catch (Exception e) {
                redisUtil.lPush(redisKey, data);
            }
        }
    }

    private StoreOrder getJavaBeanStoreOrder(Object data) {
        return JSONObject.toJavaObject(JSONObject.parseObject(data.toString()), StoreOrder.class);
    }

    /**
     * 执行 用户退款申请
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    public void refundApply() {
        String redisKey = Constants.ORDER_TASK_REDIS_KEY_AFTER_REFUND_BY_USER;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.refundApply | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object orderId = redisUtil.getRightPop(redisKey, 10L);
            if (null == orderId) {
                continue;
            }
            try {
                StoreOrder storeOrder = storeOrderApi.getById(Integer.valueOf(orderId.toString())).getCheckedData();
                if (ObjectUtil.isNull(storeOrder)) {
                    throw new XlwebException("订单不存在,orderNo = " + orderId);
                }
//                boolean result = storeOrderTaskApi.refundApply(storeOrder);
                boolean result = storeOrderTaskApi.refundOrder(storeOrder).getCheckedData();
                if (!result) {
                    logger.error("订单退款错误：result = " + result);
                    redisUtil.lPush(redisKey, orderId);
                }
            } catch (Exception e) {
                logger.error("订单退款错误：" + e.getMessage());
                redisUtil.lPush(redisKey, orderId);
            }
        }
    }

    /**
     * 完成订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    public void complete() {
        String redisKey = Constants.ORDER_TASK_REDIS_KEY_AFTER_COMPLETE_BY_USER;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.complete | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object data = redisUtil.getRightPop(redisKey, 10L);
            if (null == data) {
                continue;
            }
            try {
                StoreOrder storeOrder = getJavaBeanStoreOrder(data);
                boolean result = storeOrderTaskApi.complete(storeOrder).getCheckedData();
                if (!result) {
                    redisUtil.lPush(redisKey, data);
                }
            } catch (Exception e) {
                redisUtil.lPush(redisKey, data);
            }
        }
    }

    /**
     * 订单支付成功后置处理
     */
    @Override
    public void orderPaySuccessAfter() {
        String redisKey = TaskConstants.ORDER_TASK_PAY_SUCCESS_AFTER;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.orderPaySuccessAfter | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object data = redisUtil.getRightPop(redisKey, 10L);
            if (ObjectUtil.isNull(data)) {
                continue;
            }
            try {
                StoreOrder storeOrder = storeOrderApi.getByOderId(String.valueOf(data)).getCheckedData();
                if (ObjectUtil.isNull(storeOrder)) {
                    logger.error("OrderTaskServiceImpl.orderPaySuccessAfter | 订单不存在，orderNo: " + data);
                    throw new XlwebException("订单不存在，orderNo: " + data);
                }
                boolean result = orderPayService.paySuccess(storeOrder);
                if (!result) {
                    redisUtil.lPush(redisKey, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                redisUtil.lPush(redisKey, data);
            }
        }
    }

    /**
     * 自动取消未支付订单
     */
    @Override
    public void autoCancel() {
        String redisKey = Constants.ORDER_AUTO_CANCEL_KEY;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.autoCancel | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object data = redisUtil.getRightPop(redisKey, 10L);
            if (null == data) {
                continue;
            }
            try {
                StoreOrder storeOrder = storeOrderApi.getByOderId(String.valueOf(data)).getCheckedData();
                if (ObjectUtil.isNull(storeOrder)) {
                    logger.error("OrderTaskServiceImpl.autoCancel | 订单不存在，orderNo: " + data);
                    throw new XlwebException("订单不存在，orderNo: " + data);
                }
                boolean result = storeOrderTaskApi.autoCancel(storeOrder).getCheckedData();
                if (!result) {
                    redisUtil.lPush(redisKey, data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                redisUtil.lPush(redisKey, data);
            }
        }
    }

    /**
     * 订单收货
     */
    @Override
    public void orderReceiving() {
        String redisKey = TaskConstants.ORDER_TASK_REDIS_KEY_AFTER_TAKE_BY_USER;
        Long size = redisUtil.getListSize(redisKey);
        logger.info("OrderTaskServiceImpl.orderReceiving | size:" + size);
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            //如果10秒钟拿不到一个数据，那么退出循环
            Object id = redisUtil.getRightPop(redisKey, 10L);
            if (null == id) {
                continue;
            }
            try {
                Boolean result = storeOrderTaskApi.orderReceiving(Integer.valueOf(id.toString())).getCheckedData();
                if (!result) {
                    redisUtil.lPush(redisKey, id);
                }
            } catch (Exception e) {
                redisUtil.lPush(redisKey, id);
            }
        }
    }

    /**
     * 订单自动完成
     */
    @Override
    public void autoComplete() {
        // 查找所有收获状态订单
        List<StoreOrder> orderList = storeOrderApi.findIdAndUidListByReceipt().getCheckedData();
        if (CollUtil.isEmpty(orderList)) {
            return ;
        }
        logger.info("OrderTaskServiceImpl.autoComplete | size:0");

        // 根据订单状态表判断订单是否可以自动完成
        for (StoreOrder order : orderList) {
            StoreOrderStatus orderStatus = storeOrderStatusApi.getLastByOrderId(order.getId()).getCheckedData();
            if (!"user_take_delivery".equals(orderStatus.getChangeType())) {
                logger.error("订单自动完成：订单记录最后一条不是收货状态，orderId = " + order.getId());
                continue ;
            }
            // 判断是否到自动完成时间（收货时间向后偏移7天）
            String comTime = DateUtil.addDay(orderStatus.getCreateTime(), 7, Constants.DATE_FORMAT);
            int compareDate = DateUtil.compareDate(comTime, DateUtil.nowDateTime(Constants.DATE_FORMAT), Constants.DATE_FORMAT);
            if (compareDate < 0) {
                continue ;
            }

            /**
             * ---------------
             * 自动好评转完成
             * ---------------
             */
            // 获取订单详情
            List<StoreOrderInfoOldVo> orderInfoVoList = storeOrderInfoApi.getOrderListByOrderId(order.getId()).getCheckedData();
            if (CollUtil.isEmpty(orderInfoVoList)) {
                logger.error("订单自动完成：无订单详情数据，orderId = " + order.getId());
                continue;
            }
            List<StoreProductReply> replyList = CollUtil.newArrayList();
            User user = userApi.getById(order.getUid()).getCheckedData();
            // 生成评论
            for (StoreOrderInfoOldVo orderInfo : orderInfoVoList) {
                // 判断是否已评论
                if (orderInfo.getInfo().getIsReply().equals(1)) {
                    continue;
                }
                String replyType = Constants.STORE_REPLY_TYPE_PRODUCT;
//                if (ObjectUtil.isNotNull(orderInfo.getInfo().getSeckillId()) && orderInfo.getInfo().getSeckillId() > 0) {
//                    replyType = Constants.STORE_REPLY_TYPE_SECKILL;
//                }
//                if (ObjectUtil.isNotNull(orderInfo.getInfo().getBargainId()) && orderInfo.getInfo().getBargainId() > 0) {
//                    replyType = Constants.STORE_REPLY_TYPE_BARGAIN;
//                }
//                if (ObjectUtil.isNotNull(orderInfo.getInfo().getCombinationId()) && orderInfo.getInfo().getCombinationId() > 0) {
//                    replyType = Constants.STORE_REPLY_TYPE_PINTUAN;
//                }
                StoreProductReply reply = new StoreProductReply();
                reply.setUid(order.getUid());
                reply.setOid(order.getId());
                reply.setProductId(orderInfo.getProductId());
                reply.setUnique(orderInfo.getUnique());
                reply.setReplyType(replyType);
                reply.setProductScore(5);
                reply.setServiceScore(5);
                reply.setComment("");
                reply.setPics("");
                reply.setNickname(user.getNickname());
                reply.setAvatar(user.getAvatar());
                reply.setSku(orderInfo.getInfo().getSku());
                reply.setCreateTime(DateUtil.nowDateTime());
                replyList.add(reply);
            }
            order.setStatus(Constants.ORDER_STATUS_INT_COMPLETE);
            Boolean execute = transactionTemplate.execute(e -> {
                storeOrderApi.updateById(order);
                storeProductReplyApi.saveBatch(replyList);
                return Boolean.TRUE;
            });
            if (execute) {
                redisUtil.lPush(Constants.ORDER_TASK_REDIS_KEY_AFTER_COMPLETE_BY_USER, order.getId());
            } else {
                logger.error("订单自动完成：更新数据库失败，orderId = " + order.getId());
            }
        }
    }
}
