package com.specture.core.model.buckets;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PackageBucket {
    private Long packageId;
    private Long docCounter;
}
