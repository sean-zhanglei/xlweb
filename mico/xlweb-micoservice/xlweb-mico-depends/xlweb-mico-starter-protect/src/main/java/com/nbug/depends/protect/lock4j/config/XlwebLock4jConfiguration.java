package com.nbug.depends.protect.lock4j.config;

import com.baomidou.lock.spring.boot.autoconfigure.LockAutoConfiguration;
import com.nbug.depends.protect.lock4j.core.DefaultLockFailureStrategy;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = LockAutoConfiguration.class)
@ConditionalOnClass(name = "com.baomidou.lock.annotation.Lock4j")
public class XlwebLock4jConfiguration {

    @Bean
    public DefaultLockFailureStrategy lockFailureStrategy() {
        return new DefaultLockFailureStrategy();
    }

}
