# Spring Cloud Tutorials – Introduction to Spring Cloud Config Server

<!--https://www.sivalabs.in/2017/08/spring-cloud-tutorials-introduction-to-spring-cloud-config-server/-->

## Spring Cloud Config Server
配置服务，保证可以读取到git和 svn或者Consul的资源。

### 创建一个 Git Config Repository
https://github.com/thefirstwind/springcloud-series-config-repo.git

### 创建 项目
https://github.com/thefirstwind/springcloud-series/tree/master/spring-cloud-config-server.git

### 创建文件 catalogservice.properties
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/catalog
spring.datasource.username=root
spring.datasource.password=admin
```

### orderservice.properties
```
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

### catalogservice-prod.properties
```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://appsrv1:3306/catalog
spring.datasource.username=appuser46
spring.datasource.password=T(iV&#)X84@1!
```

### orderservice-prod.properties
```
spring.rabbitmq.host=srv245.ind.com
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin23
spring.rabbitmq.password=uY7&%we@1!
```

## 创建 Spring Cloud Config Server Application项目

源代码 https://github.com/thefirstwind/springcloud-series/spring-cloud-config-server

### pom.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.thefirstwind</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <start-class>com.thefirstwind.springCloudConfigServer.ConfigServerApplication</start-class>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Finchley.M8</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>

```


### application.properties 文件
```properties
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/thefirstwind/springcloud-series-config-repo.git
management.security.enabled=false
```


### ConfigServerApplication 文件
```java
package com.thefirstwind.springCloudConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    public static void main(String[] args){
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```


### 启动验证
* 请求以下地址  http://localhost:8888/catalogservice/default
```json
{
    "name": "catalogservice",
    "profiles": [
        "default"
    ],
    "label": null,
    "version": "8a06f25aeb3f28a8f06b5634eae01858b2c6465d",
    "state": null,
    "propertySources": [
        {
            "name": "https://github.com/thefirstwind/springcloud-series-config-repo.git/catalogservice.properties",
            "source": {
                "spring.datasource.username": "root",
                "spring.datasource.driver-class-name": "com.mysql.jdbc.Driver",
                "spring.datasource.password": "admin",
                "spring.datasource.url": "jdbc:mysql://localhost:3306/catalog"
            }
        }
    ]
}
```

* 请求以下地址 http://localhost:8888/catalogservice/prod
```json
http://localhost:8888/catalogservice/prod then you will get the following response with catalogservice prod configuration details.

{
    "name": "catalogservice",
    "profiles": [
        "prod"
    ],
    "label": null,
    "version": "8a06f25aeb3f28a8f06b5634eae01858b2c6465d",
    "state": null,
    "propertySources": [
        {
            "name": "https://github.com/thefirstwind/springcloud-series-config-repo.git/catalogservice-prod.properties",
            "source": {
                "spring.datasource.username": "appuser46",
                "spring.datasource.driver-class-name": "com.mysql.jdbc.Driver",
                "spring.datasource.password": "T(iV&#)X84@1!",
                "spring.datasource.url": "jdbc:mysql://appsrv1:3306/catalog"
            }
        },
        {
            "name": "https://github.com/thefirstwind/springcloud-series-config-repo.git/catalogservice.properties",
            "source": {
                "spring.datasource.username": "root",
                "spring.datasource.driver-class-name": "com.mysql.jdbc.Driver",
                "spring.datasource.password": "admin",
                "spring.datasource.url": "jdbc:mysql://localhost:3306/catalog"
            }
        }
    ]
}
```

## 后2篇
* [Spring Cloud Config Client的介绍](README11_Spring_Cloud_Config_Client.md)
* [使用Spring Cloud Bus来自动更新配置](README12_Spring_Cloud_Bus.md)

## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)

* [Spring cloud config server](README11_Spring_Cloud_Config_Server.md)
* [Spring cloud config client](README11_Spring_Cloud_Config_Client.md)
* [Spring cloud bus](README12_Spring_Cloud_Bus.md)
* [Spring Microservices Docker Example](https://github.com/thefirstwind/spring-microservices-docker-example/blob/master/README.md)
* [Spring Cloud Eureka and Feign](README13_Spring_Cloud_Eureka.md)
* [Netflix Hystrix How It Works](README14_Netflix_Hystrix_How_it_works.md)
