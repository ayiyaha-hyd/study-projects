package com.hyd.work.controller;

import com.hyd.work.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 扣减账户余额
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping("decrease")
    public String decrease(@RequestParam("userId") String userId, @RequestParam("money") Integer money){
        accountService.decrease(userId,money);
        return "Account decrease success";
    }

    /**
     * 查询指定用户的余额
     * @param userId
     * @return
     */
    @RequestMapping("getMoneyByUserId")
    public int getMoneyByUserId(@RequestParam("userId") String userId){
        return accountService.getMoneyByUserId(userId);
    }
}
