package com.nbug.depends.tenant.config;

import com.nbug.depends.tenant.core.rpc.TenantRequestInterceptor;
import com.nbug.module.system.api.tenant.TenantApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(prefix = "xlweb.tenant", value = "enable", matchIfMissing = true) // 允许使用 xlweb.tenant.enable=false 禁用多租户
@EnableFeignClients(clients = TenantApi.class) // 主要是引入相关的 API 服务
public class XlwebTenantRpcAutoConfiguration {

    @Bean
    public TenantRequestInterceptor tenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}
