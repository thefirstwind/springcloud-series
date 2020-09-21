# Spring Cloud Tutorials – Auto Refresh Config Changes using Spring Cloud Bus

<!--https://www.sivalabs.in/2017/08/spring-cloud-tutorials-auto-refresh-config-changes-using-spring-cloud-bus/-->
通过webhook 向 bus项目，推送跟配置更新

## Client 项目的修改
### 修改 pom.xml 文件
```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```
### 添加 docker-compose.yml文件
```yml
version: '2'

services:
  rabbitmq:
      container_name: rabbitmq-server
      image: 'rabbitmq:management'
      environment:
        - RABBITMQ_DEFAULT_USER=guest
        - RABBITMQ_DEFAULT_PASS=guest
      ports:
        - "5672:5672"
        - "15672:15672"
```
### 执行命令 docker-compose up
```shell
$ docker-compose up
```
### 修改bootstrap.properties文件
```
spring.cloud.bus.enabled=true
spring.cloud.bus.refresh.enabled=true
```

## Server 项目的修改
### 修改 application.properties 文件
```
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

```
## Monitor的添加

## Related Content
* [Part1 overview](README.md)
* [Part2 Spring Cloud Config and Vault](README02_Config_Vault.md)
* [Part3 Service Registry and Discovery](README03_Registry_Discovery.md)
* [Part4 Circuit Breaker using Netflix Hystrix](README04_Circuit_Breaker.md)
* [Part5 Zuul Proxy as API Gateway](README05_API_Gateway.md)
* [Part6 Distributed Tracing with Sleuth and Zipkin](README06_Distributed_Tracing.md)

* [Spring cloud config server](README11_Spring_Cloud_Config_Server.md)
* [Spring cloud config client](README11_Spring_Cloud_Config_Client.md)
* [Spring cloud bus]()
