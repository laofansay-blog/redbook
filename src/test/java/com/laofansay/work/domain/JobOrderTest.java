package com.laofansay.work.domain;

import static com.laofansay.work.domain.JobOrderTestSamples.*;
import static com.laofansay.work.domain.JobResultTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JobOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOrder.class);
        JobOrder jobOrder1 = getJobOrderSample1();
        JobOrder jobOrder2 = new JobOrder();
        assertThat(jobOrder1).isNotEqualTo(jobOrder2);

        jobOrder2.setId(jobOrder1.getId());
        assertThat(jobOrder1).isEqualTo(jobOrder2);

        jobOrder2 = getJobOrderSample2();
        assertThat(jobOrder1).isNotEqualTo(jobOrder2);
    }

    @Test
    void jobResultTest() {
        JobOrder jobOrder = getJobOrderRandomSampleGenerator();
        JobResult jobResultBack = getJobResultRandomSampleGenerator();

        jobOrder.addJobResult(jobResultBack);
        assertThat(jobOrder.getJobResults()).containsOnly(jobResultBack);

        jobOrder.removeJobResult(jobResultBack);
        assertThat(jobOrder.getJobResults()).doesNotContain(jobResultBack);

        jobOrder.jobResults(new HashSet<>(Set.of(jobResultBack)));
        assertThat(jobOrder.getJobResults()).containsOnly(jobResultBack);

        jobOrder.setJobResults(new HashSet<>());
        assertThat(jobOrder.getJobResults()).doesNotContain(jobResultBack);
    }
}
