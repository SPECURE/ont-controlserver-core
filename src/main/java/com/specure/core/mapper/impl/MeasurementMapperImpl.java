package com.specure.core.mapper.impl;

import com.specure.core.model.GeoLocation;
import com.specure.core.model.Measurement;
import com.specure.core.model.Ping;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.response.MeasurementHistoryResponse;
import com.specure.core.response.measurement.response.GeoLocationResponse;
import com.specure.core.response.measurement.response.PingResponse;
import com.specure.core.response.measurement.response.SpeedDetailResponse;
import com.specure.core.enums.NetworkType;
import com.specure.core.mapper.GeoLocationMapper;
import com.specure.core.mapper.MeasurementMapper;
import com.specure.core.mapper.PingMapper;
import com.specure.core.mapper.SpeedDetailMapper;
import com.specure.core.model.SpeedDetail;
import com.specure.core.utils.MeasurementCalculatorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class MeasurementMapperImpl implements MeasurementMapper {

    private final PingMapper pingMapper;
    private final SpeedDetailMapper speedDetailMapper;
    private final GeoLocationMapper geoLocationMapper;

    @Override
    public Measurement measurementRequestToMeasurement(MeasurementRequest measurementRequest) {
        String openTestUuid = measurementRequest.getTestToken().split("_")[0];

        Integer lte_rsrp = null;
        Integer lte_rsrq = null;
        String resultJitter = null;
        String packetLoss = null;


        if (measurementRequest.getSignals() != null) {
            Optional<Integer> data;
            data = getValueByKeyFromSignal(measurementRequest.getSignals(), "lte_rsrp");
            if (data.isPresent()) {
                lte_rsrp = data.get();
            }
            data = getValueByKeyFromSignal(measurementRequest.getSignals(), "lte_rsrq");
            if(data.isPresent()) {
                lte_rsrq = data.get();
            }
        }
        if (measurementRequest.getJpl() != null) {
            resultJitter = MeasurementCalculatorUtil.calculateMeanJitterInMms(
                    getLongByKeyFromJPL(measurementRequest,"voip_result_in_mean_jitter"),
                    getLongByKeyFromJPL(measurementRequest,"voip_result_out_mean_jitter")
            );
            packetLoss = resultJitter != null ? MeasurementCalculatorUtil.calculateMeanPacketLossInPercent(
                    getLongByKeyFromJPL(measurementRequest, "voip_objective_delay"),
                    getLongByKeyFromJPL(measurementRequest,"voip_objective_call_duration"),
                    getLongByKeyFromJPL(measurementRequest,"voip_result_in_num_packets"),
                    getLongByKeyFromJPL(measurementRequest,"voip_result_out_num_packets")
            ) : null;
        }

        int networkType = Integer.parseInt(measurementRequest.getNetworkType());

        double speedDownload = MeasurementCalculatorUtil.getSpeedBitPerSec(measurementRequest.getTestBytesDownload(), measurementRequest.getTestNsecDownload())/ 1e3;
        double speedUpload = MeasurementCalculatorUtil.getSpeedBitPerSec(measurementRequest.getTestBytesUpload(), measurementRequest.getTestNsecUpload())/ 1e3;

        List<Ping> pings = Collections.emptyList();
        if(measurementRequest.getPings() != null) {
            pings = measurementRequest.getPings()
                    .stream()
                    .map(pingMapper::pingRequestToPing)
                    .collect(Collectors.toList());
        }
        List<SpeedDetail> details = Collections.emptyList();
        if(measurementRequest.getSpeedDetail() != null) {
            details = measurementRequest.getSpeedDetail()
                    .stream()
                    .map(speedDetailMapper::speedDetailRequestToSpeedDetail)
                    .collect(Collectors.toList());
        }
        List<GeoLocation> geoLocations = Collections.emptyList();
        if(measurementRequest.getGeoLocations() != null) {
            geoLocations = measurementRequest.getGeoLocations()
                    .stream()
                    .map(geoLocationMapper::geoLocationRequestToGeoLocation)
                    .collect(Collectors.toList());
        }

        Integer signalStrength = null;
        Optional<Integer> signalStrengthOptional =  getSignalStrength(measurementRequest.getSignals(), NetworkType.fromValue(networkType));
        if (signalStrengthOptional.isPresent()) {
            signalStrength = signalStrengthOptional.get();
        }

        return Measurement.builder()
                .pingMedian(MeasurementCalculatorUtil.median(measurementRequest.getPings(), measurementRequest.getTestPingShortest()))
                .token(measurementRequest.getTestToken())
                .openTestUuid(openTestUuid)
                .time(new Timestamp(measurementRequest.getTime()))
                .speedDownload((int) speedDownload)
                .speedUpload((int) speedUpload)
                .signalStrength(signalStrength)
                .lte_rsrp(lte_rsrp)
                .lte_rsrq(lte_rsrq)
                .device(measurementRequest.getDevice())
                .tag(measurementRequest.getTag())
                .networkType(networkType)
                .voip_result_jitter(resultJitter)
                .voip_result_packet_loss(packetLoss)
                .clientLanguage(measurementRequest.getClientLanguage())
                .clientName(measurementRequest.getClientName())
                .clientVersion(measurementRequest.getClientVersion())
                .model(measurementRequest.getModel())
                .platform(MeasurementCalculatorUtil.getPlatform(measurementRequest.getPlatform(), measurementRequest.getModel()))
                .product(measurementRequest.getProduct())
                .testBytesDownload(measurementRequest.getTestBytesDownload())
                .testBytesUpload(measurementRequest.getTestBytesUpload())
                .testNsecDownload(measurementRequest.getTestNsecDownload())
                .testNsecUpload(measurementRequest.getTestNsecUpload())
                .testNumThreads(measurementRequest.getTestNumThreads())
                .numThreadsUl(measurementRequest.getNumThreadsUl())
                .testPingShortest(measurementRequest.getTestPingShortest())
                .testSpeedDownload(measurementRequest.getTestSpeedDownload())
                .testSpeedUpload(measurementRequest.getTestSpeedUpload())
                .timezone(measurementRequest.getTimezone())
                .type(measurementRequest.getType())
                .versionCode(measurementRequest.getVersionCode())
                .pings(pings)
                .speedDetail(details)
                .geoLocations(geoLocations)
                .build();
    }

    @Override
    public MeasurementHistoryResponse measurementToMeasurementHistoryResponse(Measurement measurement) {

        List<PingResponse> pings = measurement.getPings()
                .stream()
                .map(pingMapper::pingToPingResponse)
                .collect(Collectors.toList());
        List<SpeedDetailResponse> details = measurement.getSpeedDetail()
                .stream()
                .map(speedDetailMapper::speedDetailToSpeedDetailResponse)
                .collect(Collectors.toList());
        List<GeoLocationResponse> geoLocations = measurement.getGeoLocations()
                .stream()
                .map(geoLocationMapper::geoLocationToGeoLocationResponse)
                .collect(Collectors.toList());

        return MeasurementHistoryResponse.builder()
                .pingMedian(measurement.getPingMedian())
                .signalStrength(measurement.getSignalStrength())
                .token(measurement.getToken())
                .openTestUuid(measurement.getOpenTestUuid())
                .time(measurement.getTime())
                .speedDownload(measurement.getSpeedDownload())
                .speedUpload(measurement.getSpeedUpload())
                .lte_rsrp(measurement.getLte_rsrp())
                .lte_rsrq(measurement.getLte_rsrq())
                .device(measurement.getDevice())
                .tag(measurement.getTag())
                .networkType(measurement.getNetworkType())
                .networkOperator(measurement.getNetworkOperator())
                .ipAddress(measurement.getIpAddress())
                .voip_result_jitter(measurement.getVoip_result_jitter())
                .voip_result_packet_loss(measurement.getVoip_result_packet_loss())
                .clientLanguage(measurement.getClientLanguage())
                .clientName(measurement.getClientName())
                .clientProvider(measurement.getClientProvider())
                .clientVersion(measurement.getClientVersion())
                .model(measurement.getModel())
                .platform(measurement.getPlatform().name())
                .product(measurement.getProduct())
                .testBytesDownload(measurement.getTestBytesDownload())
                .testBytesUpload(measurement.getTestBytesUpload())
                .testNsecDownload(measurement.getTestNsecDownload())
                .testNsecUpload(measurement.getTestNsecUpload())
                .testNumThreads(measurement.getTestNumThreads())
                .numThreadsUl(measurement.getNumThreadsUl())
                .testPingShortest(measurement.getTestPingShortest())
                .testSpeedDownload(measurement.getTestSpeedDownload())
                .testSpeedUpload(measurement.getTestSpeedUpload())
                .timezone(measurement.getTimezone())
                .type(measurement.getType())
                .versionCode(measurement.getVersionCode())
                .pings(pings)
                .speedDetail(details)
                .geoLocations(geoLocations)
                .measurementServerId(measurement.getMeasurementServerId())
                .build();
    }


    private static Long getLongByKeyFromJPL(MeasurementRequest measurementRequest, String key) {
        String data = measurementRequest.getJpl().get(key);
        try {
            return Long.valueOf(data);
        } catch ( NumberFormatException e) {
            log.error("attempt to get Long by key='{}' failed; data = {}", key, data);
            return 0L;
        }

    }

    public static Optional<Integer> getSignalStrength(List<Map<String, Integer>> signals, NetworkType networkType) {
        switch (networkType.getValue()){
            case 13: // LTE use RSRP
                return getValueByKeyFromSignal(signals, "lte_rsrp");

            case 97: // wired connections
            case 98: return Optional.empty();

            default: // try to find RSSI
                return getValueByKeyFromSignal(signals, "signal_strength");
        }
    }

    private static Optional<Integer> getValueByKeyFromSignal(List<Map<String, Integer>> signals, String key) {
        Optional<Map<String, Integer>> signalStrengthObject = signals.stream()
                .filter(dataPiece -> dataPiece.containsKey(key))
                .findFirst();
        if (signalStrengthObject.isPresent()) {
            Integer result = signalStrengthObject.get().get(key);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
