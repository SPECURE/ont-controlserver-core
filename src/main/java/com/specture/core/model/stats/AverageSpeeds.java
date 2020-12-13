package com.specture.core.model.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageSpeeds {
    Long upload;
    Long download;
}
