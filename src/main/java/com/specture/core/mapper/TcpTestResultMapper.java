package com.specture.core.mapper;

import com.specture.core.model.qos.TcpTestResult;
import com.specture.core.request.measurement.qos.request.TcpTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TcpTestResultMapper {
    TcpTestResult tcpTestResultToTcpTestResultRequest(TcpTestResultRequest tcpTestResultRequest);
}
