package com.specure.core.service;

import com.specure.core.request.MeasurementQosParametersRequest;
import com.specure.core.request.MeasurementQosRequest;
import com.specure.core.response.measurement.qos.response.MeasurementQosParametersResponse;

public interface MeasurementQosService {
    void saveMeasurementQos(MeasurementQosRequest measurementQosRequest);
    MeasurementQosParametersResponse getQosParameters(MeasurementQosParametersRequest measurementQosParametersRequest);
    public void deleteByOpenUUID(String uuid);
}
