package com.specure.core.service;

import com.specure.core.model.MeasurementServerDescription;

public interface MeasurementServerDescriptionService {
    MeasurementServerDescription save(MeasurementServerDescription measurementServerDescription);
    void deleteById(Long id);
    boolean existById(Long id);
}
