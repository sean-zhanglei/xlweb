package com.nbug.depends.rpc.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@AutoConfiguration
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
public class XlwebFeignAutoConfiguration {

}