package com.hyd.test;

import com.hyd.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:applicationContext.xml")
@ContextConfiguration(classes = {SpringConfig.class})
public class SpringTest {
//    @Autowired
//    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

//    @Test
//    public void test(){
//        userDao.save();
//    }

    @Test
    public void test2() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
}
