package com.nbug.module.user.framework.rpc.config;

import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.wechat.WeChatPayApi;
import com.nbug.module.infra.api.wechat.WechatNewApi;
import com.nbug.module.store.api.storeCoupon.StoreCouponApi;
import com.nbug.module.store.api.storeCouponUser.StoreCouponUserApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.store.api.storeProductRelation.StoreProductRelationApi;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.city.CityApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.system.api.systemMenu.SystemMenuApi;
import com.nbug.module.system.api.systemUserLevel.SystemUserLevelApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ConfigApi.class, AttachmentApi.class,
        CityApi.class, SystemUserLevelApi.class, SystemGroupDataApi.class,
        StoreCouponApi.class, StoreCouponApi.class, WechatNewApi.class, StoreCouponUserApi.class,
        StoreOrderApi.class, WeChatPayApi.class, AdminApi.class, StoreProductRelationApi.class,
        SystemMenuApi.class, SmsApi.class, StoreProductApi.class, StoreProductApi.class})
public class RpcConfiguration {
}
