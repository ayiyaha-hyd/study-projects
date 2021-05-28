package com.hyd.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "provider ---> paymentZipkin() 调用成功!";
    }
}
