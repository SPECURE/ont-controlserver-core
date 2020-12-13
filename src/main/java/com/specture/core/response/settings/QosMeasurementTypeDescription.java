package com.specture.core.response.settings;

import com.specture.core.enums.QosMeasurement;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QosMeasurementTypeDescription {
    private String name;
    private QosMeasurement testType;
}
