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


## 1 Why do we need API Gateway?
> API Gateway, aka Edge Service, provides a unified interface for a set  
> of microservices so that clients no need to know about all the details  
> of microservices internals. However, there are some pros and cons of  
> using API Gateway pattern in microservices architecture.

### 1.1  Pros:
Provides easier interface to clients
Can be used to prevent exposing the internal microservices structure to clients
Allows to refactor microservices without forcing the clients to refactor consuming logic
Can centralize cross-cutting concerns like security, monitoring, rate limiting etc

### 1.2 Cons:
It could become a single point of failure if proper measures are not taken to make it highly available
Knowledge of various microservice API may creep into API Gateway

## 2 Implementing API Gateway using Spring Cloud Zuul Proxy
### 2.1 添加pom依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.thefirstwind</groupId>
    <artifactId>shoppingcart-ui</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <name>shoppingcart-ui</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.0.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Finchley.M8</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
        <!--<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
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

### 2.2 创建 html 和 js资源
```js
new Vue({
    el: '#app',
    data: {
        products: [],
        cart: {
            customerEmail: '',
            customerAddress: '',
            items: []
        },
        searchOrderId: '',
        order: {},
        orderStatus: ''
    },
    created : function()
    {
        this.fetchProducts();
    },
    methods: {
        fetchProducts: function() {
            $.ajax({
               // url: 'http://localhost:9191/products'
                url: '/ui/api/catalog-service/api/products'
            })
            .done(function(data) {
                this.products = data;
            }.bind(this));
        },
        addToCart: function (product) {
            console.log('addToCart', this.cart);
            var added = false;
            this.cart.items.forEach(function (p) {
                if(p.productId == product.id) {
                    p.quantity = p.quantity +1;
                    added = true;
                    return;
                }
            });
            if(added) return;

            this.cart.items.push({
                productId: product.id,
                quantity: 1,
                productPrice: product.price
            });
            console.log('Cart: ', this.cart);
        },
        placeOrder: function () {
            $.ajax({
                type: 'POST',
                //url: 'http://localhost:9292/orders',
                url: '/ui/api/order-service/api/orders',
                contentType : 'application/json',
                data: JSON.stringify(this.cart)
            })
            .done(function(data) {
                this.cart = {
                    customerEmail: '',
                    customerAddress: '',
                    items: []
                };
                this.orderStatus = "Order created successfully. Order ID:"+data.id

            }.bind(this));
        },
        searchOrder: function () {
            $.ajax({
                type: 'GET',
                //url: 'http://localhost:9292/orders/'+this.searchOrderId,
                url: '/ui/api/order-service/api/orders/'+this.searchOrderId,
                contentType : 'application/json'
            })
            .done(function(data) {
                this.order = data;
            }.bind(this));
        }
    },
    computed: {

    }
});

```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>ShoppingCart</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous"/>
    <style>
        body {
            padding-top: 75px;
        }
    </style>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">ShoppingCart</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
        </ul>
    </div>
</nav>

<div class="container" id="app">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
        <ul class="list-group">
            <li class="list-group-item" v-for="product in products">
                <span>{{ product.name }}</span>
                <button class="badge" v-on:click="addToCart(product)">Add To Cart</button>
            </li>
        </ul>
        </div>
    </div>
    <hr/>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <p>Customer Email: <input type="text" v-model="cart.customerEmail"/></p>
            <p>Customer Address: <input type="text" v-model="cart.customerAddress"/></p>
            <pre>{{cart}}</pre>
            <p><button type="button" class="btn btn-primary"
                    v-on:click="placeOrder">Place Order</button></p>
            <p>{{ orderStatus }}</p>
        </div>
    </div>
    <hr/>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <p>Get Order Details By Order ID: <input type="text" v-model="searchOrderId"/>
            <button type="button" class="btn btn-warning"
                    v-on:click="searchOrder">Search Order</button>
            </p>
            <pre>{{order}}</pre>
        </div>
    </div>

</div>
<script src="jquery.min.js"></script>
<script src="vue.min.js"></script>
<script type="text/javascript" src="app.js"></script>
</body>
</html>

```

```java
package com.thefirstwind.shoppingcartui;

import com.thefirstwind.shoppingcartui.filters.AuthHeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ShoppingcartUiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShoppingcartUiApplication.class, args);
    }

    @Bean
    AuthHeaderFilter authHeaderFilter() {
        return new AuthHeaderFilter();
    }
}

```
```java
package com.thefirstwind.shoppingcartui.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class AuthHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //RequestContext ctx = RequestContext.getCurrentContext();

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getAttribute("AUTH_HEADER") == null) {
            //generate or get AUTH_TOKEN, ex from Spring Session repository
            String sessionId = UUID.randomUUID().toString();
            //request.setAttribute("AUTH_HEADER", sessionId);
            ctx.addZuulRequestHeader("AUTH_HEADER", sessionId);
        }
        return null;
    }
}

```

```properties
spring.application.name=shoppingcart-ui
server.port=8080
spring.cloud.config.uri=http://localhost:8888

server.servlet.context-path=/ui
management.endpoints.web.exposure.include=*

```
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
