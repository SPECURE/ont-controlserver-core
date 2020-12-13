package com.specture.core.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeasurementServerForWebResponse {
    private Long id;
    private Integer port;
    private String address;
    private String city;
    private String country;
    private String distance;
    private String sponsor;
}
