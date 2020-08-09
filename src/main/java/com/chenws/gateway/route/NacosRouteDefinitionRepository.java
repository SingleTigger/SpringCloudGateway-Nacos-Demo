package com.chenws.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.chenws.gateway.constants.GatewayConstants.DYNAMIC_ROUTE_DATA_ID;
import static com.chenws.gateway.constants.GatewayConstants.DYNAMIC_ROUTE_GROUP_ID;

/**
 * Created by chenws on 2019/11/6.
 * 基于Nacos的动态路由
 * 实现RouteDefinitionRepository，加载自定义路由信息仓库
 * {@link org.springframework.cloud.gateway.config.GatewayAutoConfiguration}
 */
@Slf4j
public class NacosRouteDefinitionRepository extends AbstractRouteDefinitionRepository {


    private ApplicationEventPublisher publisher;

    private NacosConfigProperties nacosConfigProperties;

    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigProperties nacosConfigProperties) {
        this.publisher = publisher;
        this.nacosConfigProperties = nacosConfigProperties;
        addListener();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            //加载动态配置
            NacosConfigManager nacosConfigManager = new NacosConfigManager(nacosConfigProperties);
            String content = nacosConfigManager.getConfigService().getConfig(DYNAMIC_ROUTE_DATA_ID, DYNAMIC_ROUTE_GROUP_ID, 3000);
            if(StringUtils.isNotBlank(content)){
                List<RouteDefinition> routeDefinitions = JSONObject.parseArray(content, RouteDefinition.class);
                return Flux.fromIterable(Optional.ofNullable(routeDefinitions).orElseGet(ArrayList::new));
            }
        } catch (NacosException e) {
            log.error("获取动态路由配置信息失败", e);
        }
        return Flux.fromIterable(new ArrayList<>());
    }

    private void addListener() {
        try {
            NacosConfigManager nacosConfigManager = new NacosConfigManager(nacosConfigProperties);
            nacosConfigManager.getConfigService().addListener(DYNAMIC_ROUTE_DATA_ID, DYNAMIC_ROUTE_GROUP_ID, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("接收到Nacos配置更新事件");
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            log.error("Nacos添加监听器失败", e);
        }
    }

}
