package com.specture.core.service.impl;


import com.specture.core.mapper.MeasurementQosMapper;
import com.specture.core.model.AdHocCampaign;
import com.specture.core.model.Measurement;
import com.specture.core.model.qos.MeasurementQos;
import com.specture.core.repository.MeasurementQosRepository;
import com.specture.core.repository.MeasurementServerRepository;
import com.specture.core.request.MeasurementQosRequest;
import com.specture.core.request.measurement.qos.request.VoipTestResultRequest;
import com.specture.core.service.BasicQosTestService;
import com.specture.core.service.MeasurementQosService;
import com.specture.core.service.MeasurementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.specture.core.TestConstants.DEFAULT_AD_HOC_CAMPAIGN;
import static com.specture.core.TestConstants.DEFAULT_TOKEN;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MeasurementQosServiceImplTest {

    @MockBean
    private MeasurementQosMapper measurementQosMapper;
    @MockBean
    private MeasurementQosRepository measurementQosRepository;
    @MockBean
    private BasicQosTestService basicQosTestService;
    @MockBean
    private MeasurementServerRepository measurementServerRepository;
    @MockBean
    private MeasurementService measurementService;

    private MeasurementQosService measurementQosService;

    @Before
    public void setUp() {
        measurementQosService = new MeasurementQosServiceImpl(measurementServerRepository, measurementQosRepository, basicQosTestService, measurementQosMapper, measurementService);
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

        measurementQosService.saveMeasurementQos(measurementQosRequest);

        verify(measurementQosRepository).save(measurementQos);

    }

    private MeasurementQos getDefaultMeasurementQos() {
        return MeasurementQos.builder()
            .testToken(DEFAULT_TOKEN)
//                .openTestUuid(DEFAULT_OPEN_TEST_UUID)
//                .voipObjectiveCallDuration(DEFAULT_VOIP_OBJECTIVE_CALL_DURATION)
//                .voipObjectiveDelay(DEFAULT_VOIP_OBJECTIVE_DELAY)
//                .voipResultInMeanJitter(DEFAULT_VOIP_RESULT_IN_MEAN_JITTER)
//                .voipResultOutMeanJitter(DEFAULT_VOIP_RESULT_OUT_MEAN_JITTER)
//                .voipResultOutNumPackets(DEFAULT_VOIP_RESULT_OUT_NUM_PACKETS)
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
