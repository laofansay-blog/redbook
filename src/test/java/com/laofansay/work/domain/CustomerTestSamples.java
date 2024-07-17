package com.laofansay.work.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Customer getCustomerSample1() {
        return new Customer().id("id1").name("name1").mobile("mobile1").email("email1").introduce("introduce1").times(1);
    }

    public static Customer getCustomerSample2() {
        return new Customer().id("id2").name("name2").mobile("mobile2").email("email2").introduce("introduce2").times(2);
    }

    public static Customer getCustomerRandomSampleGenerator() {
        return new Customer()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .mobile(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .introduce(UUID.randomUUID().toString())
            .times(intCount.incrementAndGet());
    }
}
