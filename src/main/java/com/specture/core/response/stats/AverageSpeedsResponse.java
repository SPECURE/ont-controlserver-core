package com.specture.core.response.stats;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageSpeedsResponse {
    Long upload;
    Long download;
}
