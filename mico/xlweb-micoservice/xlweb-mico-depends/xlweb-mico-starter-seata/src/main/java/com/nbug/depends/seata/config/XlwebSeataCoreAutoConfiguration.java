package com.nbug.depends.seata.config;

import org.apache.seata.spring.boot.autoconfigure.provider.SpringApplicationContextProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@ConditionalOnProperty(
        prefix = "seata",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@ComponentScan(
        basePackages = {"org.apache.seata.spring.boot.autoconfigure.properties"}
)
@Configuration(
        proxyBeanMethods = false
)
@AutoConfigureAfter({FlywayAutoConfiguration.class})
public class XlwebSeataCoreAutoConfiguration {
    public XlwebSeataCoreAutoConfiguration() {
    }

    @Bean({"springApplicationContextProvider"})
    @DependsOn("flywayInitializer")
    @ConditionalOnMissingBean(
            name = {"springApplicationContextProvider"}
    )
    public SpringApplicationContextProvider springApplicationContextProvider() {
        return new SpringApplicationContextProvider();
    }
}