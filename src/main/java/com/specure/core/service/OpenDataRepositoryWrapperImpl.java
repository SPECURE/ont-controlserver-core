package com.specure.core.service;

import com.specure.core.enums.MeasurementStatus;
import com.specure.core.model.OpenData;
import com.specure.core.repository.OpenDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
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
