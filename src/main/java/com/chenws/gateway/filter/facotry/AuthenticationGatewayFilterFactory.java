package com.chenws.gateway.filter.facotry;

import com.chenws.gateway.service.PathVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * Created by chenws on 2019/11/1.
 * 鉴权过滤器
 */
@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    private final PathVerifyService pathVerifyService;

    public AuthenticationGatewayFilterFactory(PathVerifyService pathVerifyService) {
        this.pathVerifyService = pathVerifyService;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            URI uri = request.getURI();
            String path = uri.getPath();
            log.info("鉴权过滤器，拦截的uri [{}]", path);
            //判断是否需要拦截
            boolean shouldFilter = pathVerifyService.shouldFilter(path);
            if (!shouldFilter) {
                return chain.filter(exchange);
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            }));
        };
    }

}
