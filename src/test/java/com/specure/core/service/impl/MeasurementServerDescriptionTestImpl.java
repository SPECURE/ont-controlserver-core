package com.specure.core.service.impl;

import com.specure.core.model.MeasurementServerDescription;
import com.specure.core.repository.MeasurementServerDescriptionRepository;
import com.specure.core.service.MeasurementServerDescriptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.specure.core.TestConstants.DEFAULT_ID;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class MeasurementServerDescriptionTestImpl {

    @MockBean
    private MeasurementServerDescriptionRepository measurementServerDescriptionRepository;
    @Mock
    private MeasurementServerDescription measurementServerDescription;

    private MeasurementServerDescriptionService measurementServerDescriptionService;

    @Before
    public void setUp() {
        measurementServerDescriptionService =
                new MeasurementServerDescriptionServiceImpl(measurementServerDescriptionRepository);
    }

    @Test
    public void save_correctInvocation_saved() {
        measurementServerDescriptionService.save(measurementServerDescription);
        verify(measurementServerDescriptionRepository).save(measurementServerDescription);
    }

    @Test
    public void deleteById_correctInvocation_removed() {
        measurementServerDescriptionService.deleteById(DEFAULT_ID);
        verify(measurementServerDescriptionRepository).deleteById(DEFAULT_ID);
    }

    @Test
    public void existById_correctInvocation_repositoryCall() {
        measurementServerDescriptionService.existById(DEFAULT_ID);
        verify(measurementServerDescriptionRepository).existsById(DEFAULT_ID);
    }

}
