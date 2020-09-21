package com.thefirstwind.springCloudConfigClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RefreshScope
@EnableConfigurationProperties(Datasource.class)
public class HomeController {
    @Value("${name}")
    String name;

    @Autowired
    Datasource datasource;

    @GetMapping("/properties")
    public Map<String,Object> properties()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("name", name);
        result.put("datasource", datasource);
        return result;
    }
}
