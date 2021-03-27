package com.hyd.provider.service.impl;

import com.hyd.provider.service.ProviderService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

@Component
@DubboService//属于Dubbo的Service注解，非Spring  作用：暴露服务
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String provide() {
        return "调用成功，提供服务";
    }
}
