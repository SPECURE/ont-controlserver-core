package com.specure.core.mapper;

import com.specure.core.model.qos.NonTransparentProxyTestResult;
import com.specure.core.request.measurement.qos.request.NonTransparentProxyTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NonTransparentProxyTestResultMapper {
    NonTransparentProxyTestResult nonTransparentProxyTestResultRequestToNonTransparentProxyTestResult(NonTransparentProxyTestResultRequest nonTransparentProxyTestResultRequest);
}
