package com.specture.core.mapper;

import com.specture.core.model.Measurement;
import com.specture.core.request.MeasurementRequest;
import com.specture.core.response.MeasurementHistoryResponse;

public interface MeasurementMapper {
    Measurement measurementRequestToMeasurement(MeasurementRequest measurementResultRequest);
    MeasurementHistoryResponse measurementToMeasurementHistoryResponse(Measurement measurement);
}
