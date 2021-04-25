package com.cloud.cloud;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;


@Component
@Slf4j
public class MyGateWayFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("-----------come in MyGateWayFilter:"+new Date());
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if (username==null){
            log.info("---------用户名为空，拒绝访问，_(¦3」∠)_");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//设置返回的http状态码
            return  exchange.getResponse().setComplete();//返回响应
        }
        return chain.filter(exchange);//满足条件放行
    }

    @Override
    public int getOrder() {
        //路由优先级，越小越优先
        return 0;
    }
}
