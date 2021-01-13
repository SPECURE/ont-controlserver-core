package com.specure.core.mapper.impl;

import com.specure.core.enums.NetworkType;
import com.specure.core.mapper.GeoLocationMapper;
import com.specure.core.mapper.PingMapper;
import com.specure.core.mapper.SpeedDetailMapper;
import com.specure.core.model.Measurement;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.utils.MeasurementCalculatorUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.specure.core.TestConstants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class MeasurementMapperImplTest {

    @MockBean
    PingMapper pingMapper;
    @MockBean
    SpeedDetailMapper speedDetailMapper;
    @MockBean
    GeoLocationMapper geoLocationMapper;

    private MeasurementMapperImpl measurementMapper;

    @Before
    public void setUp() {
        measurementMapper = new MeasurementMapperImpl(pingMapper, speedDetailMapper, geoLocationMapper);
    }

    @Test
    public void measurementRequestToMeasurement() {
        Map<String, Integer> signalReport = new HashMap<>();
        signalReport.put("lte_rsrp", DEFAULT_LTE_RSRP);
        signalReport.put("lte_rsrq", DEFAULT_LTE_RSRQ);
        List<Map<String, Integer>> signals = List.of(signalReport);

        Map<String, String> jpl = new HashMap<>();
        jpl.put("voip_result_in_mean_jitter", DEFAULT_VOIP_RESULT_IN_MEAN_JITTER.toString());
        jpl.put("voip_result_out_mean_jitter", DEFAULT_VOIP_RESULT_OUT_MEAN_JITTER.toString());
        jpl.put("voip_objective_delay", DEFAULT_VOIP_OBJECTIVE_DELAY.toString());
        jpl.put("voip_objective_call_duration", DEFAULT_VOIP_OBJECTIVE_CALL_DURATION.toString());
        jpl.put("voip_result_in_num_packets", DEFAULT_VOIP_RESULT_IN_NUM_PACKETS.toString());
        jpl.put("voip_result_out_num_packets", DEFAULT_VOIP_RESULT_OUT_NUM_PACKETS.toString());


        Long timeStamp = System.currentTimeMillis();
        MeasurementRequest measurementRequest = MeasurementRequest.builder()
            .clientUuid(DEFAULT_UUID)
            .testToken(DEFAULT_TOKEN)
            .device(DEFAULT_DEVICE)
            .tag(DEFAULT_TAG)
            .signals(signals)
            .jpl(jpl)
            .time(timeStamp)
            .testPingShortest(DEFAULT_PING.longValue())
            .testBytesDownload(DEFAULT_BYTES_DOWNLOAD)
            .testNsecDownload(DEFAULT_N_SEC_DOWNLOAD)
            .testBytesUpload(DEFAULT_BYTES_UPLOAD)
            .testNsecUpload(DEFAULT_N_SEC_UPLOAD)
            .networkType(DEFAULT_NETWORK_TYPE.toString())
            .build();

        double speedDownload = MeasurementCalculatorUtil.getSpeedBitPerSec(DEFAULT_BYTES_DOWNLOAD, DEFAULT_N_SEC_DOWNLOAD) / 1e3;
        double speedUpload = MeasurementCalculatorUtil.getSpeedBitPerSec(DEFAULT_BYTES_UPLOAD, DEFAULT_N_SEC_UPLOAD) / 1e3;
        String resultJitter = MeasurementCalculatorUtil.calculateMeanJitterInMms(DEFAULT_VOIP_RESULT_IN_MEAN_JITTER, DEFAULT_VOIP_RESULT_OUT_MEAN_JITTER);
        String packetLoss = MeasurementCalculatorUtil.calculateMeanPacketLossInPercent(
            DEFAULT_VOIP_OBJECTIVE_DELAY,
            DEFAULT_VOIP_OBJECTIVE_CALL_DURATION,
            DEFAULT_VOIP_RESULT_IN_NUM_PACKETS,
            DEFAULT_VOIP_RESULT_OUT_NUM_PACKETS
        );

        Measurement mapped = measurementMapper.measurementRequestToMeasurement(measurementRequest);
        assertEquals(DEFAULT_TOKEN.split("_")[0], mapped.getOpenTestUuid());
        assertEquals(DEFAULT_TOKEN, mapped.getToken());
        assertEquals(Math.round(speedDownload), mapped.getSpeedDownload().longValue());
        assertEquals(Math.round(speedUpload), mapped.getSpeedUpload().longValue());
        assertEquals(DEFAULT_PING.longValue(), mapped.getPingMedian().longValue());
        assertEquals(DEFAULT_DEVICE, mapped.getDevice());
        assertEquals(DEFAULT_TAG, mapped.getTag());
        assertEquals(DEFAULT_NETWORK_TYPE, mapped.getNetworkType());
        assertEquals(resultJitter, mapped.getVoip_result_jitter());
        assertEquals(packetLoss, mapped.getVoip_result_packet_loss());
        assertEquals(DEFAULT_LTE_RSRP, mapped.getSignalStrength());
    }

    @Test
    public void getSignalStrength_WhenGet101Network_returnSignalStrength() {
        NetworkType networkType = NetworkType.fromValue(101);
        Map<String, Integer> signals = new HashMap<>() {{
            put("signal_strength", -105);
        }};
        Optional<Integer> signalStrength = MeasurementMapperImpl.getSignalStrength(Collections.singletonList(signals), networkType);
        assertEquals(Optional.of(-105), signalStrength);
    }

    @Test
    public void getSignalStrength_WhenGet101NetworkAndBadKey_returnEmpty() {
        NetworkType networkType = NetworkType.fromValue(101);
        Map<String, Integer> signals = new HashMap<>() {{
            put("smth_else", -105);
        }};
        Optional<Integer> signalStrength = MeasurementMapperImpl.getSignalStrength(Collections.singletonList(signals), networkType);
        assertEquals(Optional.empty(), signalStrength);
    }

    @Test
    public void getSignalStrength_WhenGet97Network_returnEmpty() {
        NetworkType networkType = NetworkType.fromValue(97);
        Map<String, Integer> signals = Collections.emptyMap();
        Optional<Integer> signalStrength = MeasurementMapperImpl.getSignalStrength(Collections.singletonList(signals), networkType);
        assertEquals(Optional.empty(), signalStrength);
    }

    @Test
    public void getSignalStrength_WhenGet98Network_returnEmpty() {
        NetworkType networkType = NetworkType.fromValue(98);
        Map<String, Integer> signals = Collections.emptyMap();
        Optional<Integer> signalStrength = MeasurementMapperImpl.getSignalStrength(Collections.singletonList(signals), networkType);
        assertEquals(Optional.empty(), signalStrength);
    }

    @Test
    public void getSignalStrength_WhenGet13Network_let_rsrp() {
        NetworkType networkType = NetworkType.fromValue(13);
        Map<String, Integer> signals = new HashMap<>() {{
            put("smth_else", -105);
            put("lte_rsrp", -104);
        }};
        Optional<Integer> signalStrength = MeasurementMapperImpl.getSignalStrength(Collections.singletonList(signals), networkType);
        assertEquals(Optional.of(-104), signalStrength);
    }
}
