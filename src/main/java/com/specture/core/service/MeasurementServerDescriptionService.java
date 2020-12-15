package com.specture.core.service;

import com.specture.core.model.MeasurementServerDescription;

public interface MeasurementServerDescriptionService {
    MeasurementServerDescription save(MeasurementServerDescription measurementServerDescription);
    void deleteById(Long id);
    boolean existById(Long id);
}
