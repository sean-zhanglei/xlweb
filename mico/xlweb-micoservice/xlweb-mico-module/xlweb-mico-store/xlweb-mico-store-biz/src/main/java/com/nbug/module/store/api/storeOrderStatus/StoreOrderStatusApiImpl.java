package com.nbug.module.store.api.storeOrderStatus;

import com.nbug.mico.common.model.order.StoreOrderStatus;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreOrderStatusService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreOrderStatusApiImpl implements StoreOrderStatusApi {

    @Resource
    private StoreOrderStatusService storeOrderStatusService;

    /**
     * 添加订单日志
     * @param orderId 订单id
     * @param type 类型
     * @param message 备注
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> createLog(Integer orderId, String type, String message) {
        return success(storeOrderStatusService.createLog(orderId, type, message));
    }

    /**
     * 根据实体参数获取
     * @param storeOrderStatus 订单状态参数
     * @return 订单状态结果
     */
    @Override
    public CommonResult<List<StoreOrderStatus>> getByEntity(StoreOrderStatus storeOrderStatus) {
        return success(storeOrderStatusService.getByEntity(storeOrderStatus));
    }

    /**
     * 根据订单id获取最后一条记录
     * @param orderId 订单id
     * @return StoreOrderStatus
     */
    @Override
    public CommonResult<StoreOrderStatus> getLastByOrderId(Integer orderId) {
        return success(storeOrderStatusService.getLastByOrderId(orderId));
    }

}
