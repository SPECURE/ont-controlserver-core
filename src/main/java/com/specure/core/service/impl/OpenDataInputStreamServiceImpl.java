package com.specure.core.service.impl;

import com.specure.core.enums.MeasurementStatus;
import com.specure.core.mapper.OpenDataMapper;
import com.specure.core.model.OpenDataExport;
import com.specure.core.model.OpenDataExportList;
import com.specure.core.repository.OpenDataRepository;
import com.specure.core.service.OpenDataInputStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenDataInputStreamServiceImpl implements OpenDataInputStreamService {



    private final OpenDataRepository openDataRepository;
    private final OpenDataMapper openDataMapper;

    @Override
    public List<Object> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd ) {
        return openDataRepository
                .findAllByTimeBetweenAndStatus(timeStart, timeEnd, MeasurementStatus.FINISHED)
                .stream()
                .map(openDataMapper::openDataToOpenDataExport)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object> findAllByStatus() {
        return openDataRepository.findAllByStatus(MeasurementStatus.FINISHED).stream()
                .map(openDataMapper::openDataToOpenDataExport)
                .collect(Collectors.toList());
    }

    @Override
    public String getSourceLabel() {
        return "postgreSQL";
    }

    @Override
    public Class<?> getOpenDataClass() {
        return OpenDataExport.class;
    }

    @Override
    public Class<?> getOpenDataListClass() {
        return OpenDataExportList.class;
    }

    @Override
    public Object getOpenDataList(List<Object> data) {
        return OpenDataExportList.builder()
                .openDataExport(data)
                .build();
    }
}
