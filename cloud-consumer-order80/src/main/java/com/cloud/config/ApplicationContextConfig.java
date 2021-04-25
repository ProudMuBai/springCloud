package com.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    //@LoadBalanced   赋予RestTemplate负载均衡的能力
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){

        return  new RestTemplate();
    }
}
