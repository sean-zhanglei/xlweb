package com.nbug.module.infra.service.sms.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.NotifyConstants;
import com.nbug.mico.common.constants.SmsConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.mico.common.model.system.SystemNotification;
import com.nbug.mico.common.utils.RestTemplateUtil;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.utils.validation.ValidateFormUtil;
import com.nbug.mico.common.vo.SendSmsVo;
import com.nbug.module.infra.service.sms.SmsService;
import com.nbug.module.infra.service.sms.SmsTemplateService;
import com.nbug.module.infra.service.sms.SystemNotificationService;
import com.nbug.module.infra.util.SmsAliyunUtil;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.user.api.user.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * SmsServiceImpl 接口实现

 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private ConfigApi configApi;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserApi userApi;

    @Autowired
    private SystemNotificationService systemNotificationService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    /**
     * 发送短信
     * @param phone 手机号
     * @param tag 短信标识
     * @param msgTempKey 短信模板Key
     * @param pram 参数
     * @return Boolean
     */
    private Boolean sendMessages(String phone, Integer tag, String msgTempKey, HashMap<String, Object> pram) {
        //发送手机验证码， 记录到redis  sms_validate_code_手机号
        switch (tag) {
            case SmsConstants.SMS_CONFIG_TYPE_VERIFICATION_CODE: // 验证码 特殊处理 code
                //获取短信验证码过期时间
                String codeExpireStr = configApi.getValueByKey(Constants.CONFIG_KEY_SMS_CODE_EXPIRE).getCheckedData();
                if (StrUtil.isBlank(codeExpireStr) || Integer.parseInt(codeExpireStr) == 0) {
                    codeExpireStr = Constants.NUM_FIVE + "";// 默认5分钟过期
                }
                Integer code = XlwebUtil.randomCount(111111, 999999);
                HashMap<String, Object> justPram = new HashMap<>();
                justPram.put("code", code);
                // justPram.put("time", codeExpireStr);
                SystemNotification notification = systemNotificationService.getByMark(NotifyConstants.VALIDATE_CODE_MARK);
                SmsTemplate smsTemplate = smsTemplateService.getDetail(notification.getSmsId());
                if (! notification.getIsSms().equals(1)) {
                    throw new XlwebException("发送短信失败，请联系后台管理员");
                }

                push(phone, smsTemplate.getTempKey(), justPram);

                // 将验证码存入redis
                redisUtil.set(userApi.getValidateCodeRedisKey(phone).getCheckedData(), code, Long.valueOf(codeExpireStr), TimeUnit.MINUTES);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_LOWER_ORDER_SWITCH: // 支付成功短信提醒 pay_price order_id
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_DELIVER_GOODS_SWITCH: // 发货短信提醒 nickname store_name
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_CONFIRM_TAKE_OVER_SWITCH: // 确认收货短信提醒 order_id store_name
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_ADMIN_LOWER_ORDER_SWITCH: // 用户下单管理员短信提醒 admin_name order_id
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_ADMIN_PAY_SUCCESS_SWITCH: // 支付成功管理员短信提醒 admin_name order_id
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_ADMIN_REFUND_SWITCH: // 用户确认收货管理员短信提醒 admin_name order_id
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_ADMIN_CONFIRM_TAKE_OVER_SWITCH: // 用户发起退款管理员短信提醒 admin_name order_id
                push(phone, msgTempKey, pram);
                break;
            case SmsConstants.SMS_CONFIG_TYPE_PRICE_REVISION_SWITCH: // 改价短信提醒 order_id pay_price
                push(phone, msgTempKey, pram);
                break;
        }
        return true;
    }

    /**
     * 组装发送对象
     *
     * @param phone     手机号
     * @param msgTempKey 模板key
     * @param mapPram   参数map
     */
    private Boolean push(String phone, String msgTempKey, HashMap<String, Object> mapPram) {
        if (StrUtil.isBlank(phone) || StrUtil.isBlank(msgTempKey)) {
            return false;
        }
        SendSmsVo smsVo = new SendSmsVo();
        smsVo.setMobile(phone);
        smsVo.setTemplateKey(msgTempKey);
        smsVo.setContent(JSONObject.toJSONString(mapPram));
        return sendCode(smsVo);
    }

    /**
     * 发送公共验证码
     *
     * @param phone 手机号
     * @return Boolean
     * 1.校验后台是否配置短信
     * 3.发送短信
     */
    @Override
    public Boolean sendCommonCode(String phone) {
        ValidateFormUtil.isPhone(phone,"手机号码错误");
        SendSmsVo sendSmsVo = new SendSmsVo();
        sendSmsVo.setMobile(phone);
        //获取短信验证码过期时间
        String codeExpireStr = configApi.getValueByKey(Constants.CONFIG_KEY_SMS_CODE_EXPIRE).getCheckedData();
        if (StrUtil.isBlank(codeExpireStr) || Integer.parseInt(codeExpireStr) == 0) {
            codeExpireStr = Constants.NUM_FIVE + "";// 默认5分钟过期
        }
        Integer code = XlwebUtil.randomCount(111111, 999999);
        HashMap<String, Object> justPram = new HashMap<>();
        justPram.put("code", code);
        // justPram.put("time", codeExpireStr);

        SystemNotification notification = systemNotificationService.getByMark(NotifyConstants.VALIDATE_CODE_MARK);
        SmsTemplate smsTemplate = smsTemplateService.getDetail(notification.getSmsId());
        if (! notification.getIsSms().equals(1)) {
            throw new XlwebException("发送短信失败，请联系后台管理员");
        }
        sendSmsVo.setTemplateKey(smsTemplate.getTempKey());
        sendSmsVo.setContent(JSONObject.toJSONString(justPram));
        Boolean aBoolean = sendCode(sendSmsVo);
        if (!aBoolean) {
            throw new XlwebException("发送短信失败，请联系后台管理员");
        }
        // 将验证码存入redis
        redisUtil.set(userApi.getValidateCodeRedisKey(phone).getCheckedData(), code, Long.valueOf(codeExpireStr), TimeUnit.MINUTES);
        return aBoolean;
    }

    /**
     * 发送支付成功短信
     *
     * @param phone    手机号
     * @param orderNo  订单编号
     * @param payPrice 支付金额
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    @Override
    public Boolean sendPaySuccess(String phone, String orderNo, BigDecimal payPrice, String msgTempKey) {
        HashMap<String, Object> map = new HashMap();
        map.put("pay_price", payPrice);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_LOWER_ORDER_SWITCH, msgTempKey, map);
    }

    /**
     * 发送管理员下单短信提醒
     *
     * @param phone    手机号
     * @param orderNo  订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    @Override
    public Boolean sendCreateOrderNotice(String phone, String orderNo, String realName, String msgTempKey) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("admin_name", realName);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_ADMIN_LOWER_ORDER_SWITCH, msgTempKey, map);
    }

    /**
     * 发送订单支付成功管理员提醒短信
     *
     * @param phone    手机号
     * @param orderNo  订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    @Override
    public Boolean sendOrderPaySuccessNotice(String phone, String orderNo, String realName, String msgTempKey) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("admin_name", realName);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_ADMIN_PAY_SUCCESS_SWITCH, msgTempKey, map);
    }

    /**
     * 发送用户退款管理员提醒短信
     *
     * @param phone    手机号
     * @param orderNo  订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    @Override
    public Boolean sendOrderRefundApplyNotice(String phone, String orderNo, String realName, String msgTempKey) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("admin_name", realName);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_ADMIN_CONFIRM_TAKE_OVER_SWITCH, msgTempKey, map);
    }

    /**
     * 发送用户确认收货管理员提醒短信
     * @param phone 手机号
     * @param orderNo 订单编号
     * @param realName 管理员名称
     * @param msgTempKey 短信模板Key
     */
    @Override
    public Boolean sendOrderReceiptNotice(String phone, String orderNo, String realName, String msgTempKey) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("admin_name", realName);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_ADMIN_REFUND_SWITCH, msgTempKey, map);
    }

    /**
     * 发送订单改价提醒短信
     *
     * @param phone   手机号
     * @param orderNo 订单编号
     * @param price   修改后的支付金额
     * @param msgTempKey 短信模板Key
     * @return Boolean
     */
    @Override
    public Boolean sendOrderEditPriceNotice(String phone, String orderNo, BigDecimal price, String msgTempKey) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("order_id", orderNo);
        map.put("pay_price", price);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_PRICE_REVISION_SWITCH, msgTempKey, map);
    }

    /**
     * 发送订单发货提醒短信
     *
     * @param phone     手机号
     * @param nickName  用户昵称
     * @param storeName 商品名称
     * @param orderNo   订单编号
     * @param msgTempKey 短信模板Key
     */
    @Override
    public Boolean sendOrderDeliverNotice(String phone, String nickName, String storeName, String orderNo, String msgTempKey) {
        HashMap<String, Object> map = new HashMap();
        map.put("nickname", nickName);
        map.put("store_name", storeName);
        map.put("order_id", orderNo);
        return sendMessages(phone, SmsConstants.SMS_CONFIG_TYPE_DELIVER_GOODS_SWITCH, msgTempKey, map);
    }

    /**
     * 检测结构请求返回的数据
     *
     * @param result 接口返回的结果
     * @return JSONObject
     * @author Mr.Zhang
     * @since 2020-04-16
     */
    private void checkResult(SendSmsResponse result) {
        if (Objects.isNull(result)) {
            throw new XlwebException("短信平台接口异常，没任何数据返回！");
        }

        if (! SmsConstants.SMS_SUCCESS_CODE.equals(result.getStatusCode())) {
            throw new XlwebException("短信平台接口" + result.getBody().getMessage());
        }
    }

    /**
     * 发送短信
     * @param sendSmsVo 短信参数
     */
    private Boolean sendCode(SendSmsVo sendSmsVo) {
        try {
            logger.info("============发送短信=========sendCode==");
            SendSmsResponse response = SmsAliyunUtil.sendSms(sendSmsVo);
            checkResult(response);
        } catch (Exception e) {
            //接口请求异常，需要重新发送
            logger.error("发送短信失败：" + e.getMessage());
            return false;
        }
        return true;
    }
}
