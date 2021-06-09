package com.hyd.test;

import com.hyd.config.SpringConfig;
import com.hyd.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class BeanTest {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        DataSource dataSource = (DataSource)applicationContext.getBean("dataSource");
        System.out.println(dataSource);
        applicationContext.close();
    }
    @Test
    public void save(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean(UserService.class);
        userService.save();
        applicationContext.close();
    }
}
