package com.nbug.module.order.api;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.OrderRefundApplyRequest;
import com.nbug.module.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OrderApiImpl implements OrderApi {

    @Resource
    private OrderService orderService;

    /**
     * 订单退款申请
     * @param request 申请参数
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> refundApply(OrderRefundApplyRequest request) {
        return CommonResult.success(orderService.refundApply(request));
    }

    /**
     * 订单退款申请Task使用
     * @param applyList 退款List
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> refundApplyTask(List<OrderRefundApplyRequest> applyList) {
        return CommonResult.success(orderService.refundApplyTask(applyList));
    }
}
