package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.model.OpenDataExportList;

import java.sql.Timestamp;

public interface OpenDataInputStreamService {
    OpenDataExportList<?> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, DigitalSeparator digitalSeparator);
    OpenDataExportList<?> findAllByStatus(DigitalSeparator digitalSeparator);
    String getSourceLabel();
    Class<?> getOpenDataClass();
}
