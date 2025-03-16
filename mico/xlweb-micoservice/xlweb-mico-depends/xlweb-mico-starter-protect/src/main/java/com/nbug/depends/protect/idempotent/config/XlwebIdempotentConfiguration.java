package com.nbug.depends.protect.idempotent.config;

import com.nbug.depends.protect.idempotent.core.aop.IdempotentAspect;
import com.nbug.depends.protect.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.nbug.depends.protect.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.nbug.depends.protect.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.nbug.depends.protect.idempotent.core.keyresolver.impl.UserIdempotentKeyResolver;
import com.nbug.depends.protect.idempotent.core.redis.IdempotentRedisDAO;
import com.nbug.depends.protect.redis.config.XlwebRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = XlwebRedisAutoConfiguration.class)
public class XlwebIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver() {
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
