package com.hyd.springcloud.controller.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandlerExceptionUtil {
    public static String handleEx1(Integer id, BlockException ex){
        return "test3---blockHandlerClass---"+id+"---"+ex.getClass().getCanonicalName();
    }
}
