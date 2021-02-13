package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.model.OpenDataExportList;

import java.sql.Timestamp;

public interface OpenDataInputStreamService {
    OpenDataExportList<?> getAllByTimeBetweenWithSeparator(Timestamp timeStart, Timestamp timeEnd, DigitalSeparator digitalSeparator);
    OpenDataExportList<?> getAllOpenDataWithSeparator(DigitalSeparator digitalSeparator);
    String getSourceLabel();
    Class<?> getOpenDataClass();
}
