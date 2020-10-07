package com.thefirstwind.hystrix.howtouse.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.thefirstwind.hystrix.howtouse.demo.model.PaymentInformation;
import com.thefirstwind.hystrix.howtouse.demo.model.UserAccount;

/**
 * Sample HystrixCommand simulating one that would fetch PaymentInformation objects from a remote service or database.
 * <p>
 * This fails fast with no fallback and does not use request caching.
 */
public class GetPaymentInformationCommand extends HystrixCommand<PaymentInformation> {

    private final UserAccount user;

    public GetPaymentInformationCommand(UserAccount user) {
        super(HystrixCommandGroupKey.Factory.asKey("PaymentInformation"));
        this.user = user;
    }

    @Override
    protected PaymentInformation run() {
        /* simulate performing network call to retrieve order */
        try {
            Thread.sleep((int) (Math.random() * 20) + 5);
        } catch (InterruptedException e) {
            // do nothing
        }

        /* fail rarely ... but allow failure */
        if (Math.random() > 0.9999) {
            throw new RuntimeException("random failure loading payment information over network");
        }

        /* latency spike 2% of the time */
        if (Math.random() > 0.98) {
            // random latency spike
            try {
                Thread.sleep((int) (Math.random() * 100) + 25);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        /* success ... create (a very insecure) PaymentInformation with data "from" the remote service response */
        return new PaymentInformation(user, "4444888833337777", 12, 15);
    }

}
