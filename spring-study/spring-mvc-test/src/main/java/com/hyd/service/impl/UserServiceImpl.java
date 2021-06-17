package com.hyd.service.impl;

import com.hyd.dao.RoleDao;
import com.hyd.dao.UserDao;
import com.hyd.domain.Role;
import com.hyd.domain.User;
import com.hyd.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }
    private RoleDao roleDao;
    public void setRoleDao(RoleDao roleDao){
        this.roleDao = roleDao;
    }
    public List<User> list() {
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            Long id = user.getId();
            List<Role> roles = roleDao.findRoleByUserId(id);
            user.setRoles(roles);
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        Long userId = userDao.save(user);
        userDao.saveUserRoleRel(userId,roleIds);
    }

    public void del(Long userId) {
        //删除关系表
        userDao.delUserRoleRel(userId);
        //删除用户表
        userDao.del(userId);
    }

    public User login(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username,password);
    }
}
