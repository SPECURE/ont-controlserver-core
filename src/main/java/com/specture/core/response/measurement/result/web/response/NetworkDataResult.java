package com.specture.core.response.measurement.result.web.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NetworkDataResult {
    private String title;
    private String value;
}
