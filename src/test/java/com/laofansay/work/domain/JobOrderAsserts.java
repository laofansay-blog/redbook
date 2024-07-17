package com.laofansay.work.domain;

import static com.laofansay.work.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class JobOrderAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJobOrderAllPropertiesEquals(JobOrder expected, JobOrder actual) {
        assertJobOrderAutoGeneratedPropertiesEquals(expected, actual);
        assertJobOrderAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJobOrderAllUpdatablePropertiesEquals(JobOrder expected, JobOrder actual) {
        assertJobOrderUpdatableFieldsEquals(expected, actual);
        assertJobOrderUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJobOrderAutoGeneratedPropertiesEquals(JobOrder expected, JobOrder actual) {
        assertThat(expected)
            .as("Verify JobOrder auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJobOrderUpdatableFieldsEquals(JobOrder expected, JobOrder actual) {
        assertThat(expected)
            .as("Verify JobOrder relevant properties")
            .satisfies(e -> assertThat(e.getSettlementOrderNo()).as("check settlementOrderNo").isEqualTo(actual.getSettlementOrderNo()))
            .satisfies(e -> assertThat(e.getAmount()).as("check amount").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getAmount()))
            .satisfies(e -> assertThat(e.getPaymentStatus()).as("check paymentStatus").isEqualTo(actual.getPaymentStatus()))
            .satisfies(e -> assertThat(e.getSettlementDate()).as("check settlementDate").isEqualTo(actual.getSettlementDate()))
            .satisfies(e -> assertThat(e.getPaymentDate()).as("check paymentDate").isEqualTo(actual.getPaymentDate()))
            .satisfies(e -> assertThat(e.getCustomerId()).as("check customerId").isEqualTo(actual.getCustomerId()))
            .satisfies(e -> assertThat(e.getChannel()).as("check channel").isEqualTo(actual.getChannel()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJobOrderUpdatableRelationshipsEquals(JobOrder expected, JobOrder actual) {
        assertThat(expected)
            .as("Verify JobOrder relationships")
            .satisfies(e -> assertThat(e.getJobResults()).as("check jobResults").isEqualTo(actual.getJobResults()));
    }
}