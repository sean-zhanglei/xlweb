package com.nbug.service.service;


import com.nbug.common.model.order.StoreOrder;

/**
 * 订单任务服务

 */
public interface StoreOrderTaskService {

    Boolean cancelByUser(StoreOrder storeOrder);

    Boolean complete(StoreOrder storeOrder);

    Boolean refundOrder(StoreOrder storeOrder);

    Boolean autoCancel(StoreOrder storeOrder);

    Boolean orderReceiving(Integer orderId);
}
