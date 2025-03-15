package com.nbug.module.infra.framework.rpc.config;

import com.nbug.module.store.api.storeBargain.StoreBargainApi;
import com.nbug.module.store.api.storeCombination.StoreCombinationApi;
import com.nbug.module.store.api.storeOrder.StoreOrderApi;
import com.nbug.module.store.api.storeOrderInfo.StoreOrderInfoApi;
import com.nbug.module.store.api.storePink.StorePinkApi;
import com.nbug.module.store.api.storeProduct.StoreProductApi;
import com.nbug.module.system.api.article.ArticleApi;
import com.nbug.module.system.api.category.CategoryApi;
import com.nbug.module.system.api.config.ConfigApi;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.api.userBill.UserBillApi;
import com.nbug.module.user.api.userCenter.UserCenterApi;
import com.nbug.module.user.api.userRecharge.UserRechargeApi;
import com.nbug.module.user.api.userToken.UserTokenApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {UserApi.class, UserBillApi.class, UserRechargeApi.class, UserTokenApi.class,
        StoreOrderApi.class, StoreCombinationApi.class, StorePinkApi.class, StoreProductApi.class,
        StoreBargainApi.class, CategoryApi.class, ConfigApi.class, StoreOrderInfoApi.class, UserCenterApi.class, ArticleApi.class} )
public class RpcConfiguration {
}
