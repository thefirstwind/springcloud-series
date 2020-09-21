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

另外还有2篇文章
* [Spring Cloud Config Server的介绍](README11_Spring_Cloud_Config_Server.md)
* [使用Spring Cloud Bus来自动更新配置](README12_Spring_Cloud_Bus.md)
## 2 Create our first micro-service: catalog-service
## 3 Create Spring Cloud Config Server
## 4 Refactor catalog-service to use Config Server
## 5 Using Vault for storing sensitive data
## 6 Summary

## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault]()
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)