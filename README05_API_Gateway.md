# Part5 Zuul Proxy as API Gateway

> In microservices architecture, there could be a number of API services  
> and few UI components that are talking to APIs. As of now, many microservices  
> based application still use monolithic front-ends where the entire UI  
> is built as a single module. You may choose to go with micro-frontends  
> where the UI is also decomposed into multiple microservice talking to  
> APIs to get the relevant data. Instead of letting UI know about all our  
> microservices details we can provide a unified proxy interface that will  
> delegate the calls to various microservices based on URL pattern.  
> In this post, we will learn how to create API Gateway using Spring Cloud Zuul Proxy.


## Why do we need API Gateway?
## Implementing API Gateway using Spring Cloud Zuul Proxy
## Using Zuul Filters for cross-cutting concerns


## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway]()
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
