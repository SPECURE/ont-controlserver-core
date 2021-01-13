package com.specure.core.response.measurement.result.Detail;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasurementDetailDataFragment {
    private String key;
    private String title;
    private String value;
}
