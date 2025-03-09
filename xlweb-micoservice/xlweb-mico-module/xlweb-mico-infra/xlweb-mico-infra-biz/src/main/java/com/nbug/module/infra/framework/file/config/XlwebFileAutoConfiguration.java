package com.nbug.module.infra.framework.file.config;

import com.nbug.module.infra.framework.file.core.client.FileClientFactory;
import com.nbug.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author NBUG
 */
@Configuration(proxyBeanMethods = false)
public class XlwebFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
