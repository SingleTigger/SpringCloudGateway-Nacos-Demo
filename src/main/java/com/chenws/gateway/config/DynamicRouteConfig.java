package com.chenws.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.chenws.gateway.route.NacosRouteDefinitionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenws on 2019/11/6.
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway.dynamic.route", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {

    private final ApplicationEventPublisher publisher;

    private final NacosConfigProperties nacosConfigProperties;

    public DynamicRouteConfig(ApplicationEventPublisher publisher, NacosConfigProperties nacosConfigProperties) {
        this.publisher = publisher;
        this.nacosConfigProperties = nacosConfigProperties;
    }

    @Bean
    public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
        return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
    }

}