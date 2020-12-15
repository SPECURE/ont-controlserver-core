package com.specture.core.response.measurement.qos.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeasurementQosParametersResponse {
    private String clientRemoteIp;
    private String testDuration;
    private Objectives objectives;
    private List<String> error;
}

