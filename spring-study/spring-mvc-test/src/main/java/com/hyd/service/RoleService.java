package com.hyd.service;

import com.hyd.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> list();
    void save(Role role);
}
