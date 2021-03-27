package com.hyd.provider.service.impl;

import com.hyd.provider.service.ProviderService;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String provide() {
        return "提供服务...";
    }
}
