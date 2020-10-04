package com.thefirstwind.hystrix.user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActivityServiceBulkhead {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @param userId
     * @return
     */
    public String firstLogin(Long userId) {

        return restTemplate.postForObject("http://spring-cloud-hystrix-intro-activity/firstLoginActivity", userId, String.class);

    }

    /**
     *
     * HystrixHtreadPoolProperties $ Setter (可以查找配置)
     * @param userId
     * @return
     */
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

        return restTemplate.postForObject("http://spring-cloud-hystrix-intro-activity/firstLoginActivityTimeout", userId, String.class);

    }

    @HystrixCommand(
            threadPoolKey = "firstLoginFallback",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "20"),
            },
            fallbackMethod = "firstLoginFallback0")
    public String firstLoginFallback(Long userId) {
        return restTemplate.postForObject("http://spring-cloud-hystrix-intro-activity/firstLoginActivityError", userId, String.class);
    }

    public String firstLoginFallback0(Long userId) {
        return "circrutBreaker";
    }


}
