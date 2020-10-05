package com.thefirstwind.hystrix.user.feign;

import org.springframework.stereotype.Component;

@Component
public class FeignActivityServiceFallback implements IFeignActivityService{
    @Override
    public String firstLoginActivity(Long userId) {
        return null;
    }

    @Override
    public String firstLoginActivityTimeout(Long userId) {
        return "feign fallback of firstLoginActivityTimeout";
    }

    @Override
    public String firstLoginActivityError(Long userId) {
        return "feign fallback of firstLoginActivityError ";
    }
}
