package com.specure.core.mapper;

import com.specure.core.model.Measurement;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.response.MeasurementHistoryResponse;

public interface MeasurementMapper {
    Measurement measurementRequestToMeasurement(MeasurementRequest measurementResultRequest);
    MeasurementHistoryResponse measurementToMeasurementHistoryResponse(Measurement measurement);
}
