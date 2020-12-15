package com.specture.core.request.measurement.qos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("non_transparent_proxy")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class NonTransparentProxyTestResultRequest extends TestResult {

    @JsonProperty("nontransproxy_result")
    private String nonTransparentProxyResult;

    @JsonProperty("nontransproxy_objective_request")
    private String nonTransparentProxyObjectiveRequest;

    @JsonProperty("nontransproxy_objective_timeout")
    private long nonTransparentProxyObjectiveTimeout;

    @JsonProperty("nontransproxy_objective_port")
    private int nonTransparentProxyObjectivePort;

    @JsonProperty("nontransproxy_result_response")
    private String nonTransparentProxyResultResponse;

    private long qosTestUid;
    private long durationNs;
    private long startTimeNs;
}
