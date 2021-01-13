package com.specure.core.mapper;

import com.specure.core.model.qos.MeasurementQos;
import com.specure.core.request.MeasurementQosRequest;


public interface MeasurementQosMapper {
    MeasurementQos measurementQosRequestToMeasurementQos(MeasurementQosRequest measurementQosRequest);
}
