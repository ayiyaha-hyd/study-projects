package com.hyd.springcloud.controller.exception;

public class FallbackExceptionUtil {
    public static String fallbackEx1(Integer id){
        return "test5---fallbackClass---"+id;
    };
}
