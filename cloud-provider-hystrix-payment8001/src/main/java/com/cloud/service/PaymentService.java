package com.cloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import sun.security.x509.SerialNumber;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问，不会出错的方法;
     * @param id
     * @return
     */
    public String paymentInfo_ok(Integer id){
        return  "线程池：" +Thread.currentThread().getName()+"paymmentInfo_ok,id:  "+id+"\t"+"好心累啊";
    }
    /*服务降级注解，设置3秒超时后调用兜底方法*/
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){
        int timeNumber = 3;
        //int timeNumber = 5;//模拟超时响应
        //int sum = 10/0;
        try { TimeUnit.SECONDS.sleep(timeNumber);} catch (InterruptedException e) {e.printStackTrace();}
        //return  "线程池：" +Thread.currentThread().getName()+"paymmentInfo_Timeout,id:  "+id+"\t"+"耗时："+timeNumber;
        return  "线程池：" +Thread.currentThread().getName()+"paymmentInfo_Timeout,id:  "+id+"\t"+"耗时："+"秒";
    }
    /*服务降级时调用的兜底方法*/
    public String paymentInfo_TimeOutHandler(Integer id){
        return  "线程池：8001系统出错" +Thread.currentThread().getName()+"paymentInfo_TimeOutHandler,id:  "+id+"\t"+"o(╥﹏╥)o";
    }

    //========服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties ={
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期,熔断后的计时时间
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
    })
    public  String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id <0){
            throw new RuntimeException("*******id 不能为负数");
        }
        String SerialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t 调用成功"+ SerialNumber;
    }
    //熔断兜底方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return  "id 不能为负数，请稍后再试，(＃￣～￣＃)"+id;
    }

}
