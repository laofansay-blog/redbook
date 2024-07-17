package com.laofansay.work.domain;

import static com.laofansay.work.domain.CstAccountTestSamples.*;
import static com.laofansay.work.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.laofansay.work.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CstAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CstAccount.class);
        CstAccount cstAccount1 = getCstAccountSample1();
        CstAccount cstAccount2 = new CstAccount();
        assertThat(cstAccount1).isNotEqualTo(cstAccount2);

        cstAccount2.setId(cstAccount1.getId());
        assertThat(cstAccount1).isEqualTo(cstAccount2);

        cstAccount2 = getCstAccountSample2();
        assertThat(cstAccount1).isNotEqualTo(cstAccount2);
    }

    @Test
    void customerTest() {
        CstAccount cstAccount = getCstAccountRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        cstAccount.setCustomer(customerBack);
        assertThat(cstAccount.getCustomer()).isEqualTo(customerBack);

        cstAccount.customer(null);
        assertThat(cstAccount.getCustomer()).isNull();
    }
}
