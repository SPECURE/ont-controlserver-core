package com.specture.core.request.measurement.qos.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DnsResultEntriesRequest {
    private String dnsResultAddress;
    private String dnsResultTtl;
}
