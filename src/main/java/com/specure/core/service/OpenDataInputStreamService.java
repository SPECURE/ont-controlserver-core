package com.specure.core.service;

import com.specure.core.model.OpenDataExportList;

import java.sql.Timestamp;

public interface OpenDataInputStreamService {
    OpenDataExportList<?> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd);
    OpenDataExportList<?> findAllByStatus();
    String getSourceLabel();
    Class<?> getOpenDataClass();
}
