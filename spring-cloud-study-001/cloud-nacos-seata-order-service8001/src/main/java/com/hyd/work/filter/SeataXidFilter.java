package com.hyd.work.filter;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 跨服务调用的事务传播
 * 把 RootContext XID 通过服务调用传递到服务提供方
 * 将 HttpServletRequest 中的 Seata XID 绑定到 RootContext 中
 *
 * 服务提供方获取 XID
 *
 * 过滤器解析请求头数据
 */
@Slf4j
//@Component
public class SeataXidFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取请求头 XID
        String xid = ((HttpServletRequest) request).getHeader(RootContext.KEY_XID);
        log.info("xid in HttpServletRequest is:[" + xid + "]");
        boolean bind = false;
        if(StringUtils.isNotBlank(xid)){
            RootContext.bind(xid);
            bind = true;
            log.info("bind xid[" + xid + "] to RootContext");
        }
        try {
            chain.doFilter(request,response);
        }finally {
            if(bind){
                String unbind = RootContext.unbind();
                log.info("unbind xid[" + unbind + "] from RootContext");
            }
        }

    }

    @Override
    public void destroy() {

    }
}
