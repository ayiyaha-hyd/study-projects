package com.hyd.dao;

import com.hyd.domain.User;

import java.util.List;

public interface UserMapper {
    public List<User> findAll() throws Exception;
    public void save() throws Exception;
    public void delete() throws Exception;
    public void update() throws Exception;

    public List<User> findByCondition(User user)throws Exception;

    public List<User> findByIds(List<Integer> ids);
}
