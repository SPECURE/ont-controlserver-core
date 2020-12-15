package com.specture.core.enums;

public enum Direction {
    download("download"),
    upload("upload");

    private final String value;

    Direction(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
