package com.specture.core.mapper;

import com.specture.core.model.qos.UdpTestResult;
import com.specture.core.request.measurement.qos.request.UdpTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UdpTestResultMapper {
    UdpTestResult udpTestResultRequestToUdpTestResult(UdpTestResultRequest udpTestResultRequest);
}
