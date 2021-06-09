package com.hyd.controller;

import com.hyd.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        UserService userService = (UserService) context.getBean("userService");
        UserService userService = context.getBean(UserService.class);
        userService.save();
    }
}
