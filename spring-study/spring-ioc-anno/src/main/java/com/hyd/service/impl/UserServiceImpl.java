package com.hyd.service.impl;

import com.hyd.dao.UserDao;
import com.hyd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//<bean id="userService" class="com.hyd.service.impl.UserServiceImpl">
@Component("userService")
public class UserServiceImpl implements UserService {
    @Value("${jdbc.driver}")
    private String driver;

    //<property name="userDao" ref="userService"/>
    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void save() {
//        System.out.println(driver);
        userDao.save();
    }

    @PostConstruct
    public void init(){
        System.out.println("init()...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("destroy()...");
    }

}
