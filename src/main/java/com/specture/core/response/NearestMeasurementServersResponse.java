package com.specture.core.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class NearestMeasurementServersResponse {
    private List<String> error;
    private List<MeasurementServerForWebResponse> servers;
}
