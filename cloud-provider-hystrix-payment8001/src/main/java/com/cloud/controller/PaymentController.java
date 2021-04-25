package com.cloud.controller;


import com.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/payment/hystrix")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

  /*  @Value("${server.port}")
    private String serverPort;*/

    @GetMapping("/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_ok(id);
        log.info("******result:" +result);
        return result;
    }

    @GetMapping("/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        String result = paymentService.paymentInfo_Timeout(id);
        log.info("******result:" +result);
        return result;
    }
    //======服务熔断
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreak(@PathVariable("id")Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("******result: "+result);
        return result;
    }

}
