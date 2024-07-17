package com.laofansay.work.domain;

import java.util.UUID;

public class JobResultTestSamples {

    public static JobResult getJobResultSample1() {
        return new JobResult()
            .id("id1")
            .name("name1")
            .jobUrl("jobUrl1")
            .authorName("authorName1")
            .accountId("accountId1")
            .customerId("customerId1")
            .jobNo("jobNo1")
            .replay("replay1")
            .replayTheme("replayTheme1")
            .errorMsg("errorMsg1");
    }

    public static JobResult getJobResultSample2() {
        return new JobResult()
            .id("id2")
            .name("name2")
            .jobUrl("jobUrl2")
            .authorName("authorName2")
            .accountId("accountId2")
            .customerId("customerId2")
            .jobNo("jobNo2")
            .replay("replay2")
            .replayTheme("replayTheme2")
            .errorMsg("errorMsg2");
    }

    public static JobResult getJobResultRandomSampleGenerator() {
        return new JobResult()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .jobUrl(UUID.randomUUID().toString())
            .authorName(UUID.randomUUID().toString())
            .accountId(UUID.randomUUID().toString())
            .customerId(UUID.randomUUID().toString())
            .jobNo(UUID.randomUUID().toString())
            .replay(UUID.randomUUID().toString())
            .replayTheme(UUID.randomUUID().toString())
            .errorMsg(UUID.randomUUID().toString());
    }
}
