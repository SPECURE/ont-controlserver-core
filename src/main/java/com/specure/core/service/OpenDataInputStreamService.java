package com.specure.core.service;

import java.sql.Timestamp;
import java.util.List;

public interface OpenDataInputStreamService {
    List<Object> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd);
    List<Object> findAllByStatus();
    String getSourceLabel();
    Class<?> getOpenDataClass();
    Class<?> getOpenDataListClass();
    Object getOpenDataList(List<Object> data);
}
