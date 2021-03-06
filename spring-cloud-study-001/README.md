SpringCloud学习

---
[TOC]



---

## 版本选择

上篇：SpirngBoot2.X版和SpringCloud H版
下篇：SpringCloud Alibaba版

SpringBoot,SpringCloud版本选择以及相互的版本依赖

| Release Train                                                | Boot Version                     |
| :----------------------------------------------------------- | :------------------------------- |
| [2020.0.x](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2020.0-Release-Notes) aka Ilford | 2.4.x                            |
| [Hoxton](https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-Hoxton-Release-Notes) | 2.2.x, 2.3.x (Starting with SR5) |
| [Greenwich](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Greenwich-Release-Notes) | 2.1.x                            |
| [Finchley](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Finchley-Release-Notes) | 2.0.x                            |
| [Edgware](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Edgware-Release-Notes) | 1.5.x                            |
| [Dalston](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Dalston-Release-Notes) | 1.5.x                            |

更详细的依赖：https://start.spring.io/actuator/info



组成框架（停更升级替换）（理念与落地实现）

1. 服务注册中心	
   1. Eureka（停更）
   2. Zookeeper（老项目）
   3. Consul
   4. Nacos（推荐）

1. 服务调用
   1. Ribbon（进入维护）
   2. LoadBalancer
2. 服务调用2
   1. Feign
   2. OpenFeign（推荐）
3. 服务降级
   1. Hystrix（国内大量使用，国外停用）
   2. resilience4j（国外）
   3. Sentinel（国内推荐）
4. 服务网关
   1. Zuul
   2. Spring Cloud Gateway（推荐）
5. 服务配置
   1. SpringCloud Config
   2. Apollo（携程）
   3. Nacos（推荐）
6. 消息总线
   1. Spring Cloud Bus
   2. Nacos



约定>配置>编码



## 搭建项目

搭建maven聚合项目

pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- 坐标 -->
    <groupId>com.hyd.springcloud</groupId>
    <artifactId>cloud2021</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- 打包类型：pom 聚合项目 -->
    <packaging>pom</packaging>

    <name>cloud2021</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <!-- 模块 -->
    <modules>
        <module>cloud-provider-payment8001</module>
        <module>cloud-consumer-order80</module>
        <module>cloud-api-commons</module>
        <module>cloud-eureka-server7001</module>
        <module>cloud-eureka-server7002</module>
        <module>cloud-eureka-server7003</module>
        <module>cloud-provider-payment8002</module>
        <module>cloud-provider-payment8003</module>
        <module>cloud-provider-payment8004</module>
        <module>cloud-consumerzk-order80</module>
        <module>cloud-providerconsul-payment8005</module>
        <module>cloud-consumerconsul-order80</module>
        <module>cloud-consumer-feign-order80</module>
        <module>cloud-provider-hystrix-payment8001</module>
        <module>cloud-consumer-feign-hystrix-order80</module>
        <module>cloud-consumer-hystrix-dashboard9001</module>
        <module>cloud-zuul-gateway9527</module>
        <module>cloud-gateway-gateway9527</module>
        <module>cloud-config-center3344</module>
        <module>cloud-config-client3355</module>
        <module>cloud-config-client3356</module>
        <module>cloud-config-client3357</module>
        <module>cloud-stream-rabbitmq-provider8801</module>
        <module>cloud-stream-rabbitmq-consumer8802</module>
        <module>cloud-stream-rabbitmq-consumer8803</module>
        <module>cloud-stream-rabbitmq-consumer8804</module>
        <module>cloud-provider-sleuth-payment8001</module>
        <module>cloud-consumer-sleuth-order80</module>
        <module>cloud-alibaba-provider-nacos-payment9001</module>
    </modules>

    <!--统一管理jar包和版本-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <log4j.version>1.2.17</log4j.version>
        <lombok.version>1.16.18</lombok.version>
        <mysql.version>5.1.47</mysql.version>
        <druid.verison>1.1.16</druid.verison>
        <mybatis.spring.boot.verison>1.3.0</mybatis.spring.boot.verison>
        <spring-cloud-alibaba.version>2.2.0.RELEASE</spring-cloud-alibaba.version>
        <spring-boot.version>2.2.2.RELEASE</spring-boot.version>
    </properties>

    <!--子模块继承之后，提供作用：锁定版本+子module不用谢groupId和version-->
    <dependencyManagement>
        <dependencies>
            <!--spring boot 2.2.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud alibaba 2.2.0.RELEASE-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- MySql -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!-- Druid -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.verison}</version>
            </dependency>
            <!-- mybatis-springboot整合 -->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.spring.boot.verison}</version>
            </dependency>
            <!--lombok-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!-- log4j -->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>

```



dependencyManagement

maven中使用dependencyManagement元素来提供一种管理版本依赖号的方式。通常在一个组织或者项目的最顶层父POM中看到dependencyManagement元素。

使用pom.xml中的dependencyManagement元素，能够让所有在子项目引用一个依赖而不用显式的列出版本号。

dependencyManagement里只是声明依赖，并不实现引入，因此子项目需要显式的引入所需要的依赖。dependencyManagement只是起一个规范作用。如果不在子项目声明，是不会依赖的；只有子项目写了依赖，没有指定具体版本，才会继承父项目，读取version和scope。如果子项目写了版本号，则使用子项目的版本。



执行mvn:install将父工程发布到仓库方便子工程继承。 



### 支付模块

创建module子项目

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>


    <dependencies>
        <!-- web依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--监控-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- mybatis依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- 数据库连接池 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <!--如果没写版本,从父层面找,找到了就直接用,全局统一-->
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```



application.yaml

```yaml
server:
  port: 8001
spring:
  application:
    name: cloud-payment-service
  datasource:
    # 当前数据源操作类型
    type: com.alibaba.druid.pool.DruidDataSource
    # mysql驱动类
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cloud2021?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: root
    password: 123456
mybatis:
  mapper-locations: classpath*:mapper/*.xml
  type-aliases-package: com.hyd.springcloud.entity
```



启动类：

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 微服务提供者支付模块
 */
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}

```



建表sql

```mysql
CREATE TABLE `payment`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `serial` varchar(200) DEFAULT '',
    PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
```



实体类

```java
package com.hyd.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code,String message){
        this(code,message,null);
    }
}

```



```java
package com.hyd.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;
}

```



dao层

```java
package com.hyd.springcloud.dao;

import com.hyd.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}

```



PaymentMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.hyd.springcloud.dao.PaymentDao">
    <resultMap id="BaseResultMap" type="com.hyd.springcloud.entity.Payment">
        <id column="id" property="id" jdbcType="BIGINT" />
        <id column="serial" property="serial" jdbcType="VARCHAR"/>
    </resultMap>

    <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
        insert into payment(serial) values(#{serial})
    </insert>

    <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
        select id,serial from payment where id=#{id};
    </select>
</mapper>
```



service层

```java
package com.hyd.springcloud.service;

import com.hyd.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}

```



serviceImpl实现类

```java
package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.dao.PaymentDao;
import com.hyd.springcloud.entity.Payment;
import com.hyd.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;
    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}

```



controller层

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.entity.CommonResult;
import com.hyd.springcloud.entity.Payment;
import com.hyd.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果：-->{}",result);

        if(result>0){
            return new CommonResult(200,"插入数据库成功",result);
        }else {
            return new CommonResult(444,"插入数据库失败");
        }
    }
    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果：-->{}",payment);
        if(payment!=null){
            return new CommonResult(200,"查询成功",payment);
        }else {
            return new CommonResult(444,"无记录");
        }
    }
}

```



### 订单模块

创建moudel子项目

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumer-order80</artifactId>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```



application.yaml

```yaml
server:
  port: 80
```



启动类，entity实体类同支付模块，略



controller层



服务之间调用：httpcilent --> restTemplate

RestTemplate介绍

 原本发送Http请求我们会使用封装好的HttpClient工具类，而Spring为我们提供了RestTemplate工具集。它提供了多种便携访问远程Http服务的方法，是一种简单便捷的访问restful服务模板类。类似于jdbcTemplate,redisTemplate。



配置类

```java
package com.hyd.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置类
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```



这里create时，通过get方式提交json数据，通过requestbody接收，服务调用时，通过post方式提交,requestbody接收

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.entity.CommonResult;
import com.hyd.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping(value = "/consumer/payment")
public class OrderController {

    public static final String PAYMENT_URL="http://localhost:8001";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}

```



小知识：通过**Run Dashboard**快速管理服务启动等。

以上支付模块调用订单模块基本完成。但存在冗余代码。



### 代码重构：

创建moudel子模块，名为cloud-api-commons，提取公共通用类

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-api-commons</artifactId>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.1.0</version>
        </dependency>
    </dependencies>

</project>
```





提取支付模块和订单模块公共entity类，删除两个工程原项目下的entity包，提取到cloud-api-commons模块下。，并将cloud-api-commons依赖引入两个模块中，以便调用公共entity类。maven clean install该模块。

```xml
        <!-- 引入公共commons模块 -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```





---



微服务-初级



## 服务注册中心



### Eureka服务注册与发现

#### Eureka基础知识

###### 什么是服务注册中心：

* 在环境搭建中，我们实现了服务模块直接通过Http的方式进行调用。而当我们的服务越来越多时就会不方便管理（服务运行状态等等），所以将这些服务在某个地方注册并进行统一的管理，这个地方就是我们的服务注册中心。

###### 什么是服务治理：

* Spring Cloud封装了Netflix 公司开发的Eureka模块来实现服务治理

  在传统的RPC远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务于服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册。

###### 什么是服务注册与发现：

* Eureka采用了CS的设计架构，Eureka Sever作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用Eureka的客户端连接到 Eureka Server并维持心跳连接。这样系统的维护人员就可以通过Eureka Server来监控系统中各个微服务是否正常运行。

  在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息比如服务地址通讯地址等以别名方式注册到注册中心上。另一方(消费者服务提供者)，以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用RPC远程调用框架核心设计思想:在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系(服务治理概念)。在任何RPC远程框架中，都会有一个注册中心存放服务地址相关信息(接口地址)。

* 可以对比Eureka和Dubbo框架。

配置集群为了避免单点故障。



###### Eureka包含两个组件:Eureka Server和Eureka Client

* Eureka Server提供服务注册服务

  各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。

* EurekaClient通过注册中心进行访问

  它是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒)。



Eureka application.yaml配置：[eureka hostname作用_【springcloud】Eureka 常用配置解析_张笑笑的酒窝的博客-CSDN博客](https://blog.csdn.net/weixin_42298645/article/details/112043261)

#### 单机版Eureka构建步骤



Eureka版本选择(spring-cloud-starter-eureka和spring-cloud-starter-netflix-eureka-server):

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-eureka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
    <version>1.4.7.RELEASE</version>
</dependency>
```



```xml
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-netflix-eureka-server -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.8.RELEASE</version>
</dependency>
```



spring-cloud-starter-eureka-server 在1.5版本中可以使用，

在2.0版本中废弃，建议使用spring-cloud-starter-netflix-eureka-server



##### 创建moudle子模块

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-eureka-server7001</artifactId>

    <dependencies>
        <!--eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--boot web actuator-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--一般通用配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
    </dependencies>
</project>
```



application.yaml

```yaml
server:
  port: 7001

eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: #设置与Eureka server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```



启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka 服务注册中心
 */
@SpringBootApplication
@EnableEurekaServer //表明此为Eureka 服务注册中心服务端
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}

```



启动之后访问 http://localhost:7001 查看。



##### 注册支付模块和订单模块



改造支付模块和订单模块

引入依赖

```xml
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```



修改application.yaml，添加以下配置

```yaml
eureka:
  client:
    #表示是否将自己注册进Eurekaserver默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:7001/eureka
```



启动类添加`@EnableEurekaClient`注解：

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 微服务提供者支付模块
 */
@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}

```



服务实例名称

```yaml
spring:
  application:
    name: cloud-payment-service
```



注册到Eureka中的**CLOUD-PAYMENT-SERVICE**，就是yaml配置的**cloud-payment-service**



Eureka自我保护机制：

EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.

紧急情况！EUREKA可能错误地声称实例在没有启动的情况下启动了。续订小于阈值，因此实例不会为了安全而过期。



#### 集群Eureka搭建步骤



1.Eureka集群原理说明

服务注册：将服务信息注册进注册中心

服务发现：从注册中心上获取服务信息

实质：存key服务命取value闭用地址

1先启动eureka注主册中心

2启动服务提供者payment支付服务

3支付服务启动后会把自身信息(比服务地址L以别名方式注朋进eureka

4消费者order服务在需要调用接口时，使用服务别名去注册中心获取实际的RPC远程调用地址

5消去者导调用地址后，底屋实际是利用HttpClient技术实现远程调用

6消费者实癸导服务地址后会缓存在本地jvm内存中，默认每间隔30秒更新—次服务调用地址

问题:微服务RPC远程服务调用最核心的是什么
高可用，试想你的注册中心只有一个only one，万一它出故障了，会导致整个为服务环境不可用。

解决办法：搭建Eureka注册中心集群，实现负载均衡+故障容错。

互相注册，相互守望。



##### 创建子模块

再创建两个Eureka module子模块

略

现在有了三个Eureka模块cloud-eureka-server7001,cloud-eureka-server7002,cloud-eureka-server7002搭建集群，接下来进行下一步操作。

修改Windows host 文件(C:\Windows\System32\drivers\etc)

```
# Eureka映射配置(SpringCloud学习)
127.0.0.1       cloud-eureka-server7001
127.0.0.1       cloud-eureka-server7002
127.0.0.1       cloud-eureka-server7003
```



修改Eureka服务端三个配置文件(application.yaml)

```yaml
server:
  port: 7001

eureka:
  instance:
    hostname: cloud-eureka-server7001 #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: #设置与Eureka server交互的地址查询服务和注册服务都需要依赖这个地址。
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机配置
      defaultZone: http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ # 集群相互注册
```



```yaml
server:
  port: 7002

eureka:
  instance:
    hostname: cloud-eureka-server7002 #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: #设置与Eureka server交互的地址查询服务和注册服务都需要依赖这个地址。
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机配置
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7003:7003/eureka/ # 集群相互注册
```



```yaml
server:
  port: 7003

eureka:
  instance:
    hostname: cloud-eureka-server7003 #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: #设置与Eureka server交互的地址查询服务和注册服务都需要依赖这个地址。
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机配置
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/ # 集群相互注册
```



启动三个服务，访问以下网址

```http
#Eureka
http://localhost:7001/
http://localhost:7002/
http://localhost:7003/
http://cloud-eureka-server7001:7001/
http://cloud-eureka-server7002:7002/
http://cloud-eureka-server7003:7003/
```

会发现分别对应另外两个地址，现阶段配置完成。



#### 支付服务提供者集群环境搭建



新建两个module子模块，修改端口号，组成三个支付服务提供集群：cloud-provider-payment8001,cloud-provider-payment8002,cloud-provider-payment8003



此时启动服务访问订单模块，会发现始终访问8001端口的项目，其实是由于代码之前都是写死的地址导致。修改为服务名。

```java
//    public static final String PAYMENT_URL="http://localhost:8001";//服务提供者单机版
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";//服务提供者集群环境
```

到这一步，再访问，会报错：

```
"status": 500,
"error": "Internal Server Error",
"message": "I/O error on GET request for \"http://CLOUD-PAYMENT-SERVICE/payment/get/11\": CLOUD-PAYMENT-SERVICE; nested exception is java.net.UnknownHostException: CLOUD-PAYMENT-SERVICE",
```

负载均衡：

使用@LoadBalanced注解赋予RestTemplate负载均衡的能力

Ribbon作为Spring Cloud的负载均衡机制的实现，使用@LoadBalanced让RestTemplate使用相关的负载均衡策略。

```java
package com.hyd.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置类
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```



此时访问服务，默认根据轮询机制，调用端口为8001,8002,8003的服务。

此时的服务访问形势如下：

订单模块 --> Eureka(集群) --> 支付模块(集群)



#### actuator微服务信息完善

服务名称修改，访问信息有ip提示：

当前服务含有主机名称：[PIG:cloud-payment-service:8001](http://pig:8001/actuator/info) , [PIG:cloud-payment-service:8002](http://pig:8002/actuator/info) , [PIG:cloud-payment-service:8003](http://pig:8003/actuator/info)，并且当前鼠标停留在服务字段上没有ip显示

修改三个模块application.yaml，新增配置字段

```yaml
eureka:
  instance:
    instance-id: payment8003
```

修改后服务：[payment8003](http://pig:8003/actuator/info) , [payment8002](http://pig:8002/actuator/info) , [payment8001](http://pig:8001/actuator/info)

访问信息有ip提示http://pig:8003/actuator/info --> http://192.168.1.102:8003/actuator/info



#### 服务发现Discovery

对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息



注入DiscoverClient服务发现客户端，写一个服务信息查询接口

```java
    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/discovery")
    public Object discovery() {
        //获取服务列表
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service ---> {}", service);
        }

        //获取具体服务下的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + " ," + instance.getInstanceId() + " ," + instance.getHost() + " ," + instance.getPort() + " ," + instance.getUri());
            ;
        }

        return this.discoveryClient;

    }
}

```

启动类添加注解

```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient //添加服务发现注解
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}
```



启动服务访问 http://localhost:8001/payment/discovery

返回以下内容:

```java
{
    "discoveryClients": [
        {
            "services": [
                "cloud-payment-service",
                "cloud-order-service"
            ],
            "order": 0
        },
        {
            "services": [],
            "order": 0
        }
    ],
    "services": [
        "cloud-payment-service",
        "cloud-order-service"
    ],
    "order": 0
}
```



后台打印内容：

```
service ---> cloud-payment-service
service ---> cloud-order-service
CLOUD-PAYMENT-SERVICE ,payment8001 ,192.168.1.103 ,8001 ,http://192.168.1.103:8001
CLOUD-PAYMENT-SERVICE ,payment8003 ,192.168.1.103 ,8003 ,http://192.168.1.103:8003
CLOUD-PAYMENT-SERVICE ,payment8002 ,192.168.1.103 ,8002 ,http://192.168.1.103:8002
```



#### Eureka自我保护

##### 概述

保护模式主要用于一组客户端和Eureka Server之间存在网络分区场景下的保护。一旦进入保护模式，Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务。

如果在Eureka Server的首页看到以下这段提示，则说明Eureka进入了保护模式:

EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY’RE NOT. RENEWALS ARE LESSER THANTHRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUSTTO BE SAFE

##### 导致原因

一句话：某时刻某一个微服务不可用了，Eureka不会立刻清理，依旧会对该微服务的信息进行保存。

属于CAP里面的AP分支。

##### 为什么会产生Eureka自我保护机制?

为了EurekaClient可以正常运行，防止与EurekaServer网络不通情况下，EurekaServer不会立刻将EurekaClient服务剔除

##### 什么是自我保护模式?

默认情况下，如果EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例(默认90秒)。但是当网络分区故障发生(延时、卡顿、拥挤)时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。Eureka通过“自我保护模式”来解决这个问题——当EurekaServer节点在短时间内丢失过多客户端时(可能发生了网络分区故障)，那么这个节点就会进入自我保护模式。

自我保护机制∶默认情况下EurekaClient定时向EurekaServer端发送心跳包

如果Eureka在server端在一定时间内(默认90秒)没有收到EurekaClient发送心跳包，便会直接从服务注册列表中剔除该服务，但是在短时间( 90秒中)内丢失了大量的服务实例心跳，这时候Eurekaserver会开启自我保护机制，不会剔除该服务（该现象可能出现在如果网络不通但是EurekaClient为出现宕机，此时如果换做别的注册中心如果一定时间内没有收到心跳会将剔除该服务，这样就出现了严重失误，因为客户端还能正常发送心跳，只是网络延迟问题，而保护机制是为了解决此问题而产生的)。

在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。

它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例。一句话讲解：好死不如赖活着。

综上，自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留）也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮、稳定。


设计思想：分布式CAP里边的AP分支





##### 自我保护模式配置

默认自我保护模式是开启的，可以通过配置进行禁用：

```yaml
eureka:
  instance:
    hostname: cloud-eureka-server7001 #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: #设置与Eureka server交互的地址查询服务和注册服务都需要依赖这个地址。
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机配置
  server:
    #关闭自我保护机制，保证不可用服务被及时踢除
    enable-self-preservation: false
    # eureka server清除无效服务节点的时间间隔
    eviction-interval-timer-in-ms: 2000
```



设置客户端接受心跳时间间隔：

默认：

eureka.instance.lease-renewal-interval-in-seconds=30

eureka.instance.lease-expiration-duration-in-seconds=90

```yaml
eureka:
  instance:
    instance-id: payment8001 # 修改Eureka上显示的服务名
    prefer-ip-address: true # 访问路径鼠标停留显示ip地址
    #心跳检测与续约时间
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    lease-renewal-interval-in-seconds: 1
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    lease-expiration-duration-in-seconds: 2
```



启动提示：

```
THE SELF PRESERVATION MODE IS TURNED OFF. THIS MAY NOT PROTECT INSTANCE EXPIRY IN CASE OF NETWORK/OTHER PROBLEMS.
```



启动服务，然后停掉payment8001，发现过会儿检测的服务就会被注销掉



目前Eureka已经停更

详情见：[Home · Netflix/eureka Wiki (github.com)](https://github.com/Netflix/eureka/wiki)

> Eureka 2.0 (Discontinued)
> The existing open source work on eureka 2.0 is discontinued. The code base and artifacts that were released as part of the existing repository of work on the 2.x branch is considered use at your own risk.
>
> Eureka 1.x is a core part of Netflix's service discovery system and is still an active project.



---

### Zookeeper服务注册与发现

3.4.9版本下载地址 [Index of /dist/zookeeper/zookeeper-3.4.9 (apache.org)](https://archive.apache.org/dist/zookeeper/zookeeper-3.4.9/)

此处使用windows版zookeeper 3.4.9演示。下载完成，解压，复制conf\zoo_sample.cfg文件，重命名为zoo.cfg，添加如下字段：

```
dataDir=C:\\dev\\zookeeper\\data
dataLogDir=C:\\dev\\zookeeper\\log
```



#### 创建支付模块 cloud-provider-payment8004

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-provider-payment8004</artifactId>

    <dependencies>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- SpringBoot整合zookeeper客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <!--先排除自带的zookeeper3.5.3 防止与3.4.9起冲突-->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- 引入公共commons模块 -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

</project>
```



application.yaml

```yaml
server:
  port: 8004
spring:
  application:
    name: cloud-provider-payment
  cloud:
    zookeeper:
      connect-string: 127.0.0.1:2181
```



启动类

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/zk")
    public String paymentZk(){
        return "spring cloud with zookeeper : --> "+serverPort+ UUID.randomUUID().toString();
    }
}

```



启动服务：双击bin\zkServer.cmd 服务端

启动支付服务项目

测试1：访问 [localhost:8004/payment/zk](http://localhost:8004/payment/zk)

返回结果：

```
spring cloud with zookeeper : --> 80048a8bfb6c-16f9-42e0-9700-a486cddb3a17
```



测试2：双击bin\zkCli.cmd客户端，进行如下命令操作：

```shell
[zk: localhost:2181(CONNECTED) 0] ls /services
[cloud-provider-payment]
[zk: localhost:2181(CONNECTED) 1] ls /services/cloud-provider-payment
[dbac9cf3-6555-40e2-996e-d2686190f47e]
[zk: localhost:2181(CONNECTED) 2] get /services/cloud-provider-payment/dbac9cf3-6555-40e2-996e-d2686190f47e
{"name":"cloud-provider-payment","id":"dbac9cf3-6555-40e2-996e-d2686190f47e","address":"PIG","port":8004,"sslPort":null,"payload":{"@class":"org.springframework.cloud.zookeeper.discovery.ZookeeperInstance","id":"application-1","name":"cloud-provider-payment","metadata":{}},"registrationTimeUTC":1621155920122,"serviceType":"DYNAMIC","uriSpec":{"parts":[{"value":"scheme","variable":true},{"value":"://","variable":false},{"value":"address","variable":true},{"value":":","variable":false},{"value":"port","variable":true}]}}
cZxid = 0x5
ctime = Sun May 16 17:05:20 CST 2021
mZxid = 0x5mtime = Sun May 16 17:05:20 CST 2021
pZxid = 0x5
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x179746c00c80000
dataLength = 524
numChildren = 0
[zk: localhost:2181(CONNECTED) 3]
```



##### 服务节点是临时节点还是持久节点

zookeeper是临时节点。



#### 创建order消费module子模块注册到zookeeper

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumerzk-order80</artifactId>

    <dependencies>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- SpringBoot整合zookeeper客户端 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <!--先排除自带的zookeeper-->
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--添加zookeeper3.4.9版本-->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```



application.yaml

```yaml
server:
  port: 80
spring:
  application:
    name: cloud-consumer-order
  cloud:
    zookeeper:
      connect-string: 127.0.0.1:2181
```



启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderZKMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderZKMain80.class,args);
    }
}

```



config配置类

```java
package com.hyd.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```



controller层

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderZKController {
    public static final String INVOKE_URL="http://cloud-provider-payment";
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/consumer/payment/zk")
    public String paymentZKInfo(){
        return restTemplate.getForObject(INVOKE_URL+"/payment/zk",String.class);
    }

}

```

注意zookeeper服务区分大小写。



启动测试无问题。



---

### Consul服务注册与发现



#### Consul简介

[Consul by HashiCorp](https://www.consul.io/)

Consul是一套开源的分布式服务发现和配置管理系统，由HashiCorp 公司用Go语言开发。

提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建全方位的服务网格，总之Consul提供了一种完整的服务网格解决方案。

它具有很多优点。包括：基于raft协议，比较简洁；支持健康检查，同时支持HTTP和DNS协议支持跨数据中心的WAN集群提供图形界面跨平台，支持Linux、Mac、Windows。

能干嘛？

- 服务发现 - 提供HTTP和DNS两种发现方式。
- 健康监测 - 支持多种方式，HTTP、TCP、Docker、Shell脚本定制化
- KV存储 - Key、Value的存储方式
- 多数据中心 - Consul支持多数据中心
- 可视化Web界面

#### Consul安装与运行

命令：

```
# 查看版本
consul -v
Consul v1.9.5
Revision 3c1c22679
Protocol 2 spoken by default, understands 2 to 3 (agent will automatically use protocol >2 when speaking to compatible agents)
# 开发模式启动
consul agent -dev 
```

图形化界面，启动后访问：http://localhost:8500/



#### 服务提供者注册Consul

创建子模块cloud-providerconsul-payment8005

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-providerconsul-payment8005</artifactId>

    <dependencies>
        <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--SpringCloud consul-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--日常通用jar包配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>RELEASE</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```



application.yaml

```yaml
server:
  port: 8005
spring:
  application:
    name: cloud-provider-payment
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```



主启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8005 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8005.class,args);
    }
}

```



controller层

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/consul")
    public String paymentConsul(){
        return "spring cloud with consul : --> "+serverPort+" "+ UUID.randomUUID().toString();
    }
}

```



访问：http://localhost:8005/payment/consul

结果：

```
spring cloud with consul : --> 8005 98d5d1b2-0d81-4dd2-9ad2-cbf1dd2c4e2c
```



#### 服务消费者注册Consul

创建子模块cloud-consumerconsul-order80

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumerconsul-order80</artifactId>

    <dependencies>
        <!--SpringCloud consul-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
        <!-- SpringBoot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--日常通用jar包配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```



application.yaml

```yaml
server:
  port: 80
spring:
  application:
    name: cloud-consumer-order
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        service-name: ${spring.application.name}
```



主启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain80.class,args);
    }
}

```



config配置类

```java
package com.hyd.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```



controller层

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderConsulController {
    public static final String INVOKE_URL = "http://consul-provider-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/consul")
    public String paymentInfo() {
        return restTemplate.getForObject(INVOKE_URL + "/payment/consul", String.class);
    }
}

```



访问：http://localhost/consumer/payment/consul

结果：

```
spring cloud with consul : --> 8005 74b6b34c-11ef-4acd-bf08-ed013e33ad54
```

访问Consul界面：http://localhost:8500/

一共三个服务：`consul`,`cloud-consumer-order`,`cloud-provider-payment`.



#### 三个注册中心异同点

| 组件名    | 语言 | CAP  | 服务健康检查 | 对外暴露接口 | Spring Cloud集成 |
| --------- | ---- | ---- | ------------ | ------------ | ---------------- |
| Eureka    | Java | AP   | 可配支持     | HTTP         | 已集成           |
| Consul    | Go   | CP   | 支持         | HTTP/DNS     | 已集成           |
| Zookeeper | Java | CP   | 支持         | 客户端       | 已集成           |



CAP：

- C：Consistency (强一致性)
- A：Availability (可用性)
- P：Partition tolerance （分区容错性)
- 最多只能同时较好的满足两个。
- CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求。因此，根据CAP原理将NoSQL数据库分成了满足CA原则，满足CP原则和满足AP原则三大类：
  - CA-单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大。
  - CP-满足一致性，分区容忍性的系统，通常性能不是特别高。
  - AP-满足可用性，分区容忍性的系统，通常对一致性要求低一些。
- CAP理论关注粒度是数据，而不是整体系统设计的策略。



---

## 服务调用

### Ribbon

#### 概述

Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。

简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。

简单的说，就是在配置文件中列出Load Balancer(简称LB)后面所有的机器，Ribbon会自动的帮助你基于某种规则(如简单轮询，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。



官网地址：[Netflix/ribbon](https://github.com/Netflix/ribbon)

目前处于维护状态，一些功能在生产环境还在大规模使用，Ribbon未来可能被Spring Cloud LoadBalacer替代。

负载均衡是什么

* 简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA (高可用)。

  常见的负载均衡有软件Nginx，LVS，硬件F5等。

Ribbon本地负载均衡客户端VS Nginx服务端负载均衡区别

Nginx是服务器负载均衡，客户端所有请求都会交给nginx，然后由nginx实现转发请求。即负载均衡是由服务端实现的。
Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。



作用：

* LB（负载均衡）

  * 集中式LB

    * 即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5, 也可以是软件，如nginx)，由该设施负责把访问请求通过某种策略转发至服务的提供方;

  * 进程内LB

    * 将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。

      Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。

* 之前通过80端口通过轮询负载访问8001/8002

* 一句话就是：负载均衡 + RestTemplate调用



---

#### Ribbon负载均衡演示

总结：Ribbon其实就是一个软负载均衡的客户端组件，它可以和其他所需请求的客户端结合使用，和Eureka结合只是其中的一个实例。

Ribbon在工作时分成两步：

- 第一步先选择EurekaServer ,它优先选择在同一个区域内负载较少的server。
- 第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。

其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权。



其中引入Eureka依赖时，已经默认引入Ribbon依赖，无需再次引入

```xml
<!-- eureka client -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

`spring-cloud-starter-netflix-eureka-client`中，包含以下依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    <version>2.2.1.RELEASE</version>
    <scope>compile</scope>
</dependency>
```



RestTemplate的使用：

getForObject()：返回对象为响应体中数据转化成的对象，基本上可以理解为Json。

getForEntity()：返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码、响应体等。

```java
    @GetMapping("/getForEntity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode()+" ,"+entity.getStatusCodeValue()+" ,"+entity.getHeaders());
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败");
        }
    }
```

结果：

```
{
    "code": 200,
    "message": "查询成功 --- port: 8001",
    "data": {
        "id": 11,
        "serial": "test011"
    }
}
```

日志记录：

```
200 OK
200
[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Tue, 18 May 2021 09:59:43 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
```

postForEntity

```java
```

调用结果：

```json
{
    "code": 200,
    "message": "插入数据库成功",
    "data": 1
}
```



#### Ribbon核心组件IRule

IRule接口

```java
/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
package com.netflix.loadbalancer;

/**
 * Interface that defines a "Rule" for a LoadBalancer. A Rule can be thought of
 * as a Strategy for loadbalacing. Well known loadbalancing strategies include
 * Round Robin, Response Time based etc.
 * 
 * @author stonse
 * 
 */
public interface IRule{
    /*
     * choose one alive server from lb.allServers or
     * lb.upServers according to key
     * 
     * @return choosen Server object. NULL is returned if none
     *  server is available 
     */

    public Server choose(Object key);
    
    public void setLoadBalancer(ILoadBalancer lb);
    
    public ILoadBalancer getLoadBalancer();    
}

```



重要实现类：`AbstractLoadBalancerRule`

```java
/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
package com.netflix.loadbalancer;

import com.netflix.client.IClientConfigAware;

/**
 * Class that provides a default implementation for setting and getting load balancer
 * @author stonse
 *
 */
public abstract class AbstractLoadBalancerRule implements IRule, IClientConfigAware {

    private ILoadBalancer lb;
        
    @Override
    public void setLoadBalancer(ILoadBalancer lb){
        this.lb = lb;
    }
    
    @Override
    public ILoadBalancer getLoadBalancer(){
        return lb;
    }      
}

```



继承关系图：

```mermaid
graph BT
WeightedResponseTimeRule --> RoundRobinRule
RoundRobinRule --> AbstractLoadBalancerRule
AbstractLoadBalancerRule --> IClientConfigAware
AbstractLoadBalancerRule --> IRule
RandomRule --> AbstractLoadBalancerRule
RetryRule --> AbstractLoadBalancerRule
BestAvailableRule --> AbstractLoadBalancerRule
AvailabilityFilteringRule --> AbstractLoadBalancerRule
```

###### Ribbon默认自带的负载规则

lRule：根据特定算法中从服务列表中选取一个要访问的服务

* RoundRobinRule 轮询
* RandomRule 随机
* RetryRule 先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重
* WeightedResponseTimeRule 对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
* BestAvailableRule 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
* AvailabilityFilteringRule 先过滤掉故障实例，再选择并发较小的实例
* ZoneAvoidanceRule 默认规则,复合判断server所在区域的性能和server的可用性选择服务器

###### 如何替换

注意配置细节

官方文档明确给出了警告:

这个自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下，

否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊化定制的目的了。

（也就是说不要将Ribbon配置类与主启动类同包）

```
com.hyd |- springcloud
			- OrderMain80.java
		|- myrule
			- MySelfRule.java
```



新建包，新建规则类

```java
package com.hyd.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new RandomRule();//随机负载均衡算法
    }
}

```

修改启动类，添加`@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)`

表示访问CLOUD-PAYMENT-SERVICE服务时，使用我们自定义的负载均衡算法

```java
package com.hyd.springcloud;

import com.hyd.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 微服务消费者订单模块
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)//表示访问CLOUD-PAYMENT-SERVICE服务时，使用我们自定义的负载均衡算法
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}

```



启动服务后，访问：http://localhost/consumer/payment/getForEntity/11 ，发现随机选取服务实例



#### Ribbon负载均衡算法

##### 原理

默认负载轮训算法: rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标。（每次服务重启动后rest接口计数从1开始。）

```
请求数：n，服务器集群总数量：k
实际调用服务下标：(n++)%k
服务重启后，n=1开始
```

```java
//获取具体服务下的实例列表
List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
```

实例：

```java
List[0] instances = 127.0.0.1:8001;
List[1] instances = 127.0.0.1:8002;
List[2] instances = 127.0.0.1:8003;
```

8001,8002,8003组成集群，共计3台机器，集群总数为3，按照轮询算法原理：

* 当总请求数为1时:1%3=1对应下标位置为1，则获得服务地址为127.0.0.1:8002
* 当总请求数位2时:2%3=2对应下标位置为2，则获得服务地址为127.0.0.1:8003
* 当总请求数位3时:3%3=0对应下标位置为0，则获得服务地址为127.0.0.1:8001
* 当总请求数位4时:4%3=1对应下标位置为1，则获得服务地址为127.0.0.1:8002
  如此类推…

##### 源码分析

`IRule.java`

```java
/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
package com.netflix.loadbalancer;

/**
 * Interface that defines a "Rule" for a LoadBalancer. A Rule can be thought of
 * as a Strategy for loadbalacing. Well known loadbalancing strategies include
 * Round Robin, Response Time based etc.
 * 
 * @author stonse
 * 
 */
public interface IRule{
    /*
     * choose one alive server from lb.allServers or
     * lb.upServers according to key
     * 
     * @return choosen Server object. NULL is returned if none
     *  server is available 
     */

    public Server choose(Object key);
    
    public void setLoadBalancer(ILoadBalancer lb);
    
    public ILoadBalancer getLoadBalancer();    
}

```

`AbstractLoadBalancerRule.java`

```java
/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/
package com.netflix.loadbalancer;

import com.netflix.client.IClientConfigAware;

/**
 * Class that provides a default implementation for setting and getting load balancer
 * @author stonse
 *
 */
public abstract class AbstractLoadBalancerRule implements IRule, IClientConfigAware {

    private ILoadBalancer lb;
        
    @Override
    public void setLoadBalancer(ILoadBalancer lb){
        this.lb = lb;
    }
    
    @Override
    public ILoadBalancer getLoadBalancer(){
        return lb;
    }      
}

```

`RoundRobinRule.java`

```java
/*
 *
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.netflix.loadbalancer;

import com.netflix.client.config.IClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The most well known and basic load balancing strategy, i.e. Round Robin Rule.
 *
 * @author stonse
 * @author Nikos Michalakis <nikos@netflix.com>
 *
 */
public class RoundRobinRule extends AbstractLoadBalancerRule {

    private AtomicInteger nextServerCyclicCounter;
    private static final boolean AVAILABLE_ONLY_SERVERS = true;
    private static final boolean ALL_SERVERS = false;

    private static Logger log = LoggerFactory.getLogger(RoundRobinRule.class);

    public RoundRobinRule() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }

    public RoundRobinRule(ILoadBalancer lb) {
        this();
        setLoadBalancer(lb);
    }

    //选出一个服务器提供服务
    public Server choose(ILoadBalancer lb, Object key) {
        //如果负载均衡算法不存在
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        //最多选择10次尝试
        while (server == null && count++ < 10) {
            //存活的服务器（可达的，健康的）
            List<Server> reachableServers = lb.getReachableServers();
            //所有的服务器
            List<Server> allServers = lb.getAllServers();
            //存活的服务器
            int upCount = reachableServers.size();
            //服务器总数
            int serverCount = allServers.size();

            //确保有可用的服务器
            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }

            //获取服务器数组下角标
            int nextServerIndex = incrementAndGetModulo(serverCount);
            //根据角标选择一个服务器
            server = allServers.get(nextServerIndex);

            if (server == null) {
                /* Transient. */
                Thread.yield();
                continue;
            }

            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            // Next.
            server = null;
        }

        //最多尝试10次，十次之后报错
        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;
    }

    /**
     * Inspired by the implementation of {@link AtomicInteger#incrementAndGet()}.
     *
     * @param modulo The modulo to bound the value of the counter.
     * @return The next value.
     */
    private int incrementAndGetModulo(int modulo) {
        //此处为自旋锁，确保获取到的角标在并发环境下的一致性
        for (;;) {
            int current = nextServerCyclicCounter.get();
            //
            int next = (current + 1) % modulo;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}

```





//TODO

##### 手写负载均衡算法

首先ApplicationContextConfig去掉RestTemplate上的注解@LoadBalanced，OrderMain80启动类去掉注解@RibbonClient

服务提供者新增测试查询接口

```java
    /**
     * 测试负载均衡算法选择
     * @return
     */
    @GetMapping("/lb")
    public String getPaymentLB(){
        return "server port: "+serverPort;
    }
```

订单消费模块，写一个负载均衡接口；

```java
package com.hyd.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface MyLoadBalancer {

    ServiceInstance instanses(List<ServiceInstance> serviceInstances);
}

```

写一个该接口的实现类

```java
package com.hyd.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements MyLoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        while (true){
            current = this.atomicInteger.get();
            next = current>=Integer.MAX_VALUE?0:current+1;
            if(this.atomicInteger.compareAndSet(current,next)){
                System.out.println("第几次访问服务：next --->: "+next);
                return next;
            }
        }
    }
    @Override
    public ServiceInstance instanses(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement()%serviceInstances.size();
        return serviceInstances.get(index);
    }
}

```



服务调用

```java
    /**
     * 引入自定义的负载均衡
     */
    @Autowired
    private MyLoadBalancer loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

```



```java
    /**
     * 测试负载均衡算法选择
     * @return
     */
    @GetMapping("/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(instances == null || instances.size() ==0 ){
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instanses(instances);
        URI serviceInstanceUri = serviceInstance.getUri();
        return restTemplate.getForObject(serviceInstanceUri+"payment/lb",String.class);
    }
```



启动服务访问：http://localhost/consumer/payment/lb

返回结果：

```
server port: 8003
server port: 8002
server port: 8001
server port: 8003
server port: 8002
...
```

日志记录

```
第几次访问服务：next --->: 1
第几次访问服务：next --->: 2
第几次访问服务：next --->: 3
第几次访问服务：next --->: 4
第几次访问服务：next --->: 5
第几次访问服务：next --->: 6
...
```



---

## 服务调用2

### Feign（停更）

Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端

Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。

Feign的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务

Feign支持的注解和用法请参考官方文档：https://github.com/OpenFeign/feign



### OpenFeign

#### 概述

##### OpenFeign是什么

OpenFeign是一个声明式的web服务客户端，让编写web服务客户端变的非常容易，只需要创建一个接口并在接口上添加注解即可，openFeign的前身是Feign，后者目前已经停更了，openFeign是SpringCloud在Feign的基础上支持了Spring MVC的注解，并通过动态代理的方式产生实现类来做负载均衡并进行调用其他服务。

##### Feign能干什么

Feign旨在使编写Java Http客户端变得更容易。

前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

#####  Feign集成了Ribbon

利用Ribbon维护了Payment的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用。

##### Feign和OpenFeign两者区别

Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。Feign的使用方式是:使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务。

OpenFeign是Spring Cloud在Feign的基础上支持了SpringMVC的注解，如@RequesMapping等等。OpenFeign的@Feignclient可以解析SpringMVc的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

##### Ribbon+RestTemplate和OpenFeign区别

//TODO

#### OpenFeign使用

创建消费者订单子模块cloud-consumer-feign-order80

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumer-feign-order80</artifactId>
    <dependencies>
        <!-- openfeign -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- 自定义通用commons包 -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--一般基础通用配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

application.yaml

```yaml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class,args);
    }
}

```

编写业务逻辑接口，进行服务调用，接口添加`@FeignClient(value = "CLOUD-PAYMENT-SERVICE")`注解，方法标明访问url路径`@GetMapping("/payment/get/{id}")`

```java
package com.hyd.springcloud.service;

import com.hyd.springcloud.entity.CommonResult;
import com.hyd.springcloud.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping("/payment/get/{id}")
    CommonResult<Payment>  getPaymentById(@PathVariable("id") Long id);
}

```

controller层

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.entity.CommonResult;
import com.hyd.springcloud.entity.Payment;
import com.hyd.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/consumer/payment")
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @GetMapping("/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }
}

```



启动服务，访问：http://localhost/consumer/payment/get/11

结果：

```
{
    "code": 200,
    "message": "查询成功 --- port: 8003",
    "data": {
        "id": 11,
        "serial": "test011"
    }
}
```

Feign自带负载均衡配置项

#### OpenFeign超时控制

消费者模块改造，新增测试接口

```java
    /**
     * 测试feign超时控制
     * @return
     */
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return serverPort;
        }
    }
```

业务层逻辑接口`PaymentFeignService`新增方法

```java
    @GetMapping("/payment/feign/timeout")
    String paymentFeignTimeout();
```

controller层

```java
    @GetMapping("/feign/timeout")
    public String paymentFeignTimeout(){
        return paymentFeignService.paymentFeignTimeout();
    }
```

启动服务，访问：http://localhost/consumer/payment/feign/timeout

结果：500超时报错

```
"Read timed out executing GET http://CLOUD-PAYMENT-SERVICE/payment/feign/timeout"
```

OpenFeign默认等待1秒钟，超过后报错，实际开发调用服务可能会等几秒，需要根据实际情况设置

application.yaml新增OpenFeign客户端超时控制配置

```yaml
ribbon:
  #设置feign客户端超时时间(OpenFeign默认支持ribbon)(单位：毫秒)
  #指的是建立连接所用的时间，适用于网络状况正常的情况下,两端连接所用的时间
  ReadTimeout: 5000
  #指的是建立连接后从服务器读取到可用资源所用的时间
  ConnectTimeout: 5000
```

启动服务访问，调用正常

> 网上各种的方案，都是修改超时时间，当前版本修改超时时间有3中方案：
>
> (1) feign client
>
> ```
> #default为全局配置，如果要单独配置每个服务，改为服务名``#默认为10s``feign.client.config.default.connectTimeout=10000``#默认为60s``feign.client.config.default.readTimeout=60000
> ```
>
> （2）hystrix
>
> ```
> #默认为true可不配置``#hystrix.command.default.execution.timeout.enabled=true``#默认为1s``hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=6000
> ```
>
> （3）rubbin
>
> ```
> ribbon.ConnectTimeout=10000``ribbon.ReadTimeout=60000
> ```

#### OpenFeign日志打印功能

Feign提供了日志打印功能，我们可以通过配置来调整日恙级别，从而了解Feign 中 Http请求的细节。

说白了就是对Feign接口的调用情况进行监控和输出

##### 日志级别

* NONE：默认的，不显示任何日志;
* BASIC：仅记录请求方法、URL、响应状态码及执行时间;
* HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息;
* FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。



配置日志Bean

```java
package com.hyd.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}

```

application.yaml配置中为指定类设置日志级别

```yaml
logging:
  level:
    # feign 日志以什么级别监控哪个接口
    com.hyd.springcloud.service.PaymentFeignService: debug
```

启动服务，访问：http://localhost/consumer/payment/get/11

日志：

```
[PaymentFeignService#getPaymentById] ---> GET http://CLOUD-PAYMENT-SERVICE/payment/get/11 HTTP/1.1
[PaymentFeignService#getPaymentById] ---> END HTTP (0-byte body)
[PaymentFeignService#getPaymentById] <--- HTTP/1.1 200 (54ms)
[PaymentFeignService#getPaymentById] connection: keep-alive
[PaymentFeignService#getPaymentById] content-type: application/json
[PaymentFeignService#getPaymentById] date: Wed, 19 May 2021 13:14:44 GMT
[PaymentFeignService#getPaymentById] keep-alive: timeout=60
[PaymentFeignService#getPaymentById] transfer-encoding: chunked
[PaymentFeignService#getPaymentById] 
[PaymentFeignService#getPaymentById] {"code":200,"message":"查询成功 --- port: 8002","data":{"id":11,"serial":"test011"}}
[PaymentFeignService#getPaymentById] <--- END HTTP (88-byte body)

```



---

## 服务降级

### Hystrix断路器

#### 概述

##### 分布式系统面临的问题

复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免地失败。

##### 服务雪崩

多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”.
对于高流量的应用来说，单一的后避依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。

所以，通常当你发现一个模块下的某个实例失败后，这时候这个模块依然还会接收流量，然后这个有问题的模块还调用了其他的模块，这样就会发生级联故障，或者叫雪崩。

##### Hystrix是什么

Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。

"断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝)，向调用方返回一个符合预期的、可处理的备选响应（FallBack)，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

##### Hystrix能干什么

* 服务降级
* 服务熔断
* 服务限流
* 接近实时的监控
* ...

官网：[Netflix/Hystrix](https://github.com/Netflix/Hystrix)

Hystrix已经进入停止更新，维护状态。

#### Hystrix重要概念

##### 服务降级(fallback)

比如当某个服务繁忙，不能让客户端的请求一直等待，应该立刻返回给客户端一个备选方案。

出现降级的情况：

* 程序运行异常
* 超时
* 发生服务熔断
* 线程池/信号量打满

##### 服务熔断(break)

服务熔断的作用类似于我们家用的保险丝，当某服务出现不可用或响应超时的情况时，为了防止整个系统出现雪崩，暂时停止对该服务的调用。

服务的降级 -> 进而熔断 -> 恢复调用链路

##### 服务限流(flowlimit)

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行。

#### Hystrix案例

##### 服务降级

###### 创建消费者服务提供子模块

创建消费者服务提供子模块cloud-provider-hystrix-payment8001

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-provider-hystrix-payment8001</artifactId>

    <dependencies>
        <!-- hystrix -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 自定义api通用包 -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- devtools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 8001
spring:
  application:
    name: cloud-provider-hystrix-payment
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

主启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentHystrixMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8001.class,args);
    }
}

```

service层

```java
package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池: "+Thread.currentThread().getName()+" ,paymentInfo_OK ,id: "+id;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池: "+Thread.currentThread().getName()+" ,paymentInfo_TimeOut ,id: "+id+" ,耗时: 3秒";
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/hystrix")
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_OK(id);
        log.info("OK result ---> {}",result);
        return result;
    }

    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("TimeOut result ---> {}",result);
        return result;
    }
}

```

启动服务，访问：

http://localhost:8001/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-6 ,paymentInfo_OK ,id: 1
```

http://localhost:8001/payment/hystrix/timeout/1

```
线程池: http-nio-8001-exec-8 ,paymentInfo_TimeOut ,id: 1 ,耗时: 3秒
```

以上服务均正常，以该子模块为基础，演示：正确 --> 错误 --> 降级熔断 --> 恢复。

###### JMeter高并发压测后卡顿

使用Jmeter进行压力测试，建立一个线程组，创建200个线程，间隔1秒，循环100次，并创建http请求，访问timeout接口，会发现ok接口访问速度下降了，tomcat的默认的工作线程数被打满了，没有多余的线程来分解压力和处理。

###### 创建订单消费者模块，加入进来测试

模块cloud-consumer-feign-hystrix-order80

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-consumer-feign-hystrix-order80</artifactId>

    <dependencies>
        <!--hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <!--openfeign-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!--eureka client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!--一般基础通用配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderHystrixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain80.class,args);
    }
}

```

服务调用接口

```java
package com.hyd.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT")
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}

```

controller层

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/payment/hystrix")
@Slf4j
public class OrderHystirxController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id)
    {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
}

```

访问：http://localhost/consumer/payment/hystrix/ok/1

高并发压测下，order微服务模块访问卡顿

现阶段存在的问题：

* 超时导致服务器响应变慢 --> 超时不再等待
* 出错（宕机或程序出错）--> 出错要有兜底

解决：

* 对方服务(8001)超时了，调用者(80)不能一直等待，必须服务降级；
* 对方服务(8001)宕机了，调用者(80)不能一直卡死等待，必须服务降级；
* 对方服务(8001)正常，调用者(80)出故障或有自我要求（允许的等待时间小于调用的时间），必须服务降级。

###### 改造为服务降级的服务提供者子模块

改造cloud-provider-hystrix-payment8001服务提供模块

降级配置 - @HystrixCommand

8001先从自身找问题

设置自身调用超时时间的峰值，峰值内可以正常运行，超过了需要有兜底的方法处埋，作服务降级fallback。

8001fallback

业务类启用 - @HystrixCommand报异常后如何处理

—旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法。

改造cloud-provider-hystrix-payment8001服务提供模块

改造service服务

```java
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        try {
//            TimeUnit.SECONDS.sleep(3);
            int n=10/0;//故意制造异常
            TimeUnit.SECONDS.sleep(5);//故意制造超出允许时间的时长

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: " + Thread.currentThread().getName() + " ,paymentInfo_TimeOut ,id: " + id + " ,耗时: 3秒";
    }

    //用来善后的方法
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  8001系统繁忙或者运行报错，请稍后再试,id:  " + id + "\t" + "o(╥﹏╥)o";
    }
```

启动类添加注解`@EnableCircuitBreaker`

@EnableHystrix注解与@EnableCircuitBreaker的区别

* 查看@EnableHystrix的源码可以发现，它继承了@EnableCircuitBreaker，并对它进行了在封装。
* 这两个注解都是激活hystrix的功能，我们根据上面代码得出来结论，只需要在服务启动类加入@EnableHystrix注解即可，无须增加@EnableCircuitBreaker注解，本身@EnableHystrix注解已经涵盖了EnableCircuitBreaker的功能。

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker//实现hystrix断路器功能
public class PaymentHystrixMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8001.class,args);
    }
}

```

启动服务，访问：http://localhost/consumer/payment/hystrix/timeout/1

返回结果：

```
线程池:  hystrix-PaymentServiceImpl-1  8001系统繁忙或者运行报错，请稍后再试,id:  1	o(╥﹏╥)o
```

成功调用了服务超时的应对处理方法，当前服务不可用了，做服务降级，兜底的方案都是paymentInfo_TimeOutHandler。



###### 订单消费者调用方进行服务降级处理

改造子模块cloud-consumer-feign-hystrix-order80

一般服务降级，都是放在客户端。

修改application.yaml，新增配置

```yaml
#feign开启hystrix支持
feign:
  hystrix:
    enabled: true
```

修改启动类，添加`@EnableHystrix`注解

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix//开启hystrix注解
public class OrderHystrixMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain80.class,args);
    }
}

```

修改controller层逻辑，方法上添加`@HystrixCommand`注解，此注解表示此方法是hystrix方法，其中fallbackMethod定义回退方法的名称

```java
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
    })
    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    //善后方法
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
```

访问：http://localhost/consumer/payment/hystrix/timeout/1

服务端需要3秒处理(TimeUnit.SECONDS.sleep(3);)，此处调用方允许时间为1.5秒

结果：

```
我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o
```



**目前两个降级模块出现的问题**

* 每个业务方法都写了一个降级方法，重复代码多（问题1）
* 降级方法与业务方法写在了一块，耦合度高（问题2）

**解决**（问题1）

配置一个全局的降级方法，所有方法都可以走这个降级方法，至于某些特殊创建，再单独创建方法。

除了个别重要核心业务有专属，其它普通的可以通过@DefaultProperties(defaultFallback = “”)统一跳转到统一处理结果页面，通用的和独享的各自分开，避免了代码膨胀，合理减少了代码量

**改造**

添加`@DefaultProperties`注解，defaultFallback指定统一服务降级处理方法，使用`@HystrixCommand`表明该方法走默认统一服务降级处理方法，如方法paymentInfo_TimeOut2。

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/payment/hystrix")
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")//hystrix全局服务降级处理
public class OrderHystirxController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id)
    {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
    })
    @GetMapping("/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    //善后方法
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }

    @HystrixCommand
    @GetMapping("/timeout2/{id}")
    public String paymentInfo_TimeOut2(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
    // 下面是全局fallback方法
    public String payment_Global_FallbackMethod() {
        return "---全局异常处理---，请稍后重试";
    }
}

```



启动服务，访问：http://localhost/consumer/payment/hystrix/timeout2/1

结果：

```
---全局异常处理---，请稍后重试
```



**解决**（问题2）

降级方法与业务方法写在了一块，耦合度高

**服务降级，客户端去调用服务端，碰上服务端宕机或关闭**

本次案例服务降级处理是在客户端80实现完成的，与服务端8001没有关系，只需要为Feign客户端定义的接口添加一个服务降级处理的实现类即可实现解耦

未来我们要面对的异常

* 运行
* 超时
* 宕机

**改造订单消费者模块**cloud-consumer-feign-hystrix-order80

PaymentFallbackServiceImpl实现服务调用PaymentHystrixService接口，重写的方法便是该服务接口方法调用失败之后，执行实现类的方法，实现类为fallback方法。

```java
package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "fallback --> paymentInfo_OK() :"+id;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "fallback --> paymentInfo_TimeOut() :"+id;
    }
}

```

服务调用接口注解修改，新增`fallback = PaymentFallbackServiceImpl.class`字段，指定服务降级方法

```java
package com.hyd.springcloud.service;

import com.hyd.springcloud.service.impl.PaymentFallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentFallbackServiceImpl.class)
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}

```

启动服务，访问：http://localhost/consumer/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-1 ,paymentInfo_OK ,id: 1
```

关掉订单服务提供者cloud-provider-hystrix-payment8001，模拟服务提供方宕机，再次访问

```
fallback --> paymentInfo_OK() :1
```

> 另外发现实现PaymentHystrixService接口paymentInfo_TimeOut方法，controller使用了@HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",...注解，最后服务降级方法执行的是接口实现类的方法。



##### 服务熔断

断路器，相当于保险丝。

**熔断机制概述**

熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。当检测到该节点微服务调用响应正常后，恢复调用链路。

在Spring Cloud框架里，熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，当失败的调用到一定阈值，缺省是5秒内20次调用失败，就会启动熔断机制。熔断机制的注解是@HystrixCommand。

**通俗解释**

* 调用失败会触发降级，而降级会调用fallback方法
* 但无论如何，降级的流程一定会先调用正常的方法再调用fallback方法
* 假如单位时间内调用失败次数过多，也就是降级次数过多，则出发熔断
* 所谓“熔断后服务不可用”就是因为跳过了正常方法，直接执行fallback方法

[CircuitBreaker (martinfowler.com)](https://martinfowler.com/bliki/CircuitBreaker.html)

![断路器](https://martinfowler.com/bliki/images/circuitBreaker/state.png)

**案例**

修改支付服务提供模块cloud-provider-hystrix-payment8001

新增`paymentCircuitBreaker`服务方法，开启断路器

```java
    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),// 时间窗口期，会放入请求，该时间内如果放入的请求执行成功，则关闭断路器
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("---id 不能为负数："+id);
        }
        String serialNumber = IdUtil.simpleUUID();
        return "调用成功 --> "+Thread.currentThread().getName()+" ,serialNumber: "+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "fallback(): id不能为负数 --> id: "+id;
    }
```

controller层

```java
    //服务熔断
    @GetMapping("/breaker/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("breaker result --> {}"+result);
        return result;
    }
```

启动服务，访问：

http://localhost:8001/payment/hystrix/breaker/2

```
调用成功 --> hystrix-PaymentServiceImpl-5 ,serialNumber: b836707606b54a9899cc32b558e79ef0
```

http://localhost:8001/payment/hystrix/breaker/-3

```
breaker result --> {}fallback(): id不能为负数 --> id: -3
```

使用JMeter多次访问breaker/-3接口，触发熔断，断路器开启，此时访问

http://localhost:8001/payment/hystrix/breaker/2

```
breaker result --> {}fallback(): id不能为负数 --> id: 2
```

即便正常，也无法访问接口，直接走降级方法，之后自动恢复



**熔断类型**

- 熔断打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR(平均故障处理时间)，当打开时长达到所设时钟则进入半熔断状态。
- 熔断关闭：熔断关闭不会对服务进行熔断。
- 熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断。

**涉及到断路器的三个重要参数**

* 快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒。
* 请求总数阀值：在快照时间窗内，必须满足请求总数阀值才有资格熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用次数不足20次7,即使所有的请求都超时或其他原因失败，断路器都不会打开。
* 错误百分比阀值：当请求总数在快照时间窗内超过了阀值，比如发生了30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阀值情况下，这时候就会将断路器打开。
  断路器开启或者关闭的条件

到达以下阀值，断路器将会开启：

* 当满足一定的阀值的时候（默认10秒内超过20个请求次数)
* 当失败率达到一定的时候（默认10秒内超过50%的请求失败)
* 当开启的时候，所有请求都不会进行转发

一段时间之后（默认是5秒)，这个时候断路器是半开状态，会让其中一个请求进行转发。如果成功，断路器会关闭，若失败，继续开启。

断路器打开之后

1：再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback。通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。

2：原来的主逻辑要如何恢复呢？

对于这一问题，hystrix也为我们实现了自动恢复功能。

当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑是临时的成为主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

服务降级 --> 服务熔断 --> 恢复调用链路





`HystrixCommandProperties`配置类源码

```java
    /* defaults */
    /* package */ static final Integer default_metricsRollingStatisticalWindow = 10000;// default => statisticalWindow: 10000 = 10 seconds (and default of 10 buckets so each bucket is 1 second)
    private static final Integer default_metricsRollingStatisticalWindowBuckets = 10;// default => statisticalWindowBuckets: 10 = 10 buckets in a 10 second window so each bucket is 1 second
    private static final Integer default_circuitBreakerRequestVolumeThreshold = 20;// default => statisticalWindowVolumeThreshold: 20 requests in 10 seconds must occur before statistics matter
    private static final Integer default_circuitBreakerSleepWindowInMilliseconds = 5000;// default => sleepWindow: 5000 = 5 seconds that we will sleep before trying again after tripping the circuit
    private static final Integer default_circuitBreakerErrorThresholdPercentage = 50;// default => errorThresholdPercentage = 50 = if 50%+ of requests in 10 seconds are failures or latent then we will trip the circuit
    private static final Boolean default_circuitBreakerForceOpen = false;// default => forceCircuitOpen = false (we want to allow traffic)
    /* package */ static final Boolean default_circuitBreakerForceClosed = false;// default => ignoreErrors = false 
    private static final Integer default_executionTimeoutInMilliseconds = 1000; // default => executionTimeoutInMilliseconds: 1000 = 1 second
    private static final Boolean default_executionTimeoutEnabled = true;
    private static final ExecutionIsolationStrategy default_executionIsolationStrategy = ExecutionIsolationStrategy.THREAD;
    private static final Boolean default_executionIsolationThreadInterruptOnTimeout = true;
    private static final Boolean default_executionIsolationThreadInterruptOnFutureCancel = false;
    private static final Boolean default_metricsRollingPercentileEnabled = true;
    private static final Boolean default_requestCacheEnabled = true;
    private static final Integer default_fallbackIsolationSemaphoreMaxConcurrentRequests = 10;
    private static final Boolean default_fallbackEnabled = true;
    private static final Integer default_executionIsolationSemaphoreMaxConcurrentRequests = 10;
    private static final Boolean default_requestLogEnabled = true;
    private static final Boolean default_circuitBreakerEnabled = true;
    private static final Integer default_metricsRollingPercentileWindow = 60000; // default to 1 minute for RollingPercentile 
    private static final Integer default_metricsRollingPercentileWindowBuckets = 6; // default to 6 buckets (10 seconds each in 60 second window)
    private static final Integer default_metricsRollingPercentileBucketSize = 100; // default to 100 values max per bucket
    private static final Integer default_metricsHealthSnapshotIntervalInMilliseconds = 500; // default to 500ms as max frequency between allowing snapshots of health (error percentage etc)

    @SuppressWarnings("unused") private final HystrixCommandKey key;
    private final HystrixProperty<Integer> circuitBreakerRequestVolumeThreshold; // number of requests that must be made within a statisticalWindow before open/close decisions are made using stats
    private final HystrixProperty<Integer> circuitBreakerSleepWindowInMilliseconds; // milliseconds after tripping circuit before allowing retry
    private final HystrixProperty<Boolean> circuitBreakerEnabled; // Whether circuit breaker should be enabled.
    private final HystrixProperty<Integer> circuitBreakerErrorThresholdPercentage; // % of 'marks' that must be failed to trip the circuit
    private final HystrixProperty<Boolean> circuitBreakerForceOpen; // a property to allow forcing the circuit open (stopping all requests)
    private final HystrixProperty<Boolean> circuitBreakerForceClosed; // a property to allow ignoring errors and therefore never trip 'open' (ie. allow all traffic through)
    private final HystrixProperty<ExecutionIsolationStrategy> executionIsolationStrategy; // Whether a command should be executed in a separate thread or not.
    private final HystrixProperty<Integer> executionTimeoutInMilliseconds; // Timeout value in milliseconds for a command
    private final HystrixProperty<Boolean> executionTimeoutEnabled; //Whether timeout should be triggered
    private final HystrixProperty<String> executionIsolationThreadPoolKeyOverride; // What thread-pool this command should run in (if running on a separate thread).
    private final HystrixProperty<Integer> executionIsolationSemaphoreMaxConcurrentRequests; // Number of permits for execution semaphore
    private final HystrixProperty<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests; // Number of permits for fallback semaphore
    private final HystrixProperty<Boolean> fallbackEnabled; // Whether fallback should be attempted.
    private final HystrixProperty<Boolean> executionIsolationThreadInterruptOnTimeout; // Whether an underlying Future/Thread (when runInSeparateThread == true) should be interrupted after a timeout
    private final HystrixProperty<Boolean> executionIsolationThreadInterruptOnFutureCancel; // Whether canceling an underlying Future/Thread (when runInSeparateThread == true) should interrupt the execution thread
    private final HystrixProperty<Integer> metricsRollingStatisticalWindowInMilliseconds; // milliseconds back that will be tracked
    private final HystrixProperty<Integer> metricsRollingStatisticalWindowBuckets; // number of buckets in the statisticalWindow
    private final HystrixProperty<Boolean> metricsRollingPercentileEnabled; // Whether monitoring should be enabled (SLA and Tracers).
    private final HystrixProperty<Integer> metricsRollingPercentileWindowInMilliseconds; // number of milliseconds that will be tracked in RollingPercentile
    private final HystrixProperty<Integer> metricsRollingPercentileWindowBuckets; // number of buckets percentileWindow will be divided into
    private final HystrixProperty<Integer> metricsRollingPercentileBucketSize; // how many values will be stored in each percentileWindowBucket
    private final HystrixProperty<Integer> metricsHealthSnapshotIntervalInMilliseconds; // time between health snapshots
    private final HystrixProperty<Boolean> requestLogEnabled; // whether command request logging is enabled.
    private final HystrixProperty<Boolean> requestCacheEnabled; // Whether request caching is enabled.
```



详细配置

```java
@HystrixCommand(fallbackMethod = "fallbackMethod", 
                groupKey = "strGroupCommand", 
                commandKey = "strCommand", 
                threadPoolKey = "strThreadPool",
                
                commandProperties = {
                    // 设置隔离策略，THREAD 表示线程池 SEMAPHORE：信号池隔离
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    // 当隔离策略选择信号池隔离的时候，用来设置信号池的大小（最大并发数）
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                    // 配置命令执行的超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutinMilliseconds", value = "10"),
                    // 是否启用超时时间
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    // 执行超时的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
                    
                    // 执行被取消的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true"),
                    // 允许回调方法执行的最大并发数
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
                    // 服务降级是否启用，是否执行回调函数
                    @HystrixProperty(name = "fallback.enabled", value = "true"),
                    // 是否启用断路器
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    // 该属性用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为 20 的时候，如果滚动时间窗（默认10秒）内仅收到了19个请求， 即使这19个请求都失败了，断路器也不会打开。
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                    
                    // 该属性用来设置在滚动时间窗中，表示在滚动时间窗中，在请求数量超过 circuitBreaker.requestVolumeThreshold 的情况下，如果错误请求数的百分比超过50, 就把断路器设置为 "打开" 状态，否则就设置为 "关闭" 状态。
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    // 该属性用来设置当断路器打开之后的休眠时间窗。 休眠时间窗结束之后，会将断路器置为 "半开" 状态，尝试熔断的请求命令，如果依然失败就将断路器继续设置为 "打开" 状态，如果成功就设置为 "关闭" 状态。
                    @HystrixProperty(name = "circuitBreaker.sleepWindowinMilliseconds", value = "5000"),
                    // 断路器强制打开
                    @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
                    // 断路器强制关闭
                    @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false"),
                    // 滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
                    @HystrixProperty(name = "metrics.rollingStats.timeinMilliseconds", value = "10000"),
                    
                    // 该属性用来设置滚动时间窗统计指标信息时划分"桶"的数量，断路器在收集指标信息的时候会根据设置的时间窗长度拆分成多个 "桶" 来累计各度量值，每个"桶"记录了一段时间内的采集指标。
                    // 比如 10 秒内拆分成 10 个"桶"收集这样，所以 timeinMilliseconds 必须能被 numBuckets 整除。否则会抛异常
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                    // 该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果设置为 false, 那么所有的概要统计都将返回 -1。
                    @HystrixProperty(name = "metrics.rollingPercentile.enabled", value = "false"),
                    // 该属性用来设置百分位统计的滚动窗口的持续时间，单位为毫秒。
                    @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "60000"),
                    // 该属性用来设置百分位统计滚动窗口中使用 “ 桶 ”的数量。
                    @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "60000"),
                    // 该属性用来设置在执行过程中每个 “桶” 中保留的最大执行次数。如果在滚动时间窗内发生超过该设定值的执行次数，
                    // 就从最初的位置开始重写。例如，将该值设置为100, 滚动窗口为10秒，若在10秒内一个 “桶 ”中发生了500次执行，
                    // 那么该 “桶” 中只保留 最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间。
                    @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"),
                    
                    // 该属性用来设置采集影响断路器状态的健康快照（请求的成功、 错误百分比）的间隔等待时间。
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds", value = "500"),
                    // 是否开启请求缓存
                    @HystrixProperty(name = "requestCache.enabled", value = "true"),
                    // HystrixCommand的执行和事件是否打印日志到 HystrixRequestLog 中
                    @HystrixProperty(name = "requestLog.enabled", value = "true"),

                },
                threadPoolProperties = {
                    // 该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
                    @HystrixProperty(name = "coreSize", value = "10"),
                    // 该参数用来设置线程池的最大队列大小。当设置为 -1 时，线程池将使用 SynchronousQueue 实现的队列，否则将使用 LinkedBlockingQueue 实现的队列。
                    @HystrixProperty(name = "maxQueueSize", value = "-1"),
                    // 该参数用来为队列设置拒绝阈值。 通过该参数， 即使队列没有达到最大值也能拒绝请求。
                    // 该参数主要是对 LinkedBlockingQueue 队列的补充,因为 LinkedBlockingQueue 队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5"),
                }
               )
public String doSomething() {
	...
}
```



#### Hystrix工作流程

流程图：原图地址：[hystrix-command-flow-chart.png (1372×667) (raw.githubusercontent.com)](https://raw.githubusercontent.com/wiki/Netflix/Hystrix/images/hystrix-command-flow-chart.png)

![Hystrix工作流程图](https://github.com/Netflix/Hystrix/wiki/images/hystrix-command-flow-chart.png)



**步骤说明**

创建HystrixCommand （用在依赖的服务返回单个操作结果的时候）或HystrixObserableCommand（用在依赖的服务返回多个操作结果的时候）对象。
命令执行。
其中 HystrixCommand实现了下面前两种执行方式
execute()：同步执行，从依赖的服务返回一个单一的结果对象或是在发生错误的时候抛出异常。
2. queue()：异步执行，直接返回一个Future对象，其中包含了服务执行结束时要返回的单一结果对象。
而 HystrixObservableCommand实现了后两种执行方式：
obseve()：返回Observable对象，它代表了操作的多个统
果，它是一个Hot Observable （不论“事件源”是否有“订阅者”，都会在创建后对事件进行发布，所以对于Hot Observable的每一个“订阅者”都有可能是从“事件源”的中途开始的，并可能只是看到了整个操作的局部过程）。
2. toObservable()：同样会返回Observable对象，也代表了操作的多个结果，但它返回的是一个Cold Observable（没有“订间者”的时候并不会发布事件，而是进行等待，直到有“订阅者"之后才发布事件，所以对于Cold Observable 的订阅者，它可以保证从一开始看到整个操作的全部过程）。
若当前命令的请求缓存功能是被启用的，并且该命令缓存命中，那么缓存的结果会立即以Observable对象的形式返回。
检查断路器是否为打开状态。如果断路器是打开的，那么Hystrix不会执行命令，而是转接到fallback处理逻辑(第8步)；如果断路器是关闭的，检查是否有可用资源来执行命令(第5步)。
线程池/请求队列信号量是否占满。如果命令依赖服务的专有线程地和请求队列，或者信号量（不使用线程的时候）已经被占满，那么Hystrix也不会执行命令，而是转接到fallback处理理辑(第8步) 。
Hystrix会根据我们编写的方法来决定采取什么样的方式去请求依赖服务。
HystrixCommand.run()：返回一个单一的结果，或者抛出异常。
HystrixObservableCommand.construct()：返回一个Observable对象来发射多个结果，或通过onError发送错误通知。
Hystix会将“成功”、“失败”、“拒绝”、“超时” 等信息报告给断路器，而断路器会维护一组计数器来统计这些数据。断路器会使用这些统计数据来决定是否要将断路器打开，来对某个依赖服务的请求进行"熔断/短路"。
当命令执行失败的时候，Hystix会进入fallback尝试回退处理，我们通常也称波操作为“服务降级”。而能够引起服务降级处理的情况有下面几种：
第4步∶当前命令处于“熔断/短路”状态，断洛器是打开的时候。
第5步∶当前命令的钱程池、请求队列或者信号量被占满的时候。
第6步∶HystrixObsevableCommand.construct()或HytrixCommand.run()抛出异常的时候。
当Hystrix命令执行成功之后，它会将处理结果直接返回或是以Observable的形式返回。
tips：如果我们没有为命令实现降级逻辑或者在降级处理逻辑中抛出了异常，Hystrix依然会运回一个Obsevable对象，但是它不会发射任结果数惯，而是通过onError方法通知命令立即中断请求，并通过onError方法将引起命令失败的异常发送给调用者。

#### 服务监控HystrixDashboard

##### 概述

除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控(Hystrix Dashboard)，Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。

Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。

Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。而在spring cloud中，只有引入spring-cloud-starter-hystrix这一启动依赖，就会默认引入hystrix-metrics-event-stream依赖。

##### HystrixDashboard案例

创建监控模块cloud-consumer-hystrix-dashboard9001

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-consumer-hystrix-dashboard9001</artifactId>

    <dependencies>
        <dependency>
            <!-- hystrix dashboard 仪表盘监控 -->
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 9001
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard//开启hystrix dashboard
public class HystrixDashboardMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardMain9001.class,args);
    }
}
```

监控的目标服务，必须具备两个条件。第一，服务本身有对Hystrix做监控统计(spring-cloud-starter-hystrix启动依赖)；第二，暴露hystrix.stream端口(spring-boot-starter-actuator启动依赖)。所以需要确保目标服务引入如下依赖：

```xml
<!-- hystrix -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
<!-- actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



然后在子模块cloud-provider-hystrix-payment8001中，添加如下配置，暴露hystrix监控端点

```yaml
# 被监控的hystrix-service服务需要开启Actuator的hystrix.stream端点
management:
  endpoints:
    web:
      exposure:
        include: "hystrix.stream" # 暴露hystrix监控端点
```

启动服务，访问Hystrix Dashboard图形化界面 http://localhost:9001/hystrix

输入要监控的服务：http://127.0.0.1:8001/actuator/hystrix.stream

（注意：部分版本地址为：http://127.0.0.1:8001/hystrix.stream）

就可以看到监控仪表盘界面了



---

## 服务网关

### 概述

//TODO

### Zuul

#### 概述

##### 是什么

官网：[Netflix/zuul: Zuul is a gateway service that provides dynamic routing, monitoring, resiliency, security, and more. (github.com)](https://github.com/Netflix/zuul)

**功能**

* 代理+路由+过滤

**Zuul包含了对请求的路由和过滤两个最主要的功能**：

其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础；而过滤器功能则负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础。Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后的访问微服务都是通过Zuul跳转后获得的。

注意：Zuul服务最终还是会注册进Eureka



#### 路由基本配置

创建子模块cloud-zuul-gateway9527

pom.xml

```xml
        <!-- zuul -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

application.yaml

```yaml
server:
  port: 9527
spring:
  application:
    name: cloud-zuul-gateway9527
eureka:
  client:
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
  instance:
    instance-id: gateway9527
    prefer-ip-address: true
```

启动类

```java
@SpringBootApplication
@EnableZuulProxy
public class ZuulMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulMain9527.class,args);
    }
}

```

Zuul网关 @EnableZuulProxy 和 @EnableZuulServer 的区别

@EnableZuulProxy简单理解为@EnableZuulServer的增强版， 当Zuul与Eureka、Ribbon等组件配合使用时，我们使用@EnableZuulProxy。



修改Windows host文件主机映射配置：

```
# Zuul映射配置
127.0.0.1       cloud-zuul-gateway9527
```



启动服务，访问：

http://localhost:8001/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-1 ,paymentInfo_OK ,id: 1
```

通过Zuul网关访问：

http://cloud-zuul-gateway9527:9527/cloud-provider-hystrix-payment/payment/hystrix/ok/1

http://localhost:9527/cloud-provider-hystrix-payment/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-6 ,paymentInfo_OK ,id: 1
```





#### 路由访问映射规则

修改Zuul模块application.yaml配置

配置

```yaml
#  Zuul路由访问映射规则
zuul:
  routes:
    payment.serviceId: cloud-provider-hystrix-payment
    payment.path: /payment8001/**
```

或者：

```yaml
#  Zuul路由访问映射规则
zuul:
  routes:
    payment8001: # 此处名称随便起
      serviceId: cloud-provider-hystrix-payment # 映射的服务id名
      path: /payment8001/** # 映射路径
```

serviceId可以写为service-id

启动服务，访问：http://cloud-zuul-gateway9527:9527/payment8001/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-5 ,paymentInfo_OK ,id: 1
```

**忽略原真实服务名，设置统一公共前缀**

此时，原访问路径和通过Zuul路由访问路径都可以进行服务访问

限制不能通过访问服务名去访问微服务

```yaml
ignored-services: cloud-provider-hystrix-payment
```

如果想把其他很多的微服务都通过配置忽略掉真实访问路径则直接使用`"*"`来代替

修改application.yaml

```yaml
#  Zuul路由访问映射规则
zuul:
  prefix: /hyd # 增加统一的访问前缀
  ignored-services: "*" # 忽略所有的服务名称
  routes:
    payment8001: # 此处名称随便起
      service-id: cloud-provider-hystrix-payment # 映射的服务id名
      path: /payment8001/** # 映射路径
```

启动服务，访问：http://cloud-zuul-gateway9527:9527/hyd/payment8001/payment/hystrix/ok/1

```
线程池: http-nio-8001-exec-7 ,paymentInfo_OK ,id: 1
```

直接访问服务：http://cloud-zuul-gateway9527:9527/hyd/cloud-provider-hystrix-payment/payment/hystrix/ok/1

```json
{
    "timestamp": "2021-05-22T05:47:33.377+0000",
    "status": 404,
    "error": "Not Found",
    "message": "No message available",
    "path": "/hyd/cloud-provider-hystrix-payment/payment/hystrix/ok/1"
}
```

#### 查看路由信息

//TODO

#### 过滤器

//TODO



### Spring Cloud Gateway

#### 概念简介

##### 官网

Cloud全家桶中有个很重要的组件就是网关，在1.x版本中都是采用的Zuul网关;

但在2.x版本中，zuul的升级一直跳票，SpringCloud最后自己研发了一个网关替代Zuul，那就是SpringCloud Gateway—句话：gateway是原zuul1.x版的替代

2.2.8文档地址：[Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/2.2.8.RELEASE/reference/html/)

##### 是什么

Gateway是在Spring生态系统之上构建的API网关服务，基于Spring 5，Spring Boot 2和Project Reactor等技术。

Gateway旨在提供一种简单而有效的方式来对API进行路由，以及提供一些强大的过滤器功能，例如:熔断、限流、重试等。

SpringCloud Gateway是Spring Cloud的一个全新项目，基于Spring 5.0+Spring Boot 2.0和Project Reactor等技术开发的网关，它旨在为微服务架构提供—种简单有效的统一的API路由管理方式。

SpringCloud Gateway作为Spring Cloud 生态系统中的网关，目标是替代Zuul，在Spring Cloud 2.0以上版本中，没有对新版本的Zul 2.0以上最新高性能版本进行集成，仍然还是使用的Zuul 1.x非Reactor模式的老版本。而为了提升网关的性能，SpringCloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty。

##### 能干什么

Spring Cloud Gateway的目标提供统一的路由方式且基于 Filter链的方式提供了网关基本的功能，例如:安全，监控/指标，和限流。

作用

* 反向代理
* 鉴权
* 流量控制
* 熔断
* 日志监控
* …

**简介**

* SpringCloud Gateway使用的是WebFlux中的reactor-netty响应式编程组件，底层使用了Netty通信框架。

##### 微服务架构中网关在哪里

![微服务图](https://spring.io/images/diagram-microservices-88e01c7d34c688cb49556435c130d352.svg)

##### 为什么用Gateway替代Zuul

###### 为什么选择Gateway

* netflix不太靠谱，zuul2.0一直跳票，迟迟不发布。
  * 一方面因为Zuul1.0已经进入了维护阶段，而且Gateway是SpringCloud团队研发的，是亲儿子产品，值得信赖。而且很多功能Zuul都没有用起来也非常的简单便捷。
  * Gateway是基于异步非阻塞模型上进行开发的，性能方面不需要担心。虽然Netflix早就发布了最新的Zuul 2.x，但Spring Cloud貌似没有整合计划。而且Netflix相关组件都宣布进入维护期；不知前景如何?
    多方面综合考虑Gateway是很理想的网关选择。

* SpringCloud Gateway具有如下特性
  * 基于Spring Framework 5，Project Reactor和Spring Boot 2.0进行构建（稳健，同一家族）；
  * 动态路由：能够匹配任何请求属性；
  * 可以对路由指定Predicate (断言)和Filter(过滤器)；
  * 集成Hystrix的断路器功能；
  * 集成Spring Cloud 服务发现功能；
  * 易于编写的Predicate (断言)和Filter (过滤器)；
  * 请求限流功能；
  * 支持路径重写。

##### SpringCloud Gateway与Zuul的区别

* 在SpringCloud Finchley正式版之前，Spring Cloud推荐的网关是Netflix提供的Zuul。
* Zuul 1.x，是一个基于阻塞I/O的API Gateway。Zuul 1.x基于Servlet 2.5使用阻塞架构它不支持任何长连接(如WebSocket)Zuul的设计模式和Nginx较像，每次I/О操作都是从工作线程中选择一个执行，请求线程被阻塞到工作线程完成，但是差别是Nginx用C++实现，Zuul用Java实现，而JVM本身会有第-次加载较慢的情况，使得Zuul的性能相对较差。
* Zuul 2.x理念更先进，想基于Netty非阻塞和支持长连接，但SpringCloud目前还没有整合。Zuul .x的性能较Zuul 1.x有较大提升。在性能方面，根据官方提供的基准测试,Spring Cloud Gateway的RPS(每秒请求数)是Zuul的1.6倍。
* Spring Cloud Gateway建立在Spring Framework 5、Project Reactor和Spring Boot2之上，使用非阻塞API。
* Spring Cloud Gateway还支持WebSocket，并且与Spring紧密集成拥有更好的开发体验

##### Zuul 1.x模型

Springcloud中所集成的Zuul版本，采用的是Tomcat容器，使用的是传统的Serviet IO处理模型。

Servlet的生命周期？servlet由servlet container进行生命周期管理。

* container启动时构造servlet对象并调用servlet init()进行初始化；
* container运行时接受请求，并为每个请求分配一个线程（一般从线程池中获取空闲线程）然后调用service)；
* container关闭时调用servlet destory()销毁servlet。 

##### Gateway模型

WebFlux是什么？官方文档：https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#spring-webflux

传统的Web框架，比如说: Struts2，SpringMVC等都是基于Servlet APl与Servlet容器基础之上运行的。

但是在Servlet3.1之后有了异步非阻塞的支持。而WebFlux是一个典型非阻塞异步的框架，它的核心是基于Reactor的相关API实现的。相对于传统的web框架来说，它可以运行在诸如Netty，Undertow及支持Servlet3.1的容器上。非阻塞式+函数式编程(Spring 5必须让你使用Java 8)。

Spring WebFlux是Spring 5.0 引入的新的响应式框架，区别于Spring MVC，它不需要依赖Servlet APl，它是完全异步非阻塞的，并且基于Reactor来实现响应式流规范。

##### 三大核心概念

* Route(路由) - 路由是构建网关的基本模块,它由ID,目标URI,一系列的断言和过滤器组成,如断言为true则匹配该路由；
* Predicate(断言) - 参考的是Java8的java.util.function.Predicate，开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数),如果请求与断言相匹配则进行路由；
* Filter(过滤) - 指的是Spring框架中GatewayFilter的实例,使用过滤器,可以在请求被路由前或者之后对请求进行修改。

web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制。

predicate就是我们的匹配条件；而fliter，就可以理解为一个无所不能的拦截器。有了这两个元素，再加上目标uri，就可以实现一个具体的路由了。

//TODO

#### Gateway工作流程

工作流程图

![工作流程图](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/images/spring_cloud_gateway_diagram.png)

客户端向Spring Cloud Gateway发出请求。然后在Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到GatewayWeb Handler。

Handler再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回。

过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前(“pre”)或之后(“post"）执行业务逻辑。

Filter在“pre”类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等，在“post”类型的过滤器中可以做响应内容、响应头的修改，日志的输出，流量监控等有着非常重要的作用。

核心逻辑：路由转发 + 执行过滤器链。

#### 入门配置

//TODO

创建gateway子模块

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-gateway-gateway9527</artifactId>
    <dependencies>
        <!-- spring cloud gateway -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- 引入自定义api通用包 -->
        <dependency>
            <groupId>com.hyd.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--一般基础配置类-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

application.yaml

此处`hostname: ${spring.cloud.client.ipAddress}`在spring cloud 2.x版本中改为${spring.cloud.client.ip-address}

```yaml
server:
  port: 9527
spring:
  application:
    name: cloud-gateway-gateway9527 # 应用名
eureka:
  instance:
#    hostname: gateway-haha # 应用实例主机名，缺省默认为windows计算机主机名
    hostname: ${spring.cloud.client.ip-address} # 设置主机名为主机ip
    appname: cloud-gateway-service # 服务名，默认取 spring.application.name 配置值，如果没有则为 unknown
    instance-id: ${spring.cloud.client.ip-address}:${server.port} # 实例ID:此处为id+端口
    prefer-ip-address: false # 客户端在注册时使用自己的IP而不是主机名
  client:
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9527.class,args);
    }
}

```

启动服务，访问：http://localhost:8001/payment/get/11

```json
{
    "code": 200,
    "message": "查询成功 --- port: 8001",
    "data": {
        "id": 11,
        "serial": "test011"
    }
}
```

到此处，搭建了基本框架，没有进行路由配置，接下来进行路由配置

##### Spring Cloud Gateway配置路由的两种方式

###### 配置文件application.yaml配置

修改application.yaml

注意gateway不需要引入web依赖，否则可能报错

```yaml
spring:
  application:
    name: cloud-gateway-gateway9527 # 应用名
# <-- ***start*** Spring Cloud Gateway 网关配置
  cloud:
    gateway: # 网关
      routes: # 路由
        - id: payment_route # 路由的ID，没有固定规则但要求唯一，建议配合服务名
          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由
        - id: payment_route2
          uri: http://localhost:8001
          predicates:
            - Path=/payment/lb/**
#     ***end*** -->
```

启动服务，访问：http://localhost:8001/payment/lb

```
server port: 8001
```

访问路由配置后的地址：http://localhost:9527/payment/lb

```
server port: 8001
```

完成

###### 编码配置

案例：通过编码配置，经9527网关访问到外网：http://news.baidu.com/guonei

官网示例：

```java
@Bean
public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("circuitbreaker_route", r -> r.path("/consumingServiceEndpoint")
            .filters(f -> f.circuitBreaker(c -> c.name("myCircuitBreaker").fallbackUri("forward:/inCaseOfFailureUseThis").addStatusCode("INTERNAL_SERVER_ERROR"))
                .rewritePath("/consumingServiceEndpoint", "/backingServiceEndpoint")).uri("lb://backing-service:8088")
        .build();
}
```

官方文档地址：[Spring Cloud Gateway](https://docs.spring.io/spring-cloud-gateway/docs/2.2.8.RELEASE/reference/html/#circuit-breaker-status-codes)

新建配置类，进行配置

```java
package com.hyd.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route(
                "route1",r ->r.path("/news").uri("http://news.baidu.com/guonei")
        );
        return routes.build();
    }
}

```

成功，结果略



#### 动态路由

默认情况下Gateway会根据注册中心注册的服务列表，以注册中心上微服务名为路径创建**动态路由进行转发，从而实现动态路由的功能**（不写死一个地址）。

启动Eureka server7001,7002,7003,启动payment8001,8002,8003,启动网关gateway9527

修改网关application.yaml配置，开启动态路由，修改url协议为lb，表示启用Gateway的负载均衡功能。

```yaml
# <-- ***start*** Spring Cloud Gateway 网关配置
  cloud:
    gateway: # 网关
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes: # 路由
        - id: payment_route # 路由的ID，没有固定规则但要求唯一，建议配合服务名
#          uri: http://localhost:8001 # 匹配后提供服务的路由地址
          uri: lb://cloud-payment-service # 动态路由后，gateway负载均衡uri
          predicates:
            - Path=/payment/get/** # 断言，路径相匹配的进行路由
        - id: payment_route2
#          uri: http://localhost:8001
          uri: lb://cloud-payment-service
          predicates:
            - Path=/payment/lb/**
#     ***end*** -->
```

启动服务，访问：http://localhost:9527/payment/lb

```
server port: 8003;
server port: 8001;
server port: 8002;
...
```



#### Predicate（断言）的使用

启动我们的gateway网关，会发现后台有如下日志：

```
Loaded RoutePredicateFactory [After]
Loaded RoutePredicateFactory [Before]
Loaded RoutePredicateFactory [Between]
Loaded RoutePredicateFactory [Cookie]
Loaded RoutePredicateFactory [Header]
Loaded RoutePredicateFactory [Host]
Loaded RoutePredicateFactory [Method]
Loaded RoutePredicateFactory [Path]
Loaded RoutePredicateFactory [Query]
Loaded RoutePredicateFactory [ReadBodyPredicateFactory]
Loaded RoutePredicateFactory [RemoteAddr]
Loaded RoutePredicateFactory [Weight]
Loaded RoutePredicateFactory [CloudFoundryRouteService]
```

Spring Cloud Gateway将路由匹配作为Spring WebFlux HandlerMapping基础架构的一部分。

Spring Cloud Gateway包括许多内置的Route Predicate工厂。所有这些Predicate都与HTTP请求的不同属性匹配。多个RoutePredicate工厂可以进行组合。

Spring Cloud Gateway创建Route 对象时，使用RoutePredicateFactory 创建 Predicate对象，Predicate 对象可以赋值给Route。Spring Cloud Gateway包含许多内置的Route Predicate Factories。
所有这些谓词都匹配HTTP请求的不同属性。多种谓词工厂可以组合，并通过逻辑and。



路由断言工厂：

```
The After Route Predicate Factory
The Before Route Predicate Factory
The Between Route Predicate Factory
The Cookie Route Predicate Factory
The Header Route Predicate Factory
The Host Route Predicate Factory
The Method Route Predicate Factory
The Path Route Predicate Factory
The Query Route Predicate Factory
The RemoteAddr Route Predicate Factory
The Weight Route Predicate Factory
```

* `After`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: after_route
            uri: https://example.org
            predicates:
            - After=2017-01-20T17:42:47.789-07:00[America/Denver]
    ```

  * 指定时间之后，才可路由到指定微服务（指定的时间之前访问，都会报404）

* `Before`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: before_route
            uri: https://example.org
            predicates:
            - Before=2017-01-20T17:42:47.789-07:00[America/Denver]
    ```

  * 在指定时间之前的才可以路由到该微服务

* `Between`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: between_route
            uri: https://example.org
            predicates:
            - Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
    ```

  * 指定时间之间，才可以路由到该微服务

* `Cookie`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: cookie_route
            uri: https://example.org
            predicates:
            - Cookie=chocolate, ch.p
    ```

  * 只有包含某些指定cookie(key,value),的请求才可以路由

* `Header`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: header_route
            uri: https://example.org
            predicates:
            - Header=X-Request-Id, \d+
    ```

  * 只有包含指定请求头的请求,才可以路由（支持正则）

* `Host`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: host_route
            uri: https://example.org
            predicates:
            - Host=**.somehost.org,**.anotherhost.org
    ```

  * 只有指定主机的才可以访问

* `Method`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: method_route
            uri: https://example.org
            predicates:
            - Method=GET,POST
    ```

  * 指定请求方式才可以路由访问

* `Path`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: path_route
            uri: https://example.org
            predicates:
            - Path=/red/{segment},/blue/{segment}
    ```

  * 指定访问路径，才可以路由

* `Query`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: query_route
            uri: https://example.org
            predicates:
            - Query=red, gree.
    ```

  * 带有指定请求参数，才可以路由访问（支持正则，参数值要为整数）

* `RemoteAddr`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: remoteaddr_route
            uri: https://example.org
            predicates:
            - RemoteAddr=192.168.1.1/24
    ```

  * 指定ip地址段才可以路由访问

* `Weight`

  * ```yaml
    spring:
      cloud:
        gateway:
          routes:
          - id: weight_high
            uri: https://weighthigh.org
            predicates:
            - Weight=group1, 8
          - id: weight_low
            uri: https://weightlow.org
            predicates:
            - Weight=group1, 2
    ```

  * 通过权重路由请求，:`Weight=group，weight` ，上述路由将80%的流量转发到‎‎weighthigh.org‎‎，20%的流量转发到‎‎weighlow.org‎

上述时区时间生成方式：

```java
ZonedDateTime zonedDateTime = ZonedDateTime.now();
System.out.println(zonedDateTime);
```

结果：

```
2021-05-23T12:11:38.009+08:00[Asia/Shanghai]
```

可以通过curl命令进行上述访问测试：

```shell
# 带cookie的
curl http://localhost:9527/payment/lb --cookie "chocolate=chip"
# 带指定请求头的参数命令
curl http://localhost:9527/payment/lb -H "X-Request-Id:123"
```

案例：

```yaml
  cloud:
    gateway: # 网关
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes: # 路由
        - id: payment_route2
          uri: lb://cloud-payment-service
          predicates:
            - Path=/payment/lb/**
            - Cookie=chocolate, chip
            - Header=X-Request-Id, 123
```

启动服务，测试

```
C:\Users\ayiya>curl http://localhost:9527/payment/lb --cookie "chocolate=chip" -H "X-Request-Id:123"
server port: 8003
C:\Users\ayiya>
```



**小结**

说白了，Predicate就是为了实现一组匹配规则，让请求过来找到对应的Route进行处理。

除了通过yaml配置实现，同样可以通过编码进行配置

`AfterRoutePredicateFactory` `UML`类图

![AfterRoutePredicateFactory](.\UML类图\AfterRoutePredicateFactory.png)



```java
RemoteAddressResolver resolver = XForwardedRemoteAddressResolver
    .maxTrustedIndex(1);

...

.route("direct-route",
    r -> r.remoteAddr("10.1.1.1", "10.10.1.1/24")
        .uri("https://downstream1")
.route("proxied-route",
    r -> r.remoteAddr(resolver, "10.10.1.1", "10.10.1.1/24")
        .uri("https://downstream2")
)
```



#### Filter的使用

路由过滤器可用于修改进入的HTTP请求和返回的HTTP响应，路由过滤器只能指定路由进行使用。Spring Cloud Gateway内置了多种路由过滤器，他们都由GatewayFilter的工厂类来产生。

Spring Cloud Gateway的Filter:

生命周期：

* pre（类似spring前置通知）
* post（类似spring后置通知）

种类（具体看官方文档）：

* GatewayFilter - 有31种，官方文档地址：https://docs.spring.io/spring-cloud-gateway/docs/2.2.8.RELEASE/reference/html/#gatewayfilter-factories
* GlobalFilter - 有10种，官网文档地址：https://docs.spring.io/spring-cloud-gateway/docs/2.2.8.RELEASE/reference/html/#global-filters

常用的GatewayFilter：AddRequestParameter GatewayFilter

自定义全局GlobalFilter：

```java
@Bean
public GlobalFilter customFilter() {
    return new CustomGlobalFilter();
}

public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```



两个主要接口介绍：

* GlobalFilter
* Ordered

能干什么：

* 全局日志记录
* 统一网关鉴权
* …

##### 自定义全局过滤器

官网示例

```java
@Bean
public GlobalFilter customFilter() {
    return new CustomGlobalFilter();
}

public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```

在网关gateway模块创建自定义过滤器类

```java
package com.hyd.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("<--- enter MyGatewayFilter.filter() --->");
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if(username==null){
            log.error("--- 用户名为null ---");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        log.info("--- username: {}",username);
        //放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

```

启动服务，访问：http://localhost:9527/payment/lb

```
406 Not Acceptable
```

控制台：

```
<--- enter MyGatewayFilter.filter() --->
--- 用户名为null ---
```

正确访问：http://localhost:9527/payment/lb?username=张三

```
server port: 8003
```

```
<--- enter MyGatewayFilter.filter() --->
--- username: 张三
```

结束



---

## 服务配置

### Spring Cloud Config（分布式配置中心）

#### 概述

**分布式系统面临的配置问题**

微服务意味着要将单体应用中的业务拆分成一个个子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务。由于每个服务都需要必要的配置信息才能运行，所以一套集中式的、动态的配置管理设施是必不可少的。

SpringCloud提供了ConfigServer来解决这个问题，我们每一个微服务自己带着一个application.yml，上百个配置文件的管理.……



官网：[Spring Cloud Config](https://docs.spring.io/spring-cloud-config/docs/2.2.8.RELEASE/reference/html/)

**是什么**

SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。

**SpringCloud Config分为服务端和客户端两部分**

* 服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息等访问接口。

* 客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。

**作用**

* 集中管理配置文件
* 不同环境不同配置，动态化的配置更新，分环境部署比如dev/test/prod/beta/release
* 运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息
* 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
* 将配置信息以REST接口的形式暴露 - post/crul访问刷新即可…

**与GitHub整合配置**

由于SpringCloud Config默认使用Git来存储配置文件(也有其它方式,比如支持SVN和本地文件)，但最推荐的还是Git，而且使用的是http/https访问的形式。



#### Spring Cloud Config配置中心搭建

##### 使用Git存储配置文件

创建git仓库：`git@github.com:ayiyaha-hyd/spring-cloud-config.git`

此处注意分支名，github默认分支名为main，下面案例为master

新建三个配置文件：

`config-dev.yaml`

```yaml
config:
  info: "master branch,spring-cloud-config/config-dev.yaml version=7"
```

`config-prod.yaml`

```yaml
config:
  info: "master branch,spring-cloud-config/config-prod.yaml version=1"
```

`config-test.yaml`

```yaml
config:
  info: "master branch,spring-cloud-config/config-test.yaml version=1"
```

##### 创建config子模块cloud-config-center3344

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-config-center3344</artifactId>

    <dependencies>
        <!-- spring cloud config server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 3344
spring:
  application:
    name: cloud-config-center # 应用名，Eureka上显示的实例名
  cloud:
    config:
      server:
        git:
          uri: git@github.com:ayiyaha-hyd/spring-cloud-config.git
          search-paths: # 搜索目录
            - spring-cloud-config
      label: master # 读取分支
eureka:
  instance:
    hostname: ${spring.cloud.client.ip-address} # Eureka上显示的主机名
    appname: cloud-config-service # Eureka上显示的服务名，缺省为spring.application.name
  client:
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigMain3344.class, args);
    }
}

```

启动服务，访问：http://localhost:3344/master/config-dev.yaml

```
config:
  info: master branch,spring-cloud-config/config-dev.yaml version=7

```

成功读取到了git上的配置文件，最终所有微服务只从git上获取配置文件

##### 配置读取规则

* `/{label}/{application}-{profile}.yaml`（推荐）
  * master分支
    * http://localhost:3344/master/config-dev.yaml
    * http://localhost:3344/master/config-test.yaml
  * dev分支
    * http://localhost:3344/dev/config-dev.yaml
* `/{application}-{profile}.yaml`
  * http://localhost:3344/config-dev.yaml
  * http://localhost:3344/config-test.yaml
* `/{application}-{profile}/{label}.yaml`
  * dev分支
    * http://localhost:3344/config-dev/dev.yaml
  * master分支
    * http://localhost:3344/config-dev/master.yaml

解释：

- label：分支(branch)
- name：服务名
- profiles：环境(dev/test/prod

##### 创建Config Client（客户端）拉取配置

创建子模块

pom.xml需要config client,eureka client,web三个依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-config-client3355</artifactId>

    <dependencies>
        <!-- spring cloud config client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-client</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

其中`spring-cloud-starter-config`和`spring-cloud-config-client`都可以，只是引入的部分依赖不同

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```

bootstrap.yaml（注意此处为bootstrap.yaml）

```yaml
server:
  port: 3355
spring:
  application:
    name: cloud-config-client3355
  cloud:
    config: # Spring Cloud Config客户端配置
      label: master # 分支名
      name: config # 配置文件名
      profile: dev # 属性后缀名
      uri: http://localhost:3344 # 配置中心地址
eureka:
  instance:
    appname: cloud-config-client-service # Eureka显示服务名
  client:
    service-url:
      defaultZone: http://cloud-eureka-server7001:7001/eureka/, http://cloud-eureka-server7002:7002/eureka/, http://cloud-eureka-server7003:7003/eureka/ #集群版
```

**bootstrap.yml与application.yml区别**

* applicaiton.yml是用户级的资源配置项
* bootstrap.yml是系统级的，优先级更加高
* Spring Cloud会创建一个Bootstrap Context，作为Spring应用的Application Context的父上下文。初始化的时候，BootstrapContext负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的Environment。
* Bootstrap属性有高优先级，默认情况下，它们不会被本地配置覆盖。Bootstrap context和Application Context有着不同的约定，所以新增了一个bootstrap.yml文件，保证Bootstrap Context和Application Context配置的分离。
* 要将Client模块下的application.yml文件改为bootstrap.yml,这是很关键的，因为bootstrap.yml是比application.yml先加载的。bootstrap.yml优先级高于application.yml。

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientMain3355 {
    public static void main(String[] args) {
        SpringApplication.run(ClientMain3355.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}

```

`Git`上的配置文件`spring-cloud-config/config-dev.yaml`

```yaml
config:
  info: "master branch,spring-cloud-config/config-dev.yaml version=7"
```

启动服务，访问：http://localhost:3355/configInfo

```
master branch,spring-cloud-config/config-dev.yaml version=7
```

成功通过配置中心读取到了配置文件信息



但是，倘若此时修改git配置文件，修改`version=2`

```yaml
config:
  info: "master branch,spring-cloud-config/config-dev.yaml version=7"
```

配置中心访问：http://localhost:3344/master/config-dev.yaml

```
config:
  info: master branch,spring-cloud-config/config-dev.yaml version=2

```

客户端访问：http://localhost:3355/configInfo

```
master branch,spring-cloud-config/config-dev.yaml version=7
```

**问题**

服务启动之后，修改git配置文件，配置中心实时刷新，但客户端并没有发生改变，除非重启或重新加载。

分布式配置的动态刷新问题

Linux运维修改GitHub上的配置文件内容做调整
刷新3344，发现ConfigServer配置中心立刻响应
刷新3355，发现ConfigClient客户端没有任何响应
3355没有变化除非自己重启或者重新加载
难到每次运维修改配置文件，客户端都需要重启??噩梦

##### Config 动态刷新解决

修改客户端子模块cloud-config-client3355

pom.xml引入actuator监控依赖

```xml
<!-- 此处为了动态获取config配置中心配置 引入 actuator依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

修改bootstrap.yaml，添加暴露监控端口配置

```yaml
# 暴露监控端口
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

controller层添加`@RefreshScope`注解

```java
package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}

```



启动服务，测试：修改git配置文件version=3 --> 访问配置中心3344 --> 访问客户端3355，没有改变

```
master branch,spring-cloud-config/config-dev.yaml version=2
```

运维人员发送Post请求刷新3355

```shell
curl -X POST "http://localhost:3355/actuator/refresh"
```

```
["config.client.version","config.info"] 
```

再次访问：

```
master branch,spring-cloud-config/config-dev.yaml version=3
```

成功实现了客户端3355刷新到最新配置内容，避免了服务重启



想想还有什么问题?

- 假如有多个微服务客户端3355/3366/3377
- 每个微服务都要执行—次post请求，手动刷新?
- 可否广播，一次通知，处处生效?
- 我们想大范围的自动刷新，求方法

---

## 消息总线

#### Spring Cloud Bus（消息总线）

##### 概述

**概念**

Spring Cloud Bus可以理解为Spring Cloud体系架构中的消息总线，通过一个轻量级的Message Broker来将分布式系统中的节点连接起来。可用来实现广播状态更新（如配置更新），或其它管理指令。
Spring Cloud Bus 就像是一个分布式的Spring Boot Actuator， 目前提供了两种类型的消息队列中间件支持：RabbitMQ与Kafka（对应的pom依赖分别为spring-cloud-starter-bus-amqp， spring-cloud-starter-bus-kafka）。

**作用**

Spring Cloud Bus能管理和传播分布式系统间的消息，就像一个分布式执行器，可用于广播状态更改、事件推送等，也可以当作微服务间的通信通道。

**什么是总线**

在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播一些需要让其他连接在该主题上的实例都知道的消息。

**基本原理**

ConfigClient实例都监听MQ中同一个topic(默认是Spring Cloud Bus)。当一个服务刷新数据的时候，它会把这个信息放入到Topic中，这样其它监听同一Topic的服务就能得到通知，然后去更新自身的配置。

##### RabbitMQ环境配置

RabbitMQ依赖Erlang语言环境，且注意二者的版本对应关系。

版本对应关系：[RabbitMQ Erlang Version Requirements — RabbitMQ](https://www.rabbitmq.com/which-erlang.html)

本项目采用RabbitMQ 3.8.3，对应Erlang OTP 22.3。

下载地址：

http://erlang.org/download/otp_win64_22.3.exe

https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.3/rabbitmq-server-3.8.3.exe

默认安装，添加系统环境变量`ERLANG_HOME=C:\Program Files\erl10.7`（如果没有配置的话）在系统`path`添加`%ERLANG_HOME%\bin`。

命令行输入`erl`：

```
C:\Users\ayiya>erl
Eshell V10.7  (abort with ^G)
```

进入RabbitMQ安装目录下面的`sbin`目录，启用RabbitMQ管理功能

打开`cmd`执行命令：`rabbitmq-plugins enable rabbitmq_management`

```
C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.3\sbin>rabbitmq-plugins enable rabbitmq_management
Enabling plugins on node rabbit@PIG:
rabbitmq_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@PIG...
The following plugins have been enabled:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch

started 3 plugins.

C:\Program Files\RabbitMQ Server\rabbitmq_server-3.8.3\sbin>
```

执行成功后，访问RabbitMQ web管理界面：[RabbitMQ Management](http://localhost:15672/)

初始用户名和密码：`guest/guest`

常用命令：

```
net start RabbitMQ  启动
net stop RabbitMQ  停止
rabbitmqctl status  查看状态
```



##### Spring Cloud Bus动态刷新全局广播的设计思想

方案1.利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置

方案2.利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置

方案2显然更合适，方案1不合适的原因如下：

* 打破了微服务的职责单一性，因为微服务本身是业务模块，它本不应该承担配置刷新的职责。
* 破坏了微服务各节点的对等性。
* 有一定的局限性。例如，微服务在迁移时，它的网络地址常常会发生变化，此时如果想要做到自动刷新，那就会增加更多的修改。

##### Spring Cloud Bus动态刷新全局广播配置实现

再创建两个客户端模块cloud-config-client3356，cloud-config-client3357

现在一共三个客户端模块读取config配置中心模块配置

**config配置中心模块**

config配置中心模块cloud-config-center3344添加消息总线支持

pom.xml新增以下配置

```xml
<!-- spring cloud bus amap -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
<!-- spring cloud bus amap 消息总线动态刷新通知时，引入的actuator依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

bootstrap.yaml新增rabbitmq配置及bus端点暴露配置

```yaml
spring:
  # rabbitmq相关配置
  rabbitmq:
    host: localhost
    port: 5672 # 15672是Web管理界面的端口；5672是MQ访问的端口
    username: guest
    password: guest

# rabbitmq相关配置,暴露bus刷新配置的端点
management:
  endpoints:
    web:
      exposure:
        include: "bus-refresh"
```

**client 客户端模块配置**

为三个客户端模块cloud-config-client3355/3356/3357添加消息总线支持

pom.xml添加依赖(actuator依赖之前已添加)

```xml
<!-- spring cloud bus amap -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

bootstrap.yaml添加配置(端点暴露配置之前已添加)

```yaml
spring:
  # rabbitmq相关配置
  rabbitmq:
    host: localhost
    port: 5672 # 15672是Web管理界面的端口；5672是MQ访问的端口
    username: guest
    password: guest
```

启动服务，修改git配置文件(修改version，3-->4)，发送post请求，查看客户端读取配置情况

发送请求（—次发送，处处生效）

```shell
curl -X POST "http://localhost:3344/actuator/bus-refresh"
```

```shell
curl -X GET http://localhost:3357/configInfo
master branch,spring-cloud-config/config-dev.yaml version=4
```

**—次修改，广播通知，处处生效**



##### Bus动态刷新定点通知

修改完配置文件之后，指定具体某一个实例生效而不是全部

```shell
# 过destination参数类指定需要更新配置的服务或实例
curl -X POST "http://localhost:3344/actuator/bus-refresh/{destination}"
```

例如：修改version=5，发送post请求刷新，cloud-config-client3357为配置文件中设定的应用名称，刷新指定客户端配置

```shell
curl -X POST "http://localhost:3344/actuator/bus-refresh/cloud-config-client3357"
```

```shell
curl -X GET http://localhost:3357/configInfo
master branch,spring-cloud-config/config-dev.yaml version=5
```



结束

---

## 消息驱动

### 消息驱动概述

常见MQ(消息中间件)：

ActiveMQ
RabbitMQ
RocketMQ
Kafka
有没有一种新的技术诞生，让我们不再关注具体MQ的细节，我们只需要用一种适配绑定的方式，自动的给我们在各种MQ内切换。（类似于Hibernate）

Cloud Stream是什么？屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型。

#### Spring Cloud Stream是什么

什么是Spring Cloud Stream？

官方定义Spring Cloud Stream是一个构建消息驱动微服务的框架。

应用程序通过inputs或者 outputs 来与Spring Cloud Stream中binder对象交互。

通过我们配置来binding(绑定)，而Spring Cloud Stream 的binder对象负责与消息中间件交互。所以，我们只需要搞清楚如何与Spring Cloud Stream交互就可以方便使用消息驱动的方式。

通过使用Spring Integration来连接消息代理中间件以实现消息事件驱动。
Spring Cloud Stream为一些供应商的消息中间件产品提供了个性化的自动化配置实现，引用了发布-订阅、消费组、分区的三个核心概念。

目前仅支持RabbitMQ、 Kafka。

#### Stream的设计思想

**标准MQ**

生产者/消费者之间靠消息媒介传递信息内容
消息必须走特定的通道 - 消息通道 Message Channel
消息通道里的消息如何被消费呢，谁负责收发处理 - 消息通道MessageChannel的子接口SubscribableChannel，由MessageHandler消息处理器所订阅。

##### 为什么要用Spring Cloud Stream

比方说我们用到了RabbitMQ和Kafka，由于这两个消息中间件的架构上的不同，像RabbitMQ有exchange，kafka有Topic和Partitions分区。

这些中间件的差异性导致我们实际项目开发给我们造成了一定的困扰，我们如果用了两个消息队列的其中一种，后面的业务需求，我想往另外一种消息队列进行迁移，这时候无疑就是一个灾难性的，一大堆东西都要重新推倒重新做，因为它跟我们的系统耦合了，这时候Spring Cloud Stream给我们提供了—种解耦合的方式。

Stream凭什么可以统一底层差异？

在没有绑定器这个概念的情况下，我们的SpringBoot应用要直接与消息中间件进行信息交互的时候，由于各消息中间件构建的初衷不同，它们的实现细节上会有较大的差异性通过定义绑定器作为中间层，完美地实现了应用程序与消息中间件细节之间的隔离。通过向应用程序暴露统一的Channel通道，使得应用程序不需要再考虑各种不同的消息中间件实现。

通过定义绑定器Binder作为中间层，实现了应用程序与消息中间件细节之间的隔离。

Binder：

INPUT对应于消费者

OUTPUT对应于生产者

**Stream中的消息通信方式遵循了发布-订阅模式**

Topic主题进行广播

- 在RabbitMQ就是Exchange
- 在Kakfa中就是Topic

Stream与RabbitMQ整合相关文档：[Spring Cloud Stream RabbitMQ Binder Reference Guide](https://docs.spring.io/spring-cloud-stream-binder-rabbit/docs/3.1.2/reference/html/spring-cloud-stream-binder-rabbit.html)



##### Spring Cloud Stream标准流程

//todo

##### Stream常用API和常用注解

Binder - 很方便的连接中间件，屏蔽差异。

Channel - 通道，是队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介，通过Channel对队列进行配置。

Source和Sink - 简单的可理解为参照对象是Spring Cloud Stream自身，从Stream发布消息就是输出，接受消息就是输入。

| 组成            | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| Middleware      | 中间件，目前只支持RabbitMQ和Kafka                            |
| Binder          | Binder是应用与消息中间件之间的封装，目前实行了Kafka和RabbitMQ的Binder，通过Binder可以很方便的连接中间件，可以动态的改变消息类型(对应于Kafka的topic,RabbitMQ的exchange)，这些都可以通过配置文件来实现 |
| @Input          | 注解标识输入通道，通过该输乎通道接收到的消息进入应用程序     |
| @Output         | 注解标识输出通道，发布的消息将通过该通道离开应用程序         |
| @StreamListener | 监听队列，用于消费者的队列的消息接收                         |
| @EnableBinding  | 指信道channel和exchange绑定在一起                            |



Eureka不加`@EnableEurekaClient`也能注册到配置中心，大概因为配置文件`eureka.client.register-with-eureka: true`默认为true

```yaml
eureka:
  client:
    register-with-eureka: false # 默认为true
```

#### 消息驱动案例



**案例说明**

创建生产者模块：cloud-stream-rabbitmq-provider8801

创建两个消费者模块：cloud-stream-rabbitmq-consumer8802/8803

##### 消息驱动之生产者模块

生产者模块cloud-stream-rabbitmq-provider8801

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-stream-rabbitmq-provider8801</artifactId>
    <dependencies>
        <!-- stream rabbit -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 基础配置 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 8801
spring:
  application:
    name: cloud-stream-rabbitmq-provider8801 # 应用名
  cloud:
    stream:
      binders: # 在此处配置要绑定的rabbitmq的服务信息
        defaultRabbit: # 表示定义的名称，用于于binding整合，名称随便起
          type: rabbit # 消息组件类型
          environment: # 设置rabbitmq的相关的环境配置
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings: # 服务的整合处理
        output: # 这个名字是一个通道的名称，自定义随便起
          destination: cloud-stream-exchange # 表示要使用的Exchange名称定义
          content-type: application/json # 设置消息类型，本次为json，文本则设置“text/plain”
          binder: defaultRabbit # 设置要绑定的消息服务的具体设置
eureka:
  instance:
    prefer-ip-address: true # 访问路径鼠标停留显示ip地址
    #心跳检测与续约时间
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    lease-renewal-interval-in-seconds: 2
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    lease-expiration-duration-in-seconds: 5
    appname: cloud-stream-rabbitmq-provider-service # 服务名，默认取 spring.application.name 配置值，如果没有则为 unknown
    instance-id: ${spring.cloud.client.ip-address}:${server.port} # 实例ID:此处为id+端口
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka # 单机版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamMQMain8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQMain8801.class,args);
    }
}

```



消息发送服务接口类

```java
package com.hyd.springcloud.service;

public interface MessageProviderService {
    String send();
}

```

消息发送服务实现类

```java
package com.hyd.springcloud.service.impl;

import com.hyd.springcloud.service.MessageProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@EnableBinding(Source.class)//定义消息的推送管道
public class MessageProviderServiceImpl implements MessageProviderService {

    @Autowired
    private MessageChannel output;
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        //注意引入的是 org.springframework.messaging.support.MessageBuilder
        output.send(MessageBuilder.withPayload(serial).build());
        return "message send()~";
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import com.hyd.springcloud.service.MessageProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProviderController {
    @Autowired
    private MessageProviderService messageProviderService;

    @GetMapping("/send")
    public String send(){
        return messageProviderService.send();
    }
}

```



##### 消息驱动之消费者模块

消费者模块cloud-stream-rabbitmq-comsumer8002/consumer8003

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-stream-rabbitmq-consumer8802</artifactId>
    <dependencies>
        <!-- stream rabbit -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 基础配置 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 8802
spring:
  application:
    name: cloud-stream-rabbitmq-consumer8802 # 应用名
  cloud:
    stream:
      binders: # 在此处配置要绑定的rabbitmq的服务信息
        defaultRabbit: # 表示定义的名称，用于于binding整合，名称随便起
          type: rabbit # 消息组件类型
          environment: # 设置rabbitmq的相关的环境配置
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings: # 服务的整合处理
        input: # 这个名字是一个通道的名称，自定义随便起
          destination: cloud-stream-exchange # 表示要使用的Exchange名称定义
          content-type: application/json # 设置消息类型，本次为json，文本则设置“text/plain”
          binder: defaultRabbit # 设置要绑定的消息服务的具体设置
eureka:
  instance:
    prefer-ip-address: true # 访问路径鼠标停留显示ip地址
    #心跳检测与续约时间
    #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
    lease-renewal-interval-in-seconds: 2
    #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
    lease-expiration-duration-in-seconds: 5
    appname: cloud-stream-rabbitmq-consumer-service # 服务名，默认取 spring.application.name 配置值，如果没有则为 unknown
    instance-id: ${spring.cloud.client.ip-address}:${server.port} # 实例ID:此处为id+端口
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka # 单机版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageConsumerMain8802 {
    public static void main(String[] args) {
        SpringApplication.run(MessageConsumerMain8802.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Sink.class)
@Slf4j
public class MessageConsumerController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        log.info("consumer8802 收到message: --> {} ,server.port:{}",message.getPayload(),serverPort);
    }
}

```

启动服务

向生产者8001发送请求：

```shell
curl -X GET http://localhost:8801/send
```

```
message send()~
```

两个消费者模块立刻接收到了消息，后台日志：

```
consumer8802 收到message: --> c82f59f1-f499-45e1-ba20-46b07d4f71d4 ,server.port:8802
```

```
consumer8803 收到message: --> c82f59f1-f499-45e1-ba20-46b07d4f71d4 ,server.port:8803
```



到目前为止，存在两个问题

* 消息重复消费
* 消息持久化

##### 分组消费及持久化

###### 解决重复消费

**原理**

微服务应用放置于同一个group中，就能够保证消息只会被其中一个应用消费一次。不同的组是可以重复消费的，同一个组内会发生竞争关系，只有其中一个可以消费。



**案例**

新创建一个消费者模块cloud-stream-rabbitmq-consumer8804（过程略）

group: A_Group、B_Group

A_Group:：8002/8003；（模块8002，8003在A组）

B_Group:：8004（模块8004在B组）

application.yaml新增group配置

```yaml
server:
  port: 8804
spring:
  application:
    name: cloud-stream-rabbitmq-consumer8804 # 应用名
  cloud:
    stream:
      binders: # 在此处配置要绑定的rabbitmq的服务信息
        defaultRabbit: # 表示定义的名称，用于于binding整合，名称随便起
          type: rabbit # 消息组件类型
          environment: # 设置rabbitmq的相关的环境配置
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings: # 服务的整合处理
        input: # 这个名字是一个通道的名称，自定义随便起
          destination: cloud-stream-exchange # 表示要使用的Exchange名称定义
          content-type: application/json # 设置消息类型，本次为json，文本则设置“text/plain”
          binder: defaultRabbit # 设置要绑定的消息服务的具体设置
          group: B_Group
```

启动服务，向生产者8801发送请求

```shell
curl -X GET http://localhost:8801/send
```

```
message send()~
```

A_Group

```
consumer8802 收到message: --> 88c9dd6e-1de6-429d-8d61-4de1ffc70497 ,server.port:8802
```

```

```

B_Group

```
consumer8804 收到message: --> 88c9dd6e-1de6-429d-8d61-4de1ffc70497 ,server.port:8804
```



###### 解决持久化

通过分组已解决

停掉8802/8803/8804，去掉8804分组配置；8801发送消息，启动8802/8803/8804，8802/8803收到了消息（有分组属性配置），但8804未收到消息（无分组属性配置）

此处体现了分组之后，消息持久化，未分组的消息发生了丢失。



---

## Spring Cloud Sleuth分布式请求链路跟踪

### 概述

官方文档地址：[Spring Cloud Sleuth](https://docs.spring.io/spring-cloud-sleuth/docs/2.2.8.RELEASE/reference/html/)

为什么会出现这个技术？要解决哪些问题？

在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的的服务节点调用来协同产生最后的请求结果，每一个前段请求都会形成一条复杂的分布式服务调用链路，链路中的任何一环出现高延时或错误都会引起整个请求最后的失败。

**是什么**

- https://github.com/spring-cloud/spring-cloud-sleuth
- Spring Cloud Sleuth提供了一套完整的服务跟踪的解决方案
- 在分布式系统中提供追踪解决方案并且兼容支持了zipkin



#### Sleuth

**一般的，一个分布式服务跟踪系统，主要有三部分：数据收集、数据存储和数据展示**。

根据系统大小不同，每一部分的结构又有一定变化。

譬如，对于大规模分布式系统，数据存储可分为实时数据和全量数据两部分.

实时数据用于故障排查（troubleshooting）:全量数据用于系统优化

数据收集除了支持平台无关和开发语言无关系统的数据收集，还包括异步数据收集（需要跟踪队列中的消息，保证调用的连贯性）

以及确保更小的侵入性；数据展示又涉及到数据挖掘和分析。

虽然每一部分都可能变得很复杂，但基本原理都类似。



服务追踪的追踪单元是从客户发起请求（request）抵达被追踪系统的边界开始

到被追踪系统向客户返回响应（response）为止的过程，称为一个“trace”。

每个 trace 中会调用若干个服务，为了记录调用了哪些服务，以及每次调用的消耗时间等信息，在每次调用服务时，埋入一个调用记录，称为一个“span”。

这样，若干个有序的 span 就组成了一个 trace。

在系统向外界提供服务的过程中，会不断地有请求和响应发生，也就会不断生成 trace。

把这些带有span 的 trace 记录下来，就可以描绘出一幅系统的服务拓扑图。

附带上 span 中的响应时间，以及请求成功与否等信息，就可以在发生问题的时候，找到异常的服务。

根据历史数据，还可以从系统整体层面分析出哪里性能差，定位性能优化的目标。

Spring Cloud Sleuth为服务之间调用提供链路追踪。

通过Sleuth可以很清楚的了解到一个服务请求经过了哪些服务，每个服务处理花费了多长。

从而让我们可以很方便的理清各微服务间的调用关系。

此外Sleuth可以帮助我们：

- 耗时分析: 通过Sleuth可以很方便的了解到每个采样请求的耗时，从而分析出哪些服务调用比较耗时;
- 可视化错误: 对于程序未捕捉的异常，可以通过集成Zipkin服务界面上看到;
- 链路优化: 对于调用比较频繁的服务，可以针对这些服务实施一些优化措施。

**spring cloud sleuth可以结合zipkin，将信息发送到zipkin，利用zipkin的存储来存储信息，利用zipkin ui来展示数据**。

这是Spring Cloud Sleuth的概念图：

![Spring Cloud Sleuth的概念图](https://raw.githubusercontent.com/spring-cloud/spring-cloud-sleuth/2.2.x/docs/src/main/asciidoc/images/trace-id.png)

完整的调用链路

表示一请求链路，一条链路通过Trace ld唯一标识，Span标识发起的请求信息，各span通过parent id关联起来

名词解释

- Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识
- span：表示调用链路来源，通俗的理解span就是一次请求信息

#### Zipkin

官方文档：[openzipkin/zipkin: Zipkin is a distributed tracing system (github.com)](https://github.com/openzipkin/zipkin#quick-start)

Zipkin 分为两端，一个是 Zipkin 服务端，一个是 Zipkin 客户端，客户端也就是微服务的应用。
客户端会配置服务端的 URL 地址，一旦发生服务间的调用的时候，会被配置在微服务里面的 Sleuth 的监听器监听，并生成相应的 Trace 和 Span 信息发送给服务端。

发送的方式主要有两种：

* HTTP 报文的方式
* 消息总线的方式如 RabbitMQ

不论哪种方式，我们都需要：

* 一个 Eureka 服务注册中心，这里我们就用之前的 eureka 项目来当注册中心。
* 一个 Zipkin 服务端。
* 两个微服务应用，trace-a 和 trace-b，其中 trace-a 中有一个 REST 接口 /trace-a，调用该接口后将触发对 trace-b 应用的调用。

##### Zipkin服务端

关于 Zipkin 的服务端，在使用 Spring Boot 2.x 版本后，官方就不推荐自行定制编译了，反而是直接提供了编译好的 jar 包来给我们使用。

之前的`@EnableZipkinServer`注解已经标上了过时注解`Deprecated`。

```java
/**
 * @deprecated Custom servers are possible, but not supported by the community. Please use our
 * <a href="https://github.com/openzipkin/zipkin#quick-start">default server build</a> first.
 * If you find something missing, please <a href="https://gitter.im/openzipkin/zipkin">gitter</a> us
 * about it before making a custom server.
 *
 * <p>If you decide to make a custom server, you accept responsibility for troubleshooting your
 * build or configuration problems, even if such problems are a reaction to a change made by the
 * Zipkin maintainers. In other words, custom servers are possible, but not supported.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(InternalZipkinConfiguration.class)
@Deprecated
public @interface EnableZipkinServer {

}
```

并且文档注释说明了，可以自己构建，但出了问题，后果自负。

```
If you decide to make a custom server, you accept responsibility for troubleshooting your build or configuration problems, even if such problems are a reaction to a change made by the Zipkin maintainers. In other words, custom servers are possible, but not supported.
```

zipkin-server 2.23.2版本下载地址：https://search.maven.org/remotecontent?filepath=io/zipkin/zipkin-server/2.23.2/zipkin-server-2.23.2-exec.jar

其他版本下载：[Central Repository: io/zipkin/zipkin-server (maven.org)](https://repo1.maven.org/maven2/io/zipkin/zipkin-server/)

最新版下载：https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec

需要最低jdk1.8以上版本，启动脚本：

```shell
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

Docker版本：

```shell
# Note: this is mirrored as ghcr.io/openzipkin/zipkin
docker run -d -p 9411:9411 openzipkin/zipkin
```

Zipkin UI界面地址：

```
http://your_host:9411/zipkin/
```



### 搭建链路监控

创建sleuth整合zipkin子模块示例两个，一个provider，一个consumer

provider模块：

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-provider-sleuth-payment8001</artifactId>

    <dependencies>
        <!-- sleuth (sleuth+zipkin) -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 8001
spring:
  application:
    name: cloud-provider-sleuth-payment8001
  zipkin:
    base-url: http://localhost:9411/
  sleuth:
    sampler:
      probability: 1 # 采样率值介于 0 到 1 之间，1 则表示全部采集
eureka:
  instance:
    appname: cloud-provider-sleuth-payment-service
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka # 单机版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentSleuthMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentSleuthMain8001.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/payment/zipkin")
    public String paymentZipkin(){
        return "provider ---> paymentZipkin() 调用成功!";
    }
}

```

consumer模块

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-consumer-sleuth-order80</artifactId>

    <dependencies>
        <!-- sleuth (sleuth+zipkin) -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <!-- eureka client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 80
spring:
  application:
    name: cloud-consumer-sleuth-order80
  zipkin:
    base-url: http://localhost:9411/
  sleuth:
    sampler:
      probability: 1 # 采样率值介于 0 到 1 之间，1 则表示全部采集
eureka:
  instance:
    appname: cloud-consumer-sleuth-order-service
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka # 单机版
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderSleuthMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderSleuthMain80.class,args);
    }
}

```

配置类

```java
package com.hyd.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        return  restTemplate.getForObject("http://localhost:8001/payment/zipkin/", String.class);
    }
}
```

启动服务，发送请求：

```shell
curl -X GET http://localhost/consumer/payment/zipkin
```

```
provider ---> paymentZipkin() 调用成功!
```



访问zipkin链路跟踪web界面：http://localhost:9411/zipkin/ ，查看详细调用信息



---

## Spring Cloud Alibaba

### 概述

为什么会出现SpringCloud alibaba

Spring Cloud Netflix项目进入维护模式

https://spring.io/blog/2018/12/12/spring-cloud-greenwich-rc1-available-now

什么是维护模式？

将模块置于维护模式，意味着Spring Cloud团队将不会再向模块添加新功能。

他们将修复block级别的 bug 以及安全问题，他们也会考虑并审查社区的小型pull request。



官方文档：[Spring Cloud Alibaba Reference Documentation (spring-cloud-alibaba-group.github.io)](https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/en-us/index.html)

Github地址：[spring-cloud-alibaba/README-zh.md at master · alibaba/spring-cloud-alibaba (github.com)](https://github.com/alibaba/spring-cloud-alibaba/blob/master/README-zh.md)

#### 简介

Spring Cloud Alibaba 致力于提供微服务开发的一站式解决方案。此项目包含开发分布式应用微服务的必需组件，方便开发者通过 Spring Cloud 编程模型轻松使用这些组件来开发分布式应用服务。

依托 Spring Cloud Alibaba，您只需要添加一些注解和少量配置，就可以将 Spring Cloud 应用接入阿里微服务解决方案，通过阿里中间件来迅速搭建分布式应用系统。

#### 主要功能

- **服务限流降级**：默认支持 WebServlet、WebFlux, OpenFeign、RestTemplate、Spring Cloud Gateway, Zuul, Dubbo 和 RocketMQ 限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级 Metrics 监控。
- **服务注册与发现**：适配 Spring Cloud 服务注册与发现标准，默认集成了 Ribbon 的支持。
- **分布式配置管理**：支持分布式系统中的外部化配置，配置更改时自动刷新。
- **消息驱动能力**：基于 Spring Cloud Stream 为微服务应用构建消息驱动能力。
- **分布式事务**：使用 @GlobalTransactional 注解， 高效并且对业务零侵入地解决分布式事务问题。
- **阿里云对象存储**：阿里云提供的海量、安全、低成本、高可靠的云存储服务。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。
- **分布式任务调度**：提供秒级、精准、高可靠、高可用的定时（基于 Cron 表达式）任务调度服务。同时提供分布式的任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有 Worker（schedulerx-client）上执行。
- **阿里云短信服务**：覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

更多功能请参考 [Roadmap](https://github.com/alibaba/spring-cloud-alibaba/blob/master/Roadmap-zh.md)。

#### 如何使用

##### 如何引入依赖

如果需要使用已发布的版本，在 `dependencyManagement` 中添加如下配置。

```
	<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.5.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

然后在 `dependencies` 中添加自己所需使用的依赖即可使用。

#### 演示 Demo

为了演示如何使用，Spring Cloud Alibaba 项目包含了一个子模块`spring-cloud-alibaba-examples`。此模块中提供了演示用的 example ，您可以阅读对应的 example 工程下的 readme 文档，根据里面的步骤来体验。

Example 列表：

[Sentinel Example](https://github.com/alibaba/spring-cloud-alibaba/tree/master/spring-cloud-alibaba-examples/sentinel-example/sentinel-core-example/readme-zh.md)

[Nacos Config Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)

[Nacos Discovery Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md)

[RocketMQ Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/rocketmq-example/readme-zh.md)

[Seata Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/seata-example/readme-zh.md)

[Alibaba Cloud OSS Example](https://github.com/alibaba/aliyun-spring-boot/tree/master/aliyun-spring-boot-samples/aliyun-oss-spring-boot-sample)

[Alibaba Cloud SMS Example](https://github.com/alibaba/aliyun-spring-boot/tree/master/aliyun-spring-boot-samples/aliyun-sms-spring-boot-sample)

[Alibaba Cloud SchedulerX Example](https://github.com/alibaba/aliyun-spring-boot)

#### 版本管理规范

项目的版本号格式为 x.x.x 的形式，其中 x 的数值类型为数字，从 0 开始取值，且不限于 0~9 这个范围。项目处于孵化器阶段时，第一位版本号固定使用 0，即版本号为 0.x.x 的格式。

由于 Spring Boot 1 和 Spring Boot 2 在 Actuator 模块的接口和注解有很大的变更，且 spring-cloud-commons 从 1.x.x 版本升级到 2.0.0 版本也有较大的变更，因此我们采取跟 SpringBoot 版本号一致的版本:

- 1.5.x 版本适用于 Spring Boot 1.5.x
- 2.0.x 版本适用于 Spring Boot 2.0.x
- 2.1.x 版本适用于 Spring Boot 2.1.x
- 2.2.x 版本适用于 Spring Boot 2.2.x
- 2021.x 版本适用于 Spring Boot 2.4.x

**组件版本关系**

[版本说明 · alibaba/spring-cloud-alibaba Wiki (github.com)](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明)



---

### Spring Cloud Alibaba Nacos 服务注册和配置中心

#### 简介

Github地址：[alibaba/nacos: an easy-to-use dynamic service discovery, configuration and service management platform for building cloud native applications. (github.com)](https://github.com/alibaba/Nacos)

官方手册：[什么是 Nacos](https://nacos.io/zh-cn/docs/what-is-nacos.html)

官方案例：

[Nacos Config](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)

[Nacos Discovery](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md)

**为什么叫Nacos**

* 前四个字母分别为Naming和Configuration的前两个字母，最后的s为Service。

**是什么**

* 一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
* Nacos: Dynamic Naming and Configuration Service
* Nacos就是注册中心＋配置中心的组合 -> Nacos = Eureka+Config+Bus

**能干嘛**

- 替代Eureka做服务注册中心
- 替代Config做服务配置中心

#### Nacos安装与运行

##### 下载并解压

nacos-server 1.4.1下载地址：https://github.com/alibaba/nacos/releases/download/1.4.1/nacos-server-1.4.1.zip

下载解压，进入`bin`目录。

##### 启动服务器

Linux/Unix/Mac

启动命令(standalone代表着单机模式运行，非集群模式):

```shell
sh startup.sh -m standalone
```

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

```shell
bash startup.sh -m standalone
```

Windows

启动命令(standalone代表着单机模式运行，非集群模式):

```shell
startup.cmd -m standalone
```

##### 访问Nacos web管理界面

```
# 默认用户名和密码：nacos/nacos
http://localhost:8848/nacos
```



##### 关闭服务器

Linux/Unix/Mac

```
sh shutdown.sh
```

Windows

```
shutdown.cmd
```

或者双击shutdown.cmd运行文件。



#### Nacos服务发现

文档：[Nacos Spring Cloud 快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

Nacos discovery文档：[Nacos discovery · alibaba/spring-cloud-alibaba Wiki (github.com)](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-discovery)



创建两个服务提供者模块 cloud-alibaba-provider-nacos-payment9001/9002，此处以9001为例

pom.xml

此处父pom.xml已经有了cloud-alibaba依赖，此处使用的是2.2.0 RELEASE

```xml
<dependencyManagement>
    <!--spring cloud alibaba 2.2.0.RELEASE-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>${spring-cloud-alibaba.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencyManagement>
```

子模块pom.xml

```xml
<!-- nacos discovery 服务发现 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

**注意**

Spring Cloud Alibaba已经从Spring Cloud孵化器中孵化成功。孵化成功后的最新版本是2.1.0。这一节来详细探讨如何将Spring Cloud Alibaba从0.9.0升级到2.1.0。

从0.9.0开始，Maven的Group id发生了变化！

0.9.0的Group id是 org.springframework.cloud
0.9.0以上的版本是 com.alibaba.cloud
这看起来还挺吓人的，而且此前也引发了一些吐槽。其实这主要是由于Spring Cloud考虑简化自己的Release Train（即：Spring Cloud自身的版本管理），于是修改了发布政策：非Spring Cloud团队维护的Spring Cloud的子项目，一律使用自己的GroupId即可，不再强制使用 org.springframework.cloud 。详情见：[Simplifying the Spring Cloud Release Train](https://spring.io/blog/2019/07/24/simplifying-the-spring-cloud-release-train)。



application.yaml

```yaml
server:
  port: 9001
spring:
  application:
    name: cloud-alibaba-provider-nacos-payment9001
  cloud:
    nacos: # nacos
      discovery: # 服务发现
        service: cloud-alibaba-provider-nacos-payment-service
        server-addr: 127.0.0.1:8848

```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosMain9001.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller.com.hyd.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "nacos payment ---> "+serverPort+" ,id: "+id;
    }
}

```



创建一个服务消费者模块cloud-alibaba-consumer-nacos-order80

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-alibaba-consumer-nacos-order80</artifactId>

    <dependencies>
        <!-- nacos discovery 服务发现 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- actuator -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <!-- 日常通用配置 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</project>
```

application.yaml

```yaml
server:
  port: 80
spring:
  application:
    name: cloud-alibaba-consumer-nacos-order80
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderNacosMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderNacosMain80.class,args);
    }
}

```

为什么nacos支持负载均衡？因为spring-cloud-starter-alibaba-nacos-discovery内含netflix-ribbon包

配置类

```java
package com.hyd.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String serverPort;

    private static final String PROVIDER_SERVICE="cloud-alibaba-provider-nacos-payment-service";

    @GetMapping("/consumer/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return restTemplate.getForObject("http://"+PROVIDER_SERVICE+"/payment/nacos/"+id,String.class)+"---> server.port: "+serverPort;
    }
}

```



启动服务，访问：http://localhost/consumer/payment/nacos/11

```
nacos payment ---> 9002 ,id: 11---> server.port: 80
nacos payment ---> 9001 ,id: 11---> server.port: 80
...
```

同时通过nacos web管理界面，查看微服务信息http://localhost:8848/nacos



#### Nacos服务注册中心对比提升

Nacos生态图

![Nacos生态图](https://cdn.nlark.com/lark/0/2018/png/11189/1533045871534-e64b8031-008c-4dfc-b6e8-12a597a003fb.png)



Nacos CAP
Nacos与其他注册中心特性对比

略

Nacos服务发现实例模型

略

**Nacos支持AP和CP模式的切换**

C是所有节点在同一时间看到的数据是一致的;而A的定义是所有的请求都会收到响应。

**何时选择使用何种模式?**

—般来说，如果不需要存储服务级别的信息且服务实例是通过nacos-client注册，并能够保持心跳上报，那么就可以选择AP模式。当前主流的服务如Spring cloud和Dubbo服务，都适用于AP模式，AP模式为了服务的可能性而减弱了一致性，因此AP模式下只支持注册临时实例。

如果需要在服务级别编辑或者存储配置信息，那么CP是必须，K8S服务和DNS服务则适用于CP模式。CP模式下则支持注册持久化实例，此时则是以Raft协议为集群运行模式，该模式下注册实例之前必须先注册服务，如果服务不存在，则会返回错误。

切换命令：

```shell
curl -X PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP
```

#### Nacos 配置管理

**官方文档**：[Nacos Spring Cloud 快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html)

**官方wiki**：[Nacos config · alibaba/spring-cloud-alibaba Wiki (github.com)](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)



##### 基础配置

###### Nacos 服务端初始化

启动服务：

```shell
startup.cmd -m standalone
```

添加或配置配置文件：

配置Data ID：

dataid是以 properties(默认的文件扩展名方式)为扩展名。可以在应用的 bootstrap.yaml配置文件中显示的声明 dataid 文件扩展名。配置为：`spring.cloud.nacos.config.file-extension=yaml`。

配置内容：

略。

Data ID命名规则：

```
${spring.application.name}.${file-extension:properties} # abc-.yaml
${spring.application.name}-${profile}.${file-extension:properties # abc-dev.yaml
```

多套配置时，使用：`spring.profiles.active=develop`指定选择的配置文件

支持配置的动态更新

可以通过配置 `spring.cloud.nacos.config.refresh.enabled=false` 来关闭动态刷新，默认开启。（官方文档介绍使用`@RefreshScope`注解，实测现版本默认开启，无需该注解）



创建config客户端子模块cloud-alibaba-nacos-config-client3358

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-alibaba-nacos-config-client3358</artifactId>

    <dependencies>
        <!-- naocs config -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```



nacos服务器地址可以通过以下几方式指定：

```properties
# discovery
spring.cloud.nacos.discovery.server-addr:127.0.0.1:8848
# config
spring.cloud.nacos.config.server-addr:127.0.0.1:8848
#
spring.cloud.nacos.server-addr:127.0.0.1:8848
```

bootstrap.yaml

```yaml
# 系统级的配置文件
spring:
  application:
    name: cloud-alibaba-nacos-config-client3358
  cloud:
    nacos:
      config:
#        server-addr: 127.0.0.1:8848 # 配置nacos server地址
        file-extension: yaml # 显示的声明 dataid 文件扩展名
      server-addr: 127.0.0.1:8848
```

application.yaml

```yaml
# 用户级的配置文件
server:
  port: 3358
spring:
  profiles:
    active: dev # 当前激活配置（多个配置文件）
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NacosConfigClientMain3358 {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClientMain3358.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosConfigClientController {
    @Autowired
    private Environment environment;

    @GetMapping("/nacos/config")
    public String getConfig(){
        String applicationName = environment.getProperty("spring.application.name");
        String name = environment.getProperty("user.name");
        String age = environment.getProperty("user.age");
        String profile = environment.getProperty("user.profile");
        return applicationName+" ,\t"+name+" ,\t"+age+" ,\t"+profile;
    }
}

```

启动服务，发送请求：

```shell
curl -X GET http://localhost:3358/nacos/config
```

```
cloud-alibaba-nacos-config-client3358 ,	zhangsan ,	20 ,	dev
```

nacos服务端配置文件修改以后，动态的刷新了配置。





##### 进阶配置

**支持自定义 namespace 的配置**

首先看一下 Nacos 的 Namespace 的概念， [Nacos 概念](https://nacos.io/zh-cn/docs/concepts.html)

> 用于进行租户粒度的配置隔离。不同的命名空间下，可以存在相同的 Group 或 Data ID 的配置。Namespace 的常用场景之一是不同环境的配置的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等。

在没有明确指定 `${spring.cloud.nacos.config.namespace}` 配置的情况下， 默认使用的是 Nacos 上 Public 这个namespae。如果需要使用自定义的命名空间，可以通过以下配置来实现：

```
spring.cloud.nacos.config.namespace=b3404bc0-d7dc-4855-b519-570ed34b62d7
```

| Note | 该配置必须放在 bootstrap.properties 文件中。此外 `spring.cloud.nacos.config.namespace` 的值是 namespace 对应的 id，id 值可以在 Nacos 的控制台获取。并且在添加配置时注意不要选择其他的 namespae，否则将会导致读取不到正确的配置。 |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

**支持自定义 Group 的配置**

在没有明确指定 `${spring.cloud.nacos.config.group}` 配置的情况下， 默认使用的是 DEFAULT_GROUP 。如果需要自定义自己的 Group，可以通过以下配置来实现：

```
spring.cloud.nacos.config.group=DEVELOP_GROUP
```

| Note | 该配置必须放在 bootstrap.properties 文件中。并且在添加配置时 Group 的值一定要和 `spring.cloud.nacos.config.group` 的配置值一致。 |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

**支持自定义扩展的 Data Id 配置**

Spring Cloud Alibaba Nacos Config 从 0.2.1 版本后，可支持自定义 Data Id 的配置。关于这部分详细的设计可参考 [这里](https://github.com/spring-cloud-incubator/spring-cloud-alibaba/issues/141)。 一个完整的配置案例如下所示：

```
spring.application.name=opensource-service-provider
spring.cloud.nacos.config.server-addr=127.0.0.1:8848

# config external configuration
# 1、Data Id 在默认的组 DEFAULT_GROUP,不支持配置的动态刷新
spring.cloud.nacos.config.extension-configs[0].data-id=ext-config-common01.properties

# 2、Data Id 不在默认的组，不支持动态刷新
spring.cloud.nacos.config.extension-configs[1].data-id=ext-config-common02.properties
spring.cloud.nacos.config.extension-configs[1].group=GLOBALE_GROUP

# 3、Data Id 既不在默认的组，也支持动态刷新
spring.cloud.nacos.config.extension-configs[2].data-id=ext-config-common03.properties
spring.cloud.nacos.config.extension-configs[2].group=REFRESH_GROUP
spring.cloud.nacos.config.extension-configs[2].refresh=true
```

可以看到:

- 通过 `spring.cloud.nacos.config.extension-configs[n].data-id` 的配置方式来支持多个 Data Id 的配置。
- 通过 `spring.cloud.nacos.config.extension-configs[n].group` 的配置方式自定义 Data Id 所在的组，不明确配置的话，默认是 DEFAULT_GROUP。
- 通过 `spring.cloud.nacos.config.extension-configs[n].refresh` 的配置方式来控制该 Data Id 在配置变更时，是否支持应用中可动态刷新， 感知到最新的配置值。默认是不支持的。

| Note | 多个 Data Id 同时配置时，他的优先级关系是 `spring.cloud.nacos.config.extension-configs[n].data-id` 其中 n 的值越大，优先级越高。 |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

| Note | `spring.cloud.nacos.config.extension-configs[n].data-id` 的值必须带文件扩展名，文件扩展名既可支持 properties，又可以支持 yaml/yml。 此时 `spring.cloud.nacos.config.file-extension` 的配置对自定义扩展配置的 Data Id 文件扩展名没有影响。 |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

通过自定义扩展的 Data Id 配置，既可以解决多个应用间配置共享的问题，又可以支持一个应用有多个配置文件。

为了更加清晰的在多个应用间配置共享的 Data Id ，你可以通过以下的方式来配置：

```
# 配置支持共享的 Data Id
spring.cloud.nacos.config.shared-configs[0].data-id=common.yaml

# 配置 Data Id 所在分组，缺省默认 DEFAULT_GROUP
spring.cloud.nacos.config.shared-configs[0].group=GROUP_APP1

# 配置Data Id 在配置变更时，是否动态刷新，缺省默认 false
spring.cloud.nacos.config.shared-configs[0].refresh=true
```

可以看到：

- 通过 `spring.cloud.nacos.config.shared-configs[n].data-id` 来支持多个共享 Data Id 的配置。
- 通过 `spring.cloud.nacos.config.shared-configs[n].group` 来配置自定义 Data Id 所在的组，不明确配置的话，默认是 DEFAULT_GROUP。
- 通过 `spring.cloud.nacos.config.shared-configs[n].refresh` 来控制该Data Id在配置变更时，是否支持应用中动态刷新，默认false。

**配置的优先级**

Spring Cloud Alibaba Nacos Config 目前提供了三种配置能力从 Nacos 拉取相关的配置。

- A: 通过 `spring.cloud.nacos.config.shared-configs[n].data-id` 支持多个共享 Data Id 的配置
- B: 通过 `spring.cloud.nacos.config.extension-configs[n].data-id` 的方式支持多个扩展 Data Id 的配置
- C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置

当三种方式共同使用时，他们的一个优先级关系是:A < B < C

**完全关闭配置**

通过设置 spring.cloud.nacos.config.enabled = false 来完全关闭 Spring Cloud Nacos Config





#### Nacos集群和持久化配置

##### 部署

**Nacos支持三种部署模式**

- 单机模式 - 用于测试和单机试用。
- 集群模式 - 用于生产环境，确保高可用。
- 多集群模式 - 用于多数据中心场景

**单机模式运行**

```shell
# Linux/Unix/Mac
sh startup.sh -m standalone
# Windows
startup.cmd -m standalone
```

**单机模式支持mysql**

在0.7版本之前，在单机模式时nacos使用嵌入式数据库实现数据的存储，不方便观察数据存储的基本情况。0.7版本增加了支持mysql数据源能力，具体的操作步骤：

- 1.安装数据库，版本要求：5.6.5+
- 2.初始化mysql数据库，数据库初始化文件：nacos-mysql.sql（位置：`nacos-1.4.1\conf\nacos-mysql.sql`）。
- 3.修改conf/application.properties文件，增加支持mysql数据源配置（目前只支持mysql），添加mysql数据源的url、用户名和密码。（位置：`nacos-1.4.1\conf\application.properties`）。

```properties
spring.datasource.platform=mysql

db.num=1
db.url.0=jdbc:mysql://11.162.196.16:3306/nacos_devtest?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
db.user=nacos_devtest
db.password=youdontknow
```



按照文档，创建本地数据库

```mysql
CREATE DATABASE nacos_config;
```

执行脚本，修改application.properties

```properties
# <--- nacos mysql 数据源配置 start ---> #
spring.datasource.platform=mysql
db.num=1
db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
db.user.0=root
db.password.0=123456
# <--- nacos mysql 数据源配置 end ---> #
```

再以单机模式启动nacos，nacos所有写嵌入式数据库的数据都写到了mysql，数据源切换成功。



本次主要介绍集群模式下运行Nacos

##### Nacos集群部署



**集群部署架构图**

因此开源的时候推荐用户把所有服务列表放到一个vip下面，然后挂到一个域名下面

[http://ip1](http://ip1/):port/openAPI 直连ip模式，机器挂则需要修改ip才可以使用。

[http://SLB](http://slb/):port/openAPI 挂载SLB模式(内网SLB，不可暴露到公网，以免带来安全风险)，直连SLB即可，下面挂server真实ip，可读性不好。

[http://nacos.com](http://nacos.com/):port/openAPI 域名 + SLB模式(内网SLB，不可暴露到公网，以免带来安全风险)，可读性好，而且换ip方便，推荐模式

![集群部署架构图](https://nacos.io/img/deployDnsVipMode.jpg)



1. 预备环境准备
   请确保是在环境中安装使用:

   1. 64 bit OS Linux/Unix/Mac，推荐使用Linux系统。
   2. 64 bit JDK 1.8+；[下载](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).[配置](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)。
   3. Maven 3.2.x+；[下载](https://maven.apache.org/download.cgi).[配置](https://maven.apache.org/settings.html)。
   4. 3个或3个以上Nacos节点才能构成集群。

2. 下载安装包并配置环境（以nacos-server 1.1.4版本为例）
   下载地址：https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.tar.gz
   解压：

   ```shell
   sudo tar -zxvf nacos-server-1.1.4.tar.gz
   mv nacos nacos-server-1.1.4
   cd nacos-server-1.1.4
   ```

   nacos默认自带的是嵌入式数据库derby，官方源码pom.xml中：

   ```xml
   <dependency>
       <groupId>org.apache.derby</groupId>
       <artifactId>derby</artifactId>
       <version>${derby.version}</version>
   </dependency>
   ```

   切换数据源到mysql：

   ```mysql
   CREATE DATABASE nacos_config;
   ```

   执行脚本：

   ```shell
   sudo mysql -u hyd -p;# 密码123456
   mysql> use nacos_config;
   mysql> source /usr/local/nacos/nacos-server-1.1.4/conf/nacos-mysql.sql;
   ```

   修改application.properties

   ```shell
   sudo vim application.properties
   ```

   添加以下内容：

   ```properties
   # <--- nacos mysql 数据源配置 start ---> #
   spring.datasource.platform=mysql
   db.num=1
   db.url.0=jdbc:mysql://127.0.0.1:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
   db.user.0=hyd
   db.password.0=123456
   # <--- nacos mysql 数据源配置 end ---> #
   ```

   查看linux主机ip地址：10.9.22.145

   ```shell
   [hyd@10-9-22-145 conf]$ hostname -I
   10.9.22.145 172.17.0.1 
   [hyd@10-9-22-145 conf]$ ip addr | grep inet
       inet 127.0.0.1/8 scope host lo
       inet6 ::1/128 scope host 
       inet 10.9.22.145/16 brd 10.9.255.255 scope global eth0
       inet6 fe80::5054:ff:fe94:780a/64 scope link 
       inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       inet6 fe80::42:e1ff:fe68:4db2/64 scope link 
       inet6 fe80::5045:dcff:fe3b:d637/64 scope link 
   [hyd@10-9-22-145 conf]$
   ```

   

   在nacos的解压目录nacos/的conf目录下，有配置文件cluster.conf，请每行配置成ip:port，此处伪集群配置演示。（请配置3个或3个以上节点）

   ```conf
   # ip:port
   10.9.22.145:3301
   10.9.22.145:3302
   10.9.22.145:3303
   ```

   修改`startup.sh`启动脚本

   修改`while getopts ":m:f:s:" opt` → `while getopts ":m:f:s:p:" opt`；，新增

           p)
               PORT=$OPTARG;;

   nohup新增`- Dserver.port=${PORT}`

   ```sh
   while getopts ":m:f:s:p:" opt
   do
       case $opt in
           m)
               MODE=$OPTARG;;
           f)
               FUNCTION_MODE=$OPTARG;;
           s)
               SERVER=$OPTARG;;
           p)
               PORT=$OPTARG;;
           ?)
           echo "Unknown parameter"
           exit 1;;
       esac
   done
   ```

   ```sh
   nohup $JAVA -Dserver.port=${PORT} ${JAVA_OPT} nacos.nacos >> ${BASE_DIR}/logs/start.out
   ```

   启动测试，`sudo startup.sh -p 3301`报错：

   ```
   ERROR: Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! !!
   ```
   
   修改`startup.sh`脚本，设置java环境变量
   
   ```
   [ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/local/java/jdk1.8.0_281
   ```
   
   再次启动报错，查看`/logs/start.out`日志，修改java启动参数，减少启动内存
   
   ```
   # 原参数
   JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
   # 修改后
   JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
   ```
   
   查看nacos进程启动数
   
   ```shell
   ps -ef | grep nacos | grep -v grep | wc -l
   ```
   
   ```
   3
   ```
   
   
   
   配置nginx
   
   修改nginx配置文件nginx.conf
   
   ```
   # nacos 集群 begin #
   upstream nacos-cluster{
   	server 127.0.0.1:3301;
   	server 127.0.0.1:3302;
   	server 127.0.0.1:3303;
   }
   server {
   	listen 3300;
   	server_name nacos_server;
   	location / {
   		proxy_pass http://nacos-cluster;
   	}
   }
   # nacos 集群 end #
   ```
   
   此处注意，如果headername包含下划线会报错400，nginx对header name的字符做了限制，默认 underscores_in_headers 为off，表示如果header name中包含下划线，则忽略掉。可以在http块配置：
   
   ```
   http {
       underscores_in_headers on;
   }
   ```
   
   或者设置`proxy_set_header HOST $host;`
   
   访问：`http://117.50.3.120:3300/nacos`，成功访问到nacos web管理界面
   
   注意上述过程，需要开启服务器对应端口防火墙，允许对应端口外网访问。
   
   上述启动成功后，nacos web界面，节点列表如下：
   
   | 节点Ip           | 节点状态 | 集群任期 | Leader止时(ms) | 心跳止时(ms) |
   | :--------------- | :------- | :------- | :------------- | :----------- |
   | 10.9.22.145:3301 | FOLLOWER | 3        | 15070          | 4000         |
   | 10.9.22.145:3302 | FOLLOWER | 3        | 15388          | 3500         |
   | 10.9.22.145:3303 | LEADER   | 3        | 16637          | 4000         |



创建模块cloud-alibaba-nacos-config-cluster-client3358

引入discovery和config依赖

bootstrap.yaml

```yaml
# 系统级的配置文件
spring:
  application:
    name: cloud-alibaba-nacos-config-cluster-client3358
  cloud:
    nacos:
      config:
        file-extension: yaml # 显示的声明 dataid 文件扩展名
      server-addr: 117.50.3.120:3300 # nginx地址，对应转发到nacos集群上
```

启动服务

应用后台报错：

```
nacos server is STARTING now, please try again later!
```

```
failed to req API:http://117.50.3.120:3300/nacos/v1/ns/instance
```

start.out日志

```
2021-05-27 20:05:10,084 INFO Nacos is starting...
```

nacos.log日志

```
java.lang.IllegalStateException: unable to find local peer: 10.9.22.145:3303, all peers: [200.8.9.17:8848, 200.8.9.16:8848, 200.8.9.18:8848]
```

定位到是nacos未成功启动，cluster.conf配置文件莫名其妙变成了200.8.9.17:8848等等，重新修改为内网ip+自定义端口。

启动服务，发送请求

```
curl -X GET http://117.50.3.120:3300/nacos/v1/ns/instance/list?serviceName=cloud-alibaba-nacos-config-cluster-client3358
```

```json
{
    "metadata":{

    },
    "dom":"cloud-alibaba-nacos-config-cluster-client3358",
    "cacheMillis":3000,
    "useSpecifiedURL":false,
    "hosts":[
        {
            "valid":true,
            "marked":false,
            "metadata":{
                "preserved.register.source":"SPRING_CLOUD"
            },
            "instanceId":"192.168.1.106#3358#DEFAULT#DEFAULT_GROUP@@cloud-alibaba-nacos-config-cluster-client3358",
            "port":3358,
            "healthy":true,
            "ip":"192.168.1.106",
            "clusterName":"DEFAULT",
            "weight":"1.0",
            "ephemeral":true,
            "serviceName":"cloud-alibaba-nacos-config-cluster-client3358",
            "enabled":true
        }
    ],
    "name":"DEFAULT_GROUP@@cloud-alibaba-nacos-config-cluster-client3358",
    "checksum":"c12d4def76439f701dd1a1813d0b8d20",
    "lastRefTime":1622117799906,
    "env":"",
    "clusters":""
}
```

发送请求

```
curl -X GET http://localhost:3358/nacos/config
```

```
cloud-alibaba-nacos-config-cluster-client3358 , zhangsan ,      20 ,    dev
```

可通过发送请求配置或获取数据



---



### Spring Cloud Alibaba Sentinel 实现熔断与限流

#### 简介

官网：[home (sentinelguard.io)](https://sentinelguard.io/zh-cn/)

官网文档：[introduction (sentinelguard.io)](https://sentinelguard.io/zh-cn/docs/introduction.html)

官方wiki：[Sentinel · alibaba/spring-cloud-alibaba Wiki (github.com)](https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel)

官方：[sentinel-example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/sentinel-example/sentinel-core-example/readme-zh.md)

参考文档：[如何使用 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/如何使用)

**什么是sentinel**

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。sentinel是面向分布式服务框架的轻量级流量控制框架。

主要特性：
![主要特性](https://user-images.githubusercontent.com/9434884/50505538-2c484880-0aaf-11e9-9ffc-cbaaef20be2b.png)



Sentinel 分为两个部分:

- 核心库（Java 客户端）不依赖任何框架/库，能够运行于所有 Java 运行时环境，同时对 Dubbo / Spring Cloud 等框架也有较好的支持。
- 控制台（Dashboard）基于 Spring Boot 开发，打包后可以直接运行，不需要额外的 Tomcat 等应用容器。

**Sentinel基本概念**

* 资源
  资源是 Sentinel 的关键概念。它可以是 Java 应用程序中的任何内容，例如，由应用程序提供的服务，或由应用程序调用的其它应用提供的服务，甚至可以是一段代码。在接下来的文档中，我们都会用资源来描述代码块。
  只要通过 Sentinel API 定义的代码，就是资源，能够被 Sentinel 保护起来。大部分情况下，可以使用方法签名，URL，甚至服务名称作为资源名来标示资源。

* 规则
  围绕资源的实时状态设定的规则，可以包括流量控制规则、熔断降级规则以及系统保护规则。所有规则可以动态实时调整。

**Sentinel功能**

* 流量控制

  * 什么是流量控制
    流量控制在网络传输中是一个常用的概念，它用于调整网络包的发送数据。然而，从系统稳定性角度考虑，在处理请求的速度上，也有非常多的讲究。任意时间到来的请求往往是随机不可控的，而系统的处理能力是有限的。我们需要根据系统的处理能力对流量进行控制。Sentinel 作为一个调配器，可以根据需要把随机的请求调整成合适的形状，如下图所示：
    ![流量控制](https://github.com/alibaba/Sentinel/wiki/image/limitflow.gif)

  * 流量控制设计理念

    流量控制有以下几个角度:

    - 资源的调用关系，例如资源的调用链路，资源和资源之间的关系；
    - 运行指标，例如 QPS、线程池、系统负载等；
    - 控制的效果，例如直接限流、冷启动、排队等。

* 熔断降级

  * 在限制的手段上，Sentinel 和 Hystrix 采取了完全不一样的方法。

    Hystrix 通过 [线程池隔离](https://github.com/Netflix/Hystrix/wiki/How-it-Works#benefits-of-thread-pools) 的方式，来对依赖（在 Sentinel 的概念中对应 *资源*）进行了隔离。这样做的好处是资源和资源之间做到了最彻底的隔离。缺点是除了增加了线程切换的成本（过多的线程池导致线程数目过多），还需要预先给各个资源做线程池大小的分配。

    Sentinel 对这个问题采取了两种手段:

    - 通过并发线程数进行限制

    和资源池隔离的方法不同，Sentinel 通过限制资源并发线程的数量，来减少不稳定资源对其它资源的影响。这样不但没有线程切换的损耗，也不需要您预先分配线程池的大小。当某个资源出现不稳定的情况下，例如响应时间变长，对资源的直接影响就是会造成线程数的逐步堆积。当线程数在特定资源上堆积到一定的数量之后，对该资源的新请求就会被拒绝。堆积的线程完成任务后才开始继续接收请求。

    - 通过响应时间对资源进行降级

    除了对并发线程数进行控制以外，Sentinel 还可以通过响应时间来快速降级不稳定的资源。当依赖的资源出现响应时间过长后，所有对该资源的访问都会被直接拒绝，直到过了指定的时间窗口之后才重新恢复。

* 系统自适应保护
  Sentinel 同时提供系统维度的自适应保护能力。防止雪崩，是系统防护中重要的一环。当系统负载较高的时候，如果还持续让请求进入，可能会导致系统崩溃，无法响应。在集群环境下，网络负载均衡会把本应这台机器承载的流量转发到其它的机器上去。如果这个时候其它的机器也处在一个边缘状态的时候，这个增加的流量就会导致这台机器也崩溃，最后导致整个集群不可用。

  针对这个情况，Sentinel 提供了对应的保护机制，让系统的入口流量和系统的负载达到一个平衡，保证系统在能力范围之内处理最多的请求。



Hystrix与Sentinel比较：

- Hystrix
  1. 需要我们程序员自己手工搭建监控平台
  2. 没有一套web界面可以给我们进行更加细粒度化得配置流控、速率控制、服务熔断、服务降级
- Sentinel
  1. 单独一个组件，可以独立出来。
  2. 直接界面化的细粒度统一配置。



##### Sentinel 控制台

Sentinel 提供一个轻量级的开源控制台，它提供机器发现以及健康情况管理、监控（单机和集群），规则管理和推送的功能。（实时监控，规则管理）。

**下载控制台jar包**

https://github.com/alibaba/Sentinel/releases

**启动控制台**

启动命令：

```bash
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
```



#### 流量控制

**埋点**

埋点指的是针对特定用户行为或事件进行捕获、处理和发送的相关技术及其实施过程。学名叫事件追踪，数据埋点是一种常用的数据采集的方法。

**资源**

**资源** 是 Sentinel 中的核心概念之一。最常用的资源是我们代码中的 Java 方法。Sentinel 提供了 `@SentinelResource` 注解用于定义资源。

**@SentinelResource 注解**

> 注意：注解方式埋点不支持 private 方法。

`@SentinelResource` 用于定义资源，并提供可选的异常处理和 fallback 配置项。

**接入限流埋点**

- HTTP 埋点

  Sentinel starter 默认为所有的 HTTP 服务提供了限流埋点，如果只想对 HTTP 服务进行限流，那么只需要引入依赖，无需修改代码。

- 自定义埋点

  如果需要对某个特定的方法进行限流或降级，可以通过 `@SentinelResource` 注解来完成限流的埋点，示例代码如下：

  ```
   @SentinelResource("resource")
   public String hello() {
       return "Hello";
   }
  ```

  当然也可以通过原始的 `SphU.entry(xxx)` 方法进行埋点，可以参见 [Sentinel 文档](https://github.com/alibaba/Sentinel/wiki/如何使用#定义资源)。

**配置限流规则**

Sentinel 提供了两种配置限流规则的方式：代码配置 和 控制台配置。本示例使用的方式为通过控制台配置。

1. 通过代码来实现限流规则的配置。一个简单的限流规则配置示例代码如下，更多限流规则配置详情请参考 [Sentinel 文档](https://github.com/alibaba/Sentinel/wiki/如何使用#定义规则)。

```
List<FlowRule> rules = new ArrayList<FlowRule>();
FlowRule rule = new FlowRule();
rule.setResource(str);
// set limit qps to 10
rule.setCount(10);
rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
rule.setLimitApp("default");
rules.add(rule);
FlowRuleManager.loadRules(rules);
```

1. 通过控制台进行限流规则配置（略）。



**规则的种类**

Sentinel 的所有规则都可以在内存态中动态地查询及修改，修改之后立即生效。同时 Sentinel 也提供相关 API，供您来定制自己的规则策略。

Sentinel 支持以下几种规则：**流量控制规则**、**熔断降级规则**、**系统保护规则**、**来源访问控制规则** 和 **热点参数规则**。

文档：[basic-api-resource-rule (sentinelguard.io)](https://sentinelguard.io/zh-cn/docs/basic-api-resource-rule.html)







创建一个简单的演示案例：

创建模块cloud-alibaba-sentinel-service8401

pom.xml引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2021</artifactId>
        <groupId>com.hyd.springcloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>cloud-alibaba-sentinel-service8401</artifactId>

    <dependencies>
        <!-- sentinel -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>

</project>
```

application.yaml

```yaml
server:
  port: 8401
spring:
  application:
    name: cloud-alibaba-sentinel-service8401
  cloud:
    sentinel:
      transport:
        port: 8719 # 一个http server与sentinel控制台交互端口
        dashboard: localhost:8080 # sentinel控制台地址
```

启动类

```java
package com.hyd.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentinelServerMain8401 {
    public static void main(String[] args) {
        SpringApplication.run(SentinelServerMain8401.class,args);
    }
}

```

controller层

```java
package com.hyd.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SentinelController {

    @GetMapping("/t1")
    public String test1(){
        log.info(Thread.currentThread().getName()+"--> "+"t1");
        return "t1---";
    }
}

```

启动sentinel控制台(sentinel-dashboard)：

```shell
java -jar sentinel-dashboard.jar
```

默认访问地址：`http://localhost:8080/`

启动服务。

控制台监控为空，这是因为Sentinel 使用了 lazy load 策略（懒加载）。调用一下进行了 Sentinel 埋点的 URL 或方法，会发现控制台出现监控的应用。



##### 流控规则

**流控规则内容**

* 资源名：限流的 URL 相对路径，或者 `@SentinelResource` 注解 `value` 字段的值；
* 阈值类型/单机阈值；
  * QPS：（每秒钟的请求数量︰当调用该API的QPS达到阈值的时候，进行限流） ；
  * 线程数：当调用该API的线程数达到阈值的时候，进行限流；
* 是否集群；
* 流控模式： 
  * 直接：API达到限流条件时，直接限流；
  * 关联：当关联的资源达到阈值时，就限流；
  * 链路 ：只记录指定链路上的流量（指定资源从入口资源进来的流量，如果达到阈值，就进行限流)【API级别的针对来源】；
* 流控效果；
  * 快速失败：直接失败，抛异常；
  * Warm Up；根据Code Factor（冷加载因子，默认3）的值，从阈值/codeFactor，经过预热时长，才达到设置的QPS阈值；参考文档：[限流 冷启动 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/限流---冷启动)；
  * 排队等待：匀速排队，让请求以匀速的速度通过，阈值类型必须设置为QPS，否则无效。

**流控规则类型**

* URL 限流规则。资源名填写需要限流的 URL 相对路径。
* 自定义限流规则。资源名填写 `@SentinelResource` 注解 `value` 字段的值。

[流量控制 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/流量控制)



##### 降级规则

* 资源名；
* 降级策略；
  * 慢调用比例（秒级）：RT平均响应时间 (`DEGRADE_GRADE_RT`)：当 1s 内持续进入 N 个请求，对应时刻的平均响应时间（秒级）均超过阈值（`count`，以 ms 为单位），那么在接下的时间窗口（`DegradeRule` 中的 `timeWindow`，以 s 为单位）之内，对这个方法的调用都会自动地熔断（抛出 `DegradeException`）；
  * 异常比例（秒级）：单位时间异常比例超过规定值，触发降级；
  * 异常数策略（分钟级）：单位时间异常数超过规定值，触发降级；
* 时间窗口；

[熔断降级 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/熔断降级)



##### 热点规则

热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其访问进行限制。比如：

- 商品 ID 为参数，统计一段时间内最常购买的商品 ID 并进行限制
- 用户 ID 为参数，针对一段时间内频繁访问的用户 ID 进行限制

热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式，对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。

热点规则原理图：

![热点规则](https://github.com/alibaba/Sentinel/wiki/image/sentinel-hot-param-overview-1.png)



热点参数规则（`ParamFlowRule`）类似于流量控制规则（`FlowRule`）：

| 属性              | 说明                                                         | 默认值   |
| ----------------- | ------------------------------------------------------------ | -------- |
| resource          | 资源名，必填                                                 |          |
| count             | 限流阈值，必填                                               |          |
| grade             | 限流模式                                                     | QPS 模式 |
| durationInSec     | 统计窗口时间长度（单位为秒），1.6.0 版本开始支持             | 1s       |
| controlBehavior   | 流控效果（支持快速失败和匀速排队模式），1.6.0 版本开始支持   | 快速失败 |
| maxQueueingTimeMs | 最大排队等待时长（仅在匀速排队模式生效），1.6.0 版本开始支持 | 0ms      |
| paramIdx          | 热点参数的索引，必填，对应 `SphU.entry(xxx, args)` 中的参数索引位置 |          |
| paramFlowItemList | 参数例外项，可以针对指定的参数值单独设置限流阈值，不受前面 `count` 阈值的限制。**仅支持基本类型和字符串类型** |          |
| clusterMode       | 是否是集群参数流控规则                                       | `false`  |
| clusterConfig     | 集群流控相关配置                                             |          |

[热点参数限流 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/热点参数限流)



##### 系统规则

系统保护规则是从应用级别的入口流量进行控制，从单台机器的 load、CPU 使用率、平均 RT、入口 QPS 和并发线程数等几个维度监控应用指标，让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性。

系统保护规则是应用整体维度的，而不是资源维度的，并且**仅对入口流量生效**。入口流量指的是进入应用的流量（`EntryType.IN`），比如 Web 服务或 Dubbo 服务端接收的请求，都属于入口流量。

系统规则支持以下的模式：

- **Load 自适应**（仅对 Linux/Unix-like 机器生效）：系统的 load1 作为启发指标，进行自适应系统保护。当系统 load1 超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护（BBR 阶段）。系统容量由系统的 `maxQps * minRt` 估算得出。设定参考值一般是 `CPU cores * 2.5`。
- **CPU usage**（1.5.0+ 版本）：当系统 CPU 使用率超过阈值即触发系统保护（取值范围 0.0-1.0），比较灵敏。
- **平均 RT**：当单台机器上所有入口流量的平均 RT 达到阈值即触发系统保护，单位是毫秒。
- **并发线程数**：当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护。
- **入口 QPS**：当单台机器上所有入口流量的 QPS 达到阈值即触发系统保护。



##### 自定义限流处理逻辑

URL 限流触发后默认处理逻辑是，直接返回 "Blocked by Sentinel (flow limiting)"。

使用 `@SentinelResource` 注解下的限流异常处理

如果需要自定义处理逻辑，填写 `@SentinelResource` 注解的 `blockHandler` 属性（针对所有类型的 `BlockException`，需自行判断）或 `fallback` 属性（针对熔断降级异常），注意**对应方法的签名和位置有限制**，详情见 [Sentinel 注解支持文档](https://github.com/alibaba/Sentinel/wiki/注解支持#sentinelresource-注解)。示例实现如下：

```java
public class TestService {

    // blockHandler 是位于 ExceptionUtil 类下的 handleException 静态方法，需符合对应的类型限制.
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }

    // blockHandler 是位于当前类下的 exceptionHandler 方法，需符合对应的类型限制.
    @SentinelResource(value = "hello", blockHandler = "exceptionHandler")
    public String hello(long s) {
        return String.format("Hello at %d", s);
    }

    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }
}
public final class ExceptionUtil {

    public static void handleException(BlockException ex) {
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
    }
}
```

[注解支持 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/注解支持)



##### Endpoint 信息查看

Spring Boot 应用支持通过 Endpoint 来暴露相关信息，Sentinel Starter 也支持这一点。

在使用之前需要在 Maven 中添加 `spring-boot-starter-actuator`依赖，并在配置中允许 Endpoints 的访问。

- Spring Boot 1.x 中添加配置 `management.security.enabled=false`
- Spring Boot 2.x 中添加配置 `management.endpoints.web.exposure.include=*`

Spring Boot 1.x 可以通过访问 http://127.0.0.1:8401/sentinel 来查看 Sentinel Endpoint 的信息。Spring Boot 2.x 可以通过访问 http://127.0.0.1:8401/actuator/sentinel 来访问。（8401为对应spring应用的端口）。

pom.xml引入springboot监控依赖

```xml
<!-- actuaotr -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

application.yaml

```yaml
management:
  endpoints: # endpoint 信息查看
    web:
      exposure:
        include: '*'
```

启动服务，访问：`http://localhost:8401/actuator/sentinel`

```json
{
    "blockPage": null,
    "appName": "cloud-alibaba-sentinel-service8401",
    "consoleServer": "localhost:8080",
    "coldFactor": "3",
    "rules": {
        "systemRules": [],
        "authorityRule": [],
        "paramFlowRule": [],
        "flowRules": [],
        "degradeRules": []
    },
    "metricsFileCharset": "UTF-8",
    "filter": {
        "order": -2147483648,
        "urlPatterns": [
            "/*"
        ],
        "enabled": true
    },
    "totalMetricsFileCount": 6,
    "datasource": {},
    "clientIp": "192.168.1.106",
    "clientPort": "8720",
    "logUsePid": false,
    "metricsFileSize": 52428800,
    "logDir": "C:\\Users\\ayiya\\logs\\csp\\",
    "heartbeatIntervalMs": 10000
}
```



##### 查看实时监控

Sentinel 控制台支持实时监控查看，您可以通过 Sentinel 控制台查看各链路的请求的通过数和被限流数等信息。 其中 `p_qps` 为通过(pass) 流控的 QPS，`b_qps` 为被限流 (block) 的 QPS。

![实时监控图](https://user-images.githubusercontent.com/9434884/55449295-84866d80-55fd-11e9-94e5-d3441f4a2b63.png)



##### @SentinelResource 注解

官方文档：[注解支持 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/注解支持)

**@SentinelResource 注解**

> 注意：注解方式埋点不支持 private 方法。

`@SentinelResource` 用于定义资源，并提供可选的异常处理和 fallback 配置项。 `@SentinelResource` 注解包含以下属性：

- `value`：资源名称，必需项（不能为空）

- `entryType`：entry 类型，可选项（默认为 `EntryType.OUT`）

- `blockHandler` / `blockHandlerClass`: `blockHandler` 对应处理 `BlockException` 的函数名称，可选项。blockHandler 函数访问范围需要是 `public`，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 `BlockException`。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `blockHandlerClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。

- `fallback` / `fallbackClass`：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 `exceptionsToIgnore` 里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：
  - 返回值类型必须与原函数返回值类型一致；
  - 方法参数列表需要和原函数一致，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
  - fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。
  
- `defaultFallback`（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所有类型的异常（除了 `exceptionsToIgnore` 里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：
  - 返回值类型必须与原函数返回值类型一致；
  - 方法参数列表需要为空，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
  - defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。

- `exceptionsToIgnore`（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。

1.8.0 版本开始，`defaultFallback` 支持在类级别进行配置。

> 注：1.6.0 之前的版本 fallback 函数只针对降级异常（`DegradeException`）进行处理，**不能针对业务异常进行处理**。

特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 `BlockException` 时只会进入 `blockHandler` 处理逻辑。若未配置 `blockHandler`、`fallback` 和 `defaultFallback`，则被限流降级时会将 `BlockException` **直接抛出**（若方法本身未定义 throws BlockException 则会被 JVM 包装一层 `UndeclaredThrowableException`）。



总结：

`blockHandler` / `blockHandlerClass`：处理发生降级时的逻辑；

`fallback` / `fallbackClass`：处理发生异常时的逻辑；

`defaultFallback`：处理除`fallback`和`xx`外的逻辑；

`exceptionsToIgnore`：指定哪些异常被排除；



`blockHandler` / `fallback` / `defaultFallback`都未配置：被限流降级时会将 `BlockException` **直接抛出**（若方法本身未定义 throws BlockException 则会被 JVM 包装一层 `UndeclaredThrowableException`）；

`blockHandler` / `fallback` 配置：则被限流降级而抛出 `BlockException` 时**只会进入** `blockHandler` 处理逻辑。

示例：

```java
public class TestService {

    // 原函数 注解方式埋点不支持 private 方法
    @SentinelResource(value = "hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
    public String hello(long s) {
        return String.format("Hello at %d", s);
    }
    
    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(long s) {
        return String.format("Halooooo %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(long s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

    // 这里单独演示 blockHandlerClass 的配置.
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 public static 函数.
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        System.out.println("Test");
    }
}
```

```java
public final class ExceptionUtil {

    //blockHandler方法必须为 public static 函数（参数最后多一个 BlockException，其余与原函数一致.）
    public static void handleException(BlockException ex) {
        System.out.println("Oops: " + ex.getClass().getCanonicalName());
    }
}

```



案例：

修改模块cloud-alibaba-sentinel-service8401

BlockHandlerExceptionUtil类

```java
package com.hyd.springcloud.controller.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockHandlerExceptionUtil {
    public static String handleEx1(Integer id, BlockException ex){
        return "test3---blockHandlerClass---"+id+"---"+ex.getClass().getCanonicalName();
    }
}

```

FallbackExceptionUtil类

```java
package com.hyd.springcloud.controller.exception;

public class FallbackExceptionUtil {
    public static String fallbackEx1(Integer id){
        return "test5---fallbackClass---"+id;
    };
}

```

controller层

```java
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
```



**当方法发生降级时**：

什么都不配置；返回默认语句

```
Blocked by Sentinel (flow limiting)
```

配置 blockHandler；执行blockHandler对应方法

```
blockHandler---8---com.alibaba.csp.sentinel.slots.block.flow.FlowException
```

配置 blockHandlerClass；执行blockHandlerClass对应类中，对应方法

```
test3---blockHandlerClass---8---com.alibaba.csp.sentinel.slots.block.flow.FlowException
```

配置 fallback；执行fallback对应方法

```
fallback---8
```

配置 fallbackClass；执行fallbackClass对应类中，对应方法

```
test5---fallbackClass---8
```

配置 blockHandler 和 fallback；执行blockHandler对应方法

```
blockHandler---8---com.alibaba.csp.sentinel.slots.block.flow.FlowException
```

**当方法发生业务异常时**：

配置 blockHandler；抛出异常（若此时触发降级，则执行blockHandler对应方法）

```
{
    "timestamp": "2021-06-01T11:50:36.123+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "/ by zero",
    "path": "/t2/1"
}
```

```
blockHandler---1---com.alibaba.csp.sentinel.slots.block.flow.FlowException
```

配置 fallback；执行fallback对应方法

```
fallback---1
```

配置 blockHandler 和 fallback；执行fallback对应方法（若此时触发降级，则执行blockHandler对应方法）

```
fallback---1
```

```
blockHandler---1---com.alibaba.csp.sentinel.slots.block.flow.FlowException
```



##### 动态规则

`DataSource` 扩展常见的实现方式有:

- **拉模式**：客户端主动向某个规则管理中心定期轮询拉取规则，这个规则中心可以是 RDBMS、文件，甚至是 VCS 等。这样做的方式是简单，缺点是无法及时获取变更；
- **推模式**：规则中心统一推送，客户端通过注册监听器的方式时刻监听变化，比如使用 [Nacos](https://github.com/alibaba/nacos)、Zookeeper 等配置中心。这种方式有更好的实时性和一致性保证。

![datasource扩展](https://user-images.githubusercontent.com/9434884/45406233-645e8380-b698-11e8-8199-0c917403238f.png)

Sentinel 目前支持以下数据源扩展：

- Pull-based: 动态文件数据源、[Consul](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-consul), [Eureka](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-eureka)
- Push-based: [ZooKeeper](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-zookeeper), [Redis](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-redis), [Nacos](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-nacos), [Apollo](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-apollo), [etcd](https://github.com/alibaba/Sentinel/tree/master/sentinel-extension/sentinel-datasource-etcd)

###### 使用 Nacos 作为规则配置数据源

引入nacos依赖，以及sentinel适配nacos数据源依赖

pom.xml

```xml
```

nacos新建配置：

```
dataId:cloud-alibaba-sentinel-service8401-sentinel-rules
group:DEFAULT_GROUP
配置格式：json
```

配置内容：

```json
[{
    "resource": "/t1",
    "IimitApp": "default",
    "grade": 1,
    "count": 1, 
    "strategy": 0,
    "controlBehavior": 0,
    "clusterMode": false
}]
```

application.yaml配置

```yaml
server:
  port: 8401
spring:
  application:
    name: cloud-alibaba-sentinel-service8401
  cloud:
    sentinel:
      transport:
        port: 8719 # 一个http server与sentinel控制台交互端口
        dashboard: localhost:8080 # sentinel控制台地址
      datasource:
        ds1: # 随便起名
          nacos:
            server-addr: localhost:8848
            dataId: cloud-alibaba-sentinel-service8401-sentinel-rules # 绑定nacos规则配置
            groupId: DEFAULT_GROUP
            data-type: json # 配置类型json
            rule-type: flow # 流量控制规则
```

启动类及controller层代码略

启动nacos控制台，sentinel控制台

```shell
startup.cmd -m standalone
```

```shell
java -jar sentinel-dashboard-1.7.1.jar
```

启动服务，调用url接口，查看sentinel控制台，出现流控规则，成功。



可以配置application.yaml配置字段及nacos配置内容，格式如下：

```yaml
spring:
  cloud:
    sentinel:
      datasource:
        ds1: # 随便起名
          nacos:
            server-addr: localhost:8848
            dataId: cloud-alibaba-sentinel-service8401-sentinel-rules
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: flow
```

nacos flow流控规则配置如下：

```json
[
	{
		"resource": "/t1",//资源名
		"IimitApp": "default",//来源应用
		"grade": 1,//阈值类型；0：线程数，1：QPS
		"count": 1,//单机阈值
		"strategy": 0,//流控模式；0：快速失败，1：Warm Up，2：排队等待
		"controlBehavior": 0,//流控效果
		"clusterMode": false//是否集群
	}
]
```

官方流控规则demo：[Sentinel/NacosConfigSender.java at master · alibaba/Sentinel (github.com)](https://github.com/alibaba/Sentinel/blob/master/sentinel-demo/sentinel-demo-nacos-datasource/src/main/java/com/alibaba/csp/sentinel/demo/datasource/nacos/NacosConfigSender.java)



[动态规则扩展 · alibaba/Sentinel Wiki (github.com)](https://github.com/alibaba/Sentinel/wiki/动态规则扩展)



###### 相关源码

SentinelProperties配置类：`spring.cloud.sentinel`

```java
public static final String PROPERTY_PREFIX = "spring.cloud.sentinel";
```

SentinelProperties配置类源码：

```java
/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.sentinel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.log.LogBase;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * {@link ConfigurationProperties} for Sentinel.
 *
 * @author xiaojing
 * @author hengyunabc
 * @author jiashuai.xie
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
@ConfigurationProperties(prefix = SentinelConstants.PROPERTY_PREFIX)
@Validated
public class SentinelProperties {

	/**
	 * Earlier initialize heart-beat when the spring container starts when the transport
	 * dependency is on classpath, the configuration is effective.
	 */
	private boolean eager = false;

	/**
	 * Enable sentinel auto configure, the default value is true.
	 */
	private boolean enabled = true;

	/**
	 * The process page when the flow control is triggered.
	 */
	private String blockPage;

	/**
	 * Configurations about datasource, like 'nacos', 'apollo', 'file', 'zookeeper'.
	 */
	private Map<String, DataSourcePropertiesConfiguration> datasource = new TreeMap<>(
			String.CASE_INSENSITIVE_ORDER);

	/**
	 * Transport configuration about dashboard and client.
	 */
	private Transport transport = new Transport();

	/**
	 * Metric configuration about resource.
	 */
	private Metric metric = new Metric();

	/**
	 * Web servlet configuration when the application is web, the configuration is
	 * effective.
	 */
	private Servlet servlet = new Servlet();

	/**
	 * Sentinel interceptor when the application is web, the configuration is effective.
	 */
	private Filter filter = new Filter();

	/**
	 * Sentinel Flow configuration.
	 */
	private Flow flow = new Flow();

	/**
	 * Sentinel log configuration {@link LogBase}.
	 */
	private Log log = new Log();

	/**
	 * Add HTTP method prefix for Sentinel Resource.
	 */
	private Boolean httpMethodSpecify = false;

	public boolean isEager() {
		return eager;
	}

	public void setEager(boolean eager) {
		this.eager = eager;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public Servlet getServlet() {
		return servlet;
	}

	public void setServlet(Servlet servlet) {
		this.servlet = servlet;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Map<String, DataSourcePropertiesConfiguration> getDatasource() {
		return datasource;
	}

	public void setDatasource(Map<String, DataSourcePropertiesConfiguration> datasource) {
		this.datasource = datasource;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Boolean getHttpMethodSpecify() {
		return httpMethodSpecify;
	}

	public void setHttpMethodSpecify(Boolean httpMethodSpecify) {
		this.httpMethodSpecify = httpMethodSpecify;
	}

	public String getBlockPage() {
		if (StringUtils.hasText(this.blockPage)) {
			return this.blockPage;
		}
		return this.servlet.getBlockPage();
	}

	public void setBlockPage(String blockPage) {
		this.blockPage = blockPage;
	}

	public static class Flow {

		/**
		 * The cold factor {@link SentinelConfig#COLD_FACTOR}.
		 */
		private String coldFactor = SentinelConstants.COLD_FACTOR;

		public String getColdFactor() {
			return coldFactor;
		}

		public void setColdFactor(String coldFactor) {
			this.coldFactor = coldFactor;
		}

	}

	public static class Servlet {

		/**
		 * The process page when the flow control is triggered.
		 */
		private String blockPage;

		@Deprecated
		@DeprecatedConfigurationProperty(
				reason = "replaced to SentinelProperties#blockPage.",
				replacement = SentinelConstants.PROPERTY_PREFIX + ".block-page")
		public String getBlockPage() {
			return blockPage;
		}

		@Deprecated
		public void setBlockPage(String blockPage) {
			this.blockPage = blockPage;
		}

	}

	public static class Metric {

		/**
		 * The metric file size {@link SentinelConfig#SINGLE_METRIC_FILE_SIZE}.
		 */
		private String fileSingleSize;

		/**
		 * The total metric file count {@link SentinelConfig#TOTAL_METRIC_FILE_COUNT}.
		 */
		private String fileTotalCount;

		/**
		 * Charset when sentinel write or search metric file.
		 * {@link SentinelConfig#CHARSET}
		 */
		private String charset = SentinelConstants.CHARSET;

		public String getFileSingleSize() {
			return fileSingleSize;
		}

		public void setFileSingleSize(String fileSingleSize) {
			this.fileSingleSize = fileSingleSize;
		}

		public String getFileTotalCount() {
			return fileTotalCount;
		}

		public void setFileTotalCount(String fileTotalCount) {
			this.fileTotalCount = fileTotalCount;
		}

		public String getCharset() {
			return charset;
		}

		public void setCharset(String charset) {
			this.charset = charset;
		}

	}

	public static class Transport {

		/**
		 * Sentinel api port, default value is 8719 {@link TransportConfig#SERVER_PORT}.
		 */
		private String port = SentinelConstants.API_PORT;

		/**
		 * Sentinel dashboard address, won't try to connect dashboard when address is
		 * empty {@link TransportConfig#CONSOLE_SERVER}.
		 */
		private String dashboard = "";

		/**
		 * Send heartbeat interval millisecond
		 * {@link TransportConfig#HEARTBEAT_INTERVAL_MS}.
		 */
		private String heartbeatIntervalMs;

		/**
		 * Get heartbeat client local ip. If the client ip not configured, it will be the
		 * address of local host.
		 */
		private String clientIp;

		public String getHeartbeatIntervalMs() {
			return heartbeatIntervalMs;
		}

		public void setHeartbeatIntervalMs(String heartbeatIntervalMs) {
			this.heartbeatIntervalMs = heartbeatIntervalMs;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getDashboard() {
			return dashboard;
		}

		public void setDashboard(String dashboard) {
			this.dashboard = dashboard;
		}

		public String getClientIp() {
			return clientIp;
		}

		public void setClientIp(String clientIp) {
			this.clientIp = clientIp;
		}

	}

	public static class Filter {

		/**
		 * SentinelWebInterceptor order, will be register to InterceptorRegistry.
		 */
		private int order = Ordered.HIGHEST_PRECEDENCE;

		/**
		 * URL pattern for SentinelWebInterceptor, default is /*.
		 */
		private List<String> urlPatterns = Arrays.asList("/*");

		/**
		 * Enable to instance
		 * {@link com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor}.
		 */
		private boolean enabled = true;

		public int getOrder() {
			return this.order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public List<String> getUrlPatterns() {
			return urlPatterns;
		}

		public void setUrlPatterns(List<String> urlPatterns) {
			this.urlPatterns = urlPatterns;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

	}

	public static class Log {

		/**
		 * Sentinel log base dir.
		 */
		private String dir;

		/**
		 * Distinguish the log file by pid number.
		 */
		private boolean switchPid = false;

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public boolean isSwitchPid() {
			return switchPid;
		}

		public void setSwitchPid(boolean switchPid) {
			this.switchPid = switchPid;
		}

	}

}
```



SentinelProperties配置类中有`dashboard`字段：

```java
/**
	 * Configurations about datasource, like 'nacos', 'apollo', 'file', 'zookeeper'.
	 */
private Map<String, DataSourcePropertiesConfiguration> datasource = new TreeMap<>(
    String.CASE_INSENSITIVE_ORDER);
```

`datasource`是Map类型，配置application.yaml要注意。

该Map类型有以下字段配置：

```java
private FileDataSourceProperties file;

private NacosDataSourceProperties nacos;

private ZookeeperDataSourceProperties zk;

private ApolloDataSourceProperties apollo;

private RedisDataSourceProperties redis;
```

DataSourcePropertiesConfiguration源码：

```java
/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.sentinel.datasource.config;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.util.ObjectUtils;

/**
 * Using By ConfigurationProperties.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 * @see NacosDataSourceProperties
 * @see ApolloDataSourceProperties
 * @see ZookeeperDataSourceProperties
 * @see FileDataSourceProperties
 * @see RedisDataSourceProperties
 */
public class DataSourcePropertiesConfiguration {

	private FileDataSourceProperties file;

	private NacosDataSourceProperties nacos;

	private ZookeeperDataSourceProperties zk;

	private ApolloDataSourceProperties apollo;

	private RedisDataSourceProperties redis;

	public DataSourcePropertiesConfiguration() {
	}

	public DataSourcePropertiesConfiguration(FileDataSourceProperties file) {
		this.file = file;
	}

	public DataSourcePropertiesConfiguration(NacosDataSourceProperties nacos) {
		this.nacos = nacos;
	}

	public DataSourcePropertiesConfiguration(ZookeeperDataSourceProperties zk) {
		this.zk = zk;
	}

	public DataSourcePropertiesConfiguration(ApolloDataSourceProperties apollo) {
		this.apollo = apollo;
	}

	public DataSourcePropertiesConfiguration(RedisDataSourceProperties redis) {
		this.redis = redis;
	}

	public FileDataSourceProperties getFile() {
		return file;
	}

	public void setFile(FileDataSourceProperties file) {
		this.file = file;
	}

	public NacosDataSourceProperties getNacos() {
		return nacos;
	}

	public void setNacos(NacosDataSourceProperties nacos) {
		this.nacos = nacos;
	}

	public ZookeeperDataSourceProperties getZk() {
		return zk;
	}

	public void setZk(ZookeeperDataSourceProperties zk) {
		this.zk = zk;
	}

	public ApolloDataSourceProperties getApollo() {
		return apollo;
	}

	public void setApollo(ApolloDataSourceProperties apollo) {
		this.apollo = apollo;
	}

	public RedisDataSourceProperties getRedis() {
		return redis;
	}

	public void setRedis(RedisDataSourceProperties redis) {
		this.redis = redis;
	}

	@JsonIgnore
	public List<String> getValidField() {
		return Arrays.stream(this.getClass().getDeclaredFields()).map(field -> {
			try {
				if (!ObjectUtils.isEmpty(field.get(this))) {
					return field.getName();
				}
				return null;
			}
			catch (IllegalAccessException e) {
				// won't happen
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@JsonIgnore
	public AbstractDataSourceProperties getValidDataSourceProperties() {
		List<String> invalidFields = getValidField();
		if (invalidFields.size() == 1) {
			try {
				this.getClass().getDeclaredField(invalidFields.get(0))
						.setAccessible(true);
				return (AbstractDataSourceProperties) this.getClass()
						.getDeclaredField(invalidFields.get(0)).get(this);
			}
			catch (IllegalAccessException e) {
				// won't happen
			}
			catch (NoSuchFieldException e) {
				// won't happen
			}
		}
		return null;
	}

}
```



我们本次以nacos为数据源进行配置。

以nacos作为数据源的配置参数类源码：

```java
/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.sentinel.datasource.config;

import javax.validation.constraints.NotEmpty;

import com.alibaba.cloud.sentinel.datasource.factorybean.NacosDataSourceFactoryBean;

import org.springframework.util.StringUtils;

/**
 * Nacos Properties class Using by {@link DataSourcePropertiesConfiguration} and
 * {@link NacosDataSourceFactoryBean}.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public class NacosDataSourceProperties extends AbstractDataSourceProperties {

	private String serverAddr;

	@NotEmpty
	private String groupId = "DEFAULT_GROUP";

	@NotEmpty
	private String dataId;

	private String endpoint;

	private String namespace;

	private String accessKey;

	private String secretKey;

	public NacosDataSourceProperties() {
		super(NacosDataSourceFactoryBean.class.getName());
	}

	@Override
	public void preCheck(String dataSourceName) {
		if (StringUtils.isEmpty(serverAddr)) {
			serverAddr = this.getEnv().getProperty(
					"spring.cloud.sentinel.datasource.nacos.server-addr",
					"localhost:8848");
		}
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
```

`NacosDataSourceProperties` 继承于  `AbstractDataSourceProperties`。

`NacosDataSourceProperties`本类加上继承自父类的字段，再去除`@JsonIgnore`注解标记的字段，一共有以下配置字段：

```java
/** 继承自父类的字段 **/
@NotEmpty
private String dataType = "json";

@NotNull
private RuleType ruleType;

private String converterClass;

/** 本类中的字段 **/
private String serverAddr;

@NotEmpty
private String groupId = "DEFAULT_GROUP";

@NotEmpty
private String dataId;

private String endpoint;

private String namespace;

private String accessKey;

private String secretKey;
```



数据源的规则类型：

`ruleType`有以下类型：

```
flow;//流量控制规则；
degrade;//熔断降级规则；
param-flow;//热点规则；
system;//系统保护规则；
authority;//访问控制规则；

//gateway引入sentinel路由和api分组
gw-flow;
gw-api-group;
```

RuleType源码：

```java
/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.sentinel.datasource;

import java.util.Arrays;
import java.util.Optional;

import com.alibaba.cloud.sentinel.datasource.config.AbstractDataSourceProperties;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;

import org.springframework.util.StringUtils;

/**
 * Enum for {@link AbstractRule} class, using in
 * {@link AbstractDataSourceProperties#ruleType}.
 *
 * @author <a href="mailto:fangjian0423@gmail.com">Jim</a>
 */
public enum RuleType {

	/**
	 * flow.
	 */
	FLOW("flow", FlowRule.class),
	/**
	 * degrade.
	 */
	DEGRADE("degrade", DegradeRule.class),
	/**
	 * param flow.
	 */
	PARAM_FLOW("param-flow", ParamFlowRule.class),
	/**
	 * system.
	 */
	SYSTEM("system", SystemRule.class),
	/**
	 * authority.
	 */
	AUTHORITY("authority", AuthorityRule.class),
	/**
	 * gateway flow.
	 */
	GW_FLOW("gw-flow",
			"com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule"),
	/**
	 * api.
	 */
	GW_API_GROUP("gw-api-group",
			"com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition");

	/**
	 * alias for {@link AbstractRule}.
	 */
	private final String name;

	/**
	 * concrete {@link AbstractRule} class.
	 */
	private Class clazz;

	/**
	 * concrete {@link AbstractRule} class name.
	 */
	private String clazzName;

	RuleType(String name, Class clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	RuleType(String name, String clazzName) {
		this.name = name;
		this.clazzName = clazzName;
	}

	public String getName() {
		return name;
	}

	public Class getClazz() {
		if (clazz != null) {
			return clazz;
		}
		else {
			try {
				return Class.forName(clazzName);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Optional<RuleType> getByName(String name) {
		if (StringUtils.isEmpty(name)) {
			return Optional.empty();
		}
		return Arrays.stream(RuleType.values())
				.filter(ruleType -> name.equals(ruleType.getName())).findFirst();
	}

	public static Optional<RuleType> getByClass(Class clazz) {
		return Arrays.stream(RuleType.values())
				.filter(ruleType -> clazz == ruleType.getClazz()).findFirst();
	}

}
```

流量控制规则源码：

```java
/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.slots.block.flow;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;

/**
 * <p>
 * Each flow rule is mainly composed of three factors: <strong>grade</strong>,
 * <strong>strategy</strong> and <strong>controlBehavior</strong>:
 * </p>
 * <ul>
 *     <li>The {@link #grade} represents the threshold type of flow control (by QPS or thread count).</li>
 *     <li>The {@link #strategy} represents the strategy based on invocation relation.</li>
 *     <li>The {@link #controlBehavior} represents the QPS shaping behavior (actions on incoming request when QPS
 *     exceeds the threshold).</li>
 * </ul>
 *
 * @author jialiang.linjl
 * @author Eric Zhao
 */
public class FlowRule extends AbstractRule {

    public FlowRule() {
        super();
        setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
    }

    public FlowRule(String resourceName) {
        super();
        setResource(resourceName);
        setLimitApp(RuleConstant.LIMIT_APP_DEFAULT);
    }

    /**
     * The threshold type of flow control (0: thread count, 1: QPS).
     */
    private int grade = RuleConstant.FLOW_GRADE_QPS;

    /**
     * Flow control threshold count.
     */
    private double count;

    /**
     * Flow control strategy based on invocation chain.
     *
     * {@link RuleConstant#STRATEGY_DIRECT} for direct flow control (by origin);
     * {@link RuleConstant#STRATEGY_RELATE} for relevant flow control (with relevant resource);
     * {@link RuleConstant#STRATEGY_CHAIN} for chain flow control (by entrance resource).
     */
    private int strategy = RuleConstant.STRATEGY_DIRECT;

    /**
     * Reference resource in flow control with relevant resource or context.
     */
    private String refResource;

    /**
     * Rate limiter control behavior.
     * 0. default(reject directly), 1. warm up, 2. rate limiter, 3. warm up + rate limiter
     */
    private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    private int warmUpPeriodSec = 10;

    /**
     * Max queueing time in rate limiter behavior.
     */
    private int maxQueueingTimeMs = 500;

    private boolean clusterMode;
    /**
     * Flow rule config for cluster mode.
     */
    private ClusterFlowConfig clusterConfig;

    /**
     * The traffic shaping (throttling) controller.
     */
    private TrafficShapingController controller;

    public int getControlBehavior() {
        return controlBehavior;
    }

    public FlowRule setControlBehavior(int controlBehavior) {
        this.controlBehavior = controlBehavior;
        return this;
    }

    public int getMaxQueueingTimeMs() {
        return maxQueueingTimeMs;
    }

    public FlowRule setMaxQueueingTimeMs(int maxQueueingTimeMs) {
        this.maxQueueingTimeMs = maxQueueingTimeMs;
        return this;
    }

    FlowRule setRater(TrafficShapingController rater) {
        this.controller = rater;
        return this;
    }

    TrafficShapingController getRater() {
        return controller;
    }

    public int getWarmUpPeriodSec() {
        return warmUpPeriodSec;
    }

    public FlowRule setWarmUpPeriodSec(int warmUpPeriodSec) {
        this.warmUpPeriodSec = warmUpPeriodSec;
        return this;
    }

    public int getGrade() {
        return grade;
    }

    public FlowRule setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public double getCount() {
        return count;
    }

    public FlowRule setCount(double count) {
        this.count = count;
        return this;
    }

    public int getStrategy() {
        return strategy;
    }

    public FlowRule setStrategy(int strategy) {
        this.strategy = strategy;
        return this;
    }

    public String getRefResource() {
        return refResource;
    }

    public FlowRule setRefResource(String refResource) {
        this.refResource = refResource;
        return this;
    }

    public boolean isClusterMode() {
        return clusterMode;
    }

    public FlowRule setClusterMode(boolean clusterMode) {
        this.clusterMode = clusterMode;
        return this;
    }

    public ClusterFlowConfig getClusterConfig() {
        return clusterConfig;
    }

    public FlowRule setClusterConfig(ClusterFlowConfig clusterConfig) {
        this.clusterConfig = clusterConfig;
        return this;
    }

    @Override
    public boolean passCheck(Context context, DefaultNode node, int acquireCount, Object... args) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }

        FlowRule rule = (FlowRule)o;

        if (grade != rule.grade) { return false; }
        if (Double.compare(rule.count, count) != 0) { return false; }
        if (strategy != rule.strategy) { return false; }
        if (controlBehavior != rule.controlBehavior) { return false; }
        if (warmUpPeriodSec != rule.warmUpPeriodSec) { return false; }
        if (maxQueueingTimeMs != rule.maxQueueingTimeMs) { return false; }
        if (clusterMode != rule.clusterMode) { return false; }
        if (refResource != null ? !refResource.equals(rule.refResource) : rule.refResource != null) { return false; }
        return clusterConfig != null ? clusterConfig.equals(rule.clusterConfig) : rule.clusterConfig == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + grade;
        temp = Double.doubleToLongBits(count);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        result = 31 * result + strategy;
        result = 31 * result + (refResource != null ? refResource.hashCode() : 0);
        result = 31 * result + controlBehavior;
        result = 31 * result + warmUpPeriodSec;
        result = 31 * result + maxQueueingTimeMs;
        result = 31 * result + (clusterMode ? 1 : 0);
        result = 31 * result + (clusterConfig != null ? clusterConfig.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlowRule{" +
            "resource=" + getResource() +
            ", limitApp=" + getLimitApp() +
            ", grade=" + grade +
            ", count=" + count +
            ", strategy=" + strategy +
            ", refResource=" + refResource +
            ", controlBehavior=" + controlBehavior +
            ", warmUpPeriodSec=" + warmUpPeriodSec +
            ", maxQueueingTimeMs=" + maxQueueingTimeMs +
            ", clusterMode=" + clusterMode +
            ", clusterConfig=" + clusterConfig +
            ", controller=" + controller +
            '}';
    }
}

```



##### 熔断框架比较

|                | Sentinel                                                   | Hystrix                | resilience4j                     |
| -------------- | ---------------------------------------------------------- | ---------------------- | -------------------------------- |
| 隔离策略       | 信号量隔离（并发线程数限流）                               | 线程池隔商/信号量隔离  | 信号量隔离                       |
| 熔断降级策略   | 基于响应时间、异常比率、异常数                             | 线程池隔商/信号量隔离  | 基于异常比率、响应时间           |
| 实时统计实现   | 滑动窗口（LeapArray）                                      | 滑动窗口（基于RxJava） | Ring Bit Buffer                  |
| 动态规则配置   | 支持多种数据源                                             | 支持多种数据源         | 有限支持                         |
| 扩展性         | 多个扩展点                                                 | 插件的形式             | 接口的形式                       |
| 基于注解的支持 | 支持                                                       | 支持                   | 支持                             |
| 限流           | 基于QPS，支持基于调用关系的限流                            | 有限的支持             | Rate Limiter                     |
| 流量整形       | 支持预热模式匀速器模式、预热排队模式                       | 不支持                 | 简单的Rate Limiter模式           |
| 系统自适应保护 | 支持                                                       | 不支持                 | 不支持                           |
| 控制台         | 提供开箱即用的控制台，可配置规则、查看秒级监控，机器发观等 | 简单的监控查看         | 不提供控制台，可对接其它监控系统 |



---



### Spring Cloud Alibaba Seata 处理分布式事务

#### 分布式事务

##### 分布式事务问题

分布式系统一次业务操作需要跨多个数据源或需要跨多个系统进行远程调用，就会产生分布式事务问题。

分布式事务主要解决分布式一致性的问题。说到底就是数据的分布式操作导致仅依靠本地事务无法保证原子性。与单机版的事务不同的是，单机是把多个命令打包成一个统一处理，分布式事务是将多个机器上执行的命令打包成一个命令统一处理。

上一篇我们讲 InnoDB 下的事务，MySQL 提供了redo log，undo log， Read View，两阶段提交，MVCC 机制等等来保障事务的安全。分布式事务是不是更难呢？拭目以待。

##### 常见的分布式事务场景

分布式事务其实就在我们身边，你一直在用，但是你却一直不注意它。

**转账**

扣你账户的余额，增加别人账户余额，如果只扣了你的，别人没增加这是失败；如果没扣你的钱别人也增加了那银行的赔钱。

**下订单/扣库存**

电商系统中这是很常见的一个场景，用户下单成功了，店家没收到单，不发货；用户取消了订单，但是店家却看到了订单，发了货。

##### 分库分表场景

当我们的数据量大了之后，我们可能会部署很多独立的数据库，但是你的一个逻辑可能会同时操作很多个数据库的表，这时候该如何保证所有的操作要么成功，要么失败。

##### 分布式系统调用问题

微服务的拆分让各个系统各司其职，但是带来的也有很多痛苦，一个操作可能会伴随很多的外部请求，如果某一个外部系统挂掉了，之前操作已完成的这些是否需要回滚。

针对上面这些问题，我们前面学过的数据库4大特性：ACID 似乎在这里想要达到就变得很困难，单机情况下你还可以通过锁和日志机制来控制数据，在分布式场景又该如何实现呢？在不同的分布式应用架构下，实现一个分布式事务要考虑的问题并不完全一样，比如对多资源的协调、事务的跨服务传播等，实现机制也是复杂多变。尽管有这么多工程细节需要考虑，但分布式事务最核心的还是其 ACID 特性，只是这种 ACID 变换了场景。

##### 分布式理论

###### CAP 定理

传统的 ACID 模型肯定无法解决分布式环境下的挑战，基于此加州大学伯克利分校 Eric Brewer教授提出 CAP 定理，他指出 现代 WEB 服务无法同时满足以下 3 个属性：

- 一致性(Consistency) ： 所有的客户端都能返回最新的操作。
- 可用性(Availability) ： 非故障的节点在合理的时间内返回合理的响应(不是错误和超时的响应)。
- 分区容错性(Partition tolerance) ： 即使出现单个组件无法可用，操作依然可以完成。

关于一致性的理解后面分出来三个方向：

- 强一致：任何一次读都能读到某个数据的最近一次写的数据。系统中的所有进程，看到的操作顺序，都和全局时钟下的顺序一致。简言之，在任意时刻，所有节点中的数据是一样的。
- 弱一致：数据更新后，如果能容忍后续的访问只能访问到部分或者全部访问不到，则是弱一致性。
- 最终一致：不保证在任意时刻任意节点上的同一份数据都是相同的，但是随着时间的迁移，不同节点上的同一份数据总是在向趋同的方向变化。简单说，就是在一段时间后，节点间的数据会最终达到一致状态。

关于一致性的理解不同，后面对于 CAP 理论的实现就有所不同。

另外 Eric Brewer教授指出现代 WEB 服务无法同时满足这 3 个属性，说的是无法同时满足，那这是为什么呢？

如果在某个分布式系统中无副本，那么必然满足强一致性，同时也满足可用性，但是如果这个数据宕机了，那么可用性就得不到保证。

如果一个系统满足 AP，那么一致性又得不到保证。所以 CAP 原则的精髓就是要么 AP，要么 CP，要么 AC，但是不存在 CAP。

###### BASE 定理

基于前面提到的 CAP，反正我们都无法同时满足CAP，设计系统的最高目的就是让他跑下去不出错，那么是不是可以不要求强一致性，最终让他一致即可。所以后面又提出来了 BASE 定理：

- Basically Available（基本可用）
- Soft state（软状态）
- Eventually consistent（最终一致性）

基于现在大型分布式系统的复杂性，我们无法保证服务永远达到999，那么是否可以取舍，核心服务达到999，非核心服务允许挂为了保全核心服务。另外在非核心服务 down 机过程中允许系统暂时出现不一致但是这个不一致并不影响系统的核心功能使用。

最终系统恢复，所有服务一起修复数据，最终达到一致的状态。

业内通常把严格遵循 ACID 的事务称为**刚性事务**，而基于 BASE 思想实现的事务称为**柔性事务**。柔性事务并不是完全放弃了 ACID，仅仅是放宽了一致性要求：事务完成后的一致性严格遵循，事务中的一致性可适当放宽。

##### 常见的分布式事务实现方案

分布式事务实现方案从类型上去分刚性事务、柔型事务。刚性事务：通常无业务改造，强一致性，原生支持回滚/隔离性，低并发，适合短事务。柔性事务：有业务改造，最终一致性，实现补偿接口，实现资源锁定接口，高并发，适合长事务。

- 刚性事务：XA 协议（2PC、JTA、JTS）、3PC
- 柔型事务：TCC/FMT、Saga（状态机模式、Aop模式）、本地事务消息、消息事务（半消息）、最多努力通知型事务



分布式事务博客文章：[趁热打铁-再谈分布式事务 - rickiyang - 博客园 (cnblogs.com)](https://www.cnblogs.com/rickiyang/p/13704868.html)









#### Seata极简入门

> 详细内容见大佬博客文章：[Seata 极简入门](http://seata.io/zh-cn/blog/seata-quick-start.html)

##### 概述

Seata 是阿里开源的一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。

##### 四种事务模式

- AT 模式：参见[《Seata AT 模式》](https://seata.io/zh-cn/docs/dev/mode/at-mode.html)文档
- TCC 模式：参见[《Seata TCC 模式》](https://seata.io/zh-cn/docs/dev/mode/tcc-mode.html)文档
- Saga 模式：参见[《SEATA Saga 模式》](https://seata.io/zh-cn/docs/dev/mode/saga-mode.html)文档
- XA 模式：参见[《SEATA XA 模式》](http://seata.io/zh-cn/docs/dev/mode/xa-mode.html)文档

目前流行度情况是：AT > TCC > Saga。后续着重介绍AT模式。

##### 三种角色

在 Seata 的架构中，一共有三个角色：

<img src="http://www.iocoder.cn/images/Seata/2017-01-01/02.png" alt="三个角色" style="zoom:50%;" />



- **TC** (Transaction Coordinator) - 事务协调者：维护全局和分支事务的状态，驱动**全局事务**提交或回滚。
- **TM** (Transaction Manager) - 事务管理器：定义**全局事务**的范围，开始全局事务、提交或回滚全局事务。
- **RM** ( Resource Manager ) - 资源管理器：管理**分支事务**处理的资源( Resource )，与 TC 交谈以注册分支事务和报告分支事务的状态，并驱动**分支事务**提交或回滚。

其中，TC 为单独部署的 **Server** 服务端，TM 和 RM 为嵌入到应用中的 **Client** 客户端。

在 Seata 中，一个分布式事务的**生命周期**如下：

<img src="http://www.iocoder.cn/images/Seata/2017-01-01/01.png" alt="架构图" style="zoom: 67%;" />



> 友情提示：看下大佬艿艿添加的红色小勾。

- TM 请求 TC 开启一个全局事务。TC 会生成一个 **XID** 作为该全局事务的编号。

  > **XID**，会在微服务的调用链路中传播，保证将多个微服务的子事务关联在一起。

- RM 请求 TC 将本地事务注册为全局事务的分支事务，通过全局事务的 **XID** 进行关联。

- TM 请求 TC 告诉 **XID** 对应的全局事务是进行提交还是回滚。

- TC 驱动 RM 们将 **XID** 对应的自己的本地事务进行提交还是回滚。

##### 框架支持情况

Seata 目前提供了对主流的**微服务框架**的支持。（详细见大佬文章原文，此处略）。

- Spring Cloud OpenFeign

  > 通过 [`spring-cloud-starter-alibaba-seata`](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-starters/spring-cloud-starter-alibaba-seata/src/main/java/com/alibaba/cloud/seata/) 的 [`feign`](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-starters/spring-cloud-starter-alibaba-seata/src/main/java/com/alibaba/cloud/seata/feign/) 模块

- Spring RestTemplate

  > 通过 [`spring-cloud-starter-alibaba-seata`](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-starters/spring-cloud-starter-alibaba-seata/src/main/java/com/alibaba/cloud/seata/feign/SeataBeanPostProcessor.java) 的 [`rest`](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-starters/spring-cloud-starter-alibaba-seata/src/main/java/com/alibaba/cloud/seata/rest/) 模块

同时方便我们集成到 Java 项目当中，Seata 也提供了相应的 Starter 库：

- [`seata-spring-boot-starter`](https://mvnrepository.com/artifact/io.seata/seata-spring-boot-starter)
- [`spring-cloud-starter-alibaba-seata`](https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-seata)

因为 Seata 是基于 [DataSource](https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html) 数据源进行**代理**来拓展，所以天然对主流的 ORM 框架提供了非常好的支持：

- MyBatis、MyBatis-Plus
- JPA、Hibernate



##### 部署单机TC Server

本小节，我们来学习部署**单机** Seata **TC** Server，常用于学习或测试使用，不建议在生产环境中部署单机。

因为 TC 需要进行全局事务和分支事务的记录，所以需要对应的**存储**。目前，TC 有两种存储模式( `store.mode` )：

- file 模式：适合**单机**模式，全局事务会话信息在**内存**中读写，并持久化本地文件 `root.data`，性能较高。
- db 模式：适合**集群**模式，全局事务会话信息通过 **db** 共享，相对性能差点。

显然，我们将采用 file 模式，最终我们部署单机 TC Server 如下图所示：

<img src="http://www.iocoder.cn/images/Seata/2017-01-01/11.png" alt="单机 TC Server" style="zoom:50%;" />



###### 下载Seata软件包

下载地址：https://github.com/seata/seata/releases，完成解压（此处为Windows版本）。

###### 启动 TC Server

执行`.bat`脚本启动。此处选用的是默认file模式，直接启动就好。：

```shell
seata-server.bat
```

见到以下日志，说明启动成功。

```
2021-06-06 08:14:48.143 INFO [main]io.seata.common.loader.EnhancedServiceLoader.loadFile:236 -load TransactionStoreManager[DB] extension
by class[io.seata.server.store.db.DatabaseTransactionStoreManager]
2021-06-06 08:14:48.143 INFO [main]io.seata.common.loader.EnhancedServiceLoader.loadFile:236 -load SessionManager[DB] extension by class[io.seata.server.session.db.DataBaseSessionManager]
2021-06-06 08:14:48.593 INFO [main]io.seata.core.rpc.netty.AbstractRpcRemotingServer.start:155 -Server started ...
```

###### db模式使用Nacos作为注册中心

如果使用db模式，则进行以下配置：



**资源目录介绍**

目录地址：[seata/script at develop · seata/seata (github.com)](https://github.com/seata/seata/tree/develop/script) ，可以下载Seata源码包解压即可获取。

- client

> 存放client端sql脚本 (包含 undo_log表) ，参数配置

- config-center

> 各个配置中心参数导入脚本，config.txt(包含server和client，原名nacos-config.txt)为通用参数文件

- server

> server端数据库脚本 (包含 lock_table、branch_table 与 global_table) 及各个容器配置



**初始化数据库**

在db目录下，选择[mysql脚本](https://github.com/seata/seata/tree/develop/script/server/db/mysql.sql)。在 MySQL 中，创建 `seata_server` 数据库，并在该库下执行该脚本。

全局事务会话信息由3块内容构成，全局事务-->分支事务-->全局锁，对应表global_table、branch_table、lock_table。

```mysql
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(128),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```

修改 `conf/file.conf` 配置文件，修改事务存储模式为使用 db 数据库，并配置数据库信息。实现 Seata TC Server 的全局事务会话信息的共享。（如果registry.conf中的config.type不为file，为其它配置中心，则此处不配置file.conf，只要默认config.type=file时，才需要）。

```
## transaction log store, only used in seata-server
store {
  ## store mode: file、db
  # 修改事务日志存储模式
  mode = "db"

  ## file store property
  file {
    ## store location dir
    dir = "sessionStore"
  }

  ## database store property
  # 修改数据库为自己的数据库
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/seata_server"
    user = "root"
    password = "123456"
    min-conn = 1
    max-conn = 10
    global.table = "global_table"
    branch.table = "branch_table"
    lock-table = "lock_table"
    query-limit = 100
  }
}
```

修改 `conf/registry.conf` 配置文件，设置使用 Nacos 注册中心。注意这里有一个坑，serverAddr不能带‘http://’前缀。

```
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
    password = ""
    cluster = "default"
    timeout = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
    username = ""
    password = ""
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3、springCloudConfig
  type = "nacos"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    group = "SEATA_GROUP"
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
    namespace = "application"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
    username = ""
    password = ""
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```



---

#### Seata部署指南

官方新人部署指南：[Seata部署指南](http://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html)

Seata分TC、TM和RM三个角色，TC（Server端）为单独服务端部署，TM和RM（Client端）由业务系统集成。

##### 启动Server

步骤一：初始化数据库

全局事务会话信息由3块内容构成，全局事务-->分支事务-->全局锁，对应表global_table、branch_table、lock_table。

步骤二：修改registry.conf

该配置用于指定 TC 的注册中心和配置文件，默认都是 file; 可以将 Seata-Server 注册到注册中心，同时可以通过config.type指定配置文件类型及位置，默认为file，配置文件为file.conf，可以注册到nacos上。此时，本地的file.conf就没作用了，在nacos上进行配置。

```
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "file"

  nacos {
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```

步骤三：修改file.conf

修改事务存储模式为使用 db 数据库，并配置数据库连接信息。实现 Seata TC Server 的全局事务会话信息的共享。注意**不需要配置**service{}模块，该模块是client客户端配置，有许多文章再此配置了，根本没必要，详细查看官方Seata配置文档：[Seata 参数配置](https://seata.io/zh-cn/docs/user/configurations.html)。

```
## transaction log store, only used in seata-server
store {
  ## store mode: file、db
  # 修改事务日志存储模式
  mode = "db"

  ## file store property
  file {
    ## store location dir
    dir = "sessionStore"
  }

  ## database store property
  # 修改数据库为自己的数据库
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/seata_server"
    user = "root"
    password = "123456"
    min-conn = 1
    max-conn = 10
    global.table = "global_table"
    branch.table = "branch_table"
    lock-table = "lock_table"
    query-limit = 100
  }
}
```

启动Seata-Server

```
# windows
seata-server.bat
```



##### 业务系统集成Client（AT模式）

步骤一：添加seata依赖（三选一）

* seata-all
* seata-spring-boot-starter
* spring-cloud-alibaba-seata

`seata-all`,`seata-spring-boot-starter`和`spring-cloud-alibaba-seata`三者区别

| 依赖                       | 支持yml配置 | 实现xid传递 | 支持数据源自动代理 | 自动初始化GlobalTransactionScanner入口 |
| -------------------------- | ----------- | ----------- | ------------------ | -------------------------------------- |
| seata-all                  | 否          | 否          | 是                 | 否                                     |
| seata-spring-boot-starter  | 是          | 否          | 是                 | 是                                     |
| spring-cloud-alibaba-seata | 是          | 是          | 是                 | 是                                     |

0.9.0版本开始seata支持自动代理数据源。

```
1.1.0: seata-all取消属性配置，改由注解@EnableAutoDataSourceProxy开启，并可选择jdk proxy或者cglib proxy
1.0.0: client.support.spring.datasource.autoproxy=true
0.9.0: support.spring.datasource.autoproxy=true
```

目前seata-all是需要使用conf类型配置文件，后续会支持properties和yml类型文件。当前可以在项目中依赖seata-spring-boot-starter，然后将配置项写入到application .properties 这样可以不使用conf类型文件。

- 依赖 seata-spring-boot-starter 时，自动代理数据源，无需额外处理。
- 依赖 seata-all 时，使用 @EnableAutoDataSourceProxy (since 1.1.0) 注解，注解参数可选择 jdk 代理或者 cglib 代理。
- 依赖 seata-all 时，也可以手动使用 DatasourceProxy 来包装 DataSource。

1. 配置 GlobalTransactionScanner，使用 seata-all 时需要手动配置，使用 seata-spring-boot-starter 时无需额外处理。

seata-spring-boot-starter内置GlobalTransactionScanner自动初始化功能，若外部实现初始化，请参考SeataAutoConfiguration保证依赖加载顺序 默认开启数据源自动代理，可配置seata.enable-auto-data-source-proxy: false关闭。





**seata-all**

需要手动配置GlobalTransactionScanner（全局事务扫描器）、Rpc Interceptor（解决事务跨服务传播）。

**seata-spring-boot-starter**

支持yml、properties配置(.conf可删除)，内部已依赖seata-all。

**spring-cloud-alibaba-seata**

内部集成了seata，并实现了xid传递。





**步骤二：配置seata client端registry.conf和file.conf配置文件**

通过*.conf配置seata client配置文件registry.conf和file.conf

registry 配置注册中心

```
registry {
  type = "nacos"
  nacos {
	# nacos服务地址
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
}
config {
  type = "file"
  file {
    name = "file.conf"
  }
}
```

file.conf 配置事务组，及是否开启全局事务。

自定义事务组名称（需要注意的是 `service.vgroup_mapping`这个配置，在 Spring Cloud 中默认是`${spring.application.name}-fescar-service-group`，可以通过指定`application.properties`的 `spring.cloud.alibaba.seata.tx-service-group`这个属性覆盖，但是必须要和 `file.conf `中的一致，否则会提示 `no available server to connect`）。如果不通过application配置文件指定及不自动配置，则通过GlobalTransactionScanner指定txServiceGroup。

```
service {
  #vgroup->rgroup
  vgroup_mapping.order_group = "default"
  #only support single node
  default.grouplist = "127.0.0.1:8091"
  #degrade current not support
  enableDegrade = false
  #disable
  disable = false
  disableGlobalTransaction = false
}
```

通过application.properties或application.yaml配置seata client配置文件。

目前seata-all是需要使用conf类型配置文件，后续会支持properties和yml类型文件。当前可以在项目中依赖seata-spring-boot-starter，然后将配置项写入到application .properties 这样可以不使用conf类型文件。















步骤三：undo_log建表、配置参数

为每个微服务RM（资源管理器）对应数据库建立undo_log日志回滚表。

`undo_log`日志表建表`sql`在seata-server源码包：`seata-1.0.0/script/client/at/db/`目录下的`mysql.sql`。

在业务相关的数据库中添加 undo_log 表，用于保存需要回滚的数据（AT模式）。对每个微服务连接的不同数据库都需要各自单独创建该表。

mysql.sql

```mysql
-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE IF NOT EXISTS `undo_log`
(
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'increment id',
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME     NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME     NOT NULL COMMENT 'modify datetime',
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';
```

AT模式下Seata Client端主要通过如下三个模块来实现分布式事务：

- **GlobalTransactionScanner：** GlobalTransactionScanner负责初始TM、RM模块，并为添加分布式事务注解的方法添加拦截器，拦截器负责全局事务的开启、提交或回滚
- **DatasourceProxy：** DatasourceProxy为DataSource添加拦截，拦截器会拦截所有SQL执行，并作为RM事务参与方的角色参与分布式事务执行。
- **Rpc Interceptor：**分布式事务的跨服务实例传播，解决XID跨服务传播。Rpc Interceptor的职责就是负责在多个微服务之间传播事务。

步骤四：数据源代理(不支持自动和手动配置并存,不支持XA数据源自动代理)

手动配置数据源代理

手动配置数据源代理，需要在启动类排除spring数据源自动装配：

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动创建，使用seata进行数据源代理
```



Mybatis案例

```java
/**
 * seata 数据源代理
 * Seata 通过代理数据源的方式实现分支事务；
 * MyBatis 和 JPA 都需要注入 io.seata.rm.datasource.DataSourceProxy,
 * 不同的是，MyBatis 还需要额外注入 org.apache.ibatis.session.SqlSessionFactory
 */
@Configuration
public class DataSourceProxyConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean("datasource")
    public DataSourceProxy DataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*Mapper.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
```



使用spring-cloud-starter-alibaba-seata + Mybatis手动代理数据源的时候，进行如下配置：

```java
@Configuration
public class DataSourceProxyConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*Mapper.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
```





自动配置

0.9.0版本开始seata支持自动代理数据源。seata-all:0.9.0版本开始支持自动代理数据源，默认关闭。

seata-spring-boot-starter和spring-cloud-starter-alibaba-seata支持自动代理数据源，并且默认开启。

```
1.1.0: seata-all取消属性配置，改由注解@EnableAutoDataSourceProxy开启，并可选择jdk proxy或者cglib proxy
1.0.0: client.support.spring.datasource.autoproxy=true
0.9.0: support.spring.datasource.autoproxy=true
```

以seata-all:1.0.0 为例，在file.conf添加以下属性，开启自动配置

```
client {

  support {
    # auto proxy the DataSource bean
    # 自动配置数据源代理，默认false关闭，如果配置了自动代理，则开启，否则关闭
    spring.datasource.autoproxy = true
  }
}
```



步骤五：初始化GlobalTransactionScanner

手动配置

seata-all不支持自动配置，需要手动配置，seata-spring-boot-starter、spring-cloud-starter-alibaba-seata支持自动配置，默认开启，也可以手动配置。

```java
@Configuration
public class GlobalTransactionScannerConfig {

    @Value("${spring.application.name}")
    private String applicationId;

    //与该微服务对应的 seata file.conf 中 service.vgroup_mapping 事务组字段一致
    private static final String TX_SERVICE_GROUP = "order_group";

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(applicationId, TX_SERVICE_GROUP);
    }

}
```

自动配置

引入seata-spring-boot-starter、spring-cloud-starter-alibaba-seata等jar。



步骤六：实现xid跨服务传递

官方Seata事务文档：[Seata 微服务框架支持](http://seata.io/zh-cn/docs/user/microservice.html)，介绍了事务上下文以及跨服务事务传播及实现。

手动

参考源码integration文件夹下的各种rpc实现 module

此处自己写了一个测试用的Demo，服务调用方通过拦截器传递XID，服务提供方通过过滤器获取XID。

拦截器SeataXidInterceptor

```java
/**
 * 跨服务调用的事务传播
 * 把服务调用方 RootContext XID 通过服务调用传递到服务提供方
 *
 * 服务调用方传递 XID
 *
 * 拦截器添加请求头数据
 */
@Component
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
```

过滤器SeataXidFilter

```java
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
@Component
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
```



自动

springCloud用户可以引入spring-cloud-starter-alibaba-seata，内部已经实现xid传递



步骤七：使用@GlobalTransactional开启事务
在业务的发起方的方法上使用@GlobalTransactional开启全局事务，Seata 会将事务的 xid 通过拦截器添加到调用其他服务的请求中，实现分布式事务

SpringCloud应用使用注解开启分布式事务时，若默认服务 provider 端加入 consumer 端的事务，provider 可不标注注解。但是，provider 同样需要相应的依赖和配置，仅可省略注解。

使用注解开启分布式事务时，若要求事务回滚，必须将异常抛出到事务的发起方，被事务发起方的 @GlobalTransactional 注解感知到。provide 直接抛出异常 或 定义错误码由 consumer 判断再抛出异常。



**注意事项**



seata依赖引入有以下几种方式：

```xml
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
    <version>1.0.0</version>
</dependency>
```

```xml
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    <version>最新版</version>
</dependency>
```



```xml
<!-- seata （指定版本，此处自带版本为1.0.0，无需排除，此处为演示） -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <exclusions>
        <!-- 排除自带的版本 -->
        <exclusion>
            <groupId>io.seata</groupId>
            <artifactId>seata-all</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 添加指定版本 -->
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
    <version>1.0.0</version>
</dependency>
```

spring-cloud-starter-alibaba-seata推荐依赖配置方式（解决starter依赖兼容问题）。

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <version>2.2.1.RELEASE</version>
    <exclusions>
        <!-- 排除自带的版本 -->
        <exclusion>
            <groupId>io.seata</groupId>
            <artifactId>seata-spring-boot-starter</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 添加指定版本 -->
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    <version>最新版</version>
</dependency>
```

使用seata-spring-boot-starter和spring-cloud-starter-alibaba-seata时，如果seata client使用conf配置文件，则application.yaml需要配置事务组名称，不然默认使用`${spring.application.name}-seata-service-group`作为事务组名称，注意该默认配置字段在seata事务数据库字段长度不能超过36。

```yaml
spring:
  cloud:
    alibaba:
      seata:
        # (order,storage,account)事务组名称，与seata配置文件中vgroup_mapping.storage_group值一致
        tx-service-group: storage_group
```



---



#### SpringCloud + Nacos + Feign + Seata案例

本文将通过一个简单的微服务架构的例子，说明业务如何step by step的使用 Seata、Feign、SpringCloud和 Nacos 来保证业务数据的一致性。本文所述的例子中 SpringCloud和 Seata 注册配置服务中心均使用 Nacos。

##### 业务案例

用户采购商品业务，整个业务包含3个微服务:

- 库存服务(StorageService): 扣减给定商品的库存数量。
- 订单服务(OrderService): 根据采购请求生成订单。
- 账户服务(AccountService): 用户账户金额扣减。

##### 业务结构

business --> order , business  --> storage , business  --> account

其实做成（x->a,x->b,b->c，这样的好一些，不过无所谓）。

三个微服务模块：

* order-service # 订单微服务模块
* storage-service # 库存微服务模块
* account-service # 账户微服务模块

一个商业服务模块：

* business模块

**说明:** 以上三个微服务独立部署。



##### 搭建seata-server

修改`registry.conf`

修改`file.conf`

修改事务日志存储模式，配置数据库连接信息

为seata-server创建事务日志存储数据库及对应三张表：`global_table`,`branch_table`,`lock_table`。

先启动nacos-server

nacos-server启动成功后，再启动seata-server。

seata-server启动成功，并且成功注册到nacos中。



##### 应用配置

###### 创建 undo_log（用于 Seata AT 模式）表和相关业务表

```mysql
-- 创建 order库、业务表、undo_log表
create database seata_order;
use seata_order;

DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
    `commodity_code` varchar(255) DEFAULT NULL COMMENT '产品编码',
    `count` int(11) DEFAULT 0 COMMENT '数量',
    `money` int(11) DEFAULT 0 COMMENT '金额',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '订单表';

CREATE TABLE `undo_log` (
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     BIGINT(20)   NOT NULL,
    `xid`           VARCHAR(100) NOT NULL,
    `context`       VARCHAR(128) NOT NULL,
    `rollback_info` LONGBLOB     NOT NULL,
    `log_status`    INT(11)      NOT NULL,
    `log_created`   DATETIME     NOT NULL,
    `log_modified`  DATETIME     NOT NULL,
    `ext`           VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- 创建 storage库、业务表、undo_log表
create database seata_storage;
use seata_storage;

DROP TABLE IF EXISTS `storage_tbl`;
CREATE TABLE `storage_tbl` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `commodity_code` varchar(255) DEFAULT NULL,
    `count` int(11) DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log` (
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     BIGINT(20)   NOT NULL,
    `xid`           VARCHAR(100) NOT NULL,
    `context`       VARCHAR(128) NOT NULL,
    `rollback_info` LONGBLOB     NOT NULL,
    `log_status`    INT(11)      NOT NULL,
    `log_created`   DATETIME     NOT NULL,
    `log_modified`  DATETIME     NOT NULL,
    `ext`           VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

-- 初始化库存模拟数据
INSERT INTO seata_storage.storage_tbl (id, commodity_code, count) VALUES (1, 'product-1', 9999999);
INSERT INTO seata_storage.storage_tbl (id, commodity_code, count) VALUES (2, 'product-2', 0);


-- 创建 account库、业务表、undo_log表
create database seata_order;
use seata_account;

DROP TABLE IF EXISTS `account_tbl`;
CREATE TABLE `account_tbl` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255) DEFAULT NULL,
    `money` int(11) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log` (
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     BIGINT(20)   NOT NULL,
    `xid`           VARCHAR(100) NOT NULL,
    `context`       VARCHAR(128) NOT NULL,
    `rollback_info` LONGBLOB     NOT NULL,
    `log_status`    INT(11)      NOT NULL,
    `log_created`   DATETIME     NOT NULL,
    `log_modified`  DATETIME     NOT NULL,
    `ext`           VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

-- 初始化账户模拟数据
insert into seata_account.account_tbl (id, user_id, money) VALUES (1,'user-01',10000);
insert into seata_account.account_tbl (id, user_id, money) VALUES (2,'user-02',0);

-- 创建商品购买业务数据库，用来存放undo_log，虽然它没有进行数据库操作，但属于一个事务
create database seata_business;
use seata_business;

CREATE TABLE `undo_log` (
    `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     BIGINT(20)   NOT NULL,
    `xid`           VARCHAR(100) NOT NULL,
    `context`       VARCHAR(128) NOT NULL,
    `rollback_info` LONGBLOB     NOT NULL,
    `log_status`    INT(11)      NOT NULL,
    `log_created`   DATETIME     NOT NULL,
    `log_modified`  DATETIME     NOT NULL,
    `ext`           VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- 建表sql end ---
```



###### 引入 Seata、SpringCloud和 Nacos 相关 POM 依赖

> 此处演示引入seata-all。

```xml
<dependencies>
    <!-- seata -->
    <dependency>
        <groupId>io.seata</groupId>
        <artifactId>seata-all</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!--nacos discovery -->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- feign -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!-- web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- mybatis -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- druid -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid-spring-boot-starter</artifactId>
    </dependency>
    <!-- lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

###### 配置文件

订单模块application.yaml示例，库存模块，账户模块类似，略。

```yaml
server:
  port: 8001
spring:
  application:
    name: order-service # 订单微服务模块
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/seata_order?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false
    username: root
    password: 123456
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848

mybatis:
  mapper-locations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.hyd.work.entity

logging:
  level:
    io:
      seata: debug
```

业务模块application.yaml示例，比上述模块多了一个配置，允许存在多个Feign调用相同Service的接口。

```yaml
server:
  port: 80
spring:
  application:
    name: business-service
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848

  main:
    allow-bean-definition-overriding: true #允许存在多个Feign调用相同Service的接口

feign:
  client:
    config:
      - default:
          connectTimeout: 10000
          readTimeout: 60000

logging:
  level:
    io:
      seata: debug
```

seata配置文件（此处使用seata-all:1.0.0，该依赖目前是需要使用conf类型配置文件，官方文档说后续会支持yaml等配置文件）

registry.conf

```
registry {
  type = "nacos"
  nacos {
	# nacos服务地址
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
}
config {
  type = "file"
  file {
    name = "file.conf"
  }
}
```

file.conf，此处为每个微服务模块配置了一个事务组名称，其它模块为order_group/account_group等等。（仅client需要配置，seata server端不需要配置service模块，有些文章误导人）。

```
service {
  #vgroup->rgroup
  vgroup_mapping.business_group = "default"
  #only support single node
  default.grouplist = "127.0.0.1:8091"
  #degrade current not support
  enableDegrade = false
  #disable
  disable = false
  disableGlobalTransaction = false
}
```



##### 数据源代理

此处seata-all依赖，选择手动代理模式。三个微服务模块(RM)都需要配置，业务模块不涉及资源(RM)不配置。

```java
package com.hyd.work.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * seata 数据源代理
 * Seata 通过代理数据源的方式实现分支事务；
 * MyBatis 和 JPA 都需要注入 io.seata.rm.datasource.DataSourceProxy,
 * 不同的是，MyBatis 还需要额外注入 org.apache.ibatis.session.SqlSessionFactory
 */
@Configuration
public class DataSourceProxyConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean("datasource")
    public DataSourceProxy DataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*Mapper.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

    @Value("${spring.application.name}")
    private String applicationId;

    //与该微服务对应的 seata file.conf 中 service.vgroup_mapping 事务组字段一致
    private static final String TX_SERVICE_GROUP = "order_group";

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        return new GlobalTransactionScanner(applicationId, TX_SERVICE_GROUP);
    }

}
```



##### 初始化GlobalTransactionScanner

此处使用的seata-all依赖，seata-all不支持自动代理，需要进行手动代理。

见上面代码。每个微服务模块都需要配置，以实现seata全局事务管理。

##### 实现xid跨服务传递

此处使用的seata-all依赖，需要自己实现。

服务调用方通过 SeataXidInterceptor 拦截器传递 XID

```java
/**
 * 跨服务调用的事务传播
 * 把服务调用方 RootContext XID 通过服务调用传递到服务提供方
 *
 * 服务调用方传递 XID
 *
 * 拦截器添加请求头数据
 */
@Component
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
```

服务提供方通过 SeataXidFilter 过滤器获取 XID

```java
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
@Component
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
```



###### 业务代码

三个模块：订单，库存，账户代码如下：

实体类

```java
/**
 * 订单实体类
 */
@Data
@AllArgsConstructor
public class Order {
    private Integer id;
    private String userId;//账户id
    private String commodityCode;//商品编码
    private Integer count;
    private Integer money;
}
```

dao层

```java
@Mapper
public interface OrderDao {
    /**
     * 创建订单
     *
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    void create(@Param("userId") String userId, @Param("commodityCode") String commodityCode, @Param("count") Integer count, @Param("money") Integer money);

}
```

mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.hyd.work.dao.OrderDao" >
    <!-- 查询映射结果集，此处用不到 -->
    <resultMap id="BaseResultMap" type="com.hyd.work.entity.Order" >
        <id column="id" property="id" jdbcType="INTEGER" />
        <result column="user_id" property="userId" jdbcType="VARCHAR" />
        <result column="commodity_code" property="commodityCode" jdbcType="VARCHAR" />
        <result column="count" property="count" jdbcType="INTEGER" />
        <result column="money" property="money" jdbcType="INTEGER" />
    </resultMap>
    <insert id="create">
        INSERT INTO order_tbl (`user_id`,`commodity_code`,`count`,`money`)
        VALUES(#{userId}, #{commodityCode}, #{count}, #{money});
    </insert>
</mapper>

```

service层

```java
public interface OrderService {
    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    void create(String userId, String commodityCode, Integer count, Integer money);
}
```

实现类

```java
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 创建订单
     * @param userId
     * @param commodityCode
     * @param count
     * @param money
     */
    @Override
    public void create(String userId, String commodityCode, Integer count, Integer money) {
        log.info("------->创建订单开始order中");
        orderDao.create(userId, commodityCode, count, money);
        log.info("------->创建订单结束order中");
    }

}
```

controller层

```java
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("create")
    public String create(@RequestParam("userId") String userId, @RequestParam("commodityCode")String commodityCode, @RequestParam("count")Integer count, @RequestParam("money")Integer money){
        orderService.create(userId, commodityCode, count, money);
        return "Create order success";
    }
}
```

启动类

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//使用seata进行数据源代理
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class,args);
    }
}
```



业务模块代码如下：

openfeign接口层，以AccountApi账户微服务接口为例：

需要`@FeignClient`注解，同时业务中存在多个Feign调用相同Service的接口，配置文件需要配置：`spring.main.allow-bean-definition-overriding=true`。

```java
package com.hyd.work.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "account-service")
public interface AccountApi {
    /**
     * 扣减账户余额
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping("/account/decrease")
    String decrease(@RequestParam("userId") String userId, @RequestParam("money") Integer money);

    /**
     * 查询指定用户的余额
     * @param userId
     * @return
     */
    @RequestMapping("/account/getMoneyByUserId")
    public int getMoneyByUserId(@RequestParam("userId") String userId);
}
```

service层实现类

此处发起事务，要在方法上添加`@GlobalTransactional`注解，同时除了事务之外，指定遇到异常也要回滚`rollbackFor = Exception.class`。

```java
package com.hyd.work.service.impl;

import com.hyd.work.feign.AccountApi;
import com.hyd.work.feign.OrderApi;
import com.hyd.work.feign.StorageApi;
import com.hyd.work.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private OrderApi orderApi;
    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;

    @GlobalTransactional(name = "seata-osa-group",rollbackFor = Exception.class)
    @Override
    public void purchase(String userId, String commodityCode, Integer count, Integer money) {
        log.info("------->交易开始");

        //远程方法 创建订单
        log.info("------->创建订单开始");
        orderApi.create(userId, commodityCode, count, money);
        log.info("------->创建订单结束");

        //远程方法 扣减库存
        log.info("------->扣减库存开始");
        storageApi.decrease(commodityCode,count);
        log.info("------->扣减库存结束");

        //远程方法 扣减账户余额
        log.info("------->扣减账户开始");
        accountApi.decrease(userId,money);
        log.info("------->扣减账户结束");

        //校验数据
        log.info("------->校验数据开始");
        if(!validData(userId,commodityCode)){
            throw new RuntimeException("账户余额或库存不足,执行回滚");
        }
        log.info("------->校验数据开始");

        log.info("------->交易结束");

    }

    @Override
    public boolean validData(String userId,String commodityCode) {
        if(accountApi.getMoneyByUserId(userId)<0){
            return false;
        }
        if(storageApi.getCountByCode(commodityCode)<0){
            return false;
        }
        return true;
    }
}
```

启动类

添加`@@EnableFeignClients`表明是feign客户端

```java
@SpringBootApplication
@EnableFeignClients
public class BusinessServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class,args);
    }
}
```



##### 测试

启动nacos，启动seata，启动3+1个服务

启动测试

busineess模块：

```
Initializing Global Transaction Clients ... 
Transaction Manager Client is initialized. applicationId[business-service] txServiceGroup[business_group]
Resource Manager is initialized. applicationId[business-service] txServiceGroup[business_group]
Global Transaction Clients are initialized. 
load RegistryProvider[Nacos] extension by class[io.seata.discovery.registry.nacos.NacosRegistryProvider]
will connect to 192.168.1.103:8091
will connect to 192.168.1.103:8091
NettyPool create channel to transactionRole:RMROLE,address:192.168.1.103:8091,msg:< RegisterRMRequest{resourceIds='null', applicationId='business-service', transactionServiceGroup='business_group'} >
NettyPool create channel to transactionRole:TMROLE,address:192.168.1.103:8091,msg:< RegisterTMRequest{applicationId='business-service', transactionServiceGroup='business_group'} >
io.seata.core.rpc.netty.RmRpcClient@7f23c47 msgId:1, future :io.seata.core.protocol.MessageFuture@41eb6d0d, body:version=1.0.0,extraData=null,identified=true,resultCode=null,msg=null
io.seata.core.rpc.netty.TmRpcClient@58fdde9f msgId:1, future :io.seata.core.protocol.MessageFuture@2063aead, body:version=1.0.0,extraData=null,identified=true,resultCode=null,msg=null
register RM success. server version:1.0.0,channel:[id: 0xe9af0e97, L:/192.168.1.103:55906 - R:/192.168.1.103:8091]
register success, cost 113 ms, version:1.0.0,role:TMROLE,channel:[id: 0x23f46ed4, L:/192.168.1.103:55907 - R:/192.168.1.103:8091]
register success, cost 120 ms, version:1.0.0,role:RMROLE,channel:[id: 0xe9af0e97, L:/192.168.1.103:55906 - R:/192.168.1.103:8091]
```

order模块：

```
Initializing Global Transaction Clients ... 
Transaction Manager Client is initialized. applicationId[order-service] txServiceGroup[order_group]
Resource Manager is initialized. applicationId[order-service] txServiceGroup[order_group]
Global Transaction Clients are initialized. 
will connect to 192.168.1.103:8091
RM will register :jdbc:mysql://localhost:3306/seata_order
NettyPool create channel to transactionRole:RMROLE,address:192.168.1.103:8091,msg:< RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_order', applicationId='order-service', transactionServiceGroup='order_group'} >
register RM success. server version:1.0.0,channel:[id: 0x9de67760, L:/192.168.1.103:55918 - R:/192.168.1.103:8091]
register success, cost 94 ms, version:1.0.0,role:RMROLE,channel:[id: 0x9de67760, L:/192.168.1.103:55918 - R:/192.168.1.103:8091]
will connect to 192.168.1.103:8091
NettyPool create channel to transactionRole:TMROLE,address:192.168.1.103:8091,msg:< RegisterTMRequest{applicationId='order-service', transactionServiceGroup='order_group'} >
register success, cost 5 ms, version:1.0.0,role:TMROLE,channel:[id: 0xc9ec150c, L:/192.168.1.103:55952 - R:/192.168.1.103:8091]
io.seata.core.rpc.netty.RmRpcClient@3d433124 msgId:2, body:UndoLogDeleteRequest{resourceId='jdbc:mysql://localhost:3306/seata_order', saveDays=7, branchType=AT}
```

storage模块：

```
Initializing Global Transaction Clients ... 
Transaction Manager Client is initialized. applicationId[storage-service] txServiceGroup[storage_group]
Resource Manager is initialized. applicationId[storage-service] txServiceGroup[storage_group]
Global Transaction Clients are initialized. 
will connect to 192.168.1.103:8091
RM will register :jdbc:mysql://localhost:3306/seata_storage
NettyPool create channel to transactionRole:RMROLE,address:192.168.1.103:8091,msg:< RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_storage', applicationId='storage-service', transactionServiceGroup='storage_group'} >
register RM success. server version:1.0.0,channel:[id: 0xbeeb8cbe, L:/192.168.1.103:55936 - R:/192.168.1.103:8091]
register success, cost 42 ms, version:1.0.0,role:RMROLE,channel:[id: 0xbeeb8cbe, L:/192.168.1.103:55936 - R:/192.168.1.103:8091]
will connect to 192.168.1.103:8091
NettyPool create channel to transactionRole:TMROLE,address:192.168.1.103:8091,msg:< RegisterTMRequest{applicationId='storage-service', transactionServiceGroup='storage_group'} >
register success, cost 4 ms, version:1.0.0,role:TMROLE,channel:[id: 0xf3cb6365, L:/192.168.1.103:55962 - R:/192.168.1.103:8091]
io.seata.core.rpc.netty.RmRpcClient@86d897a msgId:3, body:UndoLogDeleteRequest{resourceId='jdbc:mysql://localhost:3306/seata_storage', saveDays=7, branchType=AT}
```

account模块：

```
Initializing Global Transaction Clients ... 
Transaction Manager Client is initialized. applicationId[account-service] txServiceGroup[account_group]
Resource Manager is initialized. applicationId[account-service] txServiceGroup[account_group]
Global Transaction Clients are initialized. 
will connect to 192.168.1.103:8091
RM will register :jdbc:mysql://localhost:3306/seata_account
NettyPool create channel to transactionRole:RMROLE,address:192.168.1.103:8091,msg:< RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_account', applicationId='account-service', transactionServiceGroup='account_group'} >
register RM success. server version:1.0.0,channel:[id: 0x6b005bd2, L:/192.168.1.103:55949 - R:/192.168.1.103:8091]
register success, cost 40 ms, version:1.0.0,role:RMROLE,channel:[id: 0x6b005bd2, L:/192.168.1.103:55949 - R:/192.168.1.103:8091]
will connect to 192.168.1.103:8091
NettyPool create channel to transactionRole:TMROLE,address:192.168.1.103:8091,msg:< RegisterTMRequest{applicationId='account-service', transactionServiceGroup='account_group'} >
register success, cost 4 ms, version:1.0.0,role:TMROLE,channel:[id: 0xe68cf540, L:/192.168.1.103:55964 - R:/192.168.1.103:8091]
io.seata.core.rpc.netty.RmRpcClient@7eec2833 msgId:1, body:UndoLogDeleteRequest{resourceId='jdbc:mysql://localhost:3306/seata_account', saveDays=7, branchType=AT}
```

seata-server日志：

```
-checkAuth
for client:192.168.1.103:55907,vgroup:business_group,applicationId:business-service
-rm register success,message:RegisterRMRequest{resourceIds='null', applicationId='business-service', transactionServiceGroup='business_group'},channel:[id: 0xb11993e9, L:/192.168.1.103:8091 - R:/192.168.1.103:55906]
-rm register success,message:RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_order', applicationId='order-service', transactionServiceGroup='order_group'},channel:[id: 0x53167139, L:/192.168.1.103:8091 - R:/192.168.1.103:55918]
-rm register success,message:RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_storage', applicationId='storage-service', transactionServiceGroup='storage_group'},channel:[id: 0x00eed05d, L:/192.168.1.103:8091 - R:/192.168.1.103:55936]
-rm register success,message:RegisterRMRequest{resourceIds='jdbc:mysql://localhost:3306/seata_account', applicationId='account-service', transactionServiceGroup='account_group'},channel:[id: 0xf1f8b4d4, L:/192.168.1.103:8091 - R:/192.168.1.103:55949]
-checkAuth
for client:192.168.1.103:55952,vgroup:order_group,applicationId:order-service
-checkAuth
for client:192.168.1.103:55962,vgroup:storage_group,applicationId:storage-service
-checkAuth
for client:192.168.1.103:55964,vgroup:account_group,applicationId:account-service
```

可以看到成功注册日志。



测试正常调用

```
使用seata-all，正常调用
curl -X GET http://localhost/business/purchase?userId=user-01&commodityCode=product-1&count=1&money=1



# business-service模块
2021-06-06 08:35:57.765  INFO 5616 --- [p-nio-80-exec-5] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[null]
2021-06-06 08:35:57.766 DEBUG 5616 --- [p-nio-80-exec-5] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: timeout=60000,transactionName=seata-osa-group
2021-06-06 08:35:57.767 DEBUG 5616 --- [geSend_TMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage timeout=60000,transactionName=seata-osa-group
, channel:[id: 0xd65e8458, L:/192.168.1.103:62800 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.771 DEBUG 5616 --- [p-nio-80-exec-5] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.771  INFO 5616 --- [p-nio-80-exec-5] i.seata.tm.api.DefaultGlobalTransaction  : Begin new global transaction [192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.771  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->交易开始
2021-06-06 08:35:57.771  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->创建订单开始
2021-06-06 08:35:57.772  INFO 5616 --- [p-nio-80-exec-5] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666506] to request Header
2021-06-06 08:35:57.798  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->创建订单结束
2021-06-06 08:35:57.798  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减库存开始
2021-06-06 08:35:57.798  INFO 5616 --- [p-nio-80-exec-5] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666506] to request Header
2021-06-06 08:35:57.829  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减库存结束
2021-06-06 08:35:57.829  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减账户开始
2021-06-06 08:35:57.830  INFO 5616 --- [p-nio-80-exec-5] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666506] to request Header
2021-06-06 08:35:57.860  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减账户结束
2021-06-06 08:35:57.860  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->校验数据开始
2021-06-06 08:35:57.860  INFO 5616 --- [p-nio-80-exec-5] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666506] to request Header
2021-06-06 08:35:57.865  INFO 5616 --- [p-nio-80-exec-5] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666506] to request Header
2021-06-06 08:35:57.870  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->校验数据开始
2021-06-06 08:35:57.871  INFO 5616 --- [p-nio-80-exec-5] c.h.w.service.impl.BusinessServiceImpl   : ------->交易结束
2021-06-06 08:35:57.871 DEBUG 5616 --- [p-nio-80-exec-5] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,extraData=null
2021-06-06 08:35:57.871 DEBUG 5616 --- [geSend_TMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,extraData=null
, channel:[id: 0xd65e8458, L:/192.168.1.103:62800 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.883 DEBUG 5616 --- [p-nio-80-exec-5] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.883  INFO 5616 --- [p-nio-80-exec-5] i.seata.tm.api.DefaultGlobalTransaction  : [192.168.1.103:8091:2076666506] commit status: Committed



# order-service模块
2021-06-06 08:35:57.774  INFO 11712 --- [nio-8001-exec-2] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.774 DEBUG 11712 --- [nio-8001-exec-2] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.774  INFO 11712 --- [nio-8001-exec-2] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666506] to RootContext
2021-06-06 08:35:57.775  INFO 11712 --- [nio-8001-exec-2] c.h.work.service.impl.OrderServiceImpl   : ------->创建订单开始order中
2021-06-06 08:35:57.779 DEBUG 11712 --- [nio-8001-exec-2] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:31
2021-06-06 08:35:57.779 DEBUG 11712 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:31
, channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.786 DEBUG 11712 --- [nio-8001-exec-2] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666506","branchId":2076666508,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"INSERT","tableName":"order_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords$EmptyTableRecords","tableName":"order_tbl","rows":["java.util.ArrayList",[]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"order_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":31},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"user_id","keyType":"NULL","type":12,"value":"user-01"},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"commodity_code","keyType":"NULL","type":12,"value":"product-1"},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":1}]]}]]}}]]}
2021-06-06 08:35:57.789 DEBUG 11712 --- [nio-8001-exec-2] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchId=2076666508,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:35:57.789 DEBUG 11712 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666508,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.796  INFO 11712 --- [nio-8001-exec-2] c.h.work.service.impl.OrderServiceImpl   : ------->创建订单结束order中
2021-06-06 08:35:57.797 DEBUG 11712 --- [nio-8001-exec-2] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.798  INFO 11712 --- [nio-8001-exec-2] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666506] from RootContext
2021-06-06 08:35:58.337 DEBUG 11712 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@75190b4e msgId:7, body:xid=192.168.1.103:8091:2076666506,branchId=2076666508,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,applicationData=null
2021-06-06 08:35:58.337  INFO 11712 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666506,branchId=2076666508,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,applicationData=null
2021-06-06 08:35:58.337  INFO 11712 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch committing: 192.168.1.103:8091:2076666506 2076666508 jdbc:mysql://localhost:3306/seata_order null
2021-06-06 08:35:58.337  INFO 11712 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch commit result: PhaseTwo_Committed
2021-06-06 08:35:58.337 DEBUG 11712 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666506,branchId=2076666508,branchStatus=PhaseTwo_Committed,result code =Success,getMsg =null,channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091]
2021-06-06 08:35:58.543 DEBUG 11712 --- [  AsyncWorker_1] i.s.r.d.undo.AbstractUndoLogManager      : batch delete undo log size 1



# storage-service模块
2021-06-06 08:35:57.800  INFO 4508 --- [nio-8002-exec-4] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.801 DEBUG 4508 --- [nio-8002-exec-4] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.801  INFO 4508 --- [nio-8002-exec-4] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666506] to RootContext
2021-06-06 08:35:57.801  INFO 4508 --- [nio-8002-exec-4] c.h.w.service.impl.StorageServiceImpl    : ------->扣减库存开始storage中
2021-06-06 08:35:57.807 DEBUG 4508 --- [nio-8002-exec-4] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:1
2021-06-06 08:35:57.808 DEBUG 4508 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:1
, channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.816 DEBUG 4508 --- [nio-8002-exec-4] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666506","branchId":2076666511,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"UPDATE","tableName":"storage_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"storage_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":99}]]}]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"storage_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":98}]]}]]}}]]}
2021-06-06 08:35:57.820 DEBUG 4508 --- [nio-8002-exec-4] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchId=2076666511,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:35:57.820 DEBUG 4508 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666511,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.828  INFO 4508 --- [nio-8002-exec-4] c.h.w.service.impl.StorageServiceImpl    : ------->扣减库存结束storage中
2021-06-06 08:35:57.829 DEBUG 4508 --- [nio-8002-exec-4] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.829  INFO 4508 --- [nio-8002-exec-4] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666506] from RootContext
2021-06-06 08:35:57.867  INFO 4508 --- [nio-8002-exec-5] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.867 DEBUG 4508 --- [nio-8002-exec-5] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.867  INFO 4508 --- [nio-8002-exec-5] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666506] to RootContext
2021-06-06 08:35:57.870 DEBUG 4508 --- [nio-8002-exec-5] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.870  INFO 4508 --- [nio-8002-exec-5] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666506] from RootContext
2021-06-06 08:35:58.337 DEBUG 4508 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@7d9975ee msgId:8, body:xid=192.168.1.103:8091:2076666506,branchId=2076666511,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,applicationData=null
2021-06-06 08:35:58.337  INFO 4508 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666506,branchId=2076666511,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,applicationData=null
2021-06-06 08:35:58.337  INFO 4508 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch committing: 192.168.1.103:8091:2076666506 2076666511 jdbc:mysql://localhost:3306/seata_storage null
2021-06-06 08:35:58.337  INFO 4508 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch commit result: PhaseTwo_Committed
2021-06-06 08:35:58.337 DEBUG 4508 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666506,branchId=2076666511,branchStatus=PhaseTwo_Committed,result code =Success,getMsg =null,channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091]
2021-06-06 08:35:58.902 DEBUG 4508 --- [  AsyncWorker_1] i.s.r.d.undo.AbstractUndoLogManager      : batch delete undo log size 1



# account-service模块
2021-06-06 08:35:57.832  INFO 19292 --- [nio-8003-exec-3] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.832 DEBUG 19292 --- [nio-8003-exec-3] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.832  INFO 19292 --- [nio-8003-exec-3] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666506] to RootContext
2021-06-06 08:35:57.832  INFO 19292 --- [nio-8003-exec-3] c.h.w.service.impl.AccountServiceImpl    : ------->扣减账户开始account中
2021-06-06 08:35:57.839 DEBUG 19292 --- [nio-8003-exec-3] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
2021-06-06 08:35:57.840 DEBUG 19292 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
, channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.848 DEBUG 19292 --- [nio-8003-exec-3] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666506","branchId":2076666514,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"UPDATE","tableName":"account_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"account_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":99}]]}]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"account_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":98}]]}]]}}]]}
2021-06-06 08:35:57.851 DEBUG 19292 --- [nio-8003-exec-3] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666506,branchId=2076666514,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:35:57.851 DEBUG 19292 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666514,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:35:57.857  INFO 19292 --- [nio-8003-exec-3] c.h.w.service.impl.AccountServiceImpl    : ------->扣减账户结束account中
2021-06-06 08:35:57.860 DEBUG 19292 --- [nio-8003-exec-3] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.860  INFO 19292 --- [nio-8003-exec-3] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666506] from RootContext
2021-06-06 08:35:57.862  INFO 19292 --- [nio-8003-exec-4] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666506]
2021-06-06 08:35:57.862 DEBUG 19292 --- [nio-8003-exec-4] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.862  INFO 19292 --- [nio-8003-exec-4] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666506] to RootContext
2021-06-06 08:35:57.865 DEBUG 19292 --- [nio-8003-exec-4] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666506 
2021-06-06 08:35:57.865  INFO 19292 --- [nio-8003-exec-4] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666506] from RootContext
2021-06-06 08:35:58.353 DEBUG 19292 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@5c773314 msgId:9, body:xid=192.168.1.103:8091:2076666506,branchId=2076666514,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,applicationData=null
2021-06-06 08:35:58.353  INFO 19292 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666506,branchId=2076666514,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,applicationData=null
2021-06-06 08:35:58.353  INFO 19292 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch committing: 192.168.1.103:8091:2076666506 2076666514 jdbc:mysql://localhost:3306/seata_account null
2021-06-06 08:35:58.353  INFO 19292 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch commit result: PhaseTwo_Committed
2021-06-06 08:35:58.353 DEBUG 19292 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666506,branchId=2076666514,branchStatus=PhaseTwo_Committed,result code =Success,getMsg =null,channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091]
2021-06-06 08:35:59.093 DEBUG 19292 --- [  AsyncWorker_1] i.s.r.d.undo.AbstractUndoLogManager      : batch delete undo log size 1



# seata-server日志
2021-06-06 08:35:57.768 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage timeout=60000,transactionName=seata-osa-group
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.770 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.begin:154 -Successfully begin global transaction xid = 192.168.1.103:8091:2076666506
2021-06-06 08:35:57.780 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:31
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.785 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666506, branchId = 2076666508
2021-06-06 08:35:57.790 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666508,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.794 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666506, branchId = 2076666508
2021-06-06 08:35:57.809 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:1
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.814 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666506, branchId = 2076666511
2021-06-06 08:35:57.821 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666511,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.825 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666506, branchId = 2076666511
2021-06-06 08:35:57.841 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.846 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666506, branchId = 2076666514
2021-06-06 08:35:57.852 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,branchId=2076666514,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:57.855 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666506, branchId = 2076666514
2021-06-06 08:35:57.873 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666506,extraData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:35:58.353 INFO [AsyncCommitting_1]io.seata.server.coordinator.DefaultCore.doGlobalCommit:316 -Global[192.168.1.103:8091:2076666506] committing is successfully done.
```

异常调用

```
使用seata-all，异常调用
curl -X GET http://localhost/business/purchase?userId=user-01&commodityCode=product-2&count=1&money=1



# business-service模块
2021-06-06 08:43:12.631  INFO 5616 --- [p-nio-80-exec-7] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[null]
2021-06-06 08:43:12.633 DEBUG 5616 --- [p-nio-80-exec-7] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: timeout=60000,transactionName=seata-osa-group
2021-06-06 08:43:12.633 DEBUG 5616 --- [geSend_TMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage timeout=60000,transactionName=seata-osa-group
, channel:[id: 0xd65e8458, L:/192.168.1.103:62800 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.637 DEBUG 5616 --- [p-nio-80-exec-7] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.638  INFO 5616 --- [p-nio-80-exec-7] i.seata.tm.api.DefaultGlobalTransaction  : Begin new global transaction [192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.638  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->交易开始
2021-06-06 08:43:12.638  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->创建订单开始
2021-06-06 08:43:12.638  INFO 5616 --- [p-nio-80-exec-7] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666522] to request Header
2021-06-06 08:43:12.663  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->创建订单结束
2021-06-06 08:43:12.663  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减库存开始
2021-06-06 08:43:12.664  INFO 5616 --- [p-nio-80-exec-7] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666522] to request Header
2021-06-06 08:43:12.689  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减库存结束
2021-06-06 08:43:12.689  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减账户开始
2021-06-06 08:43:12.690  INFO 5616 --- [p-nio-80-exec-7] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666522] to request Header
2021-06-06 08:43:12.716  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->扣减账户结束
2021-06-06 08:43:12.716  INFO 5616 --- [p-nio-80-exec-7] c.h.w.service.impl.BusinessServiceImpl   : ------->校验数据开始
2021-06-06 08:43:12.717  INFO 5616 --- [p-nio-80-exec-7] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666522] to request Header
2021-06-06 08:43:12.723  INFO 5616 --- [p-nio-80-exec-7] c.h.w.interceptor.SeataXidInterceptor    : set xid[192.168.1.103:8091:2076666522] to request Header
2021-06-06 08:43:12.730 DEBUG 5616 --- [p-nio-80-exec-7] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,extraData=null
2021-06-06 08:43:12.730 DEBUG 5616 --- [geSend_TMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,extraData=null
, channel:[id: 0xd65e8458, L:/192.168.1.103:62800 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.981 DEBUG 5616 --- [p-nio-80-exec-7] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.981  INFO 5616 --- [p-nio-80-exec-7] i.seata.tm.api.DefaultGlobalTransaction  : [192.168.1.103:8091:2076666522] rollback status: Rollbacked



# order-service模块
2021-06-06 08:43:12.640  INFO 11712 --- [nio-8001-exec-4] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.641 DEBUG 11712 --- [nio-8001-exec-4] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.641  INFO 11712 --- [nio-8001-exec-4] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666522] to RootContext
2021-06-06 08:43:12.641  INFO 11712 --- [nio-8001-exec-4] c.h.work.service.impl.OrderServiceImpl   : ------->创建订单开始order中
2021-06-06 08:43:12.645 DEBUG 11712 --- [nio-8001-exec-4] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:32
2021-06-06 08:43:12.645 DEBUG 11712 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:32
, channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.653 DEBUG 11712 --- [nio-8001-exec-4] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666522","branchId":2076666524,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"INSERT","tableName":"order_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords$EmptyTableRecords","tableName":"order_tbl","rows":["java.util.ArrayList",[]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"order_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":32},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"user_id","keyType":"NULL","type":12,"value":"user-01"},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"commodity_code","keyType":"NULL","type":12,"value":"product-2"},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":1}]]}]]}}]]}
2021-06-06 08:43:12.657 DEBUG 11712 --- [nio-8001-exec-4] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchId=2076666524,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:43:12.657 DEBUG 11712 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666524,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.662  INFO 11712 --- [nio-8001-exec-4] c.h.work.service.impl.OrderServiceImpl   : ------->创建订单结束order中
2021-06-06 08:43:12.663 DEBUG 11712 --- [nio-8001-exec-4] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.663  INFO 11712 --- [nio-8001-exec-4] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666522] from RootContext
2021-06-06 08:43:12.894 DEBUG 11712 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@75190b4e msgId:12, body:xid=192.168.1.103:8091:2076666522,branchId=2076666524,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,applicationData=null
2021-06-06 08:43:12.894  INFO 11712 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666522,branchId=2076666524,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,applicationData=null
2021-06-06 08:43:12.895  INFO 11712 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacking: 192.168.1.103:8091:2076666522 2076666524 jdbc:mysql://localhost:3306/seata_order
2021-06-06 08:43:12.964  WARN 11712 --- [nfigOperate_2_2] io.seata.config.FileConfiguration        : Could not found property client.undo.data.validation, try to use default value instead.
2021-06-06 08:43:12.969  INFO 11712 --- [tch_RMROLE_1_32] i.s.r.d.undo.AbstractUndoLogManager      : xid 192.168.1.103:8091:2076666522 branch 2076666524, undo_log deleted with GlobalFinished
2021-06-06 08:43:12.969  INFO 11712 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacked result: PhaseTwo_Rollbacked
2021-06-06 08:43:12.969 DEBUG 11712 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : branch rollback result:xid=192.168.1.103:8091:2076666522,branchId=2076666524,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null
2021-06-06 08:43:12.969 DEBUG 11712 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666522,branchId=2076666524,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null,channel:[id: 0xefe82473, L:/192.168.1.103:62926 - R:/192.168.1.103:8091]



# storage-service模块
2021-06-06 08:43:12.666  INFO 4508 --- [nio-8002-exec-8] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.666 DEBUG 4508 --- [nio-8002-exec-8] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.666  INFO 4508 --- [nio-8002-exec-8] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666522] to RootContext
2021-06-06 08:43:12.666  INFO 4508 --- [nio-8002-exec-8] c.h.w.service.impl.StorageServiceImpl    : ------->扣减库存开始storage中
2021-06-06 08:43:12.671 DEBUG 4508 --- [nio-8002-exec-8] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:2
2021-06-06 08:43:12.671 DEBUG 4508 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:2
, channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.678 DEBUG 4508 --- [nio-8002-exec-8] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666522","branchId":2076666527,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"UPDATE","tableName":"storage_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"storage_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":2},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":0}]]}]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"storage_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":2},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"count","keyType":"NULL","type":4,"value":-1}]]}]]}}]]}
2021-06-06 08:43:12.681 DEBUG 4508 --- [nio-8002-exec-8] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchId=2076666527,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:43:12.681 DEBUG 4508 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666527,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.688  INFO 4508 --- [nio-8002-exec-8] c.h.w.service.impl.StorageServiceImpl    : ------->扣减库存结束storage中
2021-06-06 08:43:12.689 DEBUG 4508 --- [nio-8002-exec-8] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.689  INFO 4508 --- [nio-8002-exec-8] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666522] from RootContext
2021-06-06 08:43:12.725  INFO 4508 --- [nio-8002-exec-7] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.725 DEBUG 4508 --- [nio-8002-exec-7] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.725  INFO 4508 --- [nio-8002-exec-7] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666522] to RootContext
2021-06-06 08:43:12.727 DEBUG 4508 --- [nio-8002-exec-7] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.727  INFO 4508 --- [nio-8002-exec-7] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666522] from RootContext
2021-06-06 08:43:12.819 DEBUG 4508 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@7d9975ee msgId:11, body:xid=192.168.1.103:8091:2076666522,branchId=2076666527,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,applicationData=null
2021-06-06 08:43:12.820  INFO 4508 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666522,branchId=2076666527,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,applicationData=null
2021-06-06 08:43:12.820  INFO 4508 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacking: 192.168.1.103:8091:2076666522 2076666527 jdbc:mysql://localhost:3306/seata_storage
2021-06-06 08:43:12.880  WARN 4508 --- [nfigOperate_2_2] io.seata.config.FileConfiguration        : Could not found property client.undo.data.validation, try to use default value instead.
2021-06-06 08:43:12.885  INFO 4508 --- [tch_RMROLE_1_32] i.s.r.d.undo.AbstractUndoLogManager      : xid 192.168.1.103:8091:2076666522 branch 2076666527, undo_log deleted with GlobalFinished
2021-06-06 08:43:12.886  INFO 4508 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacked result: PhaseTwo_Rollbacked
2021-06-06 08:43:12.886 DEBUG 4508 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : branch rollback result:xid=192.168.1.103:8091:2076666522,branchId=2076666527,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null
2021-06-06 08:43:12.886 DEBUG 4508 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666522,branchId=2076666527,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null,channel:[id: 0x565684b6, L:/192.168.1.103:62828 - R:/192.168.1.103:8091]



# account-service模块
2021-06-06 08:43:12.692  INFO 19292 --- [nio-8003-exec-6] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.692 DEBUG 19292 --- [nio-8003-exec-6] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.692  INFO 19292 --- [nio-8003-exec-6] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666522] to RootContext
2021-06-06 08:43:12.693  INFO 19292 --- [nio-8003-exec-6] c.h.w.service.impl.AccountServiceImpl    : ------->扣减账户开始account中
2021-06-06 08:43:12.697 DEBUG 19292 --- [nio-8003-exec-6] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
2021-06-06 08:43:12.697 DEBUG 19292 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
, channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.705 DEBUG 19292 --- [nio-8003-exec-6] i.s.r.d.undo.AbstractUndoLogManager      : Flushing UNDO LOG: {"@class":"io.seata.rm.datasource.undo.BranchUndoLog","xid":"192.168.1.103:8091:2076666522","branchId":2076666530,"sqlUndoLogs":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.undo.SQLUndoLog","sqlType":"UPDATE","tableName":"account_tbl","beforeImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"account_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":98}]]}]]},"afterImage":{"@class":"io.seata.rm.datasource.sql.struct.TableRecords","tableName":"account_tbl","rows":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Row","fields":["java.util.ArrayList",[{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"id","keyType":"PrimaryKey","type":4,"value":1},{"@class":"io.seata.rm.datasource.sql.struct.Field","name":"money","keyType":"NULL","type":4,"value":97}]]}]]}}]]}
2021-06-06 08:43:12.708 DEBUG 19292 --- [nio-8003-exec-6] i.s.core.rpc.netty.AbstractRpcRemoting   : offer message: xid=192.168.1.103:8091:2076666522,branchId=2076666530,resourceId=null,status=PhaseOne_Done,applicationData=null
2021-06-06 08:43:12.708 DEBUG 19292 --- [geSend_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : write message:SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666530,resourceId=null,status=PhaseOne_Done,applicationData=null
, channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091],active?true,writable?true,isopen?true
2021-06-06 08:43:12.714  INFO 19292 --- [nio-8003-exec-6] c.h.w.service.impl.AccountServiceImpl    : ------->扣减账户结束account中
2021-06-06 08:43:12.715 DEBUG 19292 --- [nio-8003-exec-6] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.715  INFO 19292 --- [nio-8003-exec-6] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666522] from RootContext
2021-06-06 08:43:12.719  INFO 19292 --- [nio-8003-exec-7] com.hyd.work.filter.SeataXidFilter       : xid in HttpServletRequest is:[192.168.1.103:8091:2076666522]
2021-06-06 08:43:12.719 DEBUG 19292 --- [nio-8003-exec-7] io.seata.core.context.RootContext        : bind 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.719  INFO 19292 --- [nio-8003-exec-7] com.hyd.work.filter.SeataXidFilter       : bind xid[192.168.1.103:8091:2076666522] to RootContext
2021-06-06 08:43:12.722 DEBUG 19292 --- [nio-8003-exec-7] io.seata.core.context.RootContext        : unbind 192.168.1.103:8091:2076666522 
2021-06-06 08:43:12.722  INFO 19292 --- [nio-8003-exec-7] com.hyd.work.filter.SeataXidFilter       : unbind xid[192.168.1.103:8091:2076666522] from RootContext
2021-06-06 08:43:12.741 DEBUG 19292 --- [lector_RMROLE_1] i.s.core.rpc.netty.AbstractRpcRemoting   : io.seata.core.rpc.netty.RmRpcClient@5c773314 msgId:10, body:xid=192.168.1.103:8091:2076666522,branchId=2076666530,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,applicationData=null
2021-06-06 08:43:12.742  INFO 19292 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : onMessage:xid=192.168.1.103:8091:2076666522,branchId=2076666530,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,applicationData=null
2021-06-06 08:43:12.743  INFO 19292 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacking: 192.168.1.103:8091:2076666522 2076666530 jdbc:mysql://localhost:3306/seata_account
2021-06-06 08:43:12.804  WARN 19292 --- [nfigOperate_2_2] io.seata.config.FileConfiguration        : Could not found property client.undo.data.validation, try to use default value instead.
2021-06-06 08:43:12.809  INFO 19292 --- [tch_RMROLE_1_32] i.s.r.d.undo.AbstractUndoLogManager      : xid 192.168.1.103:8091:2076666522 branch 2076666530, undo_log deleted with GlobalFinished
2021-06-06 08:43:12.810  INFO 19292 --- [tch_RMROLE_1_32] io.seata.rm.AbstractRMHandler            : Branch Rollbacked result: PhaseTwo_Rollbacked
2021-06-06 08:43:12.810 DEBUG 19292 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.RmMessageListener     : branch rollback result:xid=192.168.1.103:8091:2076666522,branchId=2076666530,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null
2021-06-06 08:43:12.810 DEBUG 19292 --- [tch_RMROLE_1_32] i.s.core.rpc.netty.AbstractRpcRemoting   : send response:xid=192.168.1.103:8091:2076666522,branchId=2076666530,branchStatus=PhaseTwo_Rollbacked,result code =Success,getMsg =null,channel:[id: 0x2980bd8f, L:/192.168.1.103:62877 - R:/192.168.1.103:8091]



# seata-server日志
2021-06-06 08:43:12.634 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage timeout=60000,transactionName=seata-osa-group
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.636 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.begin:154 -Successfully begin global transaction xid = 192.168.1.103:8091:2076666522
2021-06-06 08:43:12.646 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_order,lockKey=order_tbl:32
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.651 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666522, branchId = 2076666524
2021-06-06 08:43:12.658 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666524,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.661 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666522, branchId = 2076666524
2021-06-06 08:43:12.672 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_storage,lockKey=storage_tbl:2
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.677 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666522, branchId = 2076666527
2021-06-06 08:43:12.682 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666527,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.686 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666522, branchId = 2076666527
2021-06-06 08:43:12.698 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchType=AT,resourceId=jdbc:mysql://localhost:3306/seata_account,lockKey=account_tbl:1
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.703 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.lambda$branchRegister$0:98 -Successfully
register branch xid = 192.168.1.103:8091:2076666522, branchId = 2076666530
2021-06-06 08:43:12.709 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,branchId=2076666530,resourceId=null,status=PhaseOne_Done,applicationData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.712 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.branchReport:126 -Successfully branch report xid = 192.168.1.103:8091:2076666522, branchId = 2076666530
2021-06-06 08:43:12.733 INFO [batchLoggerPrint_1]io.seata.core.rpc.DefaultServerMessageListenerImpl.run:205 -SeataMergeMessage xid=192.168.1.103:8091:2076666522,extraData=null
,clientIp:192.168.1.103,vgroup:projectA
2021-06-06 08:43:12.816 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.doGlobalRollback:418 -Successfully rollback branch xid=192.168.1.103:8091:2076666522 branchId=2076666530
2021-06-06 08:43:12.890 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.doGlobalRollback:418 -Successfully rollback branch xid=192.168.1.103:8091:2076666522 branchId=2076666527
2021-06-06 08:43:12.974 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.doGlobalRollback:418 -Successfully rollback branch xid=192.168.1.103:8091:2076666522 branchId=2076666524
2021-06-06 08:43:12.978 INFO [ServerHandlerThread_1_500]io.seata.server.coordinator.DefaultCore.doGlobalRollback:465 -Successfully rollback global, xid = 192.168.1.103:8091:2076666522
```



可以看到日志全局事务提交，回滚的记录，同时看到XID在各个微服务之间传递，发生异常以后，可以回滚，数据库恢复之前状态。



##### 改造项目

使用spring-cloud-starter-alibaba-seata依赖，自动配置数据源代理，自动配置GlobalTransactionScanner，将conf配置转移到yaml里。

取消DataSourceProxyConfig配置类手动配置数据源代理，去掉启动类注解属性该`(exclude = {DataSourceAutoConfiguration.class)`；

取消自定义GlobalTransactionScanner配置初始化；

将seata client端conf配置文件内容配到application.yaml。注意：disableGlobalTransaction=false需要配置到conf，yaml里配置目前版本有bug不生效)

application.yaml

```yaml
# 使用yaml配置 seata conf配置文件 -----start----|
seata:
  client:
    support:
      spring:
        datasource-autoproxy: true # 数据源自动代理 默认 true
  registry:
    nacos:
      server-addr: localhost:8848
      namespace: ""
      cluster: default
  tx-service-group: storage_group
  service:
    vgroup-mapping: default
    grouplist: 127.0.0.1:8091
    enable-degrade: false
#    disable-global-transaction: false # 默认 false
# 使用yaml配置 seata conf配置文件 -----end-----|
```

file.conf

```
service {
  disableGlobalTransaction = false
}
```

测试结果略



---

## 【分布式】全局唯一ID生成策略

为什么需要分布式全局唯一ID以及分布式ID的业务需求？集群高并发情况下如何保证分布式唯一全局Id生成？

在复杂分布式系统中，往往需婴对大量的数据和消息进行唯一标识，如在美团点评的金融、支付、餐饮、酒店，猫眼电影等产品的系统中数据日渐增长，对数据分库分表后需要有一个唯一ID来标识一条数据或消息。特别一点的如订单、骑手、优惠券也都雷要有唯一ID做标识。此时一个能够生成全局唯一ID的系统是非常必要的。

ID生成规则部分硬性要求

全局唯一：不能出现重复的ID号，既然是唯一-标识，这是最基本的要求

趋势递增：在MySQL的InnoDB引擎中使用的是聚集索引，由于多数RDBMS使用Btree的数据结构来存储索引数据，在主键的选择上面我们应该尽量使用有序的主键保证写入性能。

单调递增：保证下一个ID一定大于上一个ID，例如事务版本号、IM增量消息、排序等特殊需求

信息安全：如果ID是连续的，恶意用户的扒取工作就非常容易做了，直接按照顺序下载指定URL即可。如果是订单号就更危险了，竞对可以直接知道我们一天的单量。所以在一些应用场景下，需要ID无规则不规则，让竞争对手否好猜。

含时间戳：这样就能够在开发中快速了解这个分布式id的生成时间。

ID号生成系统的可用性要求

高可用：发一个获取分布式ID的请求，服务器就要保证99.999%的情况下给我创建一个唯一分布式ID。

低延迟：发一个获取分布式ID的请求，服务器就要快，极速。

高QPS：假如并发一口气10万个创建分布式ID请求同时杀过来，服务器要顶的住且一下子成功创建10万个分布式ID。

一般通用方案

UUID

UUID(Universally Unique ldentifer)的标准型式包含32个16进制数字，以连了号分为五段，形式为8-4-4-4-12的36个字符， 示例：550e8400-e29b-41d4-a716-446655440000

性能非常高：本地生成，没有网络消耗

如果只是考虑唯一性，那就选用它吧

但是，入数据库性能差

为什么无序的UUID会导致入库性能变差呢？

无序，无法预测他的生成顺序，不能生成递增有序的数字。首先分布式ID一般都会作为主键， 但是安装MySQL官方推荐主键要尽量越短越好，UUID每一个都很长，所以不是很推荐。

主键，ID作为主键时在特定的环境会存在一些问题。比如做DB主键的场景下，UUID就非常不适用MySQL官方有明确的建议主键要尽量越短越好36个字符长度的UUID不符合要求。

索引，既然分布式ID是主键，然后主键是包含索引的，然后MySQL的索引是通过B+树来实现的，每一次新的UUID数据的插入，为了查询的优化，都会对索引底层的B+树进行修改，因为UUID数据是无序的，所以每一次UUID数据的插入都会对主键地械的B+树进行很大的修改，这一点很不好。 插入完全无序，不但会导致一-些中间节点产生分裂，也会白白创造出很多不饱和的节点，这样大大降低了数据库插入的性能。



数据库自增主键

单机

在单机里面，数据库的自增ID机制的主要原理是：数据库自增ID和MySQL数据库的replace into实现的。

REPLACE INTO的含义是插入一条记录，如果表中唯一索引的值遇到冲突，则替换老数据。

这里的replace into跟inset功能类似，不同点在于：replace into首先尝试插入数据列表中，如果发现表中已经有此行数据（根据主键或唯一索引判断）则先删除，再插入。否则直接插入新数据。



集群分布式

那数据库自增ID机制适合作分布式ID吗？答案是不太适合

1：系统水平扩展比较困难，比如定义好了步长和机器台数之后，如果要添加机器该怎么做？假设现在只有一台机器发号是1，2，3，4，5（步长是1），这
个时候需要扩容机器一台。可以这样做：把第二台机器的初始值设置得比第一台超过很多，貌似还好，现在想象一下如果我们线上有100台机器，这
个时候要扩容该怎么做？简直是噩梦，所以系统水平扩展方案复杂难以实现。

2：数据库压力还是很大，每次获取ID都得读写一次数据库， 非常影响性能，不符合分布式ID里面的延迟低和要高QPS的规则（在高并发下，如果都去数据库里面获取id，那是非常影响性能的）

基于Redis生成全局ID策略

因为Redis是单线的天生保证原子性，可以使用原子操作INCR和INCRBY来实现

注意：在Redis集群情况下，同样和MySQL一样需要设置不同的增长步长，同时key一定要设置有效期可以使用Redis集群来获取更高的吞吐量。

假如一个集群中有5台Redis。可以初始化每台Redis的值分别是1,2,3,4,5，然后步长都是5。

各个Redis生成的ID为:

A：1, 6, 11, 16, 21
B：2, 7 , 12, 17, 22
C：3, 8, 13, 18, 23
D：4, 9, 14, 19, 24
E：5, 10, 15, 20, 25





Twitter的分布式自增ID算法snowflake

概述

Twitter的snowflake解决了这种需求，最初Twitter把存储系统从MySQL迁移到Cassandra（由Facebook开发一套开源分布式NoSQL数据库系统）。因为Cassandra没有顺序ID生成机制，所以开发了这样一套全局唯一生成服务。

Twitter的分布式雪花算法SnowFlake ，经测试snowflake 每秒能够产生26万个自增可排序的ID

Twitter的SnowFlake生成ID能够按照时间有序生成。
SnowFlake算法生成ID的结果是一个64bit大小的整数， 为一个Long型（转换成字符串后长度最多19）。
分布式系统内不会产生ID碰撞（由datacenter和workerld作区分）并且效率较高。
分布式系统中，有一些需要使用全局唯一ID的场景， 生成ID的基本要求：

在分布式的环境下必须全局且唯一 。
一般都需要单调递增，因为一般唯一ID都会存到数据库，而Innodb的特性就是将内容存储在主键索引树上的叶子节点而且是从左往右，递增的，所以考
虑到数据库性能，一般生成的ID也最好是单调递增。 为了防止ID冲突可以使用36位的UUID，但是UUID有一些缺点， 首先他相对比较长， 另外UUID一般是无序的。
可能还会需要无规则，因为如果使用唯一ID作为订单号这种，为了不然别人知道一天的订单量是多少，就需要这个规则。
结构

雪花算法的几个核心组成部分：



号段解析：

1bit：

不用，因为二进制中最高位是符号位，1表示负数，0表示正数。生成的id一般都是用整数，所以最高位固定为0。

41bit - 时间戳，用来记录时间戳，毫秒级：

41位可以表示2 41 − 1 2^{41}-12 
41
 −1个数字。
如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0至2 41 − 1 2^{41}-12 
41
 −1， 减1是因为可表示的数值范围是从0开始算的，而不是1。
也就是说41位可以表示2 41 − 1 2^{41}-12 
41
 −1个毫秒的值，转化成单位年则是( 2 41 − 1 ) / ( 1000 ∗ 60 ∗ 60 ∗ 24 ∗ 365 ) = 69 (2^{41}-1)/ (1000 * 60 * 60 * 24 *365) = 69(2 
41
 −1)/(1000∗60∗60∗24∗365)=69年。
10bit - 工作机器ID，用来记录工作机器ID：

可以部署在2 10 = 1024 2^{10}= 10242 
10
 =1024个节点，包括5位DataCenterId和5位Workerld。

5位(bit) 可以表示的最大正整数是2 5 − 1 = 31 2^{5}-1=312 
5
 −1=31,即可以用0、1、2、3、…31这32个数字，来表示不同的DataCenterld或Workerld。

12bit - 序列号，用来记录同毫秒内产生的不同id。

12位(bit) 可以表示的最大正整数是2 12 − 1 = 4095 2^{12} - 1 = 40952 
12
 −1=4095， 即可以用0、1、2、 3、…4094这4095个数字，来表示同一机器同一时间截(毫秒)内产生的4095个ID序号。
SnowFlake可以保证：

所有生成的ID按时间趋势递增。
整个分布式系统内不会产生重复id（因为有DataCenterId和Workerld来做区分)

---

end

`(*^_^*)`
