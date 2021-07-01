package com.hyd.dao;

import com.hyd.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> findAll() throws Exception;
    void save(User user) throws Exception;
}
