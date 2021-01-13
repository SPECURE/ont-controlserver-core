package com.specure.core.request;

import com.specure.core.constant.ErrorMessage;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Data
public class MeasurementServerDescriptionRequest {

    @Size(max = 31, message = ErrorMessage.MEASUREMENT_SERVER_CITY_SIZE)
    private String city;

    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_EMAIL_SIZE)
    private String email;

    @Size(max = 63, message = ErrorMessage.MEASUREMENT_SERVER_COMPANY_SIZE)
    private String company;
    private Date expiration;

    @Size(max = 255, message = ErrorMessage.MEASUREMENT_SERVER_IP_ADDRESS_SIZE)
    private String ipAddress;

    @Size(max = 255, message = ErrorMessage.COMMENT_MAX_SIZE)
    private String comment;
}
