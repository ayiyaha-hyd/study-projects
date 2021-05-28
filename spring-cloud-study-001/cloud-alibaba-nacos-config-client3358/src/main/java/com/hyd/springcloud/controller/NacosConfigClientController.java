package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosConfigClientController {
    @Autowired
    private Environment environment;

    @GetMapping("/nacos/config")
    public String getConfig(){
        String applicationName = environment.getProperty("spring.application.name");
        String name = environment.getProperty("user.name");
        String age = environment.getProperty("user.age");
        String profile = environment.getProperty("user.profile");
        return applicationName+" ,\t"+name+" ,\t"+age+" ,\t"+profile;
    }
}
