package com.hyd.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 订单实体类
 */
@Data
@AllArgsConstructor
public class Order {
    private Integer id;
    private String userId;//账户id
    private String commodityCode;//商品编码
    private Integer count;
    private Integer money;
}
