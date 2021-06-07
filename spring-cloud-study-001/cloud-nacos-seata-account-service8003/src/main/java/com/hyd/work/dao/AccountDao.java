package com.hyd.work.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountDao {

    /**
     * 扣减账户余额
     * @param userId
     * @param money
     */
    void decrease(@Param("userId") String userId, @Param("money") Integer money);

    /**
     * 获取指定用户的余额
     * @param userId
     * @return
     */
    int getMoneyByUserId(@Param("userId") String userId);
}
