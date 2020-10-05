package com.thefirstwind.hystrix.user.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thefirstwind.hystrix.activityApi.service.LoginActivityService;
import com.thefirstwind.hystrix.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class RegisterationControllerV2 {

    @Autowired
    private LoginActivityService activityService;

    /**
     * http://localhost:8200/userRegisteration
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegisteration")
    public String userRegistration(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLogin(user.getId());
    }

    @HystrixCommand(
            threadPoolKey = "firstLoginTimeout",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2"),
                    @HystrixProperty(name = "maxQueueSize", value = "20"),
            },
            commandProperties = {
                    @HystrixProperty(name= "execution.isolation.thread.timeoutInMilliseconds", value="1000")
            }
    )
    @PostMapping("/userRegisterationTimeout")
    public String userRegistrationTimeout(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLoginTimeout(user.getId());
    }

    @PostMapping("/userRegisterationError")
    public String userRegisterationError(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLoginFallback(user.getId());
    }


}
