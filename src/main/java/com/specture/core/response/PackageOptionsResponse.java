package com.specture.core.response;

import com.specture.core.enums.PackageDescription;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PackageOptionsResponse {
    private List<ProviderResponse> providerOptions;
    private List<String> packageTypeOptions;
    private List<TechnologyResponse> technologyOptions;
    private List<PackageDescription> packageDescriptionOptions;
    private List<String> probeIdOptions;
}
