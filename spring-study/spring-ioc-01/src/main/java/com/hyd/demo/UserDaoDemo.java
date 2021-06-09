package com.hyd.demo;

import com.hyd.dao.UserDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoDemo {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();
    }
}
