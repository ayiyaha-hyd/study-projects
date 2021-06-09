package com.hyd.test;

import com.hyd.dao.UserDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    //测试scope属性等
    @Test
    public void test1(){
        //ApplicationContext
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao1 = (UserDao) context.getBean("userDao");
        UserDao userDao2 = (UserDao) context.getBean("userDao");
        System.out.println(userDao1);
        System.out.println(userDao2);
        System.out.println(userDao1 == userDao2);
        context.close();
    }

}
