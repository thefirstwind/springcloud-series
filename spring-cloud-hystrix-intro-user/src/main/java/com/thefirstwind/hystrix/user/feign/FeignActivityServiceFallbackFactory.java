package com.thefirstwind.hystrix.user.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignActivityServiceFallbackFactory implements FallbackFactory<IFeignActivityService> {

    @Override
    public IFeignActivityService create(Throwable cause) {
        return new IFeignActivityService(){

            @Override
            public String firstLoginActivity(Long userId) {
                return "feign 降级 10ms";
            }

            @Override
            public String firstLoginActivityTimeout(Long userId) {
                return "feign fallbackFactory of firstLoginActivityTimeout:" + cause.getMessage();
            }

            @Override
            public String firstLoginActivityError(Long userId) {
                return null;
            }
        };
    }
}
