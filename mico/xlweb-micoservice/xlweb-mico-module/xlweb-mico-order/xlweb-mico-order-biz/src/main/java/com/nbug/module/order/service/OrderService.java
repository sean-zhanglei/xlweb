package com.nbug.module.order.service;


import com.nbug.mico.common.page.CommonPage;
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
import com.nbug.mico.common.vo.MyRecord;

import java.util.List;

/**
 * H5端订单操作

 */
public interface OrderService {

    /**
     * 订单列表
     * @param type 类型
     * @param pageRequest 分页
     * @return 订单集合
     */
    CommonPage<OrderDetailResponse> list(Integer type, PageParamRequest pageRequest);

    /**
     * 订单详情
     * @param orderId 订单id
     */
    StoreOrderDetailInfoResponse detailOrder(String orderId);

    /**
     * 订单状态数量
     * @return 订单状态数据量
     */
    OrderDataResponse orderData();

    /**
     * 查询退款理由
     * @return 退款理由集合
     */
    List<String> getRefundReason();

    /**
     * 订单删除
     * @param id 订单id
     * @return Boolean
     */
    Boolean delete(Integer id);

    /**
     * 创建订单商品评价
     * @param request 请求参数
     * @return Boolean
     */
    Boolean reply(StoreProductReplyAddRequest request);

    /**
     * 订单收货
     * @param id 订单id
     * @return Boolean
     */
    Boolean take(Integer id);

    /**
     * 订单取消
     * @param id 订单id
     * @return Boolean
     */
    Boolean cancel(Integer id);

    /**
     * 订单退款申请
     * @param request 申请参数
     * @return Boolean
     */
    Boolean refundApply(OrderRefundApplyRequest request);

    /**
     * 订单退款申请Task使用
     * @param applyList 退款List
     * @return Boolean
     */
    Boolean refundApplyTask(List<OrderRefundApplyRequest> applyList);

    /**
     * 订单物流查看
     */
    Object expressOrder(String orderId);

    /**
     * 获取待评价商品信息
     * @param getProductReply 订单详情参数
     * @return 待评价
     */
    OrderProductReplyResponse getReplyProduct(GetProductReply getProductReply);

    /**
     * 获取申请订单退款信息
     * @param orderId 订单编号
     * @return ApplyRefundOrderInfoResponse
     */
    ApplyRefundOrderInfoResponse applyRefundOrderInfo(String orderId);

    /**
     * 订单预下单
     * @param request 预下单请求参数
     * @return PreOrderResponse
     */
    MyRecord preOrder(PreOrderRequest request);

    /**
     * 加载预下单信息
     * @param preOrderNo 预下单号
     * @return 预下单信息
     */
    PreOrderResponse loadPreOrder(String preOrderNo);

    /**
     * 计算订单价格
     * @param request 计算订单价格请求对象
     * @return ComputedOrderPriceResponse
     */
    ComputedOrderPriceResponse computedOrderPrice(OrderComputedPriceRequest request);

    /**
     * 创建订单
     * @param orderRequest 创建订单请求参数
     * @return MyRecord 订单编号
     */
    MyRecord createOrder(CreateOrderRequest orderRequest);

    /**
     * 获取支付配置
     * @return PreOrderResponse
     */
    PreOrderResponse getPayConfig();
}
