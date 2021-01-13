package com.specure.core.service.impl;

import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.enums.MeasurementStatus;
import com.specure.core.mapper.MeasurementMapper;
import com.specure.core.model.Measurement;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.TimeSlot;
import com.specure.core.model.internal.DataForMeasurementRegistration;
import com.specure.core.repository.MeasurementRepository;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.service.MeasurementService;
import com.specure.core.service.UserAgentExtractService;
import com.specure.core.service.digger.DiggerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import static com.specure.core.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class MeasurementServiceImplTest {

    @MockBean
    private MeasurementRepository measurementRepository;
    @MockBean
    private MeasurementMapper measurementMapper;
    @MockBean
    private MeasurementServerConfig measurementServerConfig;
    @MockBean
    private DiggerService diggerService;
    @MockBean
    private UserAgentExtractService userAgentExtractService;

    @Mock
    private Measurement measurement;

    private MeasurementService measurementService;

    @Captor
    private ArgumentCaptor<Measurement> measurementCaptor;

    @Before
    public void setUp() {
        // for Timestamp constructor
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        measurementService = new MeasurementServiceImpl(measurementRepository, measurementMapper, measurementServerConfig, diggerService, userAgentExtractService);
    }

    @Test
    public void saveMeasurement_WhenCall_ExpectSaveRepository() {
        MeasurementRequest measurementRequest = getMeasurementRequestDefault();

        when(measurementMapper.measurementRequestToMeasurement(measurementRequest))
            .thenReturn(measurement);
        when(measurementRepository.save(measurement))
            .thenReturn(null);

        measurementService.saveMeasurement(measurementRequest);

        verify(measurementRepository).save(measurement);
    }

    @Test
    public void partialUpdateMeasurementFromProbeResult_WhenCall_ExpectSaveRepository() {
        MeasurementRequest measurementRequest = getMeasurementRequestDefault();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Measurement measurementAfterMapping = Measurement.builder()
            .token(DEFAULT_TOKEN)
            .pingMedian(DEFAULT_PIN_MEDIAN)
            .voip_result_jitter(DEFAULT_VOIP_RESULT_JITTER)
            .time(now)
            .status(MeasurementStatus.FINISHED)
            .build();
        Measurement measurementFromDB = Measurement.builder().time(now).build();

        when(measurementRepository.findByToken(DEFAULT_TOKEN))
            .thenReturn(Optional.of(measurementFromDB));
        when(measurementMapper.measurementRequestToMeasurement(measurementRequest))
            .thenReturn(measurementAfterMapping);
        when(measurementRepository.save(measurementFromDB))
            .thenReturn(null);

        measurementService.partialUpdateMeasurementFromProbeResult(measurementRequest);

        verify(measurementRepository).save(measurementAfterMapping);
    }

    @Test
    public void partialUpdateMeasurementFromProbeResult_WhenCall_ExpectPartialUpdate() {
        long id = 1L;
        MeasurementRequest measurementRequest = getMeasurementRequestDefault();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Measurement measurementFromDB = Measurement.builder().id(id).token(DEFAULT_TOKEN).time(now).build();
        Measurement afterPartialUpdate = getMeasurementDefault();
        Measurement measurementAfterMapping = getMeasurementDefault();


        when(measurementRepository.findByToken(DEFAULT_TOKEN))
            .thenReturn(Optional.of(measurementFromDB));
        when(measurementMapper.measurementRequestToMeasurement(measurementRequest))
            .thenReturn(measurementAfterMapping);
        when(measurementRepository.save(afterPartialUpdate))
            .thenReturn(null);

        afterPartialUpdate.setId(id);
        afterPartialUpdate.setTime(now);
        // we don't change this parameters
        afterPartialUpdate.setDevice(null);
        afterPartialUpdate.setTag(null);
        afterPartialUpdate.setStatus(MeasurementStatus.FINISHED);


        measurementService.partialUpdateMeasurementFromProbeResult(measurementRequest);

        verify(measurementRepository).save(afterPartialUpdate);
    }

    @Test
    public void registerMeasurement_WhenCallWithDataWithoutProvider_ExpectNullInProvider() {
        final Measurement measurement;
        MeasurementServer measurementServer = MeasurementServer.builder()
            .secretKey(DEFAULT_MEASUREMENT_SERVER_SECRET_KEY)
            .build();
        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(measurementServer)
            .clientType(DEFAULT_CLIENT)
            .build();

        when(measurementServerConfig.getSlotWindow()).thenReturn(DEFAULT_SLOT_WINDOW);
        when(measurementRepository.countAllByTestSlot(anyInt())).thenReturn(0L);
        when(measurementServerConfig.getHost()).thenReturn(DEFAULT_HOST);
        when(measurementRepository.save(any())).thenReturn(getMeasurementDefault());

        measurementService.registerMeasurement(dataForMeasurementRegistration, DEFAULT_HEADER);
        verify(measurementRepository).save(measurementCaptor.capture());
        measurement = measurementCaptor.getValue();
        assertNull(measurement.getNetworkOperator());

    }

    @Test
    public void registerMeasurement_WhenCall_ExpectSaveMeasurement() {

        MeasurementServer measurementServer = MeasurementServer.builder()
            .secretKey(DEFAULT_MEASUREMENT_SERVER_SECRET_KEY)
            .provider(DEFAULT_PROVIDER)
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .build();
        Measurement savedMeasurement = Measurement.builder().id(1L).build();
        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(measurementServer)
            .clientType(DEFAULT_CLIENT)
            .build();

        when(measurementServerConfig.getSlotWindow()).thenReturn(DEFAULT_SLOT_WINDOW);
        when(measurementRepository.countAllByTestSlot(anyInt())).thenReturn(0L);
        when(measurementRepository.save(any())).thenReturn(savedMeasurement);
        when(measurementServerConfig.getHost()).thenReturn(DEFAULT_HOST);

        measurementService.registerMeasurement(dataForMeasurementRegistration, DEFAULT_HEADER);

        verify(measurementRepository).save(any());
    }

    @Test
    public void registerMeasurement_WhenCall_ExpectGetProvider() throws UnknownHostException {

        MeasurementServer measurementServer = MeasurementServer.builder()
            .secretKey(DEFAULT_MEASUREMENT_SERVER_SECRET_KEY)
            .provider(DEFAULT_PROVIDER)
            .name(DEFAULT_MEASUREMENT_SERVER_NAME)
            .webAddress(DEFAULT_MEASUREMENT_SERVER_ADDRESS)
            .build();

        Measurement savedMeasurement = Measurement.builder().id(1L).build();
        final DataForMeasurementRegistration dataForMeasurementRegistration = DataForMeasurementRegistration.builder()
            .measurementServer(measurementServer)
            .clientType(DEFAULT_CLIENT)
            .build();

        when(measurementServerConfig.getSlotWindow()).thenReturn(DEFAULT_SLOT_WINDOW);
        when(measurementRepository.countAllByTestSlot(anyInt())).thenReturn(0L);
        when(measurementRepository.save(any())).thenReturn(savedMeasurement);
        when(measurementServerConfig.getHost()).thenReturn(DEFAULT_HOST);
        when(diggerService.digASN(InetAddress.getByName(DEFAULT_IP_FOR_PROVIDER))).thenReturn(DEFAULT_ASN);
        when(diggerService.getProviderByASN(DEFAULT_ASN)).thenReturn(DEFAULT_GEO_PROVIDER_NAME);

        Map<String, String> header = new HashMap<>() {{
            put("x-forwarded-for", DEFAULT_IP_FOR_PROVIDER);
        }};
        var result = measurementService.registerMeasurement(dataForMeasurementRegistration, header);

        verify(measurementRepository).save(any());
        assert (result.getProvider()).equals(DEFAULT_GEO_PROVIDER_NAME);
    }

    @Test
    public void getTimeSlot_WhenCall_ExpectReturnTimeSlot() {
        long now = 1590671637123L;
        int waitExpected = 0;
        int slotExpected = Math.toIntExact(now / 1000);

        when(measurementRepository.countAllByTestSlot(slotExpected)).thenReturn(0L);
        when(measurementServerConfig.getSlotWindow()).thenReturn(DEFAULT_SLOT_WINDOW);

        TimeSlot timeSlot = measurementService.getTimeSlot(now);

        assertEquals(slotExpected, timeSlot.getSlot().intValue());
        assertEquals(waitExpected, timeSlot.getTestWait().intValue());
    }

    @Test
    public void getTimeSlot_WhenCall_ExpectReturnOnlyFreeTimeSlot() {
        long now = 1590671637123L;
        int waitExpected = 3;
        int slot = Math.toIntExact(now / 1000);
        long slotWindow = 5;

        when(measurementServerConfig.getSlotWindow()).thenReturn(slotWindow);

        when(measurementRepository.countAllByTestSlot(slot)).thenReturn(slotWindow);
        when(measurementRepository.countAllByTestSlot(slot + 1)).thenReturn(slotWindow);
        when(measurementRepository.countAllByTestSlot(slot + 2)).thenReturn(slotWindow);
        when(measurementRepository.countAllByTestSlot(slot + 3)).thenReturn(slotWindow - 1);

        TimeSlot timeSlot = measurementService.getTimeSlot(now);

        assertEquals(slot + 3, timeSlot.getSlot().intValue());
        assertEquals(waitExpected, timeSlot.getTestWait().intValue());
    }

    private MeasurementRequest getMeasurementRequestDefault() {
        return MeasurementRequest.builder()
            .build();
    }

    private Measurement getMeasurementDefault() {
        return Measurement.builder()
            .token(DEFAULT_TOKEN)
            .speedDownload(DEFAULT_SPEED_DOWNLOAD)
            .speedUpload(DEFAULT_SPEED_UPLOAD)
            .lte_rsrp(DEFAULT_LTE_RSRP)
            .voip_result_packet_loss(DEFAULT_VOIP_RESULT_PACKET_LOSS)
            .voip_result_jitter(DEFAULT_VOIP_RESULT_JITTER)
            .device(DEFAULT_DEVICE)
            .networkType(DEFAULT_NETWORK_TYPE)
            .tag(DEFAULT_TAG)
            .pingMedian(DEFAULT_PIN_MEDIAN)
            .signalStrength(DEFAULT_SIGNAL_STRENGTH)
            .build();
    }
}
