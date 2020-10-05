package com.thefirstwind.hystrix.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("hystrix-intro-activity")
public interface IFeignActivityService {

    @PostMapping("firstLoginActivity")
    String firstLoginActivity(@RequestBody Long userId);

    @PostMapping("firstLoginActivityTimeout")
    String firstLoginActivityTimeout(@RequestBody Long userId);

    @PostMapping("firstLoginActivityError")
    String firstLoginActivityError(@RequestBody Long userId);
}
