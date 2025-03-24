package com.nbug.module.store.api.storeOrder;

import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderSearchRequest;
import com.nbug.mico.common.request.SystemWriteOffOrderSearchRequest;
import com.nbug.mico.common.response.OrderBrokerageData;
import com.nbug.mico.common.response.StoreOrderDetailResponse;
import com.nbug.mico.common.response.SystemWriteOffOrderResponse;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.store.service.StoreOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreOrderApiImpl implements StoreOrderApi {

    @Resource
    private StoreOrderService storeOrderService;


    @Override
    public CommonResult<Boolean> updateById(StoreOrder storeOrder) {
        return success(storeOrderService.updateById(storeOrder));
    }

    @Override
    public CommonResult<StoreOrder> getByOderId(String orderId) {
        return success(storeOrderService.getByOderId(orderId));
    }

    @Override
    public CommonResult<StoreOrder> getById(Integer id) {
        return success(storeOrderService.getById(id));
    }

    /**
     * 根据属性仅仅获取一条
     * @param storeOrder 参数
     * @return 当前查询结果
     */
    @Override
    public CommonResult<StoreOrder> getByEntityOne(StoreOrder storeOrder) {
        return success(storeOrderService.getByEntityOne(storeOrder));
    }

    @Override
    public CommonResult<Boolean> updateBatchById(List<StoreOrder> storeOrders, Integer batchSize) {
        return success(storeOrderService.updateBatchById(storeOrders, batchSize));
    }


    /**
     * H5订单列表
     * @param userId 用户uid
     * @param status 评价等级|0=未支付,1=待发货,2=待收货,3=待评价,4=已完成,-3=售后/退款
     * @param pageParamRequest 分页参数
     * @return 订单结果列表
     */
    @Override
    public CommonResult<List<StoreOrder>> getUserOrderList(Integer userId, Integer status, PageParamRequest pageParamRequest) {
        return success(storeOrderService.getUserOrderList(userId, status, pageParamRequest));
    }

    @Override
    public CommonResult<Integer> getOrderCountByUid(Integer userId) {
        return success(storeOrderService.getOrderCountByUid(userId));
    }

    /**
     * h5 top data 工具方法
     * @param status 状态参数
     * @return 查询到的订单结果
     */
    @Override
    public CommonResult<Integer> getTopDataUtil(Integer status, Integer userId) {
        return success(storeOrderService.getTopDataUtil(status, userId));
    }

    /**
     * 获取用户总消费金额
     * @param userId 用户uid
     * @return BigDecimal
     */
    @Override
    public CommonResult<BigDecimal> getSumPayPriceByUid(Integer userId) {
        return success(storeOrderService.getSumPayPriceByUid(userId));
    }

    /**
     * 创建订单
     * @param storeOrder 订单参数
     * @return 创建结果
     */
    @Override
    public CommonResult<Boolean> create(StoreOrder storeOrder) {
        return success(storeOrderService.create(storeOrder));
    }

    /**
     * 获取用户当天的秒杀数量
     *
     * @param userId 用户uid
     * @param seckillId 秒杀商品id
     * @return 用户当天的秒杀商品订单数量
     */
    @Override
    public CommonResult<List<StoreOrder>> getUserCurrentDaySecKillOrders( Integer userId, Integer seckillId) {
        return success(storeOrderService.getUserCurrentDaySecKillOrders(userId, seckillId));
    }

    /**
     * 获取砍价订单
     * @param bargainId 砍价商品id
     * @param bargainUserId 用户砍价活动id
     * @return StoreOrder
     */
    @Override
    public CommonResult<StoreOrder> getByBargainOrder(Integer bargainId, Integer bargainUserId) {
        return success(storeOrderService.getByBargainOrder(bargainId, bargainUserId));
    }

    /**
     * 获取用户当前的砍价订单
     * @param userId    用户uid
     * @return  用户当前的砍价订单
     */
    @Override
    public CommonResult<List<StoreOrder>> getUserCurrentBargainOrders( Integer userId, Integer bargainId) {
        return success(storeOrderService.getUserCurrentBargainOrders(userId, bargainId));
    }

    /**
     * 获取用户当前的拼团订单
     * @param userId    用户uid
     * @return  用户当前的拼团订单
     */
    @Override
    public CommonResult<List<StoreOrder>> getUserCurrentCombinationOrders(Integer userId, Integer combinationId) {
        return success(storeOrderService.getUserCurrentCombinationOrders(userId, combinationId));
    }

    /**
     * 获取所有收货订单id集合
     * @return List<StoreOrder>
     */
    @Override
    public CommonResult<List<StoreOrder>> findIdAndUidListByReceipt() {
        return success(storeOrderService.findIdAndUidListByReceipt());
    }

    /**
     * 更新支付结果
     * @param orderNo 订单编号
     */
    @Override
    public CommonResult<Boolean> updatePaid(String orderNo) {
        return success(storeOrderService.updatePaid(orderNo));
    }

    /**
     * 列表（PC）
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return CommonPage<StoreOrderDetailResponse>
     */
    @Override
    public CommonResult<CommonPage<StoreOrderDetailResponse>> getAdminList(StoreOrderSearchRequest request, PageParamRequest pageParamRequest) {
        return success(storeOrderService.getAdminList(request, pageParamRequest));
    }

    @Override
    public CommonResult<StoreOrder> getInfoByEntity(StoreOrder storeOrder) {
        return success(storeOrderService.getInfoByEntity(storeOrder));
    }

    /**
     * 获取推广订单总金额
     * @param orderNoList 订单编号列表
     * @return BigDecimal
     */
    @Override
    public CommonResult<BigDecimal> getSpreadOrderTotalPriceByOrderList(List<String> orderNoList) {
        return success(storeOrderService.getSpreadOrderTotalPriceByOrderList(orderNoList));
    }

    /**
     * 获取佣金相关数据
     * @param userId 用户uid
     * @param spreadId 推广人uid
     */
    @Override
    public CommonResult<OrderBrokerageData> getBrokerageData(Integer userId, Integer spreadId) {
        return success(storeOrderService.getBrokerageData(userId, spreadId));
    }

    /**
     * 跟据订单号列表获取订单列表Map
     * @param orderNoList 订单号列表
     * @return Map
     */
    @Override
    public CommonResult<Map<String, StoreOrder>> getMapInOrderNo(List<String> orderNoList) {
        return success(storeOrderService.getMapInOrderNo(orderNoList));
    }

    /**
     * 获取用户消费金额(时间)
     * @param userId 用户uid
     * @return BigDecimal
     */
    @Override
    public CommonResult<BigDecimal> getSumPayPriceByUidAndDate(Integer userId, String date) {
        return success(storeOrderService.getSumPayPriceByUidAndDate(userId, date));
    }

    /**
     * 获取订单数量(时间)
     * @param userId 用户uid
     * @return Integer
     */
    @Override
    public CommonResult<Integer> getOrderCountByUidAndDate(Integer userId, String date) {
        return success(storeOrderService.getOrderCountByUidAndDate(userId, date));
    }

    /**
     * 根据用户uid查询所有已支付订单
     * @param userId 用户uid
     * @param pageParamRequest 分页参数
     * @return List<StoreOrder>
     */
    @Override
    public CommonResult<List<StoreOrder>> findPaidListByUid(Integer userId, PageParamRequest pageParamRequest) {
        return success(storeOrderService.findPaidListByUid(userId, pageParamRequest));
    }

    /**
     * 核销列表
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<StoreOrder>
     */
    @Override
    public CommonResult<SystemWriteOffOrderResponse> getWriteOffList(SystemWriteOffOrderSearchRequest request, PageParamRequest pageParamRequest) {
        return success(storeOrderService.getWriteOffList(request, pageParamRequest));
    }

    /**
     * 按开始结束时间分组订单
     * @param dateLimit
     * @param lefTime
     * @return
     */
    @Override
    public CommonResult<List<StoreOrder>> getOrderGroupByDate(String dateLimit, int lefTime) {
        return success(storeOrderService.getOrderGroupByDate(dateLimit, lefTime));
    }

    /**
     * 通过日期获取支付订单金额
     * @param date
     * @return
     */
    @Override
    public CommonResult<BigDecimal> getPayOrderAmountByDate(String date) {
        return success(storeOrderService.getPayOrderAmountByDate(date));
    }

    /**
     * 通过日期获取订单数量
     * @param date
     * @return
     */
    @Override
    public CommonResult<Integer> getOrderNumByDate(String date) {
        return success(storeOrderService.getOrderNumByDate(date));
    }

    /**
     * 今天交易数据
     * @param time
     * @return
     */
    @Override
    public CommonResult<MyRecord> getTradetop(String time) {
        return success(storeOrderService.getTradetop(time));
    }

    /**
     * 交易概括数据
     * @param time
     * @return
     */
    @Override
    public CommonResult<MyRecord> getTradeBottom(String time) {
        return success(storeOrderService.getTradeBottom(time));
    }

    @Override
    public CommonResult<MyRecord> getOrderBasic(String time) {
        return success(storeOrderService.getOrderBasic(time));
    }

    @Override
    public CommonResult<MyRecord> getOrderTrend(String time) {
        return success(storeOrderService.getOrderTrend(time));
    }

    @Override
    public CommonResult<MyRecord> getOrderChannel(String time) {
        return success(storeOrderService.getOrderChannel(time));
    }

    @Override
    public CommonResult<MyRecord> getOrderType(String time) {
        return success(storeOrderService.getOrderType(time));
    }
}
