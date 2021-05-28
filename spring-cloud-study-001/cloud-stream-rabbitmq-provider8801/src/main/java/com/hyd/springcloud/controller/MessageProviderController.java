package com.hyd.springcloud.controller;

import com.hyd.springcloud.service.MessageProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProviderController {
    @Autowired
    private MessageProviderService messageProviderService;

    @GetMapping("/send")
    public String send(){
        return messageProviderService.send();
    }
}
