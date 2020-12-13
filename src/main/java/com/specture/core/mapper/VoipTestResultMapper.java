package com.specture.core.mapper;

import com.specture.core.model.qos.VoipTestResult;
import com.specture.core.request.measurement.qos.request.VoipTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoipTestResultMapper {
    VoipTestResult voipTestResultRequestToVoipTestResult(VoipTestResultRequest voipTestResultRequest);
}
