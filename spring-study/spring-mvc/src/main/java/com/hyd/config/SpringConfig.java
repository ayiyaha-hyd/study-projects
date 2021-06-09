package com.hyd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceConfig.class)
public class SpringConfig {

}
