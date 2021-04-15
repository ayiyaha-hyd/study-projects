package com.hyd.demo;

import ch.qos.logback.core.db.DBHelper;
import com.hyd.demo.config.BeanConfig;
import com.hyd.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootDemoApplication {

	private static Logger logger = LoggerFactory.getLogger(SpringbootDemoApplication.class);
	public static void main(String[] args) {
		//返回IOC容器
		ConfigurableApplicationContext run = SpringApplication.run(SpringbootDemoApplication.class, args);

		//查看容器内的组件
		String[] beanDefinitionNames = run.getBeanDefinitionNames();
		for(String name:beanDefinitionNames){
			logger.debug(name);
		}
		logger.debug("beanDefinitionCount: {}",run.getBeanDefinitionCount());
		//获取容器中的User实例
		logger.debug(String.valueOf(run.getBean(User.class)==run.getBean(User.class)));

		BeanConfig beanConfig = run.getBean(BeanConfig.class);
		logger.debug(String.valueOf(beanConfig.user01()==beanConfig.user01()));
		logger.debug(String.valueOf(run.getBean(DBHelper.class)==run.getBean(DBHelper.class)));
	}

}
