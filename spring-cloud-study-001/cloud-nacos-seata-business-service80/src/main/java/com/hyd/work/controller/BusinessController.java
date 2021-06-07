package com.hyd.work.controller;

import com.hyd.work.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController {
    @Autowired
    private BusinessService businessService;
    /**
     * 购买商品
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     * @return
     */
    @RequestMapping("purchase")
    public String purchase(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count, @RequestParam("money") Integer money) {
        try {
            businessService.purchase(userId, commodityCode, count, money);
        } catch (Exception e) {
            return "发生了异常，购买失败--->"+e.getMessage();
        }
        return "商品购买成功";
    }
}
