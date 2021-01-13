package com.specure.core.mapper;

import com.specure.core.model.qos.VoipTestResult;
import com.specure.core.request.measurement.qos.request.VoipTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoipTestResultMapper {
    VoipTestResult voipTestResultRequestToVoipTestResult(VoipTestResultRequest voipTestResultRequest);
}
