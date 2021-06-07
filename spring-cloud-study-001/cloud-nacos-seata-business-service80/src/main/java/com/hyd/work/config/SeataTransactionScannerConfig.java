package com.hyd.work.config;

import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//@Configuration
public class SeataTransactionScannerConfig {
    @Value("${spring.application.name}")
    private String applicationId;

    //与该微服务对应的 seata file.conf 中 service.vgroup_mapping 事务组字段一致
    private static final String TX_SERVICE_GROUP = "business_group";

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(applicationId, TX_SERVICE_GROUP);
    }
}
