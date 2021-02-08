package com.specure.core.service.impl;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator {
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
