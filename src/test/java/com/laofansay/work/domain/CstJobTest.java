package com.laofansay.work.domain;

import static com.laofansay.work.domain.CstJobTestSamples.*;
import static com.laofansay.work.domain.CustomerTestSamples.*;
import static com.laofansay.work.domain.JobResultTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CstJobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CstJob.class);
        CstJob cstJob1 = getCstJobSample1();
        CstJob cstJob2 = new CstJob();
        assertThat(cstJob1).isNotEqualTo(cstJob2);

        cstJob2.setId(cstJob1.getId());
        assertThat(cstJob1).isEqualTo(cstJob2);

        cstJob2 = getCstJobSample2();
        assertThat(cstJob1).isNotEqualTo(cstJob2);
    }

    @Test
    void jobResultTest() {
        CstJob cstJob = getCstJobRandomSampleGenerator();
        JobResult jobResultBack = getJobResultRandomSampleGenerator();

        cstJob.addJobResult(jobResultBack);
        assertThat(cstJob.getJobResults()).containsOnly(jobResultBack);
        assertThat(jobResultBack.getCstJob()).isEqualTo(cstJob);

        cstJob.removeJobResult(jobResultBack);
        assertThat(cstJob.getJobResults()).doesNotContain(jobResultBack);
        assertThat(jobResultBack.getCstJob()).isNull();

        cstJob.jobResults(new HashSet<>(Set.of(jobResultBack)));
        assertThat(cstJob.getJobResults()).containsOnly(jobResultBack);
        assertThat(jobResultBack.getCstJob()).isEqualTo(cstJob);

        cstJob.setJobResults(new HashSet<>());
        assertThat(cstJob.getJobResults()).doesNotContain(jobResultBack);
        assertThat(jobResultBack.getCstJob()).isNull();
    }

    @Test
    void customerTest() {
        CstJob cstJob = getCstJobRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        cstJob.setCustomer(customerBack);
        assertThat(cstJob.getCustomer()).isEqualTo(customerBack);

        cstJob.customer(null);
        assertThat(cstJob.getCustomer()).isNull();
    }
}
