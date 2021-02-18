package com.specure.core.mapper;

import com.specure.core.model.MobileMeasurement;
import com.specure.core.response.SignalMeasurementResponse;

public interface SignalMapper {
    SignalMeasurementResponse signalToSignalMeasurementResponse(MobileMeasurement test);
}
