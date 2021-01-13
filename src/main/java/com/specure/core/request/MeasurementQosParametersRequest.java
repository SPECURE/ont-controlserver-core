package com.specure.core.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasurementQosParametersRequest {
    private String language;
    private String timezone;
    private String uuid;
}
