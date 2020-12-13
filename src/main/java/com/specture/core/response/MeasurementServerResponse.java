package com.specture.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.specture.core.model.Provider;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Builder
@Data
public class MeasurementServerResponse {
    private Long id;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp timeOfLastMeasurement;

    private boolean lastMeasurementSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp lastSuccessfulMeasurement;
}
