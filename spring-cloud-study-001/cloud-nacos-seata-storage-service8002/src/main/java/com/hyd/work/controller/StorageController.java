package com.hyd.work.controller;

import com.hyd.work.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     * @return
     */
    @RequestMapping("decrease")
    public String decrease(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count){
        storageService.decrease(commodityCode,count);
        return "Decrease storage success";
    }

    /**
     * 获取指定商品的库存
     * @param commodityCode
     * @return
     */
    @RequestMapping("getCountByCode")
    public int getCountByCode(@RequestParam("commodityCode") String commodityCode){
        return storageService.getCountByCode(commodityCode);
    }
}
