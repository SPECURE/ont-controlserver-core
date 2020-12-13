package com.specture.core.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specture.core.enums.ClientType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MeasurementRegistrationForAdminRequest {

    @NotNull
    private Long measurementServerId;

    @NotNull
    private ClientType client;

    private String language;
    private long time;
    private String timezone;
    private String type;

    private String uuid;

    private String version;
    private String version_code;
}
