package com.specture.core.enums;

public enum Measurements {
    VOIP("voip"),
    UDP("udp"),
    NON_TRANSPARENT_PROXY("non_transparent_proxy"),
    HTTP_PROXY("http_proxy"),
    DNS("dns"),
    TCP("tcp");

    private String value;

    Measurements(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
