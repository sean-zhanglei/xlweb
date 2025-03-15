package com.nbug.module.infra.service.pay;


import com.nbug.mico.common.model.finance.UserRecharge;

/**
 * 订单支付

 */
public interface RechargePayService {

    /**
     * 支付成功处理
     * @param userRecharge 充值订单
     */
    Boolean paySuccess(UserRecharge userRecharge);
}
