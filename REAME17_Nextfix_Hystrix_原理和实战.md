# Hystrix 原理和实战

<!--https://www.bilibili.com/video/BV1V4411F7my-->

## 目录
* 1 Hystrix 的基本使用
  * 1.1 为什么需要熔断器
  * 1.2 熔断器能解决什么问题
  * 1.3 如何使用断路器？
* 2 Hystrix的工作原理与高级使用
  * 2.1 Hystrix 如何隔离服务调用
  * 2.2 Hystrix 是如何工作的
  * 2.3 如何控制 Hystrix的行为？
* 3 扩展
  * 3.1 Hystrix 与 Feign结合使用
  * 3.2 如何监控 Hystrix来了解服务间调用健康状态？

## 1 Hystrix 的基本使用

![](_images/6B1FD4DA-551D-42EE-8B15-C89A329F3741.png)

创建工程3个项目
```
<module>spring-cloud-hystrix-intro-register</module>
<module>spring-cloud-hystrix-intro-activity</module>
<module>spring-cloud-hystrix-intro-user</module>
```

### 1.1 spring-cloud-hystrix-intro-register

Application.java
```java
@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

}
```
pom.xml
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

application.yml
```yaml
server:
  port: 8000
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8000/eureka
  server:
    enable-self-preservation: false
```
### 1.2 spring-cloud-hystrix-intro-activity

application.java
```java
@SpringBootApplication
@EnableDiscoveryClient
public class ActivityApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }
}
```

LoginActivityController.java
```java
@RestController
public class LoginActivityController {

    @PostMapping("firstLoginActivity")
    public String firstLoginActivity(@RequestBody Long userId){
        System.out.println("firstLoginActivity 初始化活动" + userId);
        return "ok";
    }
}
```

pom.xml
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

application.yml
```yaml
server:
  port: 8100
spring:
  application:
    name: spring-cloud-hystrix-intro-activity
eureka:
  client:
    serviceUrl:
      defaultZone: ${EUREKA_URI:http://localhost:8000/eureka}
  instance:
    preferIpAddress: true
```


### 1.3 spring-cloud-hystrix-intro-user

application.java
```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
```

RegisterationController.java
```java
@RestController
public class RegisterationController {

    @Autowired
    private ActivityService activityService;

    /**
     * http://localhost:8200/userRegisteration
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegisteration")
    public ResponseEntity<String> userRegistration(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLogin(user.getId());
    }

}
```

ActivityService.java
```java
@Service
public class ActivityService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> firstLogin(Long userId) {

        return restTemplate.postForEntity("http://spring-cloud-hystrix-intro-activity/firstLoginActivity", userId, String.class);
    }
}

```

pom.xml
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

application.yml
```yaml
server:
  port:8200
spring:
  application:
    name:spring-cloud-hystrix-intro-user
eureka:
  client:
    server-url:
      defaultZone: http://localhost:8000/eureka

```



访问 http://localhost:8200/userRegisteration
入口参数
```json
{
	"id": 1000,
	"name": "hello"
}
```
出口参数
```
ok
```


https://www.bilibili.com/video/BV1V4411F7my