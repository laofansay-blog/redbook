package com.laofansay.work.domain.enumeration;

/**
 * The ChannelCate enumeration.
 */
public enum ChannelCate {
    SmallRedBook("小红书"),
    ByteDance("抖音"),
    MeiTuan("美团");

    private final String value;

    ChannelCate(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
