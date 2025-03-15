package com.nbug.module.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.PayConstants;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.request.StoreOrderRefundRequest;
import com.nbug.mico.common.utils.RestTemplateUtil;
import com.nbug.mico.common.utils.wx.WxPayUtil;
import com.nbug.mico.common.vo.WxRefundVo;
import com.nbug.module.infra.api.wechat.WechatNewApi;
import com.nbug.module.store.dal.StoreOrderDao;
import com.nbug.module.store.service.StoreOrderRefundService;
import com.nbug.module.system.api.config.ConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * StoreOrderServiceImpl 接口实现

 */
@Service
public class StoreOrderRefundServiceImpl extends ServiceImpl<StoreOrderDao, StoreOrder> implements StoreOrderRefundService {

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private WechatNewApi wechatNewApi;

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
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
//            path = configApi.getValueByKeyException("pay_routine_client_p12").getCheckedData();
            path = configApi.getValueByKeyException("pay_weixin_certificate_path").getCheckedData();
        }
        if (storeOrder.getIsChannel() == 1) {// 小程序
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY).getCheckedData();
//            path = configApi.getValueByKeyException("pay_mini_client_p12").getCheckedData();
            path = configApi.getValueByKeyException("pay_routine_certificate_path").getCheckedData();
        }
        if (storeOrder.getIsChannel() == 2) {// H5, 使用公众号的
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
//            path = configApi.getValueByKeyException("pay_mini_client_p12").getCheckedData();
            path = configApi.getValueByKeyException("pay_weixin_certificate_path").getCheckedData();
        }

        String apiDomain = configApi.getValueByKeyException(Constants.CONFIG_KEY_API_URL).getCheckedData();

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

        wechatNewApi.payRefund(wxRefundVo, path);
    }

}

