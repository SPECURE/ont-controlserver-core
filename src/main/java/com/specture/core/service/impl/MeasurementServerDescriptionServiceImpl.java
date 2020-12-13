package com.specture.core.service.impl;

import com.specture.core.model.MeasurementServerDescription;
import com.specture.core.service.MeasurementServerDescriptionService;
import com.specture.core.repository.MeasurementServerDescriptionRepository;
import com.specture.core.service.MeasurementServerDescriptionService;
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
