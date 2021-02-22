package com.specure.core.service.impl;

import com.specure.core.config.MeasurementServerConfig;
import com.specure.core.constant.ErrorMessage;
import com.specure.core.constant.MeasurementServerConstants;
import com.specure.core.enums.MeasurementStatus;
import com.specure.core.enums.Platform;
import com.specure.core.enums.ServerNetworkType;
import com.specure.core.exception.MeasurementNotFoundByUuidException;
import com.specure.core.mapper.MeasurementMapper;
import com.specure.core.model.AdHocCampaign;
import com.specure.core.model.Measurement;
import com.specure.core.model.MeasurementServer;
import com.specure.core.model.TimeSlot;
import com.specure.core.model.internal.DataForMeasurementRegistration;
import com.specure.core.repository.MeasurementRepository;
import com.specure.core.request.MeasurementRequest;
import com.specure.core.response.MeasurementHistoryResponse;
import com.specure.core.response.MeasurementRegistrationResponse;
import com.specure.core.service.MeasurementService;
import com.specure.core.service.UserAgentExtractService;
import com.specure.core.service.digger.DiggerService;
import com.specure.core.utils.HeaderExtrudeUtil;
import com.specure.core.utils.MeasurementCalculatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final MeasurementServerConfig measurementServerConfig;
    private final DiggerService diggerService;
    private final UserAgentExtractService userAgentExtractService;
    private Clock clock; //todo to bean

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    @Deprecated
    public void saveMeasurement(MeasurementRequest measurementRequest) {
        Measurement measurement = measurementMapper.measurementRequestToMeasurement(measurementRequest);
        measurement.setStatus(MeasurementStatus.FINISHED);
        measurementRepository.save(measurement);
    }

    @Override
    public Measurement partialUpdateMeasurementFromProbeResult(MeasurementRequest measurementRequest) {
        Measurement afterMeasure = measurementMapper.measurementRequestToMeasurement(measurementRequest);
        var registeredMeasurement = measurementRepository
                .findByToken(afterMeasure.getToken())
                .orElseThrow(() -> new MeasurementNotFoundByUuidException(afterMeasure.getToken()));

        Long id = registeredMeasurement.getId();

        afterMeasure.setId(id);
        afterMeasure.setOpenTestUuid(registeredMeasurement.getOpenTestUuid());
        afterMeasure.setClientUuid(registeredMeasurement.getClientUuid());
        afterMeasure.setNetworkOperator(registeredMeasurement.getNetworkOperator());
        afterMeasure.setDevice(registeredMeasurement.getDevice());
        afterMeasure.setTag(registeredMeasurement.getTag());
        afterMeasure.setIpAddress(registeredMeasurement.getIpAddress());
        afterMeasure.setToken(registeredMeasurement.getToken());
        afterMeasure.setTestSlot(registeredMeasurement.getTestSlot());
        afterMeasure.setTime(registeredMeasurement.getTime());
        afterMeasure.setMeasurementServerId(registeredMeasurement.getMeasurementServerId());
        afterMeasure.setServerType(registeredMeasurement.getServerType());
        afterMeasure.setClientProvider(registeredMeasurement.getClientProvider());
        afterMeasure.setStatus(MeasurementStatus.FINISHED);
        afterMeasure.setAdHocCampaign(registeredMeasurement.getAdHocCampaign());

        if (afterMeasure.getPings() != null) {
            afterMeasure.getPings().forEach(ping -> ping.setMeasurement(afterMeasure));
        }
        if (afterMeasure.getGeoLocations() != null) {
            afterMeasure.getGeoLocations().forEach(geoLocation -> geoLocation.setMeasurement(afterMeasure));
        }
        if (afterMeasure.getSpeedDetail() != null) {
            afterMeasure.getSpeedDetail().forEach(speedDetail -> speedDetail.setMeasurement(afterMeasure));
        }
        measurementRepository.save(afterMeasure);
        return afterMeasure;
    }

    @Override
    public MeasurementRegistrationResponse registerMeasurement(DataForMeasurementRegistration dataForMeasurementRegistration, Map<String, String> headers) {
        MeasurementServer measurementServer = dataForMeasurementRegistration.getMeasurementServer();
        String ip = HeaderExtrudeUtil.getIpFromNgNixHeader(headers);
        String clientProviderName = ErrorMessage.UNKNOWN_PROVIDER;

        try {
            Long asn = diggerService.digASN(InetAddress.getByName(ip));
            if (asn != 0L) {
                clientProviderName = diggerService.getProviderByASN(asn);
            }
        } catch (UnknownHostException e) {
            log.error("asn detection error");
        }

        String providerFromPackage = null;
        if (dataForMeasurementRegistration.getProvider() != null) {
            providerFromPackage = dataForMeasurementRegistration.getProvider().getName();
        }
        TimeSlot timeSlot = getTimeSlot(Instant.now().toEpochMilli());
        String testUuid = MeasurementCalculatorUtil.getUuid();
        String testToken = MeasurementCalculatorUtil.getToken(measurementServer.getSecretKey(), testUuid, timeSlot.getSlot());
        String device = dataForMeasurementRegistration.getDeviceOrProbeId();
        if (device == null) {
            String userAgentHeader = HeaderExtrudeUtil.getUserAgent(headers);
            device = userAgentExtractService.getBrowser(userAgentHeader);
        }

        Measurement measurement = Measurement.builder()
                .openTestUuid(testUuid)
                .clientUuid(dataForMeasurementRegistration.getClientUuid())
                .adHocCampaign(dataForMeasurementRegistration.getAdHocCampaign())
                .measurementServerId(measurementServer.getId())
                .networkOperator(providerFromPackage)
                .clientProvider(clientProviderName)
                .device(device)
                .tag(dataForMeasurementRegistration.getPort())
                .token(testToken)
                .testSlot(timeSlot.getSlot())
                .time(new Timestamp(System.currentTimeMillis()))
                .ipAddress(ip)
                .status(MeasurementStatus.STARTED)
                .platform(Platform.UNKNOWN)
                .build();

        if (dataForMeasurementRegistration.getIsOnNet() != null) {
            ServerNetworkType serverNetworkType = dataForMeasurementRegistration.getIsOnNet() ? ServerNetworkType.ON_NET : ServerNetworkType.OFF_NET;
            measurement.setServerType(serverNetworkType.toString());
        }

        Measurement savedMeasurement = measurementRepository.save(measurement);
        Integer port = dataForMeasurementRegistration.getMeasurementType().getServerTechForMeasurement().getDefaultSslPort();

        return MeasurementRegistrationResponse.builder()
                .testNumThreads(MeasurementServerConstants.TEST_NUM_THREADS)
                .testNumPings(Integer.valueOf(MeasurementServerConstants.TEST_NUM_PINGS))
                .testDuration(5)
                .testId(savedMeasurement.getId())
                .testWait(timeSlot.getTestWait())
                .testUuid(testUuid)
                .testToken(testToken)
                .testServerPort(port)
                .testServerEncryption(true)
                .resultUrl(String.format(MeasurementServerConstants.RESULT_URL, measurementServerConfig.getHost()))
                .resultQosUrl(String.format(MeasurementServerConstants.RESULT_QOS_URL, measurementServerConfig.getHost()))
                .testServerName(measurementServer.getName())
                .testServerAddress(measurementServer.getWebAddress())
                .clientRemoteIp(ip)
                .provider(clientProviderName)
                .error(Collections.emptyList())
                .build();
    }

    public TimeSlot getTimeSlot(long now) {

        int slot = Math.toIntExact(now / 1000);
        long counter = measurementRepository.countAllByTestSlot(slot);
        int wait = 0;
        long max = measurementServerConfig.getSlotWindow().intValue();

        while (counter >= max) {
            slot++;
            wait++;
            counter = measurementRepository.countAllByTestSlot(slot);
        }

        return TimeSlot.builder()
                .slot(slot)
                .testWait(wait)
                .build();
    }

    @Override
    public List<Measurement> findLastMeasurementsForMeasurementServerIds(List<Long> ids) {
        return measurementRepository.findLastMeasurementsForMeasurementServerIds(ids);
    }

    @Override
    public MeasurementHistoryResponse getMeasurementDetailByUuid(String uuid) {
        return measurementRepository
                .findByOpenTestUuid(uuid)
                .map(measurementMapper::measurementToMeasurementHistoryResponse)
                .orElseThrow(() -> new MeasurementNotFoundByUuidException(uuid));

    }

    @Override
    public Measurement findByOpenTestUuid(String uuid) {
        return measurementRepository.findByOpenTestUuid(uuid).orElseThrow(() -> new MeasurementNotFoundByUuidException(uuid));
    }

    @Override
    public Optional<Measurement> getMeasurementByToken(String token) {
        return measurementRepository.findByToken(token);
    }

    @Override
    public List<Measurement> getLastSuccessfulMeasurementByIds(List<Long> ids) {
        return measurementRepository.findTopByOrderByStatusAndMeasurementServerIdOrderByTime(ids);
    }

    @Override
    public void deleteByOpenUUID(String uuid) {
        measurementRepository.deleteByOpenTestUuid(uuid);
    }

    @Override
    public void deleteByAdHocCampaign(AdHocCampaign campaign) {
        measurementRepository.deleteByAdHocCampaign(campaign);
    }

    @Override
    public long deleteByProbeId(String probeId) {
        return measurementRepository.deleteByDevice(probeId);
    }

    @Override
    public long deleteByServerId(Long id) {
        return measurementRepository.deleteByMeasurementServerId(id);
    }
}

