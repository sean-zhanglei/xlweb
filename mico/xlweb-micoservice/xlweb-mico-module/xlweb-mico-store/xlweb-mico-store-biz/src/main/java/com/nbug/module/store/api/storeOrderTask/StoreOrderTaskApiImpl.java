package com.nbug.module.store.api.storeOrderTask;

import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreOrderTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreOrderTaskApiImpl implements StoreOrderTaskApi {

    @Resource
    private StoreOrderTaskService storeOrderTaskService;

    /**
     * 用户取消订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    public CommonResult<Boolean> cancelByUser(StoreOrder storeOrder) {
        return success(storeOrderTaskService.cancelByUser(storeOrder));
    }


    /**
     * 订单退款处理
     * 退款得时候根据userBill 来进行回滚
     */
    @Override
    public CommonResult<Boolean> refundOrder(StoreOrder storeOrder) {
        return success(storeOrderTaskService.refundOrder(storeOrder));
    }

    /**
     * 完成订单
     * @author Mr.Zhang
     * @since 2020-07-09
     */
    @Override
    public CommonResult<Boolean> complete(StoreOrder storeOrder) {
        return success(storeOrderTaskService.complete(storeOrder));
    }

    /**
     * 订单收货task处理
     * @param orderId 订单id
     * @return Boolean
     * 1.写订单日志
     * 2.分佣-佣金进入冻结期
     */
    @Override
    public CommonResult<Boolean> orderReceiving(Integer orderId) {
        return success(storeOrderTaskService.orderReceiving(orderId));
    }


    /**
     * 超时未支付系统自动取消
     */
    @Override
    public CommonResult<Boolean> autoCancel(StoreOrder storeOrder) {
        return success(storeOrderTaskService.autoCancel(storeOrder));
    }
}
