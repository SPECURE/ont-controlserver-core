package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import org.springframework.http.ResponseEntity;

public interface OpenDataService {
    ResponseEntity<Object> getOpenDataMonthlyExport(Integer year, Integer month, String fileExtension, DigitalSeparator digitalSeparator, char listSeparator);
    ResponseEntity<Object> getOpenDataFullExport(String fileExtension, DigitalSeparator digitalSeparator, char listSeparator);
}
