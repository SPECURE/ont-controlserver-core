package com.specture.core.service.impl;

import com.specture.core.config.MeasurementServerConfig;
import com.specture.core.exception.ProviderAlreadyExistsException;
import com.specture.core.exception.ProviderNotFoundException;
import com.specture.core.mapper.ProviderMapper;
import com.specture.core.model.Provider;
import com.specture.core.repository.ProviderRepository;
import com.specture.core.request.ProviderRequest;
import com.specture.core.response.ProviderResponse;
import com.specture.core.service.ProviderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static com.specture.core.TestConstants.DEFAULT_ID;
import static com.specture.core.TestConstants.DEFAULT_PROVIDER_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProviderServiceImplTest {

    @MockBean
    private ProviderRepository providerRepository;
    @MockBean
    private ProviderMapper providerMapper;
    @MockBean
    private MeasurementServerConfig measurementServerConfig;

    @Mock
    private Provider provider;
    @Mock
    private ProviderResponse providerResponse;

    private ProviderService providerService;

    @Before
    public void setUp() {
        providerService = new ProviderServiceImpl(providerRepository, providerMapper, measurementServerConfig);
    }

    @Test
    public void getAllProviders_correct_listReturned() {
        when(providerRepository.findAll()).thenReturn(Collections.singletonList(provider));
        when(providerMapper.providerToProviderResponse(provider)).thenReturn(providerResponse);
        assertEquals(Collections.singletonList(providerResponse), providerService.getAllProviders());
    }

    @Test
    public void createNewProvider_WhenCall_RunRepository() {
        ProviderRequest providerRequest = ProviderRequest.builder().name(DEFAULT_PROVIDER_NAME).build();
        Provider provider = Provider.builder().name(DEFAULT_PROVIDER_NAME).build();

        when(providerMapper.providerRequestToProvider(providerRequest)).thenReturn(provider);

        providerService.createNewProvider(providerRequest);
        verify(providerRepository).save(provider);
    }

    @Test
    public void updateProvider_WhenCall_RunRepository() {
        ProviderRequest providerRequest = ProviderRequest.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();

        Provider provider = Provider.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();


        when(providerMapper.providerRequestToProvider(providerRequest)).thenReturn(provider);
        when(providerRepository.existsById(DEFAULT_ID)).thenReturn(true);

        providerService.updateProvider(providerRequest);
        verify(providerRepository).save(provider);
    }

    @Test(expected = ProviderNotFoundException.class)
    public void updateProvider_WhenCallWithoutId_ExpectThrow() {
        ProviderRequest providerRequest = ProviderRequest.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();

        Provider provider = Provider.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();


        when(providerMapper.providerRequestToProvider(providerRequest)).thenReturn(provider);
        when(providerRepository.existsById(DEFAULT_ID)).thenReturn(false);

        providerService.updateProvider(providerRequest);
        verify(providerRepository, never()).save(provider);
    }

    @Test(expected = ProviderAlreadyExistsException.class)
    public void createProvider_WhenCallWithExistedId_ExpectThrow() {
        ProviderRequest providerRequest = ProviderRequest.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();

        Provider provider = Provider.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_PROVIDER_NAME)
            .build();


        when(providerMapper.providerRequestToProvider(providerRequest)).thenReturn(provider);
        when(providerRepository.existsById(DEFAULT_ID)).thenReturn(true);

        providerService.createNewProvider(providerRequest);
        verify(providerRepository, never()).save(provider);
    }
}
