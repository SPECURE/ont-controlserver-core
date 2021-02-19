package com.specure.core.request;

import com.specure.core.constant.ErrorMessage;
import com.specure.core.enums.MeasurementType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Builder
@Data
public class MeasurementServerRequest {

    @NotNull(message = ErrorMessage.MEASUREMENT_SERVER_NAME_REQUIRED)
    @Size(max = 63, message = ErrorMessage.MEASUREMENT_SERVER_NAME_MAX_SIZE)
    private String name;

    @NotNull(message = ErrorMessage.MEASUREMENT_SERVER_WEB_ADDRESS_REQUIRED)
    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_WEB_ADDRESS_SIZE)
    private String webAddress;

    private Integer port;
    private Integer portSsl;

    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_SECRET_KEY_SIZE)
    private String secretKey;

    private Long providerId;

    @Size(max = 31, message = ErrorMessage.MEASUREMENT_SERVER_CITY_SIZE)
    private String city;

    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_EMAIL_SIZE)
    private String email;

    @Size(max = 63, message = ErrorMessage.MEASUREMENT_SERVER_COMPANY_SIZE)
    private String company;

    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_IP_ADDRESS_SIZE)
    private String ipAddress;

    @Size(max = 255, message = ErrorMessage.COMMENT_MAX_SIZE)
    private String comment;

    private Date expiration;

//    @NotNull //todo remove after ui update
    private Set<MeasurementType> measurementTypeList;
}
