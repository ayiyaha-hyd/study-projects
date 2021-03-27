package com.hyd.consumer;

import com.hyd.consumer.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationTests {
    @Autowired
    ConsumerService consumerService;

    @Test
    void contextLoads() {
    }

    @Test
    void consumerTest(){
        System.out.println("调用consumerTest测试方法...");
        consumerService.consume();
    }

}
