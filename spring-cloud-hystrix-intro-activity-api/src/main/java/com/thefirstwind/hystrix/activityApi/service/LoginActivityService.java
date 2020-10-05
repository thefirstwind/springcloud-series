package com.thefirstwind.hystrix.activityApi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thefirstwind.hystrix.activityApi.constant.ActivityURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginActivityService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @param userId
     * @return
     */
    public String firstLogin(Long userId) {
        return restTemplate.postForObject(ActivityURL.PREFIX + ActivityURL.FIRST_LOGIN_ACTIVITY, userId, String.class);
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
    public String firstLoginTimeout(Long userId) {
        return restTemplate.postForObject(ActivityURL.PREFIX + ActivityURL.FIRST_LOGIN_ACTIVITY_TIMEOUT, userId, String.class);
    }

    @HystrixCommand(
            threadPoolKey = "firstLoginFallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "20"),
            },
            fallbackMethod = "firstLoginFallback0")
    public String firstLoginFallback(Long userId) {
        return restTemplate.postForObject(ActivityURL.PREFIX + ActivityURL.FIRST_LOGIN_ACTIVITY_ERROR, userId, String.class);
    }

    public String firstLoginFallback0(Long userId) {
        System.out.println("firstLoginFallback0 circrutBreaker");

        return "circrutBreaker";
    }


}
