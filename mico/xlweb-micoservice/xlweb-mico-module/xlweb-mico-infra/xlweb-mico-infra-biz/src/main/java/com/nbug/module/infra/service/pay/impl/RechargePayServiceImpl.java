package com.nbug.module.infra.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.utils.date.DateUtil;
import com.nbug.module.infra.service.pay.RechargePayService;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userRecharge.UserRechargeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;


/**
 * 支付类

 */
@Service
public class RechargePayServiceImpl implements RechargePayService {

    @Autowired
    private UserRechargeApi userRechargeApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserBillApi userBillApi;

    /**
     * 支付成功处理
     * 增加余额，userBill记录
     * @param userRecharge 充值订单
     */
    @Override
    public Boolean paySuccess(UserRecharge userRecharge) {
        userRecharge.setPaid(true);
        userRecharge.setPayTime(DateUtil.nowDateTime());

        User user = userApi.getById(userRecharge.getUid()).getCheckedData();

        BigDecimal payPrice = userRecharge.getPrice().add(userRecharge.getGivePrice());
        BigDecimal balance = user.getNowMoney().add(payPrice);
        // 余额变动对象
        UserBill userBill = new UserBill();
        userBill.setUid(userRecharge.getUid());
        userBill.setLinkId(userRecharge.getOrderId());
        userBill.setPm(1);
        userBill.setTitle("充值支付");
        userBill.setCategory(Constants.USER_BILL_CATEGORY_MONEY);
        userBill.setType(Constants.USER_BILL_TYPE_PAY_RECHARGE);
        userBill.setNumber(payPrice);
        userBill.setBalance(balance);
        userBill.setMark(StrUtil.format("余额增加了{}元", payPrice));
        userBill.setStatus(1);
        userBill.setCreateTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            // 订单变动
            userRechargeApi.updateById(userRecharge);
            // 余额变动
            userApi.operationNowMoney(user.getUid(), payPrice, user.getNowMoney(), "add");
            // 创建记录
            userBillApi.save(userBill);
            return Boolean.TRUE;
        });
        return execute;
    }
}
