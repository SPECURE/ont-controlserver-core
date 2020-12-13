package com.specture.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonTypeName("tcp")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TcpTestResultRequest extends TestResult {
    private String tcpResultOut;
    private Long tcpObjectiveTimeout;
    private Long durationNs;
    private String tcpResultOutResponse;
    private int tcpObjectiveOutPort;
    private int qosTestUid;
    private Long startTimeNs;
}
