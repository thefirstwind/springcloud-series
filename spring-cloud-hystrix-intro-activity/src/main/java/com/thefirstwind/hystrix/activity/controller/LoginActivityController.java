package com.thefirstwind.hystrix.activity.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thefirstwind.hystrix.activityApi.service.ILoginActivityService;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class LoginActivityController implements ILoginActivityService {

    @PostMapping("firstLoginActivity")
    public String firstLogin(@RequestBody Long userId){
        System.out.println("firstLoginActivity 初始化活动" + userId);
        return "ok";
    }

    @PostMapping("firstLoginActivityTimeout")
    public String firstLoginTimeout(@RequestBody Long userId)  {
        try {
            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5) + 1);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("firstLoginActivity 初始化活动 Timeout" + userId);
        return "ok";
    }

    @PostMapping("firstLoginActivityError")
    public String firstLoginFallback(@RequestBody Long userId)  {
        throw new RuntimeException("firstLoginActivity 初始化活动 failed " + userId);
    }


}
