package com.thefirstwind.hystrix.activityApi.service;


public interface ILoginActivityService {

    String firstLogin(Long userId);

    String firstLoginTimeout(Long userId);

    String firstLoginFallback(Long userId);

}
