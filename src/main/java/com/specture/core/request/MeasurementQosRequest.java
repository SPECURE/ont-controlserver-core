package com.specture.core.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specture.core.request.measurement.qos.request.TestResult;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.specture.core.constant.ErrorMessage.TEST_TOKEN_REQUIRED;

@Builder
@Data
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeasurementQosRequest {
    @NotNull(message = TEST_TOKEN_REQUIRED)
    private String testToken;
    private String clientUuid;
    private long time;
    private String clientVersion;
    private List<TestResult> qosResult;
    private String clientName;
    private String clientLanguage;
}

