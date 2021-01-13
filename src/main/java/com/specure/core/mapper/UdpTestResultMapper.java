package com.specure.core.mapper;

import com.specure.core.model.qos.UdpTestResult;
import com.specure.core.request.measurement.qos.request.UdpTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UdpTestResultMapper {
    UdpTestResult udpTestResultRequestToUdpTestResult(UdpTestResultRequest udpTestResultRequest);
}
