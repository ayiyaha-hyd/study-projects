package com.hyd.web;

import com.hyd.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        req.getServletContext();
        ServletContext servletContext = this.getServletContext();
//        ApplicationContext context = (ApplicationContext) servletContext.getAttribute("applicationContext");
//        ApplicationContext context = WebApplicationContextUtil.getApplicationContext(servletContext);
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserService userService =  (UserService)context.getBean("userService");
       userService.save();
    }
}
