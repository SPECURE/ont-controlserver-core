package com.specure.core.response.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specure.core.enums.QosMeasurement;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QosMeasurementTypeDescription {
    private String name;

    @JsonProperty(value = "test_type")
    private QosMeasurement testType;

    public static QosMeasurementTypeDescription fromQosMeasurement(QosMeasurement qosMeasurement) {
        return new QosMeasurementTypeDescription(qosMeasurement.getDescription(), qosMeasurement);
    }
}
