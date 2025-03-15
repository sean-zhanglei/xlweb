package com.nbug.module.order.framework.rpc.config;

import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.infra.api.sms.NotificationApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.sms.SmsTemplateApi;
import com.nbug.module.infra.api.sms.TemplateMessageApi;
import com.nbug.module.infra.api.wechat.WeChatPayApi;
import com.nbug.module.infra.api.wechat.WechatNewApi;
import com.nbug.module.infra.api.yly.YlyApiApi;
import com.nbug.module.store.api.storeBargain.StoreBargainApi;
import com.nbug.module.store.api.storeBargainUser.StoreBargainUserApi;
import com.nbug.module.store.api.storeCart.StoreCartApi;
import com.nbug.module.store.api.storeCombination.StoreCombinationApi;
import com.nbug.module.store.api.storeCoupon.StoreCouponApi;
import com.nbug.module.store.api.storeCouponUser.StoreCouponUserApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeOrderInfo.StoreOrderInfoApi;
import com.nbug.module.store.api.storeOrderStatus.StoreOrderStatusApi;
import com.nbug.module.store.api.storeOrderTask.StoreOrderTaskApi;
import com.nbug.module.store.api.storePink.StorePinkApi;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.store.api.storeProductAttrValue.StoreProductAttrValueApi;
import com.nbug.module.store.api.storeProductCoupon.StoreProductCouponApi;
import com.nbug.module.store.api.storeProductReply.StoreProductReplyApi;
import com.nbug.module.store.api.storeSeckill.StoreSeckillApi;
import com.nbug.module.store.api.storeSeckillManger.StoreSeckillMangerApi;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.express.ExpressApi;
import com.nbug.module.system.api.logistic.LogisticApi;
import com.nbug.module.system.api.shippingTemplates.ShippingTemplatesApi;
import com.nbug.module.system.api.shippingTemplatesFree.ShippingTemplatesFreeApi;
import com.nbug.module.system.api.shippingTemplatesRegion.ShippingTemplatesRegionApi;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.system.api.systemStore.SystemStoreApi;
import com.nbug.module.system.api.systemUserLevel.SystemUserLevelApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userAddress.UserAddressApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userExperience.UserExperienceApi;
import com.nbug.module.user.api.userIntegralRecord.UserIntegralRecordApi;
import com.nbug.module.user.api.userLevel.UserLevelApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {StoreOrderApi.class, UserTokenApi.class, ConfigApi.class,
        UserApi.class, SmsApi.class, UserBillApi.class, TemplateMessageApi.class, UserLevelApi.class,
        UserBrokerageRecordApi.class, UserIntegralRecordApi.class, UserExperienceApi.class, YlyApiApi.class,
        NotificationApi.class, SmsTemplateApi.class, UserAddressApi.class, AttachmentApi.class,
        StoreOrderStatusApi.class, StoreOrderInfoApi.class, StoreProductCouponApi.class, StoreOrderStatusApi.class,
        StoreCouponUserApi.class, StoreCartApi.class, StoreOrderStatusApi.class, StoreOrderTaskApi.class,
        StoreProductReplyApi.class, StoreProductApi.class, StoreBargainApi.class, StoreCombinationApi.class,
        StoreSeckillApi.class, StorePinkApi.class, StoreCouponApi.class, StoreProductAttrValueApi.class,
        StoreBargainUserApi.class, StoreSeckillMangerApi.class, WechatNewApi.class,
        SystemStoreApi.class, ExpressApi.class, LogisticApi.class, AdminApi.class,
        SystemUserLevelApi.class, SystemGroupDataApi.class, WeChatPayApi.class,
        ShippingTemplatesFreeApi.class, ShippingTemplatesRegionApi.class, ShippingTemplatesApi.class})
public class RpcConfiguration {
}
