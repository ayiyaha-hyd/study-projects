package com.hyd.work.service.impl;

import com.hyd.work.dao.StorageDao;
import com.hyd.work.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageDao storageDao;
    @Override
    public void decrease(String commodityCode, Integer count) {
        log.info("------->扣减库存开始storage中");
        storageDao.decrease(commodityCode,count);
        log.info("------->扣减库存结束storage中");
    }

    @Override
    public int getCountByCode(String commodityCode) {
        return storageDao.getCountByCode(commodityCode);
    }
}
