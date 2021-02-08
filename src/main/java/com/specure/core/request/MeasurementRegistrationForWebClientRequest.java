package com.specure.core.request;

import com.specure.core.enums.MeasurementType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class MeasurementRegistrationForWebClientRequest {

    @NotNull
    private MeasurementType client;

    private String language;
    private long time;
    private String timezone;
    private String type;

    private String uuid;

    private String version;
    private String version_code;
}
