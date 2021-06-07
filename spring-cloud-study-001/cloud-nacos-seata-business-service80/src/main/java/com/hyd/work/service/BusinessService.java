package com.hyd.work.service;

public interface BusinessService {
    /**
     * 购买商品
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    void purchase(String userId,String commodityCode,Integer count,Integer money);

    /**
     * 数据校验（检查库存，账户余额）
     * @return
     */
    boolean validData(String userId,String commodityCode);
}
