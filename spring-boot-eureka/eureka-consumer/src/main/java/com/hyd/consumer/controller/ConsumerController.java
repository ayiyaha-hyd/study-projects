package com.hyd.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/consume")
    public String consume(){
        System.out.println("开始调用服务...");
        String resp = restTemplate.getForObject("http://EUREKA-PROVIDER/provide", String.class);
        return resp;
    }
}
