package com.specture.core.service.impl;


import com.specture.core.exception.QoSMeasurementServerNotFoundByUuidException;
import com.specture.core.exception.QosMeasurementFromOnNetServerException;
import com.specture.core.mapper.MeasurementQosMapper;
import com.specture.core.model.AdHocCampaign;
import com.specture.core.model.Measurement;
import com.specture.core.model.MeasurementServer;
import com.specture.core.model.qos.MeasurementQos;
import com.specture.core.repository.MeasurementQosRepository;
import com.specture.core.repository.MeasurementServerRepository;
import com.specture.core.request.MeasurementQosParametersRequest;
import com.specture.core.request.MeasurementQosRequest;
import com.specture.core.request.measurement.qos.request.VoipTestResultRequest;
import com.specture.core.response.measurement.qos.response.MeasurementQosParametersResponse;
import com.specture.core.service.MeasurementQosService;
import com.specture.core.service.MeasurementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.specture.core.TestConstants.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MeasurementQosServiceImplTest {

    @MockBean
    private MeasurementQosMapper measurementQosMapper;
    @MockBean
    private MeasurementQosRepository measurementQosRepository;
    @MockBean
    private MeasurementServerRepository measurementServerRepository;
    @MockBean
    private MeasurementService measurementService;
    @Mock
    private MeasurementQosParametersRequest request;
    @Mock
    private MeasurementServer measurementServer;

    @Qualifier("basicMeasurementQosService")
    private MeasurementQosService basicMeasurementQosService;

    @Before
    public void setUp() {
        basicMeasurementQosService = new MeasurementQosServiceImpl(measurementServerRepository, measurementQosRepository, measurementQosMapper, measurementService);
    }

    @Test
    public void saveMeasurementQos_WhenCall_ExpectCorrectResponse() {
        MeasurementQosRequest measurementQosRequest = getDefaultMeasurementQosRequest();
        MeasurementQos measurementQos = getDefaultMeasurementQos();
        AdHocCampaign adHocCampaign = AdHocCampaign.builder().id(DEFAULT_AD_HOC_CAMPAIGN).build();
        Measurement measurement = Measurement.builder().adHocCampaign(adHocCampaign).build();

        when(measurementQosMapper.measurementQosRequestToMeasurementQos(measurementQosRequest))
                .thenReturn(measurementQos);
        when(measurementQosRepository.save(measurementQos))
                .thenReturn(null);
        when(measurementService.getMeasurementByToken(DEFAULT_TOKEN))
                .thenReturn(Optional.of(measurement));

        basicMeasurementQosService.saveMeasurementQos(measurementQosRequest);

        verify(measurementQosRepository).save(measurementQos);

    }

    @Test(expected = QosMeasurementFromOnNetServerException.class)
    public void saveMeasurement_whenOnNetServer_expectException() {
        MeasurementQosRequest measurementQosRequest = getDefaultMeasurementQosRequest();
        MeasurementQos measurementQos = getDefaultMeasurementQos();
        Measurement measurement = Measurement.builder()
                .serverType("ON_NET")
                .openTestUuid(DEFAULT_OPEN_TEST_UUID)
                .measurementServerId(DEFAULT_MEASUREMENT_SERVER_ID)
                .build();

        when(measurementQosMapper.measurementQosRequestToMeasurementQos(measurementQosRequest))
                .thenReturn(measurementQos);
        when(measurementService.getMeasurementByToken(DEFAULT_TOKEN))
                .thenReturn(Optional.of(measurement));

        basicMeasurementQosService.saveMeasurementQos(measurementQosRequest);
    }

    @Test
    public void getQosParameters_correctRequest_expectNotNullParameters() {
        when(request.getUuid()).thenReturn(DEFAULT_UUID);
        when(measurementServerRepository.findByClientUUID(DEFAULT_UUID))
                .thenReturn(Optional.of(measurementServer));
        MeasurementQosParametersResponse response = basicMeasurementQosService.getQosParameters(request);
        assertNotNull(response);
    }

    @Test(expected = QoSMeasurementServerNotFoundByUuidException.class)
    public void getQosParameters_correctRequest_expectException() {
        when(measurementServerRepository.findByClientUUID(DEFAULT_UUID))
                .thenReturn(Optional.of(measurementServer));
        basicMeasurementQosService.getQosParameters(request);
    }

    private MeasurementQos getDefaultMeasurementQos() {
        return MeasurementQos.builder()
                .testToken(DEFAULT_TOKEN)
                .build();
    }

    private MeasurementQosRequest getDefaultMeasurementQosRequest() {
        VoipTestResultRequest voipTestResultRequest = VoipTestResultRequest.builder().build();
        return MeasurementQosRequest.builder()
                .testToken(DEFAULT_TOKEN)
                .qosResult(List.of(voipTestResultRequest))
                .build();
    }
}
