package com.cloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{
    //初始值   访问次数统计的原子类
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    //getAndIncrement获取访问次数
    public final int getAndIncrement(){
        int current;
        int next;
        do{
            current = this.atomicInteger.get(); //获取当前客户端的下标
            next = current >=Integer.MAX_VALUE ? 0: current +1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("访问次数："+next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //serviceInstances.size()   服务段的总服务器数
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
