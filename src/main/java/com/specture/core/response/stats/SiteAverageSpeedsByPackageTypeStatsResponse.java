package com.specture.core.response.stats;

import com.specture.core.model.Coordinates;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SiteAverageSpeedsByPackageTypeStatsResponse {
    private Long siteId;
    private String advertisedId;
    private Coordinates coordinates;
    private AverageSpeedsResponse fixed;
    private AverageSpeedsResponse fixedWireless;
    private AverageSpeedsResponse mobile;
}
