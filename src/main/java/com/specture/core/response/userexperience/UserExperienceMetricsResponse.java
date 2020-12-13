package com.specture.core.response.userexperience;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specture.core.response.PackageResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserExperienceMetricsResponse {

    @JsonProperty("package")
    private PackageResponse aPackage;

    private List<UserExperienceParameter> parameters;
    private List<UserExperienceMetric> metrics;
}
