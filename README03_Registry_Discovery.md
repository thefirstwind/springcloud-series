# Part3 Service Registry and Discovery

## 1 overview

In the microservices world, Service Registry and Discovery plays an important role because we most likely run multiple instances of services and we need a mechanism to call other services without hardcoding their hostnames or port numbers. In addition to that, in Cloud environments service instances may come up and go down anytime. So we need some automatic service registration and discovery mechanism. Spring Cloud provides Service Registry and Discovery features, as usual, with multiple options. We can use Netflix Eureka or Consul for Service Registry and Discovery. In this post, we will learn how to use SpringCloud Netflix Eureka for Service Registry and Discovery.

java -jar -Dserver.port=8888 config-server/target/config-server-0.0.1-SNAPSHOT.jar

java -jar -Dserver.port=8761 service-registry/target/service-registry.jar

java -jar -Dserver.port=8181 catalog-service/target/catalog-service.jar

java -jar -Dserver.port=8182 catalog-service/target/catalog-service.jar

## 2 What is Service Registry and Discovery?

## 3 Spring Cloud Netflix Eureka based Service Registry

## 4 Registering microservices as Eureka Clients

## 5 Discovering other services using Eureka Client

## 6 Summary

## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery]()
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)

* [Spring cloud config server](README11_Spring_Cloud_Config_Server.md)
* [Spring cloud config client](README11_Spring_Cloud_Config_Client.md)
* [Spring cloud bus](README12_Spring_Cloud_Bus.md)
