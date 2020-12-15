package com.specture.core.mapper.impl;

import com.specture.core.mapper.*;
import com.specture.core.model.qos.*;
import com.specture.core.request.MeasurementQosRequest;
import com.specture.core.request.measurement.qos.request.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MeasurementQosMapperImpl implements MeasurementQosMapper {

    private final TcpTestResultMapper tcpTestResultMapper;
    private final VoipTestResultMapper voipTestResultMapper;
    private final UdpTestResultMapper udpTestResultMapper;
    private final HttpProxyTestResultMapper httpProxyTestResultMapper;
    private final NonTransparentProxyTestResultMapper nonTransparentProxyTestResultMapper;
    private final DnsTestResultMapper dnsTestResultMapper;

    @Override
    public MeasurementQos measurementQosRequestToMeasurementQos(MeasurementQosRequest measurementQosRequest) {
        String openTestUuid = measurementQosRequest.getTestToken().split("_")[0];
        MeasurementQos measurementQos = MeasurementQos.builder()
                .time(new Timestamp(measurementQosRequest.getTime()))
                .openTestUuid(openTestUuid)
                .clientLanguage(measurementQosRequest.getClientLanguage())
                .clientVersion(measurementQosRequest.getClientVersion())
                .clientName(measurementQosRequest.getClientName())
                .clientUuid(measurementQosRequest.getClientUuid())
                .testToken(measurementQosRequest.getTestToken())
                .build();

        List<TcpTestResult> tcpTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("tcp"))
                .map(e -> (TcpTestResultRequest) e)
                .map(tcpTestResultMapper::tcpTestResultToTcpTestResultRequest)
                .peek(e -> e.setMeasurementQos(measurementQos))
                .collect(Collectors.toList());

        List<VoipTestResult> voipTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("voip"))
                .map(e -> (VoipTestResultRequest) e)
                .map(voipTestResultMapper::voipTestResultRequestToVoipTestResult)
                .peek(e -> e.setMeasurementQos(measurementQos))
                .collect(Collectors.toList());

        List<UdpTestResult> udpTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("udp"))
                .map(e -> (UdpTestResultRequest) e)
                .map(udpTestResultMapper::udpTestResultRequestToUdpTestResult)
                .peek(e -> e.setMeasurementQos(measurementQos))
                .collect(Collectors.toList());

        List<HttpProxyTestResult> httpProxyTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("http_proxy"))
                .map(e -> (HttpProxyTestResultRequest) e)
                .map(httpProxyTestResultMapper::httpProxyTestResultRequestToHttpProxyTestResult)
                .peek(e -> e.setMeasurementQos(measurementQos))
                .collect(Collectors.toList());

        List<NonTransparentProxyTestResult> nonTransparentProxyTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("non_transparent_proxy"))
                .map(e -> (NonTransparentProxyTestResultRequest) e)
                .map(nonTransparentProxyTestResultMapper::nonTransparentProxyTestResultRequestToNonTransparentProxyTestResult)
                .peek(e -> e.setMeasurementQos(measurementQos))
                .collect(Collectors.toList());

        List<DnsTestResult> dnsTestResults = measurementQosRequest
                .getQosResult()
                .stream()
                .filter(e -> e.getTest_type().equals("dns"))
                .map(e -> (DnsTestResultRequest) e)
                .map(dnsTestResultMapper::dnsTestResultRequestToDnsTestResult)
                .peek(e -> {
                    e.setMeasurementQos(measurementQos);
                    if (!Objects.isNull(e.getDnsResultEntries())) {
                        e.getDnsResultEntries().forEach(entry -> entry.setDnsTestResult(e));
                    }
                })
                .collect(Collectors.toList());

        measurementQos.setTcpTestResults(tcpTestResults);
        measurementQos.setVoipTestResults(voipTestResults);
        measurementQos.setUdpTestResults(udpTestResults);
        measurementQos.setHttpProxyTestResults(httpProxyTestResults);
        measurementQos.setNonTransparentProxyTestResults(nonTransparentProxyTestResults);
        measurementQos.setDnsTestResults(dnsTestResults);
        measurementQos.setClientUuid(measurementQosRequest.getClientUuid());

        return measurementQos;
    }
}
