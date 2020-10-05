package com.thefirstwind.hystrix.user.controller;

import com.thefirstwind.hystrix.user.entity.User;
import com.thefirstwind.hystrix.user.service.ActivityService;
import com.thefirstwind.hystrix.user.service.ActivityServiceBulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterationController {

    @Autowired
    private ActivityServiceBulkhead activityService;

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
