package com.cloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_ok(Integer id) {
        return "********PaymentFallbackService fall back-paymentInfo_ok,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "********PaymentFallbackService fall back-paymentInfo_Timeout,o(╥﹏╥)o";
    }
}
