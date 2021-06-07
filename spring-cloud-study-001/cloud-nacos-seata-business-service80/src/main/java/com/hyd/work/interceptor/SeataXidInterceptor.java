package com.hyd.work.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * 跨服务调用的事务传播
 * 把服务调用方 RootContext XID 通过服务调用传递到服务提供方
 *
 * 服务调用方传递 XID
 *
 * 拦截器添加请求头数据
 */
//@Component
@Slf4j
public class SeataXidInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();
        if(StringUtils.isNotBlank(xid)){
            //将当前事务的XID放入请求Header
            template.header(RootContext.KEY_XID,xid);
            log.info("set xid["+xid+"] to request Header");
        }
    }
}
