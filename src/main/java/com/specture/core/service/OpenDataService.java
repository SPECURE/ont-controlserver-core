package com.specture.core.service;

import org.springframework.http.ResponseEntity;

public interface OpenDataService {
    ResponseEntity<Object> getOpenDataMonthlyExport(Integer year, Integer month, String fileExtension);
    ResponseEntity<Object> getOpenDataFullExport(String fileExtension);
}
