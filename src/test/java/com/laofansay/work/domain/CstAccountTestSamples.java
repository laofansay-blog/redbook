package com.laofansay.work.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CstAccountTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CstAccount getCstAccountSample1() {
        return new CstAccount()
            .id("id1")
            .name("name1")
            .provider("provider1")
            .rbAccount("rbAccount1")
            .rbPwd("rbPwd1")
            .rbToken("rbToken1")
            .timesByDay(1)
            .channel("channel1");
    }

    public static CstAccount getCstAccountSample2() {
        return new CstAccount()
            .id("id2")
            .name("name2")
            .provider("provider2")
            .rbAccount("rbAccount2")
            .rbPwd("rbPwd2")
            .rbToken("rbToken2")
            .timesByDay(2)
            .channel("channel2");
    }

    public static CstAccount getCstAccountRandomSampleGenerator() {
        return new CstAccount()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .provider(UUID.randomUUID().toString())
            .rbAccount(UUID.randomUUID().toString())
            .rbPwd(UUID.randomUUID().toString())
            .rbToken(UUID.randomUUID().toString())
            .timesByDay(intCount.incrementAndGet())
            .channel(UUID.randomUUID().toString());
    }
}
