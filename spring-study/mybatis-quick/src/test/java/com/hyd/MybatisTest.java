package com.hyd;

import com.hyd.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {
    /**
     * 查询
     */
    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> userList = sqlSession.selectList("userMapper.findAll");
        System.out.println(userList);
        sqlSession.close();
    }

    @Test
    public void test2() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("Jack");
        user.setPassword("11111");
        sqlSession.insert("userMapper.save",user);
        sqlSession.commit();
        sqlSession.close();

        //mybatis默认通过sqlsession.commit,rollback提交回滚事务
        //可以通过sqlSessionFactory.openSession(true)开启


    }
    @Test
    public void test3()throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //开启事务自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //update
        User user = new User();
        user.setId(13);
        user.setUsername("zhangsan");
        user.setPassword("1818118");
        sqlSession.update("userMapper.update",user);

    }

    @Test
    public void test4()throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //开启事务自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        //delete
        sqlSession.update("userMapper.delete",13);

    }}
