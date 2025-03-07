package com.nbug.service.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.common.constants.Constants;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.model.order.StoreOrder;
import com.nbug.common.request.YlyPrintRequest;
import com.nbug.common.request.YlyPrintRequestGoods;
import com.nbug.common.vo.StoreOrderInfoOldVo;
import com.nbug.service.service.StoreOrderInfoService;
import com.nbug.service.service.StoreOrderService;
import com.nbug.service.service.SystemConfigService;
import com.nbug.service.service.YlyPrintService;
import com.nbug.service.util.YlyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 易联云打印订单 service

 */
@Service
public class YlyPrintServiceImpl implements YlyPrintService {
    private static final Logger logger = LoggerFactory.getLogger(YlyPrintServiceImpl.class);
    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    private StoreOrderInfoService storeOrderInfoService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private YlyUtil ylyUtil;
    /**
     * 易联云打印商品信息
     *
     * @param orderId 订单id
     * @param isAuto 是否自动打印
     */
    @Override
    public void YlyPrint(String orderId,boolean isAuto) {
        if(ylyUtil.checkYlyPrintStatus()){
            throw new XlwebException("易联云 未开启打印");
        }
        // 判断是否开启自动打印
        if(isAuto && ylyUtil.checkYlyPrintAfterPaySuccess()){
            return;
        }
        StoreOrder exitOrder = storeOrderService.getByOderId(orderId);
        if(ObjectUtil.isNull(exitOrder)){
            throw new XlwebException("易联云 打印时未找到 订单信息");
        }
        if(!exitOrder.getPaid()){
            throw new XlwebException("易联云 打印时出错， 订单未支付");
        }
        List<StoreOrderInfoOldVo> exitOrderInfo = storeOrderInfoService.getOrderListByOrderId(exitOrder.getId());
        List<YlyPrintRequestGoods> goods = new ArrayList<>();
        for (StoreOrderInfoOldVo storeOrderInfo : exitOrderInfo) {
            goods.add(new YlyPrintRequestGoods(storeOrderInfo.getInfo().getProductName()
                    ,storeOrderInfo.getInfo().getPrice().toString(),
                    storeOrderInfo.getInfo().getPayNum()+"",
                    exitOrder.getPayPrice().toString()));
        }

        YlyPrintRequest ylyPrintRequest = new YlyPrintRequest();
        ylyPrintRequest.setBusinessName(systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_SITE_NAME));
        ylyPrintRequest.setOrderNo(exitOrder.getOrderId());
        ylyPrintRequest.setDate(DateUtil.format(exitOrder.getPayTime(), Constants.DATE_FORMAT));
        ylyPrintRequest.setName(exitOrder.getRealName());
        ylyPrintRequest.setPhone(exitOrder.getUserPhone());
        ylyPrintRequest.setAddress(exitOrder.getUserAddress());
        ylyPrintRequest.setNote(exitOrder.getMark());
        ylyPrintRequest.setShippingType(exitOrder.getShippingType());
        ylyPrintRequest.setDeliveryTime(exitOrder.getDeliveryTime());
        ylyPrintRequest.setPickupTime(exitOrder.getPickupTime());

        ylyPrintRequest.setGoods(goods);
        ylyPrintRequest.setAmount(exitOrder.getProTotalPrice().toString());
        ylyPrintRequest.setDiscount(exitOrder.getDeductionPrice().toString());
        ylyPrintRequest.setPostal(exitOrder.getPayPostage().toString());
        ylyPrintRequest.setDeduction(exitOrder.getCouponPrice().toString());
        ylyPrintRequest.setPayMoney(exitOrder.getPayPrice().toString());

        try {
            ylyUtil.ylyPrint(ylyPrintRequest);
            logger.info("易联云打印小票成功" + JSONObject.toJSONString(ylyPrintRequest));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("易联云打印小票失败 " + e.getMessage());
        }
    }


}
