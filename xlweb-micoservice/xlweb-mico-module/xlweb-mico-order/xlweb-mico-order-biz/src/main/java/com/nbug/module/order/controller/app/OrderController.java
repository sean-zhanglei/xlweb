package com.nbug.module.order.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.CreateOrderRequest;
import com.nbug.mico.common.request.GetProductReply;
import com.nbug.mico.common.request.OrderComputedPriceRequest;
import com.nbug.mico.common.request.OrderRefundApplyRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.PreOrderRequest;
import com.nbug.mico.common.request.StoreProductReplyAddRequest;
import com.nbug.mico.common.response.ApplyRefundOrderInfoResponse;
import com.nbug.mico.common.response.ComputedOrderPriceResponse;
import com.nbug.mico.common.response.OrderDataResponse;
import com.nbug.mico.common.response.OrderDetailResponse;
import com.nbug.mico.common.response.OrderProductReplyResponse;
import com.nbug.mico.common.response.PreOrderResponse;
import com.nbug.mico.common.response.StoreOrderDetailInfoResponse;
import com.nbug.module.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * H5端订单操作
 
 */
@Slf4j
@RestController("StoreOrderFrontController")
@RequestMapping("api/front/order")
@Tag(name = "订单")
public class StoreOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 预下单
     */
    @Operation(summary = "预下单")
    @RequestMapping(value = "/pre/order", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> preOrder(@RequestBody @Validated PreOrderRequest request) {
        return CommonResult.success(orderService.preOrder(request).getColumns());
    }

    /**
     * 加载预下单
     */
    @Operation(summary = "加载预下单")
    @RequestMapping(value = "load/pre/{preOrderNo}", method = RequestMethod.GET)
    public CommonResult<PreOrderResponse> loadPreOrder(@PathVariable String preOrderNo) {
        return CommonResult.success(orderService.loadPreOrder(preOrderNo));
    }

    /**
     * 根据参数计算订单价格
     */
    @Operation(summary = "计算订单价格")
    @RequestMapping(value = "/computed/price", method = RequestMethod.POST)
    public CommonResult<ComputedOrderPriceResponse> computedPrice(@Validated @RequestBody OrderComputedPriceRequest request) {
        return CommonResult.success(orderService.computedOrderPrice(request));
    }

    /**
     * 创建订单
     */
    @Operation(summary = "创建订单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> createOrder(@Validated @RequestBody CreateOrderRequest orderRequest) {
        return CommonResult.success(orderService.createOrder(orderRequest).getColumns());
    }

    /**
     * 订单列表
     * @param type 类型
     * @param pageRequest 分页
     * @return 订单列表
     */
    @Operation(summary = "订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
        @Parameter(name = "type", description = "评价等级|0=未支付,1=待发货,2=待收货,3=待评价,4=已完成,-3=售后/退款", required = true)
    })
    public CommonResult<CommonPage<OrderDetailResponse>> orderList(@RequestParam(name = "type") Integer type,
                                                                   @ModelAttribute PageParamRequest pageRequest) {
        return CommonResult.success(orderService.list(type, pageRequest));
    }

    /**
     * 订单详情
     * @param orderId 订单编号
     * @return 订单详情
     */
    @Operation(summary = "订单详情")
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public CommonResult<StoreOrderDetailInfoResponse> orderDetail(@PathVariable String orderId) {
        return CommonResult.success(orderService.detailOrder(orderId));
    }

    /**
     * 订单头部信息
     * @return 查询集合数量
     */
    @Operation(summary = "订单头部数量")
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public CommonResult<OrderDataResponse> orderData() {
        return CommonResult.success(orderService.orderData());
    }

    /**
     * 删除已完成订单
     * @param id String 订单号
     * @return 删除结果
     */
    @Operation(summary = "删除订单")
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public CommonResult<Boolean> delete(@RequestParam Integer id) {
        if( orderService.delete(id)) {
            return CommonResult.success(true);
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 订单评价
     * @param request StoreProductReplyAddRequest 评论参数
     */
    @Operation(summary = "评价订单")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public CommonResult<Boolean> comment(@RequestBody @Validated StoreProductReplyAddRequest request) {
        if(orderService.reply(request)) {
            return CommonResult.success(true);
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 订单收货
     * @param id Integer 订单id
     */
    @Operation(summary = "订单收货")
    @RequestMapping(value = "/take", method = RequestMethod.POST)
    public CommonResult<Boolean> take(@RequestParam(value = "id") Integer id) {
        if(orderService.take(id)) {
            return CommonResult.success(true);
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 订单取消
     * @param id Integer 订单id
     */
    @Operation(summary = "订单取消")
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public CommonResult<Boolean> cancel(@RequestParam(value = "id") Integer id) {
        if(orderService.cancel(id)) {
            return CommonResult.success(true);
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取申请订单退款信息
     * @param orderId 订单编号
     */
    @Operation(summary = "获取申请订单退款信息")
    @RequestMapping(value = "/apply/refund/{orderId}", method = RequestMethod.GET)
    public CommonResult<ApplyRefundOrderInfoResponse> refundApplyOrder(@PathVariable String orderId) {
        return CommonResult.success(orderService.applyRefundOrderInfo(orderId));
    }

    /**
     * 订单退款申请
     * @param request OrderRefundApplyRequest 订单id
     */
    @Operation(summary = "订单退款申请")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public CommonResult<Boolean> refundApply(@RequestBody @Validated OrderRefundApplyRequest request) {
        if(orderService.refundApply(request)) {
            return CommonResult.success(true);
        }else{
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询订单退款理由
     * @return 退款理由
     */
    @Operation(summary = "订单退款理由（商家提供）")
    @RequestMapping(value = "/refund/reason", method = RequestMethod.GET)
    public CommonResult<List<String>> refundReason() {
        return CommonResult.success(orderService.getRefundReason());
    }

    /**
     * 根据订单号查询物流信息
     * @param orderId 订单号
     * @return 物流信息
     */
    @Operation(summary = "物流信息查询")
    @RequestMapping(value = "/express/{orderId}", method = RequestMethod.GET)
    public CommonResult<Object> getExpressInfo(@PathVariable String orderId) {
        return CommonResult.success(orderService.expressOrder(orderId));
    }

    @Operation(summary = "待评价商品信息查询")
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public CommonResult<OrderProductReplyResponse> getOrderProductForReply(@Validated @RequestBody GetProductReply request) {
        return CommonResult.success(orderService.getReplyProduct(request));
    }

    /**
     * 获取支付配置
     */
    @Operation(summary = "获取支付配置")
    @RequestMapping(value = "get/pay/config", method = RequestMethod.GET)
    public CommonResult<PreOrderResponse> getPayConfig() {
        return CommonResult.success(orderService.getPayConfig());
    }
}
