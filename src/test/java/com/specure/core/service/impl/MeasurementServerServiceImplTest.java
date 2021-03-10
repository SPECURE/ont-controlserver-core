package com.specure.core.service.impl;

import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.exception.MeasurementServerNotFoundException;
import com.specure.core.exception.ProviderNotFoundByIdException;
import com.specure.core.mapper.MeasurementServerMapper;
import com.specure.core.model.Measurement;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.MeasurementServerDescription;
import com.specure.core.model.Provider;
import com.specure.core.repository.MeasurementServerRepository;
import com.specure.core.request.MeasurementServerRequest;
import com.specure.core.response.MeasurementServerResponse;
import com.specure.core.service.JiraApiService;
import com.specure.core.service.MeasurementServerService;
import com.specure.core.service.MeasurementService;
import com.specure.core.service.ProviderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.specure.core.TestConstants.*;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MeasurementServerServiceImplTest {

    @MockBean
    private MeasurementServerRepository measurementServerRepository;
    @MockBean
    private JiraApiService jiraApiService;
    @MockBean
    private MeasurementServerMapper measurementServerMapper;
    @MockBean
    private ProviderService providerService;
    @MockBean
    private MeasurementService measurementService;
    @MockBean
    private MeasurementServerConfig measurementServerConfig;

    private MeasurementServerService measurementServerService;

    @Before
    public void setUp() {
        measurementServerService = new MeasurementServerServiceImpl(measurementServerRepository, measurementServerMapper,
            providerService, measurementService, jiraApiService, measurementServerConfig);
    }


    @Test
    public void getMeasurementServer_WhenCall_ExpectRepositoryRun() {
        MeasurementServer measurementServer = getMeasurementServerByDefault();
        MeasurementServerResponse measurementServerResponse = getMeasurementServerResponseByDefault();
        List<MeasurementServer> oneServer = Collections.singletonList(measurementServer);

        when(measurementServerRepository.findAll())
            .thenReturn(oneServer);
        when(measurementService.findLastMeasurementsForMeasurementServerIds(any()))
            .thenReturn(Collections.emptyList());
        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServer))
            .thenReturn(measurementServerResponse);

        measurementServerService.getMeasurementServers(null);

        verify(measurementServerRepository).findAll();
        verify(measurementServerRepository, never()).findByProvider(any());
    }

    @Test
    public void getMeasurementServer_WhenCallWithProviderId_ExpectRepositoryRunWithProviderFilter() {
        Provider provider = Provider.builder().id(DEFAULT_PROVIDER_ID).build();
        when(providerService.getProviderById(DEFAULT_PROVIDER_ID)).thenReturn(provider);

        measurementServerService.getMeasurementServers(DEFAULT_PROVIDER_ID);

        verify(measurementServerRepository).findByProvider(provider);
        verify(measurementServerRepository, never()).findAll();
    }

    @Test
    public void getMeasurementServer_WhenCall_ExpectGetLastMeasurement() {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = List.of(id1, id2);
        Timestamp ts1 = Timestamp.from(Instant.now());
        Timestamp ts2 = Timestamp.from(Instant.now());
        MeasurementServer measurementServer1 = MeasurementServer.builder()
            .id(id1)
            .measurementServerDescription(MeasurementServerDescription.builder().build())
            .build();
        MeasurementServer measurementServer2 = MeasurementServer.builder()
            .id(id2)
            .measurementServerDescription(MeasurementServerDescription.builder().build())
            .build();
        List<MeasurementServer> servers = List.of(measurementServer1, measurementServer2);

        Measurement measurement1 = Measurement.builder().measurementServerId(id1).time(ts1).build();
        Measurement measurement2 = Measurement.builder().measurementServerId(id1).time(ts2).build();
        List<Measurement> measurements = List.of(measurement1, measurement2);

        MeasurementServerResponse measurementServerResponse1 = MeasurementServerResponse.builder().id(id1).build();
        MeasurementServerResponse measurementServerResponse2 = MeasurementServerResponse.builder().id(id2).build();


        when(measurementServerRepository.findAll())
            .thenReturn(servers);
        when(measurementService.findLastMeasurementsForMeasurementServerIds(ids))
            .thenReturn(measurements);
        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServer1))
            .thenReturn(measurementServerResponse1);
        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServer2))
            .thenReturn(measurementServerResponse2);

        measurementServerService.getMeasurementServers(null);

        verify(measurementServerRepository).findAll();
        verify(measurementService).findLastMeasurementsForMeasurementServerIds(ids);

    }

    @Test
    public void getMeasurementServer_WhenLastSuccessMeasurementExist_ExpectEnrichResponse() {
        Timestamp timestamp1 = Timestamp.valueOf("2021-01-04 12:13:14");
        Timestamp timestamp2 = Timestamp.valueOf("2021-01-05 12:13:14");
        Timestamp timestamp3 = Timestamp.valueOf("2021-01-06 12:13:14");
        Timestamp timestamp4 = Timestamp.valueOf("2021-01-01 12:13:14");
        Timestamp timestamp5 = Timestamp.valueOf("2021-01-02 12:13:14");

        List<MeasurementServer> measurementServerList = List.of(
            MeasurementServer.builder().id(1L).build(),
            MeasurementServer.builder().id(2L).build(),
            MeasurementServer.builder().id(3L).build()
        );
        List<Measurement> measurements = List.of(
            Measurement.builder().id(101L).time(timestamp1)
                .speedDownload(DEFAULT_SPEED_DOWNLOAD)
                .speedUpload(DEFAULT_SPEED_UPLOAD)
                .pingMedian(DEFAULT_PIN_MEDIAN)
                .measurementServerId(1L)
                .build(),
            Measurement.builder().id(102L).time(timestamp2).measurementServerId(2L).build(),
            Measurement.builder().id(103L).time(timestamp3).measurementServerId(3L).build()
        );

        when(measurementServerRepository.findAll())
            .thenReturn(measurementServerList);
        when(measurementService.findLastMeasurementsForMeasurementServerIds(eq(List.of(1L, 2L, 3L))))
            .thenReturn(measurements);

        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServerList.get(0)))
            .thenReturn(MeasurementServerResponse.builder().id(1L).build());
        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServerList.get(1)))
            .thenReturn(MeasurementServerResponse.builder().id(2L).build());
        when(measurementServerMapper.measurementServerToMeasurementServerResponse(measurementServerList.get(2)))
            .thenReturn(MeasurementServerResponse.builder().id(3L).build());

        var result = measurementServerService.getMeasurementServers(null);

        assertEquals(3, result.size());

        MeasurementServerResponse firstServer = result.get(0);
        assertEquals(timestamp1, firstServer.getTimeOfLastMeasurement());

        MeasurementServerResponse secondServer = result.get(1);
        assertFalse(secondServer.isLastMeasurementSuccess());
        assertEquals(timestamp2, secondServer.getTimeOfLastMeasurement());

        MeasurementServerResponse thirdServer = result.get(2);
        assertFalse(thirdServer.isLastMeasurementSuccess());
        assertEquals(timestamp3, thirdServer.getTimeOfLastMeasurement());
    }

    @Test
    public void createNewMeasurementServer_WhenCall_ExpectRepositoryRun() {
        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();
        Provider provider = Provider.builder().id(DEFAULT_ID).build();
        measurementServer.setProvider(provider);

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        when(providerService.isExist(DEFAULT_ID))
            .thenReturn(true);

        measurementServerService.createMeasurementServer(measurementServerRequest);
        verify(measurementServerRepository, atLeastOnce()).save(measurementServer);
    }

    @Test(expected = ProviderNotFoundByIdException.class)
    public void createNewMeasurementServer_WhenCallWithNotExistProvider_ExpectRiseException() {
        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();
        Provider provider = Provider.builder().id(DEFAULT_ID).build();
        measurementServer.setProvider(provider);

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        doThrow(new ProviderNotFoundByIdException(DEFAULT_ID))
            .when(providerService)
            .getProviderById(DEFAULT_ID);
        when(providerService.isExist(DEFAULT_ID))
            .thenReturn(false);

        measurementServerService.createMeasurementServer(measurementServerRequest);
    }

    @Test
    public void createNewMeasurementServer_WhenCallWithoutProviderId_ExpectSetDefault() {
        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();
        Provider provider = Provider.builder().id(null).build();

        measurementServer.setProvider(provider);
        when(providerService.getDefault()).thenReturn(DEFAULT_PROVIDER_ID);
        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);

        measurementServerService.createMeasurementServer(measurementServerRequest);
        verify(measurementServerRepository, atLeastOnce()).save(eq(measurementServer));
    }

    @Test
    public void updateMeasurementServer_WhenCall_ExpectRepositoryRun() {
        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        when(measurementServerRepository.existsById(DEFAULT_ID))
            .thenReturn(true);
        when(measurementServerRepository.findById(DEFAULT_ID))
            .thenReturn(Optional.of(measurementServer));

        measurementServerService.updateMeasurementServer(DEFAULT_ID, measurementServerRequest);
        verify(measurementServerRepository, atLeastOnce()).save(measurementServer);
    }

    @Test
    public void updateMeasurementServer_WhenCallWithoutProviderId_ExpectSetDefault() {
        MeasurementServerRequest measurementServerRequest = MeasurementServerRequest.builder()
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .port(DEFAULT_MEASUREMENT_SERVER_PORT)
            .portSsl(DEFAULT_MEASUREMENT_SERVER_SSL_PORT)
            .city(DEFAULT_CITY)
            .email(DEFAULT_EMAIL)
            .build();

        MeasurementServer measurementServer = getMeasurementServerByDefault();

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        when(measurementServerRepository.existsById(DEFAULT_ID))
            .thenReturn(true);
        when(measurementServerRepository.findById(DEFAULT_ID))
            .thenReturn(Optional.of(measurementServer));
        when(providerService.getDefault()).thenReturn(DEFAULT_PROVIDER_ID);

        measurementServerService.updateMeasurementServer(DEFAULT_ID, measurementServerRequest);
        verify(providerService).getProviderById(DEFAULT_PROVIDER_ID);
    }

    @Test(expected = MeasurementServerNotFoundException.class)
    public void updateMeasurementServer_WhenCallWithWrongId_ExpectRiseException() {
        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        when(measurementServerRepository.existsById(DEFAULT_ID))
            .thenReturn(false);

        measurementServerService.updateMeasurementServer(DEFAULT_ID, measurementServerRequest);
    }

    @Test
    public void updateMeasurementServer_WhenCall_ExpectSaveRepository() {

        MeasurementServerRequest measurementServerRequest = getMeasurementServerRequestByDefault();
        MeasurementServer measurementServer = getMeasurementServerByDefault();
        measurementServer.setProvider(Provider.builder().id(DEFAULT_ID).build());
        measurementServer.setMeasurementServerDescription(MeasurementServerDescription.builder().city(DEFAULT_CITY).email(DEFAULT_EMAIL).build());

        when(measurementServerMapper.measurementServerRequestToMeasurementServer(measurementServerRequest))
            .thenReturn(measurementServer);
        when(measurementServerRepository.findById(DEFAULT_ID)).thenReturn(Optional.of(measurementServer));

        measurementServerService.updateMeasurementServer(DEFAULT_ID, measurementServerRequest);

        verify(measurementServerRepository, atLeastOnce()).save(measurementServer);
    }

    private MeasurementServerResponse getMeasurementServerResponseByDefault() {
        return MeasurementServerResponse.builder()
            .id(DEFAULT_MEASUREMENT_SERVER_ID)
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .build();
    }

    private MeasurementServer getMeasurementServerByDefault() {
        return MeasurementServer.builder()
            .id(DEFAULT_MEASUREMENT_SERVER_ID)
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .build();
    }

    private MeasurementServerRequest getMeasurementServerRequestByDefault() {
        return MeasurementServerRequest.builder()
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .port(DEFAULT_MEASUREMENT_SERVER_PORT)
            .portSsl(DEFAULT_MEASUREMENT_SERVER_SSL_PORT)
            .city(DEFAULT_CITY)
            .email(DEFAULT_EMAIL)
            .providerId(DEFAULT_ID)
            .build();
    }
}
