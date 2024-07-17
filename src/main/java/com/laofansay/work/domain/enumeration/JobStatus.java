package com.laofansay.work.domain.enumeration;

/**
 * The JobStatus enumeration.
 */
public enum JobStatus {
    OVER("结束了"),
    READY("准备好了"),
    ERROR("异常");

    private final String value;

    JobStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
