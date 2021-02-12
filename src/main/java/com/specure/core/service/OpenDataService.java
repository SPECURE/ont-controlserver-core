package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import org.springframework.http.ResponseEntity;

public interface OpenDataService {
    ResponseEntity<Object> getOpenDataMonthlyExport(Integer year, Integer month, String fileExtension, String label, DigitalSeparator digitalSeparator);
    ResponseEntity<Object> getOpenDataFullExport(String fileExtension, String label, DigitalSeparator digitalSeparator);
}
