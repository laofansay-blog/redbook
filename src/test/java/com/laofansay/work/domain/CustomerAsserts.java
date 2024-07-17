package com.laofansay.work.domain;

import static com.laofansay.work.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCustomerAllPropertiesEquals(Customer expected, Customer actual) {
        assertCustomerAutoGeneratedPropertiesEquals(expected, actual);
        assertCustomerAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCustomerAllUpdatablePropertiesEquals(Customer expected, Customer actual) {
        assertCustomerUpdatableFieldsEquals(expected, actual);
        assertCustomerUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCustomerAutoGeneratedPropertiesEquals(Customer expected, Customer actual) {
        assertThat(expected)
            .as("Verify Customer auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCustomerUpdatableFieldsEquals(Customer expected, Customer actual) {
        assertThat(expected)
            .as("Verify Customer relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getMobile()).as("check mobile").isEqualTo(actual.getMobile()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getIntroduce()).as("check introduce").isEqualTo(actual.getIntroduce()))
            .satisfies(
                e -> assertThat(e.getBalance()).as("check balance").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getBalance())
            )
            .satisfies(e -> assertThat(e.getTimes()).as("check times").isEqualTo(actual.getTimes()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCustomerUpdatableRelationshipsEquals(Customer expected, Customer actual) {
        assertThat(expected)
            .as("Verify Customer relationships")
            .satisfies(e -> assertThat(e.getChannels()).as("check channels").isEqualTo(actual.getChannels()));
    }
}
