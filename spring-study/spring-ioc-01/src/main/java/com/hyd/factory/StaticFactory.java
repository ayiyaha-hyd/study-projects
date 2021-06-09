package com.hyd.factory;

import com.hyd.dao.UserDao;
import com.hyd.dao.impl.UserDaoImpl;

public class StaticFactory {
    public StaticFactory(){
        System.out.println("staticFactory无参构造方法...");
    }
        public UserDao getUserDao(){
        System.out.println("动态工厂模式");
        return new UserDaoImpl();
    }
//    public static UserDao getUserDao(){
//        System.out.println("静态工厂模式");
//        return new UserDaoImpl();
//    }
}
