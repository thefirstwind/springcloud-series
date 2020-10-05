package com.thefirstwind.hystrix.user.feign;

import com.thefirstwind.hystrix.user.entity.User;
import com.thefirstwind.hystrix.user.service.ActivityServiceBulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignRegisterationController {

    @Autowired
//    private FeignActivityService activityService;
    private IFeignActivityService activityService;

    /**
     * http://localhost:8200/userRegisteration
     *
     * @param user
     * @return
     */
    @PostMapping("/userRegisteration")
    public String userRegistration(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLoginActivity(user.getId());
    }

    @PostMapping("/userRegisterationTimeout")
    public String userRegistrationTimeout(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLoginActivityTimeout(user.getId());
    }

    @PostMapping("/userRegisterationError")
    public String userRegisterationError(@RequestBody User user){

        System.out.println("用户注册 成功" + user.getName());

        return activityService.firstLoginActivityError(user.getId());
    }



}
