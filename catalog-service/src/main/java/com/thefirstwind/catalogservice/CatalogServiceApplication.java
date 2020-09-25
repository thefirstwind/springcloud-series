package com.thefirstwind.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// hystrix Circuit Breaker
//@EnableCircuitBreaker
@SpringBootApplication
public class CatalogServiceApplication {

    /**
     * 使用Ribbon LoadBalancer 访问 Eureka Fiegn Client
     * Ribbon LoadBalancer是客户端 负载均衡，所以那里调用 Eureak Client，就在哪里设置
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
