package com.specture.core.mapper;

import com.specture.core.model.MeasurementServerDescription;
import com.specture.core.request.MeasurementServerDescriptionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementServerDescriptionMapper {
    MeasurementServerDescription measurementServerDescriptionRequestToMeasurementServerDescription(MeasurementServerDescriptionRequest measurementServerDescriptionRequest);
}
