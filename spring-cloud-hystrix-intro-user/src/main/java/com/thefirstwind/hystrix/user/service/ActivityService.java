package com.thefirstwind.hystrix.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActivityService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> firstLogin(Long userId) {

        return restTemplate.postForEntity("http://spring-cloud-hystrix-intro-activity/firstLoginActivity", userId, String.class);
    }
}
