package com.hyd.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    /**
     * 创建订单
     *
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    void create(@Param("userId") String userId, @Param("commodityCode") String commodityCode, @Param("count") Integer count, @Param("money") Integer money);

}
