package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.model.OpenDataFilter;
import org.springframework.http.ResponseEntity;

public interface OpenDataService {
    ResponseEntity<Object> getOpenDataMonthlyExport(
            Integer year,
            Integer month,
            String fileExtension,
            DigitalSeparator digitalSeparator,
            char listSeparator,
            OpenDataFilter openDataFilter,
            String dataSource
    );
    ResponseEntity<Object> getOpenDataFullExport(
            String fileExtension,
            DigitalSeparator digitalSeparator,
            char listSeparator,
            OpenDataFilter openDataFilter,
            String dataSource
    );
}
