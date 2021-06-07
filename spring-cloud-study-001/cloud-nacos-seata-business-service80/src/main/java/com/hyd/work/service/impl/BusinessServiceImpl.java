package com.hyd.work.service.impl;

import com.hyd.work.feign.AccountApi;
import com.hyd.work.feign.OrderApi;
import com.hyd.work.feign.StorageApi;
import com.hyd.work.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;

    @GlobalTransactional(name = "seata-osa-group",rollbackFor = Exception.class)
    @Override
    public void purchase(String userId, String commodityCode, Integer count, Integer money) {
        log.info("------->交易开始");

        //远程方法 创建订单
        log.info("------->创建订单开始");
        orderApi.create(userId, commodityCode, count, money);
        log.info("------->创建订单结束");

        //远程方法 扣减库存
        log.info("------->扣减库存开始");
        storageApi.decrease(commodityCode,count);
        log.info("------->扣减库存结束");

        //远程方法 扣减账户余额
        log.info("------->扣减账户开始");
        accountApi.decrease(userId,money);
        log.info("------->扣减账户结束");

        //校验数据
        log.info("------->校验数据开始");
        if(!validData(userId,commodityCode)){
            throw new RuntimeException("账户余额或库存不足,执行回滚");
        }
        log.info("------->校验数据开始");

        log.info("------->交易结束");


    }

    @Override
    public boolean validData(String userId,String commodityCode) {
        if(accountApi.getMoneyByUserId(userId)<0){
            return false;
        }
        if(storageApi.getCountByCode(commodityCode)<0){
            return false;
        }
        return true;
    }
}
