package com.laofansay.work.domain;

import static com.laofansay.work.domain.CstJobTestSamples.*;
import static com.laofansay.work.domain.JobOrderTestSamples.*;
import static com.laofansay.work.domain.JobResultTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JobResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobResult.class);
        JobResult jobResult1 = getJobResultSample1();
        JobResult jobResult2 = new JobResult();
        assertThat(jobResult1).isNotEqualTo(jobResult2);

        jobResult2.setId(jobResult1.getId());
        assertThat(jobResult1).isEqualTo(jobResult2);

        jobResult2 = getJobResultSample2();
        assertThat(jobResult1).isNotEqualTo(jobResult2);
    }

    @Test
    void cstJobTest() {
        JobResult jobResult = getJobResultRandomSampleGenerator();
        CstJob cstJobBack = getCstJobRandomSampleGenerator();

        jobResult.setCstJob(cstJobBack);
        assertThat(jobResult.getCstJob()).isEqualTo(cstJobBack);

        jobResult.cstJob(null);
        assertThat(jobResult.getCstJob()).isNull();
    }

    @Test
    void jobOrderTest() {
        JobResult jobResult = getJobResultRandomSampleGenerator();
        JobOrder jobOrderBack = getJobOrderRandomSampleGenerator();

        jobResult.addJobOrder(jobOrderBack);
        assertThat(jobResult.getJobOrders()).containsOnly(jobOrderBack);
        assertThat(jobOrderBack.getJobResults()).containsOnly(jobResult);

        jobResult.removeJobOrder(jobOrderBack);
        assertThat(jobResult.getJobOrders()).doesNotContain(jobOrderBack);
        assertThat(jobOrderBack.getJobResults()).doesNotContain(jobResult);

        jobResult.jobOrders(new HashSet<>(Set.of(jobOrderBack)));
        assertThat(jobResult.getJobOrders()).containsOnly(jobOrderBack);
        assertThat(jobOrderBack.getJobResults()).containsOnly(jobResult);

        jobResult.setJobOrders(new HashSet<>());
        assertThat(jobResult.getJobOrders()).doesNotContain(jobOrderBack);
        assertThat(jobOrderBack.getJobResults()).doesNotContain(jobResult);
    }
}
