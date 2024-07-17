package com.laofansay.work.domain;

import static com.laofansay.work.domain.ChannelsTestSamples.*;
import static com.laofansay.work.domain.CstAccountTestSamples.*;
import static com.laofansay.work.domain.CstJobTestSamples.*;
import static com.laofansay.work.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = getCustomerSample1();
        Customer customer2 = new Customer();
        assertThat(customer1).isNotEqualTo(customer2);

        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);

        customer2 = getCustomerSample2();
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    void cstAccountTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        CstAccount cstAccountBack = getCstAccountRandomSampleGenerator();

        customer.addCstAccount(cstAccountBack);
        assertThat(customer.getCstAccounts()).containsOnly(cstAccountBack);
        assertThat(cstAccountBack.getCustomer()).isEqualTo(customer);

        customer.removeCstAccount(cstAccountBack);
        assertThat(customer.getCstAccounts()).doesNotContain(cstAccountBack);
        assertThat(cstAccountBack.getCustomer()).isNull();

        customer.cstAccounts(new HashSet<>(Set.of(cstAccountBack)));
        assertThat(customer.getCstAccounts()).containsOnly(cstAccountBack);
        assertThat(cstAccountBack.getCustomer()).isEqualTo(customer);

        customer.setCstAccounts(new HashSet<>());
        assertThat(customer.getCstAccounts()).doesNotContain(cstAccountBack);
        assertThat(cstAccountBack.getCustomer()).isNull();
    }

    @Test
    void cstJobTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        CstJob cstJobBack = getCstJobRandomSampleGenerator();

        customer.addCstJob(cstJobBack);
        assertThat(customer.getCstJobs()).containsOnly(cstJobBack);
        assertThat(cstJobBack.getCustomer()).isEqualTo(customer);

        customer.removeCstJob(cstJobBack);
        assertThat(customer.getCstJobs()).doesNotContain(cstJobBack);
        assertThat(cstJobBack.getCustomer()).isNull();

        customer.cstJobs(new HashSet<>(Set.of(cstJobBack)));
        assertThat(customer.getCstJobs()).containsOnly(cstJobBack);
        assertThat(cstJobBack.getCustomer()).isEqualTo(customer);

        customer.setCstJobs(new HashSet<>());
        assertThat(customer.getCstJobs()).doesNotContain(cstJobBack);
        assertThat(cstJobBack.getCustomer()).isNull();
    }

    @Test
    void channelsTest() {
        Customer customer = getCustomerRandomSampleGenerator();
        Channels channelsBack = getChannelsRandomSampleGenerator();

        customer.setChannels(channelsBack);
        assertThat(customer.getChannels()).isEqualTo(channelsBack);

        customer.channels(null);
        assertThat(customer.getChannels()).isNull();
    }
}
