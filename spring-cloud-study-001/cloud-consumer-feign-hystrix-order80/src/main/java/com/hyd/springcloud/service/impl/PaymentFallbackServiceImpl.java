package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "fallback --> paymentInfo_OK() :"+id;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "fallback --> paymentInfo_TimeOut() :"+id;
    }
}
