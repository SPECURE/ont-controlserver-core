package com.specure.core.model.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteAverageSpeedsByPackageTypeStats {
    Long siteId;
    String advertisedId;
    AverageSpeeds fixed;
    AverageSpeeds fixedWireless;
    AverageSpeeds mobile;
}
