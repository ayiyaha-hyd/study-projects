package com.hyd.consumer.service.impl;

import com.hyd.consumer.service.ConsumerService;
import com.hyd.provider.service.ProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @DubboReference
    ProviderService providerService;
    @Override
    public void consume() {
        System.out.println("调用provide()方法...");
       String result =  providerService.provide();
        System.out.println(result);
    }
}
