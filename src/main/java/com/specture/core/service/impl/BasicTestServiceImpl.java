package com.specture.core.service.impl;

import com.specture.core.enums.NetworkType;
import com.specture.core.enums.PortType;
import com.specture.core.exception.MeasurementHistoryNotAccessibleException;
import com.specture.core.mapper.BasicTestMapper;
import com.specture.core.model.BasicTest;
import com.specture.core.model.EnrichedPageWithAggregations;
import com.specture.core.model.Measurement;
import com.specture.core.model.MeasurementServer;
import com.specture.core.repository.BasicTestRepository;
import com.specture.core.response.BasicTestResponse;
import com.specture.core.service.BasicTestService;
import com.specture.core.service.MeasurementServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BasicTestServiceImpl implements BasicTestService {

    private BasicTestRepository basicTestRepository;
    private BasicTestMapper basicTestMapper;
    private MeasurementServerService measurementServerService;

    public BasicTestServiceImpl(BasicTestRepository basicTestRepository, BasicTestMapper basicTestMapper, MeasurementServerService measurementServerService) {
        this.basicTestRepository = basicTestRepository;
        this.basicTestMapper = basicTestMapper;
        this.measurementServerService = measurementServerService;
    }

    @Override
    public Page<BasicTestResponse> getBasicTests(Pageable pageable) {
        return basicTestRepository
                .findByTypeOfProbePort(PortType.FIXED.toString(), pageable)
                .map(basicTestMapper::basicTestToBasicTestResponse);
    }

    @Override
    public EnrichedPageWithAggregations<BasicTestResponse> getBasicTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return getMeasurementsWithAggregations(PortType.FIXED, packages, probes, from, to, pageable);
    }

    @Override
    public Page<BasicTestResponse> getWebTests(LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return basicTestRepository
                .findWebByPackagesAndDate(from, to, pageable)
                .map(basicTestMapper::basicTestToBasicTestResponse);
    }

    @Override
    public Page<BasicTestResponse> getBasicMobileTests(Pageable pageable) {
        return basicTestRepository
                .findByTypeOfProbePort(PortType.MOBILE.toString(), pageable)
                .map(basicTestMapper::basicTestToBasicTestResponse);
    }

    public EnrichedPageWithAggregations<BasicTestResponse> getMobileTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return getMeasurementsWithAggregations(PortType.MOBILE, packages, probes, from, to, pageable);
    }

    @Override
    public Page<BasicTestResponse> getBasicTestsByUuid(Pageable pageable, String uuid) {
        if (Objects.isNull(uuid) || uuid.isBlank()) {
            throw new MeasurementHistoryNotAccessibleException();
        }
        return basicTestRepository.findByClientUuid(uuid, pageable)
                .map(basicTestMapper::basicTestToBasicTestResponse);
    }

    @Override
    public void saveMeasurementToElastic(Measurement measurement) {
        Float packetLoss = null;//100.0F;
        try {
            if (measurement.getVoip_result_packet_loss() != null && !measurement.getVoip_result_packet_loss().isEmpty())
                packetLoss = Float.parseFloat(measurement.getVoip_result_packet_loss().trim());
        } catch (NumberFormatException nfe) {
            log.error("NumberFormatException: " + nfe.getMessage());
        }
        Float jitter = null;//-1;
        try {
            if (measurement.getVoip_result_jitter() != null && !measurement.getVoip_result_jitter().isEmpty())
                jitter = Float.parseFloat(measurement.getVoip_result_jitter());
        } catch (NumberFormatException nfe) {
            log.error("NumberFormatException: " + nfe.getMessage());
        }
        BasicTest basicTest = BasicTest.builder()
                .openTestUuid(measurement.getOpenTestUuid())
                .clientUuid(measurement.getClientUuid())
                .clientProvider(measurement.getClientProvider())
                .operator(measurement.getNetworkOperator())
                .download(measurement.getSpeedDownload())
                .upload(measurement.getSpeedUpload())
                .signal(measurement.getSignalStrength())
                .device(measurement.getDevice())
                .packetLoss(packetLoss)
                .networkType(NetworkType.fromValue(measurement.getNetworkType()).toString())
                .jitter(jitter)
                .build();

        updateBasicTestWithProbe(measurement.getTag(), measurement.getDevice(), basicTest);

        if (measurement.getMeasurementServerId() != null) {
            MeasurementServer measurementServer = this.measurementServerService.getMeasurementServerById(measurement.getMeasurementServerId());
            basicTest.setMeasurementServerId(measurementServer.getId());
            basicTest.setMeasurementServerName(measurementServer.getName());
        }

        if (measurement.getTime() != null) {
            basicTest.setTimestamp(measurement.getTimeStampToElasticIso());
            basicTest.setMeasurementDate(measurement.getTimeStampToElasticIso());
            basicTest.setGraphHour(measurement.getHourOfMeasurement());
        }

        if (measurement.getPingMedian() != null) {
            basicTest.setPing((float) measurement.getPingMedian() / 1000000);
        }

        if (measurement.getVoip_result_jitter() != null) {
            basicTest.setJitter(jitter);
        }

        if (measurement.getServerType() != null) {
            basicTest.setServerType(measurement.getServerType());
        }

        if (!Objects.isNull(measurement.getAdHocCampaign())) {
            basicTest.setAdHocCampaign(measurement.getAdHocCampaign().getId().trim());
        }

        basicTestRepository.save(basicTest);
    }

    protected void updateBasicTestWithProbe(String probePortName, String probeId, BasicTest basicTest) {
        //do nothing
    }

    private EnrichedPageWithAggregations<BasicTestResponse> getMeasurementsWithAggregations(PortType portType, List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return basicTestRepository
                .findByTypeOfProbePortPackagesAndDate(portType.toString(), packages, probes, from, to, pageable)
                .mapEnriched(basicTestMapper::basicTestToBasicTestResponse);
    }
}
