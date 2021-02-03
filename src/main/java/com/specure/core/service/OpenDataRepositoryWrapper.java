package com.specure.core.service;

import com.specure.core.enums.MeasurementStatus;
import com.specure.core.model.OpenData;

import java.sql.Timestamp;
import java.util.List;

public interface OpenDataRepositoryWrapper {
    List<OpenData> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, MeasurementStatus status);
    List<OpenData> findAllByStatus(MeasurementStatus status);
    String getSourceLabel();
}
