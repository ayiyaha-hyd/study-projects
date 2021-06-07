package com.hyd.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 库存实体类
 */
@Data
@AllArgsConstructor
public class Storage {
    private Integer id;
    private String commodityCode;//商品编码
    private Integer count;
}
