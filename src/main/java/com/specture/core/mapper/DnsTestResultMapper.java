package com.specture.core.mapper;

import com.specture.core.model.qos.DnsTestResult;
import com.specture.core.request.measurement.qos.request.DnsTestResultRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DnsTestResultMapper {
    DnsTestResult dnsTestResultRequestToDnsTestResult(DnsTestResultRequest dnsTestResultRequest);
}
