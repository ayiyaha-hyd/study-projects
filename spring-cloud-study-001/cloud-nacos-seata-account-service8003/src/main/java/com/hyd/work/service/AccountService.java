package com.hyd.work.service;

public interface AccountService {

    /**
     * 扣减账户余额
     * @param userId
     * @param money
     */
    void decrease(String userId, Integer money);

    /**
     * 获取指定用户的余额
     * @param userId
     * @return
     */
    int getMoneyByUserId(String userId);
}
