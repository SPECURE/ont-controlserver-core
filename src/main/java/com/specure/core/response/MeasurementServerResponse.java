package com.specure.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.specure.core.model.Provider;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
public class MeasurementServerResponse {
    private Long id;
    private String uuid;

    private String name;
    private String webAddress;
    private Provider provider;

    private String secretKey;

    private String city;
    private String email;

    private String company;
    private Date expiration;
    private String ipAddress;
    private String comment;

    private String countries;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp lastSuccessfulMeasurement;
}
