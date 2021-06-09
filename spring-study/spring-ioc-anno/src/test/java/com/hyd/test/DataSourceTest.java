package com.hyd.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.util.ResourceBundle;

public class DataSourceTest {

    //测试spring配置文件获取数据源
    @Test
    public void test4(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        DruidDataSource dataSource = applicationContext.getBean(DruidDataSource.class);
        System.out.println(dataSource);
    }
    //测试druid数据源
    //配置文件读取
    @Test
    public void test3() throws Exception{
        ResourceBundle prop1 = ResourceBundle.getBundle("jdbc");
        String driver = prop1.getString("jdbc.driver");
        String url = prop1.getString("jdbc.url");
        String username = prop1.getString("jdbc.username");
        String password = prop1.getString("jdbc.password");

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
        dataSource.close();
    }

    //测试手动创建druid数据源
    @Test
    public void test2() throws Exception{
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        DruidPooledConnection connection = dataSource.getConnection();
        System.out.println(connection);
        dataSource.close();
    }
    //测试手动创建c3p0数据源
    @Test
    public void test1() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
