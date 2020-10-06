package com.thefirstwind.hystrix.activity.feign;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/feign")
public class FeignLoginActivityController {

    @PostMapping("firstLoginActivity")
    public String firstLoginActivity(@RequestBody Long userId){
        System.out.println("firstLoginActivity 初始化活动" + userId);
        return "ok";
    }

    @PostMapping("firstLoginActivityTimeout")
    public String firstLoginActivityTimeout(@RequestBody Long userId)  {
        try {
            TimeUnit.SECONDS.sleep(RandomUtils.nextInt(5) + 1);
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("firstLoginActivity 初始化活动 Timeout" + userId);
        return "ok";
    }

    @PostMapping("firstLoginActivityError")
    public String firstLoginActivityError(@RequestBody Long userId)  {
        throw new RuntimeException("firstLoginActivity 初始化活动 failed " + userId);
    }


}
