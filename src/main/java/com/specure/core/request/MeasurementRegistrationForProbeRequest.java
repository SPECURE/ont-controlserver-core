package com.specure.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specure.core.enums.ClientType;
import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class MeasurementRegistrationForProbeRequest {

    @NotNull
    private ClientType client;

    @NotNull(message = ErrorMessage.PORT_NAME_REQUIRED)
    @JsonProperty("ifport")
    private String port;

    @NotBlank(message = ErrorMessage.CLIENT_UUID_REQUIRED)
    private String uuid;

    @JsonProperty("on-net")
    private boolean isOnNet;

    private String language;
    private String timezone;
}
