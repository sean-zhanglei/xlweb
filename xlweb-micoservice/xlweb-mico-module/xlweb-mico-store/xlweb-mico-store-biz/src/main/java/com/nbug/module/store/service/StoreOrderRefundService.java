package com.nbug.module.store.service1;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.request.StoreOrderRefundRequest;


/**
 * StoreOrderRefundService 接口

 */
public interface StoreOrderRefundService extends IService<StoreOrder> {

    void refund(StoreOrderRefundRequest request, StoreOrder storeOrder);
}
