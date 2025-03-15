package com.nbug.module.infra.service.Yly;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.request.YlyPrintRequest;
import com.nbug.mico.common.request.YlyPrintRequestGoods;
import com.nbug.mico.common.vo.StoreOrderInfoOldVo;
import com.nbug.module.infra.util.YlyUtil;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeOrderInfo.StoreOrderInfoApi;
import com.nbug.module.system.api.config.ConfigApi;
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
    private StoreOrderApi storeOrderApi;

    @Autowired
    private StoreOrderInfoApi storeOrderInfoApi;

    @Autowired
    private ConfigApi configApi;

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
        StoreOrder exitOrder = storeOrderApi.getByOderId(orderId).getCheckedData();
        if(ObjectUtil.isNull(exitOrder)){
            throw new XlwebException("易联云 打印时未找到 订单信息");
        }
        if(!exitOrder.getPaid()){
            throw new XlwebException("易联云 打印时出错， 订单未支付");
        }
        List<StoreOrderInfoOldVo> exitOrderInfo = storeOrderInfoApi.getOrderListByOrderId(exitOrder.getId()).getCheckedData();
        List<YlyPrintRequestGoods> goods = new ArrayList<>();
        for (StoreOrderInfoOldVo storeOrderInfo : exitOrderInfo) {
            goods.add(new YlyPrintRequestGoods(storeOrderInfo.getInfo().getProductName()
                    ,storeOrderInfo.getInfo().getPrice().toString(),
                    storeOrderInfo.getInfo().getPayNum()+"",
                    exitOrder.getPayPrice().toString()));
        }

        YlyPrintRequest ylyPrintRequest = new YlyPrintRequest();
        ylyPrintRequest.setBusinessName(configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_NAME).getCheckedData());
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
