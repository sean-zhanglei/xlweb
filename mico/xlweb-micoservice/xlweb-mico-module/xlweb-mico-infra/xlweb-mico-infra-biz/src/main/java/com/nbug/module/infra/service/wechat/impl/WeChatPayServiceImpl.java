package com.nbug.module.infra.service.wechat.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.constants.PayConstants;
import com.nbug.mico.common.constants.TaskConstants;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.model.order.StoreOrder;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.model.wechat.WechatPayInfo;
import com.nbug.mico.common.utils.RestTemplateUtil;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.utils.XmlUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.utils.wx.WxPayUtil;
import com.nbug.mico.common.vo.AttachVo;
import com.nbug.mico.common.vo.CreateOrderH5SceneInfoDetailVo;
import com.nbug.mico.common.vo.CreateOrderH5SceneInfoVo;
import com.nbug.mico.common.vo.CreateOrderRequestVo;
import com.nbug.mico.common.vo.CreateOrderResponseVo;
import com.nbug.mico.common.vo.MyRecord;
import com.nbug.module.infra.service.pay.RechargePayService;
import com.nbug.module.infra.service.wechat.WeChatPayService;
import com.nbug.module.infra.service.wechat.WechatNewService;
import com.nbug.module.infra.service.wechat.WechatPayInfoService;
import com.nbug.module.store.api.storeCombination.StoreCombinationApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storePink.StorePinkApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userRecharge.UserRechargeApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 微信支付

 */
@Data
@Service
public class WeChatPayServiceImpl implements WeChatPayService {
    private static final Logger logger = LoggerFactory.getLogger(WeChatPayServiceImpl.class);

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private UserTokenApi userTokenApi;

    @Autowired
    private StoreOrderApi storeOrderApi;

    @Autowired
    private ConfigApi configApi;

    private String signKey;

    private CreateOrderRequestVo createOrderRequestVo;

    private CreateOrderResponseVo createOrderResponseVo = null;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserRechargeApi userRechargeApi;

    @Autowired
    private RechargePayService rechargePayService;

    @Autowired
    private StoreCombinationApi storeCombinationApi;

    @Autowired
    private StorePinkApi storePinkApi;

    @Autowired
    private WechatNewService wechatNewService;

    @Autowired
    private WechatPayInfoService wechatPayInfoService;

    /**
     * 查询支付结果
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public Boolean queryPayResult(String orderNo) {
        if (StrUtil.isBlank(orderNo)) {
            throw new XlwebException("订单编号不能为空");
        }
        // 切割字符串，判断是支付订单还是充值订单
        String pre = StrUtil.subPre(orderNo, 5);
        if ("order".equals(pre)) {// 支付订单
            StoreOrder storeOrder = storeOrderApi.getByOderId(orderNo).getCheckedData();
            if (ObjectUtil.isNull(storeOrder)) {
                throw new XlwebException("订单不存在");
            }
            if (storeOrder.getIsDel()) {
                throw new XlwebException("订单已被删除");
            }
            if (!storeOrder.getPayType().equals(PayConstants.PAY_TYPE_WE_CHAT)) {
                throw new XlwebException("不是微信支付类型订单，请重新选择支付方式");
            }

            if (storeOrder.getPaid()) {
                return Boolean.TRUE;
            }

            WechatPayInfo wechatPayInfo = wechatPayInfoService.getByNo(storeOrder.getOutTradeNo());
            if (ObjectUtil.isNull(wechatPayInfo)) {
                throw new XlwebException("未找到对应微信订单");
            }

            User user = userApi.getById(storeOrder.getUid()).getCheckedData();
            if (ObjectUtil.isNull(user)) throw new XlwebException("用户不存在");


            // 获取appid、mch_id
            // 微信签名key
            String appId = "";
            String mchId = "";
            String signKey = "";
            if (storeOrder.getIsChannel() == 0) {// 公众号
                appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
                mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
                signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
            }
            if (storeOrder.getIsChannel() == 1) {// 小程序
                appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID).getCheckedData();
                mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID).getCheckedData();
                signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY).getCheckedData();
            }
            if (storeOrder.getIsChannel() == 2) {// H5
                appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
                mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
                signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
            }

            // 生成查询订单对象
            Map<String, String> payVo = getWxChantQueryPayVo(wechatPayInfo.getOutTradeNo(), appId, mchId, signKey);
            // 查询订单信息
            MyRecord record = wechatNewService.payOrderQuery(payVo);

            wechatPayInfo.setIsSubscribe(record.getStr("is_subscribe"));
            wechatPayInfo.setTradeState(record.getStr("trade_state"));
            wechatPayInfo.setBankType(record.getStr("bank_type"));
            wechatPayInfo.setCashFee(record.getInt("cash_fee"));
            wechatPayInfo.setCouponFee(record.getInt("coupon_fee"));
            wechatPayInfo.setTransactionId(record.getStr("transaction_id"));
            wechatPayInfo.setTimeEnd(record.getStr("time_end"));
            wechatPayInfo.setTradeStateDesc(record.getStr("trade_state_desc"));

            Boolean updatePaid = transactionTemplate.execute(e -> {
                storeOrderApi.updatePaid(orderNo);
                wechatPayInfoService.updateById(wechatPayInfo);
                if (storeOrder.getUseIntegral() > 0) {
                    userApi.updateIntegral(user, storeOrder.getUseIntegral(), "sub");
                }
                // 处理拼团
                if (storeOrder.getCombinationId() > 0) {
                    // 判断拼团团长是否存在
                    StorePink headPink = new StorePink();
                    Integer pinkId = storeOrder.getPinkId();
                    if (pinkId > 0) {
                        headPink = storePinkApi.getById(pinkId).getCheckedData();
                        if (ObjectUtil.isNull(headPink) || headPink.getIsRefund().equals(true) || headPink.getStatus() == 3) {
                            pinkId = 0;
                        }
                    }
                    StoreCombination storeCombination = storeCombinationApi.getById(storeOrder.getCombinationId()).getCheckedData();
                    // 如果拼团人数已满，重新开团
                    if (pinkId > 0) {
                        Integer count = storePinkApi.getCountByKid(pinkId).getCheckedData();
                        if (count >= storeCombination.getPeople()) {
                            pinkId = 0;
                        }
                    }
                    // 生成拼团表数据
                    StorePink storePink = new StorePink();
                    storePink.setUid(user.getUid());
                    storePink.setAvatar(user.getAvatar());
                    storePink.setNickname(user.getNickname());
                    storePink.setOrderId(storeOrder.getOrderId());
                    storePink.setOrderIdKey(storeOrder.getId());
                    storePink.setTotalNum(storeOrder.getTotalNum());
                    storePink.setTotalPrice(storeOrder.getTotalPrice());
                    storePink.setCid(storeCombination.getId());
                    storePink.setPid(storeCombination.getProductId());
                    storePink.setPeople(storeCombination.getPeople());
                    storePink.setPrice(storeCombination.getPrice());
                    Integer effectiveTime = storeCombination.getEffectiveTime();// 有效小时数
                    DateTime dateTime = cn.hutool.core.date.DateUtil.date();
                    storePink.setAddTime(dateTime.getTime());
                    if (pinkId > 0) {
                        storePink.setStopTime(headPink.getStopTime());
                    } else {
                        DateTime hourTime = cn.hutool.core.date.DateUtil.offsetHour(dateTime, effectiveTime);
                        long stopTime =  hourTime.getTime();
                        if (stopTime > storeCombination.getStopTime()) {
                            stopTime = storeCombination.getStopTime();
                        }
                        storePink.setStopTime(stopTime);
                    }
                    storePink.setKId(pinkId);
                    storePink.setIsTpl(false);
                    storePink.setIsRefund(false);
                    storePink.setStatus(1);
                    storePinkApi.save(storePink);
                    // 如果是开团，需要更新订单数据
                    storeOrder.setPinkId(storePink.getId());
                    storeOrderApi.updateById(storeOrder);
                }
                return Boolean.TRUE;
            });
            if (!updatePaid) {
                throw new XlwebException("支付成功更新订单失败");
            }
            // 添加支付成功task
            redisUtil.lPush(TaskConstants.ORDER_TASK_PAY_SUCCESS_AFTER, orderNo);
            return Boolean.TRUE;
        }
        // 充值订单
        UserRecharge userRecharge = new UserRecharge();
        userRecharge.setOrderId(orderNo);
        userRecharge = userRechargeApi.getInfoByEntity(userRecharge).getCheckedData();
        if (ObjectUtil.isNull(userRecharge)) {
            throw new XlwebException("没有找到订单信息");
        }
        if (userRecharge.getPaid()) {
            return Boolean.TRUE;
        }
        // 查询订单
        // 获取appid、mch_id
        // 微信签名key
        String appId = "";
        String mchId = "";
        String signKey = "";
        if ("public".equals(userRecharge.getRechargeType())) {// 公众号
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
        }
        if ("routine".equals(userRecharge.getRechargeType())) {// 小程序
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY).getCheckedData();
        }
        // 生成查询订单对象
        Map<String, String> payVo = getWxChantQueryPayVo(orderNo, appId, mchId, signKey);
        // 查询订单信息
        MyRecord record = wechatNewService.payOrderQuery(payVo);
        // 支付成功处理
        Boolean rechargePayAfter = rechargePayService.paySuccess(userRecharge);
        if (!rechargePayAfter) {
            throw new XlwebException("wechat pay error : 数据保存失败==》" + orderNo);
        }
        return rechargePayAfter;
    }

    /**
     * 微信充值预下单接口
     * @param userRecharge 充值订单
     * @param clientIp      ip
     * @return
     */
    @Override
    public Map<String, String> unifiedRecharge(UserRecharge userRecharge, String clientIp) {
        if (ObjectUtil.isNull(userRecharge)) {
            throw new XlwebException("订单不存在");
        }
        // 获取用户openId
        // 根据订单支付类型来判断获取公众号openId还是小程序openId
        UserToken userToken = new UserToken();
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_PUBLIC)) {// 公众号
            userToken = userTokenApi.getTokenByUserId(userRecharge.getUid(), 1).getCheckedData();
        }
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_PROGRAM)) {// 小程序
            userToken = userTokenApi.getTokenByUserId(userRecharge.getUid(), 2).getCheckedData();
        }
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_H5)) {// H5
            userToken.setToken("");
        }

        if (ObjectUtil.isNull(userToken)) {
            throw new XlwebException("该用户没有openId");
        }

        // 获取appid、mch_id
        // 微信签名key
        String appId = "";
        String mchId = "";
        String signKey = "";
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_PUBLIC)) {// 公众号
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
        }
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_PROGRAM)) {// 小程序
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_ROUTINE_APP_KEY).getCheckedData();
        }
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_H5)) {// H5,使用公众号的
            appId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_ID).getCheckedData();
            mchId = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_MCH_ID).getCheckedData();
            signKey = configApi.getValueByKeyException(Constants.CONFIG_KEY_PAY_WE_CHAT_APP_KEY).getCheckedData();
        }

        // 获取微信预下单对象
        CreateOrderRequestVo unifiedorderVo = getUnifiedorderVo(userRecharge, userToken.getToken(), clientIp, appId, mchId, signKey);
        // 预下单
        CreateOrderResponseVo responseVo = unifiedOrder(unifiedorderVo);

        // 组装前端预下单参数
        Map<String, String> map = new HashMap<>();
        map.put("appId", unifiedorderVo.getAppid());
        map.put("nonceStr", unifiedorderVo.getNonce_str());
        map.put("package", "prepay_id=".concat(responseVo.getPrepayId()));
        map.put("signType", unifiedorderVo.getSign_type());
        Long currentTimestamp = WxPayUtil.getCurrentTimestamp();
        map.put("timeStamp", Long.toString(currentTimestamp));
        String paySign = WxPayUtil.getSign(map, signKey);
        map.put("paySign", paySign);
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_H5)) {
            map.put("mweb_url", responseVo.getMWebUrl());
        }
        return map;
    }

    /**
     * 生成微信查询订单对象
     * @return
     */
    private Map<String, String> getWxChantQueryPayVo(String orderNo, String appId, String mchId, String signKey) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("mch_id", mchId);
        map.put("out_trade_no", orderNo);
        map.put("nonce_str", WxPayUtil.getNonceStr());
        map.put("sign_type", PayConstants.WX_PAY_SIGN_TYPE_MD5);
        map.put("sign", WxPayUtil.getSign(map, signKey));
        return map;
    }

    /**
     * 获取微信预下单对象
     * @return
     */
    private CreateOrderRequestVo getUnifiedorderVo(UserRecharge userRecharge, String openid, String ip, String appId, String mchId, String signKey) {

        // 获取域名
        String domain = configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_URL).getCheckedData();
        String apiDomain = configApi.getValueByKeyException(Constants.CONFIG_KEY_API_URL).getCheckedData();

        AttachVo attachVo = new AttachVo(Constants.SERVICE_PAY_TYPE_RECHARGE, userRecharge.getUid());
        CreateOrderRequestVo vo = new CreateOrderRequestVo();

        vo.setAppid(appId);
        vo.setMch_id(mchId);
        vo.setNonce_str(WxPayUtil.getNonceStr());
        vo.setSign_type(PayConstants.WX_PAY_SIGN_TYPE_MD5);
        vo.setBody(PayConstants.PAY_BODY);
        vo.setAttach(JSONObject.toJSONString(attachVo));
        vo.setOut_trade_no(userRecharge.getOrderId());
        // 订单中使用的是BigDecimal,这里要转为Integer类型
        vo.setTotal_fee(userRecharge.getPrice().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).intValue());
        vo.setSpbill_create_ip(ip);
        vo.setNotify_url(apiDomain + PayConstants.WX_PAY_NOTIFY_API_URI);
        vo.setTrade_type(PayConstants.WX_PAY_TRADE_TYPE_JS);
        vo.setOpenid(openid);
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_H5)) {// H5
            vo.setTrade_type(PayConstants.WX_PAY_TRADE_TYPE_H5);
            vo.setOpenid(null);
        }
        if (userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_APP_IOS) || userRecharge.getRechargeType().equals(PayConstants.PAY_CHANNEL_WE_CHAT_APP_ANDROID)) {
            vo.setTrade_type("APP");
            vo.setOpenid(null);
        }
        CreateOrderH5SceneInfoVo createOrderH5SceneInfoVo = new CreateOrderH5SceneInfoVo(
                new CreateOrderH5SceneInfoDetailVo(
                        domain,
                        configApi.getValueByKeyException(Constants.CONFIG_KEY_SITE_NAME).getCheckedData()
                )
        );
        vo.setScene_info(JSONObject.toJSONString(createOrderH5SceneInfoVo));
        String sign = WxPayUtil.getSign(vo, signKey);
        vo.setSign(sign);
        return vo;
    }

    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param vo 向wxpay post的请求数据
     * @return API返回数据
     */
    private CreateOrderResponseVo unifiedOrder(CreateOrderRequestVo vo) {
        try {
            String url = PayConstants.WX_PAY_API_URL + PayConstants.WX_PAY_API_URI;
            String request = XmlUtil.objectToXml(vo);
            String xml = restTemplateUtil.postXml(url, request);
            HashMap<String, Object> map = XmlUtil.xmlToMap(xml);
            if (null == map) {
                throw new XlwebException("微信下单失败！");
            }
            CreateOrderResponseVo responseVo = XlwebUtil.mapToObj(map, CreateOrderResponseVo.class);
            if ("FAIL".equals(responseVo.getReturnCode().toUpperCase())) {
                throw new XlwebException("微信下单失败1！" +  responseVo.getReturnMsg());
            }

            if ("FAIL".equals(responseVo.getResultCode().toUpperCase())) {
                throw new XlwebException("微信下单失败2！" + responseVo.getErrCodeDes());
            }

            responseVo.setExtra(vo.getScene_info());
            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new XlwebException(e.getMessage());
        }
    }

}
