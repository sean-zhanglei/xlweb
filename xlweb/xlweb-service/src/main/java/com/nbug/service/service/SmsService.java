package com.nbug.service.service;

import com.nbug.common.vo.MyRecord;
import com.nbug.common.request.PageParamRequest;

import java.math.BigDecimal;

/**
 * SmsService 接口

 */
public interface SmsService {

    /**
     * 发送公共验证码
     *
     * @param phone 手机号
     */
    Boolean sendCommonCode(String phone);

    /**
     * 发送支付成功短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param payPrice 支付金额
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    Boolean sendPaySuccess(String phone, String orderNo, BigDecimal payPrice, String msgTempKey);

    /**
     * 发送管理员下单短信提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    Boolean sendCreateOrderNotice(String phone, String orderNo, String realName, String msgTempKey);

    /**
     * 发送订单支付成功管理员提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    Boolean sendOrderPaySuccessNotice(String phone, String orderNo, String realName, String msgTempKey);

    /**
     * 发送用户退款管理员提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    Boolean sendOrderRefundApplyNotice(String phone, String orderNo, String realName, String msgTempKey);

    /**
     * 发送用户确认收货管理员提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    Boolean sendOrderReceiptNotice(String phone, String orderNo, String realName, String msgTempKey);

    /**
     * 发送订单改价提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param price 修改后的支付金额
     * @param msgTempKey 短信模板Key
     */
    Boolean sendOrderEditPriceNotice(String phone, String orderNo, BigDecimal price, String msgTempKey);

    /**
     * 发送订单发货提醒短信
     * @param phone 手机号
     * @param nickName 用户昵称
     * @param storeName 商品名称
     * @param orderNo 订单编号
     * @param msgTempKey 短信模板Key
     */
    Boolean sendOrderDeliverNotice(String phone, String nickName, String storeName, String orderNo, String msgTempKey);
}
