package com.specture.sah.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.specture.sah.constant.ErrorMessage.TEST_UUID_REQUIRED;

@Builder
@Data
public class MeasurementResultRequest {
    private String language;
    private List<String> options;

    @NotNull(message = TEST_UUID_REQUIRED)
    private String testUuid;

    private String timezone;
}
