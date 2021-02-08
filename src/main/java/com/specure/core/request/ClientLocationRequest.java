package com.specure.core.request;

import com.specure.core.enums.MeasurementType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Data
@Getter
public class ClientLocationRequest {
    @NotNull
    private MeasurementType client;
    private String language;
    private LocationRequest location;
}
