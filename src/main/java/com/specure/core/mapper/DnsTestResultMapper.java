package com.specure.core.mapper;

import com.specure.core.model.qos.DnsTestResult;
import com.specure.core.request.measurement.qos.request.DnsTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DnsTestResultMapper {
    DnsTestResult dnsTestResultRequestToDnsTestResult(DnsTestResultRequest dnsTestResultRequest);
}
