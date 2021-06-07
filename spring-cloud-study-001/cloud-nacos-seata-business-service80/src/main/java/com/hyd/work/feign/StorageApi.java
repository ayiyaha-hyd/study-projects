package com.hyd.work.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "storage-service")
public interface StorageApi {
    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     * @return
     */
    @RequestMapping("/storage/decrease")
    String decrease(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);

    /**
     * 获取指定商品的库存
     * @param commodityCode
     * @return
     */
    @RequestMapping("/storage/getCountByCode")
    int getCountByCode(@RequestParam("commodityCode") String commodityCode);
}