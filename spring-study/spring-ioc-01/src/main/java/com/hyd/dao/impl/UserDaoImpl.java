package com.hyd.dao.impl;

import com.hyd.dao.UserDao;
import com.hyd.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class UserDaoImpl implements UserDao {
    private List<String> strList;
    private Map<String, User> userMap;
    private Properties properties;


    public void setStrList(List<String> strList) {
        this.strList = strList;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public UserDaoImpl(){
        System.out.println("userdao无参构造创建...");
    }
    public void init(){
        System.out.println("init初始化...");
    }
    public void destroy(){
        System.out.println("destroy销毁...");
    }
    public void save() {
        System.out.println(strList);
        System.out.println(userMap);
        System.out.println(properties);
        System.out.println("save...");
    }
}
