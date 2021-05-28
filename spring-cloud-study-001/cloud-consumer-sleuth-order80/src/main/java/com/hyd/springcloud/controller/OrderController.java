package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        return  restTemplate.getForObject("http://localhost:8001/payment/zipkin/", String.class);
    }
}
