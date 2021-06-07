package com.hyd.work.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "order-service")
public interface OrderApi {
    @RequestMapping("/order/create")
    void create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
                @RequestParam("count") Integer count, @RequestParam("money") Integer money);
}
