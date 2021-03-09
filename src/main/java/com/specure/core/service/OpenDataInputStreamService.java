package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.model.OpenDataExportList;
import com.specure.core.model.OpenDataFilter;

import java.sql.Timestamp;

public interface OpenDataInputStreamService {
    OpenDataExportList<?> getAllByTimeBetweenWithSeparator(
            Timestamp timeStart,
            Timestamp timeEnd,
            DigitalSeparator digitalSeparator,
            OpenDataFilter openDataFilter
    );
    OpenDataExportList<?> getAllOpenDataWithSeparator(
            DigitalSeparator digitalSeparator,
            OpenDataFilter openDataFilter
    );
    String getSourceLabel();
    Class<?> getOpenDataClass();
}
