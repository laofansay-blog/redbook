package com.laofansay.work.domain;

import static com.laofansay.work.domain.ChannelsTestSamples.*;
import static com.laofansay.work.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChannelsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Channels.class);
        Channels channels1 = getChannelsSample1();
        Channels channels2 = new Channels();
        assertThat(channels1).isNotEqualTo(channels2);

        channels2.setId(channels1.getId());
        assertThat(channels1).isEqualTo(channels2);

        channels2 = getChannelsSample2();
        assertThat(channels1).isNotEqualTo(channels2);
    }

    @Test
    void customerTest() {
        Channels channels = getChannelsRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        channels.addCustomer(customerBack);
        assertThat(channels.getCustomers()).containsOnly(customerBack);
        assertThat(customerBack.getChannels()).isEqualTo(channels);

        channels.removeCustomer(customerBack);
        assertThat(channels.getCustomers()).doesNotContain(customerBack);
        assertThat(customerBack.getChannels()).isNull();

        channels.customers(new HashSet<>(Set.of(customerBack)));
        assertThat(channels.getCustomers()).containsOnly(customerBack);
        assertThat(customerBack.getChannels()).isEqualTo(channels);

        channels.setCustomers(new HashSet<>());
        assertThat(channels.getCustomers()).doesNotContain(customerBack);
        assertThat(customerBack.getChannels()).isNull();
    }
}
