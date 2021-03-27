package com.hyd.provider.controller;

import com.hyd.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @GetMapping("/provide")
    public String provide(){
        return providerService.provide();
    }
}
