package com.specture.core.request;

import com.specture.core.enums.PackageDescription;
import com.specture.core.enums.PortType;
import com.specture.core.enums.Technology;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

import static com.specture.core.constant.ErrorMessage.*;

@Builder
@Data
public class PackageRequest {

    @NotBlank(message = ADVERTISED_NAME_REQUIRED)
    @Size(max = 63, message = ADVERTISED_NAME_MAX_SIZE)
    private String advertisedName;

    @NotNull(message = PROVIDER_REQUIRED)
    private ProviderRequest provider;

    @NotNull(message = PACKAGE_DESCRIPTION_REQUIRED)
    private PackageDescription packageDescription;

    @NotNull(message = PACKAGE_TYPE_REQUIRED)
    private String packageType;

    @NotNull(message = TECHNOLOGY_REQUIRED)
    private Technology technology;

    private PortType readyForPort;

    @NotNull(message = THRESHOLD_REQUIRED)
    @Max(value = 10000000000L, message = PACKAGE_THRESHOLD_RANGE)
    @Min(value = 0, message = PACKAGE_THRESHOLD_RANGE)
    private Long threshold;

    @NotNull(message = DOWNLOAD_REQUIRED)
    @Max(value = 1000000000L, message = PACKAGE_DOWNLOAD_RANGE)
    @Min(value = 0, message = PACKAGE_DOWNLOAD_RANGE)
    private Long download;

    @NotNull(message = UPLOAD_REQUIRED)
    @Max(value = 1000000000L, message = PACKAGE_UPLOAD_RANGE)
    @Min(value = 0, message = PACKAGE_UPLOAD_RANGE)
    private Long upload;

    @NotNull(message = THROTTLE_SPEED_DOWNLOAD_REQUIRED)
    @Max(value = 1000000000L, message = PACKAGE_THROTTLE_RANGE)
    @Min(value = 0, message = PACKAGE_THROTTLE_RANGE)
    private Long throttleSpeedDownload;

    @NotNull(message = THROTTLE_SPEED_UPLOAD_REQUIRED)
    @Max(value = 1000000000L, message = PACKAGE_THROTTLE_RANGE)
    @Min(value = 0, message = PACKAGE_THROTTLE_RANGE)
    private Long throttleSpeedUpload;

}
