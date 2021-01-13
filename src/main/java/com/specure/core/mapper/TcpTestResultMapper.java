package com.specure.core.mapper;

import com.specure.core.model.qos.TcpTestResult;
import com.specure.core.request.measurement.qos.request.TcpTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TcpTestResultMapper {
    TcpTestResult tcpTestResultToTcpTestResultRequest(TcpTestResultRequest tcpTestResultRequest);
}
