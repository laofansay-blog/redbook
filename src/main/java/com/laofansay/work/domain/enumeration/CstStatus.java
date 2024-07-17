package com.laofansay.work.domain.enumeration;

/**
 * The CstStatus enumeration.
 */
public enum CstStatus {
    ENABLED("启用"),
    DISABLED("停用"),
    LOCKED("锁定");

    private final String value;

    CstStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
