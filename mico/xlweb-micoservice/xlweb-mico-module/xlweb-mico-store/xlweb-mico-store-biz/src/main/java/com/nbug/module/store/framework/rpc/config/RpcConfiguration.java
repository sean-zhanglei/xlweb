package com.nbug.module.store.framework.rpc.config;

import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.infra.api.sms.NotificationApi;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.infra.api.sms.SmsTemplateApi;
import com.nbug.module.infra.api.sms.TemplateMessageApi;
import com.nbug.module.infra.api.wechat.WechatNewApi;
import com.nbug.module.order.api.OrderApi;
import com.nbug.module.system.api.admin.AdminApi;
import com.nbug.module.system.api.category.CategoryApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.system.api.express.ExpressApi;
import com.nbug.module.system.api.logistic.LogisticApi;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.system.api.systemStore.SystemStoreApi;
import com.nbug.module.system.api.systemUserLevel.SystemUserLevelApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userBrokerageRecord.UserBrokerageRecordApi;
import com.nbug.module.user.api.userExperience.UserExperienceApi;
import com.nbug.module.user.api.userIntegralRecord.UserIntegralRecordApi;
import com.nbug.module.user.api.userLevel.UserLevelApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import com.nbug.module.user.api.userVisitRecord.UserVisitRecordApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ConfigApi.class, AttachmentApi.class, UserApi.class, UserLevelApi.class,
        UserIntegralRecordApi.class, UserExperienceApi.class,UserBrokerageRecordApi.class,
        UserVisitRecordApi.class, UserBillApi.class, UserTokenApi.class,
        TemplateMessageApi.class, SmsApi.class, SmsTemplateApi.class, SmsTemplateApi.class, NotificationApi.class,
        OrderApi.class, WechatNewApi.class, AdminApi.class, SystemStoreApi.class, ExpressApi.class, LogisticApi.class,
        SystemGroupDataApi.class, CategoryApi.class, SystemUserLevelApi.class})
public class RpcConfiguration {
}
