package com.nbug.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.common.constants.Constants;
import com.nbug.common.constants.PayConstants;
import com.nbug.common.request.StoreOrderRefundRequest;
import com.nbug.common.utils.RestTemplateUtil;
import com.nbug.common.utils.WxPayUtil;
import com.nbug.common.vo.WxRefundVo;
import com.nbug.common.model.order.StoreOrder;
import com.nbug.service.dao.StoreOrderDao;
import com.nbug.service.service.StoreOrderRefundService;
import com.nbug.service.service.SystemConfigService;
import com.nbug.service.service.WechatNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * StoreOrderServiceImpl 接口实现

 */
@Service
public class StoreOrderRefundServiceImpl extends ServiceImpl<StoreOrderDao, StoreOrder> implements StoreOrderRefundService {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private WechatNewService wechatNewService;

    /**
     * 退款 需要优化
     * @author Mr.Zhang
     * @since 2020-06-03
     */
    @Override
    public void refund(StoreOrderRefundRequest request, StoreOrder storeOrder) {
        refundWx(request, storeOrder);
    }

    /**
     * 公共号退款
     * @param request
     * @param storeOrder
     */
    private void refundWx(StoreOrderRefundRequest request, StoreOrder storeOrder) {
        // 获取appid、mch_id
        // 微信签名key
        String appId = "";
        String mchId = "";
        String signKey = "";
        String path = "";
        if (storeOrder.getIsChannel() == 0) {// 公众号
            appId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID);
            mchId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID);
            signKey = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY);
//            path = systemConfigService.getValueByKeyException("pay_routine_client_p12");
            path = systemConfigService.getValueByKeyException("pay_weixin_certificate_path");
        }
        if (storeOrder.getIsChannel() == 1) {// 小程序
            appId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID);
            mchId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID);
            signKey = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY);
//            path = systemConfigService.getValueByKeyException("pay_mini_client_p12");
            path = systemConfigService.getValueByKeyException("pay_routine_certificate_path");
        }
        if (storeOrder.getIsChannel() == 2) {// H5, 使用公众号的
            appId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID);
            mchId = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID);
            signKey = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY);
//            path = systemConfigService.getValueByKeyException("pay_mini_client_p12");
            path = systemConfigService.getValueByKeyException("pay_weixin_certificate_path");
        }

        String apiDomain = systemConfigService.getValueByKeyException(Constants.CONFIG_KEY_API_URL);

        //统一下单数据
        WxRefundVo wxRefundVo = new WxRefundVo();
        wxRefundVo.setAppid(appId);
        wxRefundVo.setMch_id(mchId);
        wxRefundVo.setNonce_str(WxPayUtil.getNonceStr());
        wxRefundVo.setOut_trade_no(storeOrder.getOutTradeNo());
        wxRefundVo.setOut_refund_no(storeOrder.getOrderId());
        wxRefundVo.setTotal_fee(storeOrder.getPayPrice().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue());
        wxRefundVo.setRefund_fee(request.getAmount().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue());
        wxRefundVo.setNotify_url(apiDomain + PayConstants.WX_PAY_REFUND_NOTIFY_API_URI);
        String sign = WxPayUtil.getSign(wxRefundVo, signKey);
        wxRefundVo.setSign(sign);

        wechatNewService.payRefund(wxRefundVo, path);
    }

}

