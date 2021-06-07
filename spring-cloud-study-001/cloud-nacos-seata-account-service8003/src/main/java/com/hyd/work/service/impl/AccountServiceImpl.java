package com.hyd.work.service.impl;

import com.hyd.work.dao.AccountDao;
import com.hyd.work.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public void decrease(String userId, Integer money) {
        log.info("------->扣减账户开始account中");
        //模拟超时异常，全局事务回滚
//        try {
//            TimeUnit.SECONDS.sleep(30);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountDao.decrease(userId,money);
        log.info("------->扣减账户结束account中");

    }

    @Override
    public int getMoneyByUserId(String userId) {
        return accountDao.getMoneyByUserId(userId);
    }
}
