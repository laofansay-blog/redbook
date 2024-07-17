package com.laofansay.work.domain;

import java.util.UUID;

public class CstJobTestSamples {

    public static CstJob getCstJobSample1() {
        return new CstJob().id("id1").name("name1").category("category1").jobProps("jobProps1");
    }

    public static CstJob getCstJobSample2() {
        return new CstJob().id("id2").name("name2").category("category2").jobProps("jobProps2");
    }

    public static CstJob getCstJobRandomSampleGenerator() {
        return new CstJob()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .category(UUID.randomUUID().toString())
            .jobProps(UUID.randomUUID().toString());
    }
}
