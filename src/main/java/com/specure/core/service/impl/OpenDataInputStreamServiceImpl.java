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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenDataInputStreamServiceImpl implements OpenDataInputStreamService {



    private final OpenDataRepository openDataRepository;
    private final OpenDataMapper openDataMapper;

    @Override
    public OpenDataExportList<OpenDataExport> findAllByTimeBetweenAndStatus(Timestamp timeStart, Timestamp timeEnd ) {

        var data =  openDataRepository
                .findAllByTimeBetweenAndStatus(timeStart, timeEnd, MeasurementStatus.FINISHED)
                .stream()
                .map(openDataMapper::openDataToOpenDataExport)
                .collect(Collectors.toList());

        return new OpenDataExportList<>(data);

    }

    @Override
    public OpenDataExportList<OpenDataExport> findAllByStatus() {
        var data =  openDataRepository.findAllByStatus(MeasurementStatus.FINISHED).stream()
                .map(openDataMapper::openDataToOpenDataExport)
                .collect(Collectors.toList());
        return new OpenDataExportList<>(data);
    }

    @Override
    public String getSourceLabel() {
        return "postgreSQL";
    }

    @Override
    public Class<?> getOpenDataClass() {
        return OpenDataExport.class;
    }

}
