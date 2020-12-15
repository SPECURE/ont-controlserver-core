package com.specture.core.service;

import com.specture.core.request.MeasurementQosParametersRequest;
import com.specture.core.request.MeasurementQosRequest;
import com.specture.core.response.measurement.qos.response.MeasurementQosParametersResponse;

public interface MeasurementQosService {
    void saveMeasurementQos(MeasurementQosRequest measurementQosRequest);
    MeasurementQosParametersResponse getQosParameters(MeasurementQosParametersRequest measurementQosParametersRequest);
}
