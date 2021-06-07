package com.hyd.work.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "account-service")
public interface AccountApi {
    /**
     * 扣减账户余额
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping("/account/decrease")
    String decrease(@RequestParam("userId") String userId, @RequestParam("money") Integer money);

    /**
     * 查询指定用户的余额
     * @param userId
     * @return
     */
    @RequestMapping("/account/getMoneyByUserId")
    public int getMoneyByUserId(@RequestParam("userId") String userId);
}
