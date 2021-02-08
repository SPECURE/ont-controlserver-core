package com.specure.core.service.impl;

import com.specure.core.enums.MeasurementStatus;
import com.specure.core.model.OpenData;
import com.specure.core.repository.OpenDataRepository;
import com.specure.core.service.OpenDataRepositoryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Component
public class OpenDataRepositoryWrapperImpl implements OpenDataRepositoryWrapper {

    private final OpenDataRepository openDataRepository;


    @Override
    public List<OpenData> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd, MeasurementStatus status) {
        return openDataRepository.findAllByTimeBetweenAndStatus(timeStart, timeEnd, status);
    }

    @Override
    public List<OpenData> findAllByStatus(MeasurementStatus status) {
        return openDataRepository.findAllByStatus(status);
    }

    @Override
    public String getSourceLabel() {
        return "postgreSQL";
    }
}
