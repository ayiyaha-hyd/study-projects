package com.hyd.dao.impl;

import com.hyd.dao.RoleDao;
import com.hyd.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> findAll() {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from sys_role");
        List<Object> params = new ArrayList<Object>();
        List<Role> roleList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }

    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role values(?,?,?)",null,role.getRoleName(),role.getRoleDesc());
    }

    public List<Role> findRoleByUserId(Long id) {
        List<Role> roles = jdbcTemplate.query("select * from sys_user_role a,sys_role b where a.roleId=b.id and a.userId=?", new BeanPropertyRowMapper<Role>(Role.class), id);
        return roles;
    }
}
