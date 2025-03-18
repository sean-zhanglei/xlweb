package com.nbug.module.order.controller.app;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.OrderPayRequest;
import com.nbug.mico.common.response.OrderPayResultResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.infra.api.wechat.WeChatPayApi;
import com.nbug.module.order.service.OrderPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信缓存表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/front/order/pay")
@Tag(name = "应用后台 - 支付管理")
public class OrderPayController {

    @Autowired
    private WeChatPayApi weChatPayApi;

    @Autowired
    private OrderPayService orderPayService;

    /**
     * 订单支付
     */
    @Operation(summary = "订单支付")
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public CommonResult<OrderPayResultResponse> payment(@RequestBody @Validated OrderPayRequest orderPayRequest, HttpServletRequest request) {
        String ip = XlwebUtil.getClientIp(request);
        return CommonResult.success(orderPayService.payment(orderPayRequest, ip));
    }

    /**
     * 查询支付结果
     *
     * @param orderNo |订单编号|String|必填
     */
    @Operation(summary = "查询支付结果")
    @RequestMapping(value = "/queryPayResult", method = RequestMethod.GET)
    public CommonResult<Boolean> queryPayResult(@RequestParam String orderNo) {
        return CommonResult.success(weChatPayApi.queryPayResult(orderNo).getCheckedData());
    }
}
