# Part4 Circuit Breaker using Netflix Hystrix

在微服务中，Netfix 创建了 Hystrix库，来实现 熔断机制，我们可以使用 Spring Cloud Netflix Hystrix Circuit Breaker来保护广播式的大流量导入造成的系统失败。

## 1 Implementing Netflix Hystrix Circuit Breaker pattern

### 1.1 修改 catalog-service的 pom.xml文件
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

### 1.2 修改application文件
在 application启动文件中加入注释 @EnableCircuitBreaker
```java
package com.thefirstwind.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
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
### 1.3 可以使用 @HystrixCommand 注解，应用在超时和回调方法中

如下 创建 InventoryServiceClient.java 文件
```java
package com.thefirstwind.catalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thefirstwind.catalogservice.web.models.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(
            commandKey = "inventory-by-productcode"
            ,fallbackMethod = "getDefaultProductInventoryByCode"
//            ,commandProperties = {
//                @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
//                @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
//            }
    )
    public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode){
        ResponseEntity<ProductInventoryResponse> itemResponseEntity =
                restTemplate.getForEntity("http://inventory-service/api/inventory/{code}",
                ProductInventoryResponse.class,
                productCode);
        if(itemResponseEntity.getStatusCode() == HttpStatus.OK){
            return Optional.ofNullable(itemResponseEntity.getBody());
        }else{
            log.error("Unable to get inventory level for product_code: " + productCode + ", StatusCode: " + itemResponseEntity.getStatusCode());
            return Optional.empty();
        }
    }

    public Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode){
        log.info("Returning default ProductInventoryByCode for productCode: "+productCode);
        ProductInventoryResponse response = new ProductInventoryResponse();
        response.setProductCode(productCode);
        response.setAvailableQuantity(50);
        return Optional.ofNullable(response);
    }

}

```
## 2 How to propagate ThreadLocal variables
## 3 Monitoring Circuit Breakers using Hystrix Dashboard
### 3.1  创建项目 hystrix-dashboard项目
### 3.2 在pom.xml中添加以下依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>

```
### 3.3 在application上添加 @EnableHystrixDashboard 注解
可以通过 http://localhost:8181/actuator/hystrix.stream 来监控


## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix]()
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)

* [Spring cloud config server](README11_Spring_Cloud_Config_Server.md)
* [Spring cloud config client](README11_Spring_Cloud_Config_Client.md)
* [Spring cloud bus](README12_Spring_Cloud_Bus.md)
* [Spring Microservices Docker Example](https://github.com/thefirstwind/spring-microservices-docker-example/blob/master/README.md)
* [Spring Cloud Eureka and Feign](README13_Spring_Cloud_Eureka.md)
* [Netflix Hystrix How It Works](README14_Netflix_Hystrix_How_it_works.md)
