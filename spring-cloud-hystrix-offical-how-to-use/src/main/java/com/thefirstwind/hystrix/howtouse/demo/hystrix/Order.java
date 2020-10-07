package com.thefirstwind.hystrix.howtouse.demo.hystrix;
import com.thefirstwind.hystrix.howtouse.demo.hystrix.GetUserAccountCommand;
import com.thefirstwind.hystrix.howtouse.demo.model.UserAccount;

import java.net.HttpCookie;

public class Order {
    private final int orderId;
    private UserAccount user;

    public Order(int orderId) {
        this.orderId = orderId;

        /* a contrived example of calling GetUserAccount again */
        user = new GetUserAccountCommand(new HttpCookie("mockKey", "mockValueFromHttpRequest")).execute();
    }

}
