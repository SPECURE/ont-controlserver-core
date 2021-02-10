package com.specure.core.request;

import com.specure.core.model.Coordinates;
import com.specure.core.model.MeasurementIsActive;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.specure.core.constant.ErrorMessage.*;


@Builder
@Data
public class SiteRequest {

    @NotBlank(message = SITE_ADVERTISED_ID_REQUIRED)
    @Size(max = 31, message = ADVERTISED_ID_MAX_SIZE)
    private String advertisedId;

    @NotNull(message = SITE_NAME_REQUIRED)
    @Size(max = 63, message = SITE_NAME_MAX_SIZE)
    private String name;

    @NotNull(message = SITE_ADDRESS_REQUIRED)
    @Size(max = 255, message = SITE_ADDRESS_MAX_SIZE)
    private String address;

    @NotNull(message = SITE_COORDINATES_REQUIRED)
    private Coordinates coordinates;

    @NotNull(message = SITE_MEASUREMENT_REQUIRED)
    private MeasurementIsActive measurement;

    private boolean visibleOnPublicPortal;
}
