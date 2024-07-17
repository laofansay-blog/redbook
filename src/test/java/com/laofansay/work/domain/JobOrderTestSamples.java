package com.laofansay.work.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JobOrderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static JobOrder getJobOrderSample1() {
        return new JobOrder().id("id1").settlementOrderNo("settlementOrderNo1").customerId(1L).channel("channel1");
    }

    public static JobOrder getJobOrderSample2() {
        return new JobOrder().id("id2").settlementOrderNo("settlementOrderNo2").customerId(2L).channel("channel2");
    }

    public static JobOrder getJobOrderRandomSampleGenerator() {
        return new JobOrder()
            .id(UUID.randomUUID().toString())
            .settlementOrderNo(UUID.randomUUID().toString())
            .customerId(longCount.incrementAndGet())
            .channel(UUID.randomUUID().toString());
    }
}
