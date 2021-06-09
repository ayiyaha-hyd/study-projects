package com.hyd.service.impl;

import com.hyd.dao.UserDao;
import com.hyd.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public void save() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        System.out.println("set方法注入userDao依赖...");
    }
}
