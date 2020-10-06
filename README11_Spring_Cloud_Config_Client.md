# Spring Cloud Config Client

## 创建项目 spring-cloud-config-client
https://github.com/thefirstwind/springcloud-series/tree/master/spring-cloud-config-client

## pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.thefirstwind</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>spring-cloud-config-client</name>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <start-class>com.thefirstwind.springCloudConfigClient.ConfigClientApplication</start-class>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Finchley.M8</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
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

## bootstrap.properties
```properties
server.port=8181
spring.application.name=catalogservice
spring.cloud.config.uri=http://localhost:8888
management.security.enabled=false
spring.cloud.config.enabled=true
```

spring.application.name 对应 [springcloud-series-config-repo](https://github.com/thefirstwind/springcloud-series-config-repo) 项目下的 catalogservice.properties 文件


## ConfigClientAppliction
```java
package com.thefirstwind.springCloudConfigClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
```

## HomeController
```java
package com.thefirstwind.springCloudConfigClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RefreshScope
@EnableConfigurationProperties(Datasource.class)
public class HomeController {
    @Value("${name}")
    String name;

    @Autowired
    Datasource datasource;

    @GetMapping("/properties")
    public Map<String,Object> properties()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("name", name);
        result.put("datasource", datasource);
        return result;
    }
}

```
## Datasource
```java
package com.thefirstwind.springCloudConfigClient.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public class Datasource {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

```

## 确认 catalogservice.properties文件内容
```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/catalog
spring.datasource.username=root
spring.datasource.password=dev@990990
name=kei2
```

## 请求地址 http://localhost:8181/properties
```json
{"datasource":{"driverClassName":"com.mysql.jdbc.Driver","url":"jdbc:mysql://localhost:3306/catalog","username":"root","password":"dev@990990"},"name":"kei"}
```

## 当更新了配置文件之后，可以通过refresh接口更新配置
首先请求接口 http://localhost:8181/properties
获得如下内容
```json
{"datasource":{"driverClassName":"com.mysql.jdbc.Driver","url":"jdbc:mysql://localhost:3306/catalog","username":"root","password":"dev@990990"},"name":"kei3"}
```
更新catalogservice.properties 内容为
```
name=kei4
```
```shell
curl -H "Content-Type: application/json" -d {} http://localhost:8181/actuator/refresh
```
再次请求接口 http://localhost:8181/properties
```json
{"datasource":{"driverClassName":"com.mysql.jdbc.Driver","url":"jdbc:mysql://localhost:3306/catalog","username":"root","password":"dev@990990"},"name":"kei4"}
```

##  上一篇
* [Spring Cloud Config Server的介绍](README11_Spring_Cloud_Config_Server.md)
##  下一篇
* [使用Spring Cloud Bus来自动更新配置](README12_Spring_Cloud_Bus.md)


## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)

* [Spring cloud config server](README11_Spring_Cloud_Config_Server.md)
* [Spring cloud config client]()
* [Spring cloud bus](README12_Spring_Cloud_Bus.md)
* [Spring Microservices Docker Example](https://github.com/thefirstwind/spring-microservices-docker-example/blob/master/README.md)
* [Spring Cloud Eureka and Feign](README13_Spring_Cloud_Eureka.md)
* [Netflix Hystrix How It Works](README14_Netflix_Hystrix_How_it_works.md)
* [Netflix Hystrix How to Use](README15_Netflix_Hystrix_How_To_Use.md)
