package com.hyd.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String configLocation = servletContext.getInitParameter("configLocation");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocation);
        servletContext.setAttribute("applicationContext", applicationContext);
        System.out.println("spring容器初始化完毕...");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
