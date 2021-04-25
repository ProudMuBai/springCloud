package com.cloud.service.impl;

import com.cloud.dao.OrderDao;
import com.cloud.domain.Order;
import com.cloud.service.AccountService;
import com.cloud.service.OrderService;
import com.cloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    /**
     * 创建订单-->调用库存服务扣减库存-->调用账户服务扣减账户余额-->修改订单状态
     * @param order 订单相关信息
     */
    @Override
    @GlobalTransactional(name = "my_seata",rollbackFor = Exception.class)
    public void create(Order order) {
        //新建订单
        log.info("----------->开始新建订单");
        orderDao.create(order);

        //扣减库存
        log.info("---------->订单微服务开始调用库存，做扣减");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("---------->Storage 库存扣减完成");

        //扣除账户金额
        log.info("---------->订单微服务开始调用账户，做扣减");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("---------->账户money account 扣减完成");

        //修改订单状态，从0到1,1代表已经完成
        log.info("--------------修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("------------修改订单状态结束");


        log.info("-----------订单已完成， ^_^");
    }
}
