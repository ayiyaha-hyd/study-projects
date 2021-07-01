package com.hyd.service;

import com.hyd.dao.UserMapper;
import com.hyd.dao.impl.UserMapperImpl;
import com.hyd.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ServiceDemo {
    public static void main(String[] args) throws Exception {
//        raw();
        mybatisProxy();
    }
    /**
     * mybatis代理接口，不需要写实现类
     */
    public static void mybatisProxy()throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = userMapper.findAll();
        System.out.println(userList);
        sqlSession.close();
    }

    /**
     * 传统方式
     */
    public static void raw()throws Exception{
        UserMapper userMapper = new UserMapperImpl();
        List<User> userList = userMapper.findAll();
        System.out.println(userList);
    }
}
