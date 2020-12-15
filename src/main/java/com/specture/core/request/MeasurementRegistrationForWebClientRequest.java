package com.specture.core.request;

import com.specture.core.enums.ClientType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class MeasurementRegistrationForWebClientRequest {

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
