package com.nbug.module.store.api.storeOrderInfo;

import com.nbug.mico.common.model.order.StoreOrderInfo;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.mico.common.vo.StoreOrderInfoVo;
import com.nbug.module.store.service.StoreOrderInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreOrderInfoApiImpl implements StoreOrderInfoApi {

    @Resource
    private StoreOrderInfoService storeOrderInfoService;

    @Override
    public CommonResult<List<StoreOrderInfoOldVo>> getOrderListByOrderId(Integer orderId) {
        return success(storeOrderInfoService.getOrderListByOrderId(orderId));
    }


    /**
     * 获取订单详情-订单编号
     * @param orderNo 订单编号
     * @return List
     */
    @Override
    public CommonResult<List<StoreOrderInfo>> getListByOrderNo(String orderNo) {
        return success(storeOrderInfoService.getListByOrderNo(orderNo));
    }

    /**
     * 获取订单详情vo列表
     * @param orderId 订单id
     * @return List<StoreOrderInfoVo>
     */
    @Override
    public CommonResult<List<StoreOrderInfoVo>> getVoListByOrderId(Integer orderId) {
        return success(storeOrderInfoService.getVoListByOrderId(orderId));
    }

    /**
     * 通过订单编号和规格号查询
     * @param uni 规格号
     * @param orderId 订单编号
     * @return StoreOrderInfo
     */
    @Override
    public CommonResult<StoreOrderInfo> getByUniAndOrderId(String uni, Integer orderId) {
        return success(storeOrderInfoService.getByUniAndOrderId(uni, orderId));
    }

    /**
     * 批量添加订单详情
     * @param storeOrderInfos 订单详情集合
     * @return 保存结果
     */
    @Override
    public CommonResult<Boolean> saveOrderInfos(List<StoreOrderInfo> storeOrderInfos) {
        return success(storeOrderInfoService.saveOrderInfos(storeOrderInfos));
    }
}
