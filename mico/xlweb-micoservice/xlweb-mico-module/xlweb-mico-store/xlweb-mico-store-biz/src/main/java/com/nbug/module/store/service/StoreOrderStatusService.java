package com.nbug.module.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.order.StoreOrderStatus;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderStatusSearchRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * StoreOrderStatusService 接口

 */
public interface StoreOrderStatusService extends IService<StoreOrderStatus> {

    /**
     * 订单操作记录列表
     * @param request 请求参数
     * @param pageParamRequest 分页参数
     * @return List
     */
    List<StoreOrderStatus> getList(StoreOrderStatusSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 保存订单退款记录
     * @param orderId 订单id
     * @param amount 金额
     * @param message 备注
     * @return Boolean
     */
    Boolean saveRefund(Integer orderId, BigDecimal amount, String message);

    /**
     * 添加订单日志
     * @param orderId 订单id
     * @param type 类型
     * @param message 备注
     * @return Boolean
     */
    Boolean createLog(Integer orderId, String type, String message);

    /**
     * 根据实体参数获取
     * @param storeOrderStatus 订单状态参数
     * @return 订单状态结果
     */
    List<StoreOrderStatus> getByEntity(StoreOrderStatus storeOrderStatus);

    /**
     * 根据订单id获取最后一条记录
     * @param orderId 订单id
     * @return StoreOrderStatus
     */
    StoreOrderStatus getLastByOrderId(Integer orderId);

    /**
     * 通过日期获取订单退款数量
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    Integer getRefundOrderNumByDate(String date);

    /**
     * 通过日期获取订单退款金额
     * @param date 日期，yyyy-MM-dd格式
     * @return BigDecimal
     */
    BigDecimal getRefundOrderAmountByDate(String date);
}
