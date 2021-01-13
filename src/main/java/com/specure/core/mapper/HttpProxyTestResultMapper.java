package com.specure.core.mapper;

import com.specure.core.model.qos.HttpProxyTestResult;
import com.specure.core.request.measurement.qos.request.HttpProxyTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HttpProxyTestResultMapper {
    HttpProxyTestResult httpProxyTestResultRequestToHttpProxyTestResult(HttpProxyTestResultRequest httpProxyTestResultRequest);
}
