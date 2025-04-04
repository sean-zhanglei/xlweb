package com.nbug.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.common.model.order.StoreOrder;
import com.nbug.common.request.StoreOrderRefundRequest;


/**
 * StoreOrderRefundService 接口

 */
public interface StoreOrderRefundService extends IService<StoreOrder> {

    void refund(StoreOrderRefundRequest request, StoreOrder storeOrder);
}
