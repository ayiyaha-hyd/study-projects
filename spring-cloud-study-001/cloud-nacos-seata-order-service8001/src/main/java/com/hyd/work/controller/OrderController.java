package com.hyd.work.controller;

import com.hyd.work.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("create")
    public String create(@RequestParam("userId") String userId, @RequestParam("commodityCode")String commodityCode, @RequestParam("count")Integer count, @RequestParam("money")Integer money){
        orderService.create(userId, commodityCode, count, money);
        return "Create order success";
    }
}
