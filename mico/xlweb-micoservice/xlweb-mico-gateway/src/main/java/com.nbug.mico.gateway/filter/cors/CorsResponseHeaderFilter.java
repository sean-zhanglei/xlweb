package com.nbug.mico.gateway.filter.cors;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解决 Spring Cloud Gateway 2.x 跨域时，出现重复 Origin 的 BUG
 *
 * 参考文档：<a href="https://blog.csdn.net/zimou5581/article/details/90043178" />
 *
 * @author NBUG
 */
@Component
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        // 指定此过滤器位于 NettyWriteResponseFilter 之后
        // 即待处理完响应体后接着处理响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            Set<Map.Entry<String, List<String>>> respHeaders =  exchange.getResponse().getHeaders().entrySet();
            Set<Map.Entry<String, List<String>>> filterHeaders = respHeaders.stream()
                    .filter(Objects::nonNull)
                    .filter(kv ->  kv.getKey() != null && kv.getValue() != null && ! kv.getValue().isEmpty()) // 额外增加空值检查
                    .filter(kv -> (kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)
                            || kv.getKey().equals(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS))).collect(Collectors.toSet());
            if (filterHeaders.size() > 0) {
                filterHeaders.forEach(kv -> {
                    List<String> newValue = new ArrayList<>();
                    if (!kv.getValue().isEmpty()) { // 再次确保值不为空
                        newValue.add(kv.getValue().get(0)); // 取第一个值
                    }
                    kv.setValue(newValue); // 设置新的值
                    exchange.getResponse().getHeaders().put(kv.getKey(), kv.getValue());
                });
            }
            return chain.filter(exchange);
        }));
    }

}
