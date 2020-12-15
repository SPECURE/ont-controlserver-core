package com.specture.core.mapper;

import com.specture.core.model.qos.MeasurementQos;
import com.specture.core.request.MeasurementQosRequest;


public interface MeasurementQosMapper {
    MeasurementQos measurementQosRequestToMeasurementQos(MeasurementQosRequest measurementQosRequest);
}
