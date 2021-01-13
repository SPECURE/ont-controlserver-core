package com.specure.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("dns")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DnsTestResultRequest extends TestResult {
    private List<DnsResultEntriesRequest> dnsResultEntries;
    private long dnsObjectiveTimeout;
    private long qosTestUid;
    private long startTimeNs;
    private String dnsObjectiveDnsRecord;
    private long durationNs;
    private String dnsObjectiveHost;
    private String dnsObjectiveResolver;
    private String dnsResultInfo;
    private String dnsResultStatus;
    private long dnsResultDuration;
    private int dnsResultEntriesFound;
}
