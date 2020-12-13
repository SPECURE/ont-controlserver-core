package com.specture.core.service.impl;

import com.specture.core.enums.PortType;
import com.specture.core.mapper.BasicQosTestMapper;
import com.specture.core.model.*;
import com.specture.core.model.qos.*;
import com.specture.core.response.BasicQosTestResponse;
import com.specture.core.service.BasicQosTestService;
import com.specture.core.service.MeasurementServerService;
import com.specture.core.service.MeasurementService;
import com.specture.core.repository.BasicQosTestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BasicQosTestServiceImpl implements BasicQosTestService {

    private final BasicQosTestRepository basicQosTestRepository;
    private final BasicQosTestMapper basicQosTestMapper;
    private final MeasurementService measurementService;
    private final MeasurementServerService measurementServerService;

    public BasicQosTestServiceImpl(BasicQosTestRepository basicQosTestRepository, BasicQosTestMapper basicQosTestMapper, MeasurementService measurementService, MeasurementServerService measurementServerService) {
        this.basicQosTestRepository = basicQosTestRepository;
        this.basicQosTestMapper = basicQosTestMapper;
        this.measurementService = measurementService;
        this.measurementServerService = measurementServerService;
    }

    @Override
    public Page<BasicQosTestResponse> getBasicQosTestsFixed(Pageable pageable) {
        return basicQosTestRepository
                .findByTypeOfProbePort(PortType.FIXED.toString(), pageable)
                .map(basicQosTestMapper::basicQosTestToBasicQosTestResponse);
    }

    @Override
    public EnrichedPageWithAggregations<BasicQosTestResponse> getBasicQosTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return getMeasurementsWithAggregations(PortType.FIXED, packages, probes, from, to, pageable);
    }

    @Override
    public Page<BasicQosTestResponse> getBasicQosTestsMobile(Pageable pageable) {
        return basicQosTestRepository
                .findByTypeOfProbePort(PortType.MOBILE.toString(), pageable)
                .map(basicQosTestMapper::basicQosTestToBasicQosTestResponse);
    }

    @Override
    public EnrichedPageWithAggregations<BasicQosTestResponse> getMobileQosTestsWithFilters(List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return getMeasurementsWithAggregations(PortType.MOBILE, packages, probes, from, to, pageable);
    }

    @Override
    public Page<BasicQosTestResponse> getBasicQosTestsByUuid(
            Pageable pageable,
            String uuid
    ) {
        return basicQosTestRepository
                .findByClientUuid(uuid, pageable)
                .map(basicQosTestMapper::basicQosTestToBasicQosTestResponse);
    }

    @Override
    public void saveMeasurementQosToElastic(MeasurementQos measurementQos) {
        Measurement measurement = measurementService.findByOpenTestUuid(measurementQos.getOpenTestUuid());

        String probePortName = measurement.getTag();
        String probeId = measurement.getDevice();

        BasicQosTest basicQosTest = BasicQosTest.builder()
                .tcp(tcpGetRate(measurementQos.getTcpTestResults()))
                .dns(dnsGetRate(measurementQos.getDnsTestResults()))
                .httpProxy(httpProxyRate(measurementQos.getHttpProxyTestResults()))
                .nonTransparentProxy(nonTransparentRate(measurementQos.getNonTransparentProxyTestResults()))
                .udp(udpRate(measurementQos.getUdpTestResults()))
                .voip(voipRate(measurementQos.getVoipTestResults()))
                .openTestUuid(measurementQos.getOpenTestUuid())
                .clientUuid(measurementQos.getClientUuid())
                .clientLanguage(measurementQos.getClientLanguage())
                .clientName(measurementQos.getClientName())
                .clientVersion(measurementQos.getClientVersion())
                .build();
        float overall = (basicQosTest.getTcp() + basicQosTest.getDns()
                + basicQosTest.getHttpProxy() + basicQosTest.getNonTransparentProxy() + basicQosTest.getUdp()
                + basicQosTest.getVoip()) / 6.0f;
        basicQosTest.setOverallQos(overall);

        if(measurement.getMeasurementServerId() != null) {
            MeasurementServer measurementServer = this.measurementServerService.getMeasurementServerById(measurement.getMeasurementServerId());
            basicQosTest.setMeasurementServerId(measurementServer.getId());
            basicQosTest.setMeasurementServerName(measurementServer.getName());
        }

        updateBasicQosTestWithProbe(probePortName, probeId, basicQosTest);

        if (measurement.getTime() != null) {
            basicQosTest.setTimestamp(measurement.getTimeStampToElasticIso());
            basicQosTest.setMeasurementDate(measurement.getTimeStampToElasticIso());
            basicQosTest.setGraphHour(measurement.getHourOfMeasurement());
        }
        if (measurement.getServerType() != null ) {
            basicQosTest.setServerType(measurement.getServerType());
        }
        if (!Objects.isNull(measurementQos.getAdHocCampaign() )) {
            basicQosTest.setAdHocCampaign(measurementQos.getAdHocCampaign().getId());
        }

        if (!Objects.isNull(measurement.getAdHocCampaign())) {
            basicQosTest.setAdHocCampaign(measurement.getAdHocCampaign().getId().trim());
        }

        basicQosTestRepository.save(basicQosTest);
    }

    protected void updateBasicQosTestWithProbe(String probePortName, String probeId, BasicQosTest basicQosTest) {
        //do nothing
    }

    private float tcpGetRate(List<TcpTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> e.getTcpResultOut().equals("OK")).count();
        return passed/size;
    }

    private float dnsGetRate(List<DnsTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> e.getDnsResultInfo().equals("OK")).count();
        return passed/size;
    }

    private float nonTransparentRate(List<NonTransparentProxyTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> e.getNonTransparentProxyResult().equals("OK")).count();
        return passed/size;
    }

    private float httpProxyRate(List<HttpProxyTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> (e.getHttpResultStatus() == 200)).count();
        return passed/size;
    }

    private float udpRate(List<UdpTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> (e.getUdpResultOutPacketLossRate().equals("0"))).count();
        return passed/size;
    }

    private float voipRate(List<VoipTestResult> results){
        if (!isValidList(results)) {
            return 0.0f;
        }
        float size = results.size();
        float passed = results.stream().filter(e -> (e.getVoipResultStatus().equals("OK"))).count();
        return passed/size;
    }

    private <T> boolean isValidList(List<T> results) {
        if (results == null) {
            return false;
        }
        return (results.size()) != 0;
    }

    private EnrichedPageWithAggregations<BasicQosTestResponse> getMeasurementsWithAggregations(PortType portType, List<Long> packages, List<String> probes, LocalDateTime from, LocalDateTime to, Pageable pageable){
        return  basicQosTestRepository
                .findByTypeOfProbePortPackagesAndDate(portType.toString(), packages, probes, from, to, pageable)
                .mapEnriched(basicQosTestMapper::basicQosTestToBasicQosTestResponse);
    }
}
