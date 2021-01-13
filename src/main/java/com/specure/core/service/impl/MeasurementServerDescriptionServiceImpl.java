package com.specure.core.service.impl;

import com.specure.core.model.MeasurementServerDescription;
import com.specure.core.service.MeasurementServerDescriptionService;
import com.specure.core.repository.MeasurementServerDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MeasurementServerDescriptionServiceImpl implements MeasurementServerDescriptionService {
    final private MeasurementServerDescriptionRepository measurementServerDescriptionRepository;

    @Override
    public MeasurementServerDescription save(MeasurementServerDescription measurementServerDescription) {
        return measurementServerDescriptionRepository.save(measurementServerDescription);
    }

    @Override
    public void deleteById(Long id) {
        measurementServerDescriptionRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return measurementServerDescriptionRepository.existsById(id);
    }
}
