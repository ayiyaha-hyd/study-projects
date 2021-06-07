package com.hyd.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {
    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     */
    void decrease(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

    /**
     * 获取指定商品的库存
     * @param commodityCode
     * @return
     */
    int getCountByCode(@Param("commodityCode") String commodityCode);
}
