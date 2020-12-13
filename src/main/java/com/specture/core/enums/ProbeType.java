package com.specture.core.enums;

import lombok.Getter;

@Getter
public enum ProbeType {
    OPEN_NETTEST_BEATBOX_M("OPEN_NETTEST_BEATBOX_M");

    private final String name;

    ProbeType(String name) {
        this.name = name;
    }
}
