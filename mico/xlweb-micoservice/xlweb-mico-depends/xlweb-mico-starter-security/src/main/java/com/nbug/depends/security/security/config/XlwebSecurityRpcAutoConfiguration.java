package com.nbug.depends.security.security.config;

import com.nbug.depends.security.security.core.rpc.LoginUserRequestInterceptor;
import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.system.api.userDetail.UserDetailApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author NBUG
 */
@AutoConfiguration
@EnableFeignClients(clients = {// 主要是引入相关的 API 服务
        AttachmentApi.class, UserDetailApi.class})
public class XlwebSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
