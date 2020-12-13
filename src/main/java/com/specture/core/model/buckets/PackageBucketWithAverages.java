package com.specture.core.model.buckets;

import com.specture.core.enums.PackageType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackageBucketWithAverages {
    private Long packageId;
    private String packageName;
    private PackageType packageType;
    private Long docCounter;
    private Long download;
    private Long upload;
}
