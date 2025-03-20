package com.nbug.depends.rpc.config;

import com.nbug.mico.common.config.XlwebConfig;
import feign.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({XlwebConfig.class})
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(XlwebConfig xlwebConfig) {
        if ("NONE".equals(xlwebConfig.getFeignLoggerLevel())) {
            return Logger.Level.NONE;
        } else if ("BASIC".equals(xlwebConfig.getFeignLoggerLevel())) {
            return Logger.Level.BASIC;
        } else if ("HEADERS".equals(xlwebConfig.getFeignLoggerLevel())) {
            return Logger.Level.HEADERS;
        } else if ("FULL".equals(xlwebConfig.getFeignLoggerLevel())) {
            return Logger.Level.FULL;
        } else {
            return Logger.Level.NONE;
        }
    }
}