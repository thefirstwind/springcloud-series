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
$  docker-compose up
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

## 配置服务器的docker
```
# step 1: 安装必要的一些系统工具
sudo apt-get update
sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
# step 2: 安装GPG证书
curl -fsSL http://mirrors.aliyun.com/docker-ce/linux/ubuntu/gpg | sudo apt-key add -
# Step 3: 写入软件源信息
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"
# Step 4: 更新并安装 Docker-CE
sudo apt-get -y update
sudo apt-get -y install docker-ce

sudo gpasswd -a ${USER} docker

退出当前登录，再次登录


```
## Monitor的添加

服务器global ip: 47.103.128.15
放开 8888， 8181 端口

在 https://github.com/thefirstwind/springcloud-series-config-repo 项目中的webhook中添加
http://47.103.128.159:8888/monitor



## 验证效果
修改 springcloud-series-config-repo 中的 catalogservice.properties
然后 push
```
2020-09-21 22:15:05.022  INFO 17580 --- [nio-8888-exec-7] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@10c00739: startup date [Mon Sep 21 22:15:04 CST 2020]; root of context hierarchy
2020-09-21 22:15:05.177  INFO 17580 --- [nio-8888-exec-7] o.s.cloud.bus.event.RefreshListener      : Received remote refresh request. Keys refreshed []
```

http://47.103.128.159:8181/properties
```
{"datasource":{"driverClassName":"com.mysql.jdbc.Driver","url":"jdbc:mysql://localhost:3306/catalog","username":"root","password":"dev@990990"},"name":"kei6"}
```

## 上2篇
* [Spring Cloud Config Server的介绍](README11_Spring_Cloud_Config_Server.md)
* [Spring Cloud Config Client的介绍](README11_Spring_Cloud_Config_Client.md)

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
