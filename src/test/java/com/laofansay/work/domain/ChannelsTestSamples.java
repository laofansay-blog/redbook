package com.laofansay.work.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ChannelsTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Channels getChannelsSample1() {
        return new Channels().id("id1").name("name1").rate(1).props("props1");
    }

    public static Channels getChannelsSample2() {
        return new Channels().id("id2").name("name2").rate(2).props("props2");
    }

    public static Channels getChannelsRandomSampleGenerator() {
        return new Channels()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .rate(intCount.incrementAndGet())
            .props(UUID.randomUUID().toString());
    }
}
