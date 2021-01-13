package com.specure.core.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specure.core.request.measurement.qos.request.TestResult;
import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeasurementQosRequest {
    @NotNull(message = ErrorMessage.TEST_TOKEN_REQUIRED)
    private String testToken;
    private String clientUuid;
    private long time;
    private String clientVersion;
    private List<TestResult> qosResult;
    private String clientName;
    private String clientLanguage;
}

