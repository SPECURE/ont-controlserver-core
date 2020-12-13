package com.specture.core.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.specture.core.constant.ErrorMessage.*;

@Builder
@Data
public class MeasurementServerRequest {

    @NotNull(message = MEASUREMENT_SERVER_NAME_REQUIRED)
    @Size(max = 63, message = MEASUREMENT_SERVER_NAME_MAX_SIZE)
    private String name;

    @NotNull(message = MEASUREMENT_SERVER_WEB_ADDRESS_REQUIRED)
    @Size(max = 255, message = MEASUREMENT_SERVER_WEB_ADDRESS_SIZE)
    private String webAddress;

    private Integer port;
    private Integer portSsl;

    @Size(max = 255, message = MEASUREMENT_SERVER_SECRET_KEY_SIZE)
    private String secretKey;

    private Long providerId;

    @Size(max = 31, message = MEASUREMENT_SERVER_CITY_SIZE)
    private String city;

    @Size(max = 255, message = MEASUREMENT_SERVER_EMAIL_SIZE)
    private String email;

    @Size(max = 63, message = MEASUREMENT_SERVER_COMPANY_SIZE)
    private String company;

    @Size(max = 255, message = MEASUREMENT_SERVER_IP_ADDRESS_SIZE)
    private String ipAddress;

    @Size(max = 255, message = COMMENT_MAX_SIZE)
    private String comment;

    private Date expiration;
}
