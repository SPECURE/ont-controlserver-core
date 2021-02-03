package com.specure.core.service;

import org.springframework.http.ResponseEntity;

public interface OpenDataService {
    ResponseEntity<Object> getOpenDataMonthlyExport(Integer year, Integer month, String fileExtension, String label);
    ResponseEntity<Object> getOpenDataFullExport(String fileExtension, String label);
}
