package com.chenws.gateway.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.chenws.gateway.constants.GatewayConstants.IP_ADDRESS;

/**
 * Created by chenws on 2019/11/4.
 */
@Component
@Slf4j
public class IpKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String ipAddress = exchange.getAttribute(IP_ADDRESS);
        log.info("IpKeyResolver 限流key : {}",ipAddress);
        return Mono.just(Optional.ofNullable(ipAddress).orElse(""));
    }

}

