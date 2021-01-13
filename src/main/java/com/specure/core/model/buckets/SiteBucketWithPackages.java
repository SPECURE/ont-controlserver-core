package com.specure.core.model.buckets;

import com.specure.core.model.Coordinates;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SiteBucketWithPackages {
    private Long siteId;
    private String siteName;
    private Long docCounter;
    private Coordinates coordinates;
    private List<PackageBucketWithAverages> packageBucketWithAverages;
}
