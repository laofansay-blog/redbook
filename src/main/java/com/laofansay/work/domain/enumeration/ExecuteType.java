package com.laofansay.work.domain.enumeration;

/**
 * The ExecuteType enumeration.
 */
public enum ExecuteType {
    ONCE("行行一次"),
    DAY("每次天执行");

    private final String value;

    ExecuteType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
