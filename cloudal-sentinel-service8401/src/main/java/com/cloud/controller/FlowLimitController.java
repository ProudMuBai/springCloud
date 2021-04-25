package com.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        //暂停毫秒,模拟线程数限制
        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "-------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        return "-------testB";
    }

    //测试服务熔断机制(1秒内响应次数达到五次且平均时间超时)
    @GetMapping("testC")
    public String testC(){
        //RT平均响应时间
       /* try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //异常比例
        int age = 1/0;

        log.info("testC 测试");
        return "--------testC";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){
        return "-------testHotKey";
    }
    //设置热点key异常时的兜底方法
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        //sentinel 系统默认的提示，Blocked by sentinel  (flow limiting)
        return  "------deal_testHotKey,o(╥﹏╥)o";
    }


}
