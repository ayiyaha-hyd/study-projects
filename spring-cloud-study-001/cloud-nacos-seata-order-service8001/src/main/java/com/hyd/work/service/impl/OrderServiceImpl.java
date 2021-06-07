package com.hyd.work.service.impl;

import com.hyd.work.dao.OrderDao;
import com.hyd.work.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    @Override
    public void create(String userId, String commodityCode, Integer count, Integer money) {
        log.info("------->创建订单开始order中");
        orderDao.create(userId, commodityCode, count, money);
        log.info("------->创建订单结束order中");
    }

}
