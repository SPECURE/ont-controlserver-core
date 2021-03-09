package com.specure.core.model;

import java.time.LocalDateTime;
import java.util.List;


public interface OpenDataFilter {
    List<Long> getPackages();
    List<String> getProbes();
    LocalDateTime getFrom();
    LocalDateTime getTo();
}
