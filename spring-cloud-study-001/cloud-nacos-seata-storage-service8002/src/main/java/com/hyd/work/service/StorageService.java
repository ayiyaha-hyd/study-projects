package com.hyd.work.service;

import org.apache.ibatis.annotations.Param;

public interface StorageService {

    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     */
    void decrease(String commodityCode, Integer count);

    /**
     * 获取指定商品的库存
     * @param commodityCode
     * @return
     */
    int getCountByCode(@Param("commodityCode") String commodityCode);
}
