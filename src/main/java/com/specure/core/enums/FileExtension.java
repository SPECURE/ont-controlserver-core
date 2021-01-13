package com.specure.core.enums;

public enum FileExtension {

    csv("csv"),
    json("json"),
    xml("xml");

    private final String value;

    FileExtension(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
