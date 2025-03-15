package com.nbug.module.order.service;


import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.request.OrderPayRequest;
import com.nbug.mico.common.response.OrderPayResultResponse;

/**
 * 订单支付

 */
public interface OrderPayService{

    /**
     * 支付成功处理
     * @param storeOrder 订单
     */
    Boolean paySuccess(StoreOrder storeOrder);

    /**
     * 订单支付
     * @param orderPayRequest 支付参数
     * @param ip    ip
     * @return OrderPayResultResponse
     */
    OrderPayResultResponse payment(OrderPayRequest orderPayRequest, String ip);
}
