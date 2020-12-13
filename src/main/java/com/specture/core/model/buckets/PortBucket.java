package com.specture.core.model.buckets;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PortBucket {
    private String port;
    private Long docCounter;
}
