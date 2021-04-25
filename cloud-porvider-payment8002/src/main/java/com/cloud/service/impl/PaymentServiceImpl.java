package com.cloud.service.impl;

import com.cloud.dao.PaymentDao;
import com.cloud.entities.Payment;
import com.cloud.service.PaymentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    //等效于@Autowired,自动注入
    @Resource
    private PaymentDao paymentDao;

    public  int create(Payment payment){

        return paymentDao.create(payment);
    }


    public Payment getPaymentById(Long id){

        return paymentDao.getPaymentById(id);
    }


}
