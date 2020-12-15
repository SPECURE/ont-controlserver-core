package com.specture.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("http_proxy")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class HttpProxyTestResultRequest extends TestResult {
    private String httpObjectiveUrl;
    private long httpResultDuration;
    private String httpResultHeader;
    private long durationNs;
    private int httpResultLength;
    private long qosTestUid;
    private long startTimeNs;
    private String httpResultHash;
    private int httpResultStatus;
}
