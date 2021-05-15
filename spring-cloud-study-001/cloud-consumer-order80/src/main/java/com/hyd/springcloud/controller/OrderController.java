package com.hyd.springcloud.controller;

import com.hyd.springcloud.entity.CommonResult;
import com.hyd.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping(value = "/consumer/payment")
public class OrderController {

//    public static final String PAYMENT_URL="http://localhost:8001";//服务提供者单机版
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";//服务提供者集群环境

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
