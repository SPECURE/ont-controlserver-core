package com.specure.core.service;

import com.specure.core.enums.DigitalSeparator;
import com.specure.core.model.FilterChip;
import com.specure.core.model.OpenDataExportList;

import java.sql.Timestamp;

public interface OpenDataInputStreamService {
    OpenDataExportList<?> getAllByTimeBetweenWithSeparator(
            Timestamp timeStart,
            Timestamp timeEnd,
            DigitalSeparator digitalSeparator,
            FilterChip filterChip
    );
    OpenDataExportList<?> getAllOpenDataWithSeparator(
            DigitalSeparator digitalSeparator,
            FilterChip filterChip
    );
    String getSourceLabel();
    Class<?> getOpenDataClass();
}
