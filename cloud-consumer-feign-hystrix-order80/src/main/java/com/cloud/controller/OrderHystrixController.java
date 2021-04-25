
package com.cloud.controller;

import com.cloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/consumer/payment/hystrix")
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping("/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){

        return paymentHystrixService.paymentInfo_ok(id);
    }

    @GetMapping("/timeout/{id}")
    /*@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })*/
    @HystrixCommand
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
        int age= 10/0;
        return paymentHystrixService.paymentInfo_Timeout(id);
    }

    public String paymentTimeOutFallbackMethod(Integer id){
        return "消费者80，对法支付系统繁忙，请稍后再试或自己运行出错请自检"+id+"\t(╥﹏╥)o";
    }

    //全局fallback方法,不能携带参数
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后再试，_(¦3」∠)_";
    }

}