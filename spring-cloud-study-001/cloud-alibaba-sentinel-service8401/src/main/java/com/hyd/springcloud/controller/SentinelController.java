package com.hyd.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hyd.springcloud.controller.exception.BlockHandlerExceptionUtil;
import com.hyd.springcloud.controller.exception.FallbackExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SentinelController {

    //测试各种配置情况下，发生限流降级处理逻辑

    //什么都不配置
    @GetMapping("/t1")
    public String test1(){
//        int a=10/0;
        return "test1---";
    }

    //配置 blockHandler
    @GetMapping("/t2/{id}")
    @SentinelResource(value = "test2",blockHandler = "blockHandler")
    public String test2(@PathVariable("id") Integer id){
//        int a=10/0;
        return "test2---"+id;
    }


    //配置 blockHandlerClass
    @GetMapping("/t3/{id}")
    @SentinelResource(value = "test3",blockHandler = "handleEx1",blockHandlerClass = {BlockHandlerExceptionUtil.class})
    public String test3(@PathVariable("id") Integer id){
//        int a=10/0;
        return "test3---"+id;
    }

    //配置 fallback
    @GetMapping("/t4/{id}")
    @SentinelResource(value = "test4",fallback = "fallback")
    public String test4(@PathVariable("id") Integer id){
//        int a=10/0;
        return "test4---"+id;
    }

    //配置 fallbackClass
    @GetMapping("/t5/{id}")
    @SentinelResource(value = "test5",fallback = "fallbackEx1",fallbackClass = {FallbackExceptionUtil.class})
    public String test5(@PathVariable("id")Integer id){
//        int a=10/0;
        return "test5---"+id;
    }

    //配置 blockHandler 和 fallback
    @GetMapping("t6/{id}")
    @SentinelResource(value = "test6",blockHandler = "blockHandler",fallback = "fallback")
    public String test6(@PathVariable("id")Integer id){
//        int a=10/0;
        return "test6---"+id;
    }

    //blockHandler 方法
    public String blockHandler(Integer id, BlockException ex){
        return "blockHandler---"+id+"---"+ex.getClass().getCanonicalName();
    }

    //fallback 方法
    public String fallback(Integer id){
        return "fallback---"+id;
    }
}
