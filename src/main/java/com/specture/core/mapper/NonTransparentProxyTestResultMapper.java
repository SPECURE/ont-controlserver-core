package com.specture.core.mapper;

import com.specture.core.model.qos.NonTransparentProxyTestResult;
import com.specture.core.request.measurement.qos.request.NonTransparentProxyTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NonTransparentProxyTestResultMapper {
    NonTransparentProxyTestResult nonTransparentProxyTestResultRequestToNonTransparentProxyTestResult(NonTransparentProxyTestResultRequest nonTransparentProxyTestResultRequest);
}
