package com.nbug.module.infra.api.sms;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.sms.SmsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SmsApiImpl implements SmsApi {

    @Resource
    private SmsService smsService;

    /**
     * 发送订单改价提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param price 修改后的支付金额
     * @param msgTempKey 短信模板Key
     */
    @Override
    public CommonResult<Boolean> sendOrderEditPriceNotice(String phone, String orderNo, BigDecimal price, String msgTempKey) {
        return CommonResult.success(smsService.sendOrderEditPriceNotice(phone, orderNo, price, msgTempKey));
    }

    /**
     * 发送用户确认收货管理员提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    @Override
    public CommonResult<Boolean> sendOrderReceiptNotice( String phone, String orderNo, String realName, String msgTempKey) {
        return CommonResult.success(smsService.sendOrderReceiptNotice(phone, orderNo, realName, msgTempKey));
    }

    /**
     * 发送订单发货提醒短信
     * @param phone 手机号
     * @param nickName 用户昵称
     * @param storeName 商品名称
     * @param orderNo 订单编号
     * @param msgTempKey 短信模板Key
     */
    @Override
    public CommonResult<Boolean> sendOrderDeliverNotice(String phone, String nickName, String storeName, String orderNo, String msgTempKey) {
        return CommonResult.success(smsService.sendOrderDeliverNotice(phone, nickName, storeName, orderNo, msgTempKey));
    }
}
