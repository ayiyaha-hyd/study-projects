package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String serverPort;

    private static final String PROVIDER_SERVICE="cloud-alibaba-provider-nacos-payment-service";

    @GetMapping("/consumer/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return restTemplate.getForObject("http://"+PROVIDER_SERVICE+"/payment/nacos/"+id,String.class)+"---> server.port: "+serverPort;
    }
}
