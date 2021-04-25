package com.cloud.conffig;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CateWayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        //http://news.baidu.com/game


        //return builder.routes().route("path_route_game",r->r.path("/game").uri("http://news.baidu.com/game")).build();
        return builder.routes().route("path_route_game",r->r.path("/game").uri("http://news.baidu.com/game"))
                .route("path_route_guoji",r->r.path("/guoji").uri("http://news.baidu.com/guoji")).build();

    }
}
