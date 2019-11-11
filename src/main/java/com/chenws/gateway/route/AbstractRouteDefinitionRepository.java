package com.chenws.gateway.route;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Mono;

/**
 * Created by chenws on 2019/11/6.
 */
public abstract class AbstractRouteDefinitionRepository implements RouteDefinitionRepository {

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
