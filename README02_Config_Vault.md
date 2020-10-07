# Part2 Spring Cloud Config and Vault

In this post, we are going to learn:

* What is the need for Spring Cloud Config and Vault?
* Create our first micro-service: catalog-service
* Create Spring Cloud Config Server
* Using Vault for storing sensitive data

## 1 What is the need for Spring Cloud Config and Vault?
> 在springBoot中提供了很多可扩展的配置属性，但是当这些属性被修改的时候，你需要重启应用

> 创建一个 Spring Cloud Config Server，我们可以使用 git svn database 或者 consul
> 作为后端 存储这些配置参数。当我们要更新属性的时候，使用 /refresh 去实时更新配置，
> 不需要重启服务

另外还有3篇文章，作为本篇文章的基础
* [Spring Cloud Config Server的介绍](README11_Spring_Cloud_Config_Server.md)
* [Spring Cloud Config Client的介绍](README11_Spring_Cloud_Config_Client.md)
* [使用Spring Cloud Bus来自动更新配置](README12_Spring_Cloud_Bus.md)

## 本文代码地址 需要参考 part2-config-vault 分支
```
git checkout part2-config-vault
```
https://github.com/thefirstwind/springcloud-series/tree/part2-config-vault

## 2 创建表结构
```sql
DELETE FROM products;

insert into products(id, code, name, description, price) VALUES
(1, 'P001', 'Product 1', 'Product 1 description', 25),
(2, 'P002', 'Product 2', 'Product 2 description', 32),
(3, 'P003', 'Product 3', 'Product 3 description', 50)
;
```

## 3 Create our first micro-service: catalog-service
### 3.0 创建pom.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.thefirstwind</groupId>
    <artifactId>catalog-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>catalog-service</name>
    <description>CatalogService REST API</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <start-class>com.thefirstwind.catalogservice.CatalogServiceApplication</start-class>
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
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-vault-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-vault-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
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
        <finalName>${project.artifactId}</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
### 3.1 创建 entity: Product.java
```java
package com.thefirstwind.catalogservice.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    private String description;
    private double price;
}

```
### 3.2 创建持久层: ProductPepository.java
```java
package com.thefirstwind.catalogservice.repositories;

import com.thefirstwind.catalogservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
}

```
### 3.3 创建service: ProductService.java
```java
package com.thefirstwind.catalogservice.services;

import com.thefirstwind.catalogservice.entities.Product;
import com.thefirstwind.catalogservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductByCode(String code){
        return productRepository.findByCode(code);
    }
}

```
### 3.4 创建controller
```java
package com.thefirstwind.catalogservice.web.controllers;

import com.thefirstwind.catalogservice.entities.Product;
import com.thefirstwind.catalogservice.exceptions.ProductNotFoundException;
import com.thefirstwind.catalogservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> allProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{code}")
    public Product productByCode(@PathVariable String code){
        return productService.findProductByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }


}
```
```java
package com.thefirstwind.catalogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

}

```
### 3.5 创建启动application: CatalogServiceApplication.java
```java
package com.thefirstwind.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CatalogServiceApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}

```
### 3.6 创建 application.properties文件
```properties
server.port=8181
logging.level.com.sivalabs=debug

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/catalog?useSSL=false
#spring.datasource.username=root
#spring.datasource.password=admin

spring.datasource.initialization-mode=always
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.application.name=catalog-service
management.endpoints.web.exposure.include=*
spring.cloud.config.uri=http://localhost:8888


#Vault
spring.cloud.vault.host=localhost
spring.cloud.vault.port=8200
spring.cloud.vault.scheme=http
spring.cloud.vault.authentication=token
spring.cloud.vault.uri=http://localhost:8888
spring.cloud.vault.token=934f9eae-31ff-a8ef-e1ca-4bea9e07aa09
```

### 3.7 在 docker-compose.yml 文件中加入 mysql相关操作
```yml
version: '3'
services:
  mysqldb:
    image: mysql:5.7
    container_name: mysqldb
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: catalog
```

## 4 Create Spring Cloud Config Server
### 4.1 创建pom.xml文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.thefirstwind</groupId>
    <artifactId>config-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>config-service</name>
    <description>ConfigService REST API</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <start-class>com.thefirstwind.configServer.ConfigServerApplication</start-class>
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
### 4.2 创建启动项
```java
package com.thefirstwind.configServer;

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
### 4.3 创建 properties 文件
```properties
server.port=8888
spring.config.name=configserver

spring.profiles.include=native
spring.cloud.config.server.native.search-locations=classpath:/config-repo

management.endpoints.web.exposure.include=*
```

config-repo/catalog-service.properties
```properties
logging.level.com.sivalabs=debug
```


## 5 Refactor catalog-service to use Config Server
从新构建 catalog-service使用 config server
### 5.1 确认 catalog-service 项目中 pom.xml 加入以下依赖
```xml
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-config</artifactId>
</dependency>

```

### 5.2 重命名 application.properties 为 bootstrap.properties, 并且确认包含以下配置
```properties
spring.application.name=catalog-service
server.port=8181
management.endpoints.web.exposure.include=*
spring.cloud.config.uri=http://localhost:8888

```

### 5.3 确认 config-service的 config-repo/catalog-service.properties 包含以下内容
```properties
logging.level.com.sivalabs=debug
 
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/catalog?useSSL=false
 
spring.datasource.initialization-mode=always
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

并且 catalog-service项目中 bootstrap.properties 删除重复项

### 5.4 在 docker-compose.yml 文件中加入 vault相关操作
```yml
version: '3'
services:
  vault:
    image: vault:1.4.0
    container_name: vault
    cap_add:
      - IPC_LOCK
    environment:
      VAULT_DEV_ROOT_TOKEN_ID: 934f9eae-31ff-a8ef-e1ca-4bea9e07aa09
    ports:
      - 8200:8200

  setup-vault:
    image: vault:1.4.0
    container_name: setup-vault
    entrypoint: /bin/sh
    volumes:
      - './config:/config'
    environment:
      VAULT_ADDR: 'http://vault:8200'
      CONFIG_DIR: '/config'
    command: >
      -c "
      sleep 2;
      /config/vault-init.sh;
      "
    depends_on:
      - vault
```

### 5.5 config 目录下，加入准备好 2个json文件 和 vault-int.sh文件

config/application.json
```json
{
  "spring.rabbitmq.username": "guest",
  "spring.rabbitmq.password": "guest"
}
```

config/catalog-service.json
```json
{ 
    "spring.datasource.username": "root", 
    "spring.datasource.password": "admin"
}
```

config/vault-init.sh
```shell script
#!/bin/sh

VAULT_DEV_TOKEN=934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

vault login ${VAULT_DEV_TOKEN}

vault login

vault secrets disable secret
vault secrets enable -version=1 -path=secret kv
vault kv put secret/application @${CONFIG_DIR}/application.json
vault kv put secret/catalog-service @${CONFIG_DIR}/catalog-service.json


```

## 6 运行
验证效果

```shell script

# 拉取代码& 切换分支
> git clone https://github.com/thefirstwind/springcloud-series.git
> cd springcloud-series
> git checkout part2-config-vault


# 启动docker，拉取镜像
> docker-compose up

# 验证 mysql 和 vault是否启动成功
# 登陆数据库
# 访问 http://localhost:8200/ token 934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

# 确认当前环境是 java8 ， 注 java11 是有问题的

# 编译项目
> mvn clean install 

# 启动配置服务
> cd config-server
> mvn spring-boot:run
# 访问 http://localhost:8888/actuator/env 看是否有返回数据

# 启动 catalog-service服务
> cd catalog-service
> mvn spring-boot:run
# 访问 http://localhost:8181/api/products 看是否有返回数据

```

## 7 验证vault的实时性

### 7.1 在 catalog-service.json中添加
    "product.limit": 1

### 7.2 在 ProductController中添加
```java
    @Value("${product.limit}")
    private Integer productLimit;

    @GetMapping("/getProductLimit")
    public Integer getProductLimit(){
        return productLimit;
    }
```

### 7.3 启动项目
```shell script
> docker-compose up
> cd config-server
> mvn spring-boot:run
> cd catalog-service
> mvn spring-boot:run

# 访问 http://localhost:8200/ token 934f9eae-31ff-a8ef-e1ca-4bea9e07aa09

# 访问 http://localhost:8181/api/products/getProductLimit  
# 返回结果 1

# 修改 vault中 product.limit 的值 
# 返回结果不变 TODO 待解决

```
## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault]()
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
* [Netflix Hystrix How to Use](README15_Netflix_Hystrix_How_To_Use.md)
* [Netflix Hystrix Configuration](README16_Netflix_Hystrix_Configuration.md)
* [Netflix Hystrix 原理和实战](REAME17_Nextfix_Hystrix_原理和实战.md)
