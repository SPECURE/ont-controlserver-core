package com.specture.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specture.core.enums.ClientType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.specture.core.constant.ErrorMessage.CLIENT_UUID_REQUIRED;
import static com.specture.core.constant.ErrorMessage.PORT_NAME_REQUIRED;

@Builder
@Data
public class MeasurementRegistrationForProbeRequest {

    @NotNull
    private ClientType client;

    @NotNull(message = PORT_NAME_REQUIRED)
    @JsonProperty("ifport")
    private String port;

    @NotBlank(message = CLIENT_UUID_REQUIRED)
    private String uuid;

    @JsonProperty("on-net")
    private boolean isOnNet;

    private String language;
    private String timezone;
}
