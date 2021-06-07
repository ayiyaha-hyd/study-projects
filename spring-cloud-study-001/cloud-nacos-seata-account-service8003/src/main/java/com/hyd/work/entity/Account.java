package com.hyd.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 账户实体类
 */
@Data
@AllArgsConstructor public class Account {
    private Integer id;
    private String user_id;//账户id
    private Integer money;
}
