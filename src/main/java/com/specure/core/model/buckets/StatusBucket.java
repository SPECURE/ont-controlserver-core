package com.specure.core.model.buckets;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatusBucket {
    private String status;
    private Long docCounter;
}
