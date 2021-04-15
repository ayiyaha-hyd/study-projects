package com.hyd.demo.config;

import ch.qos.logback.core.db.DBHelper;
import com.hyd.demo.entity.Pet;
import com.hyd.demo.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({DBHelper.class})
@Configuration(proxyBeanMethods = true)
public class BeanConfig {

    @Bean
    public Pet pet01(){
        return new Pet("cat");
    }

//    @ConditionalOnBean(name = {"pet01"})
    @ConditionalOnBean(value = {Pet.class})
//    @ConditionalOnBean(type = {"com.hyd.demo.entity.Pet"})
    //此处Pet需要在User前
    @Bean
    public User user01(){
        return new User("zhangsan","18",null);
    }
}
