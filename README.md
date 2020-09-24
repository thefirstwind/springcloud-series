# springcloud-series

<!--https://www.sivalabs.in/2018/03/microservices-using-springboot-spring-cloud-part-1-overview/-->

* Monoliths：单点项目
* what are MicroServices?： 什么是微服务
* Advantages of MicroServices： 微服务的好处
* Challenges with MicroServices：微服务的挑战
* Why SpringBoot & SpringCloud ?：为什么选用SpringBoot和SpringCloud
* Introducing the application：介绍application

## 1 Monoliths
* 大量代码
* 多项目组在同一个代码基础上
* 筋扩展某一个部分的应用变得不现实
* 技术的迭代更新变得复杂和高成本
## 2 MicroServices

一个微服务，是基于特定业务能力 他是可以扩展的。因此创建一个大型企业应用，
我们可以将它分为子域，使用领域驱动进行划分。最终，左右微服务在一起工作，
为用户提供一个单一的应用。

### 2.1  Advantages of MicroServices
* Comprehending smaller codebase is easy: 理解更少部分的代码更容易
* Can independently scale up highly used services：可以提供扩展高使用率的服务
* Each team can focus on one (or few) MicroService(s)：每个team只关注其自身的微服务
* Technology updates/rewrites become simpler：技术更新和迭代变得更简单

### 2.2 Challenges with MicroServices
* Getting correct sub-domain boundaries, in the beginning, is hard
* Need more skilled developers to handle distributed application complexities
* Managing MicroServices based applications without proper DevOps culture is next to impossible
* Local developer environment setup might become complex to test cross-service communications. Though using Docker/Kubernetes this can be mitigated to some extent.

## 3 Why SpringBoot & SpringCloud
### 3.1 Spring Cloud
### 3.2 Spring Cloud Config Server:
> To externalize configuration of applications in a central config  
> server with the ability to update the configuration  
> values without requiring to restart the applications.  
> We can use Spring Cloud Config Server with git or Consul or ZooKeeper as config repository.
### 3.3 Service Registry and Discovery:
### 3.4 Circuit Breaker:
### 3.5 Spring Cloud Data Streams:
### 3.6 Spring Cloud Security:
### 3.7 Distributed Tracing:
### 3.8 Spring Cloud Contract:
## 4 Sample Application
* catalog-service: It provides REST API to provide catalog information like products.
* inventory-service: It provides REST API to manage product inventory.
* cart-service: It provides REST API to hold the customer cart details.
* order-service: It provides REST API to manage orders.
* customer-service: It provides REST API to manage customer information.
* shoppingcart-ui: It is customer facing front-end web application.


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
* [Netflix Hystrix How It Works](README13_Netflix_Hystrix_How_it_works.md)
