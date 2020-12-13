package com.specture.core.model.buckets;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProbeBucket {
    private String probe;
    private Long docCounter;
}
