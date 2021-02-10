package com.specure.core.response;

import com.specure.core.enums.PackageDescription;
import com.specure.core.enums.PortType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackageResponse {
    private Long id;
    private String advertisedName;
    private ProviderResponse provider;
    private PackageDescription packageDescription;
    private String packageType;
    private TechnologyResponse technology;
    private Long threshold;
    private Long download;
    private Long upload;
    private Long throttleSpeedDownload;
    private Long throttleSpeedUpload;
    private PortType readyForPort;
    private boolean isAssignedToPort;
    private boolean visibleOnPublicPortal;
}
