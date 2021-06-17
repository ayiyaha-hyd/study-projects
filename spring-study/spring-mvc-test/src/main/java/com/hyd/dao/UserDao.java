package com.hyd.dao;

import com.hyd.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    Long save(User user);

    void saveUserRoleRel(Long userId, Long[] roleIds);

    void del(Long userId);

    void delUserRoleRel(Long userId);

    User findUserByUsernameAndPassword(String username, String password);
}
