package com.nbug.module.user.framework.rpc.config;

import com.nbug.module.infra.api.attachment.AttachmentApi;
import com.nbug.module.infra.api.config.ConfigApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {ConfigApi.class, AttachmentApi.class})
public class RpcConfiguration {
}
