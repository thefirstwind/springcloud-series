package com.thefirstwind.hystrix.howtouse.demo.model;

public class PaymentInformation {
    private final UserAccount user;
    private final String creditCardNumber;
    private final int expirationMonth;
    private final int expirationYear;

    public PaymentInformation(UserAccount user, String creditCardNumber, int expirationMonth, int expirationYear) {
        this.user = user;
        this.creditCardNumber = creditCardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }


}
