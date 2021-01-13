package com.specure.core.mapper;

import com.specure.core.model.MeasurementServerDescription;
import com.specure.core.request.MeasurementServerDescriptionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MeasurementServerDescriptionMapper {
    MeasurementServerDescription measurementServerDescriptionRequestToMeasurementServerDescription(MeasurementServerDescriptionRequest measurementServerDescriptionRequest);
}
