package com.hyd.work.service;

public interface OrderService {
    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    void create(String userId, String commodityCode, Integer count, Integer money);
}
