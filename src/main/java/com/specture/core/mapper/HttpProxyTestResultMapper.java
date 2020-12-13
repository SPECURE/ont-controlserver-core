package com.specture.core.mapper;

import com.specture.core.model.qos.HttpProxyTestResult;
import com.specture.core.request.measurement.qos.request.HttpProxyTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HttpProxyTestResultMapper {
    HttpProxyTestResult httpProxyTestResultRequestToHttpProxyTestResult(HttpProxyTestResultRequest httpProxyTestResultRequest);
}
