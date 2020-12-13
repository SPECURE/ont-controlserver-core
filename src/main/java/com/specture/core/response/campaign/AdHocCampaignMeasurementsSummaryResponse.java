package com.specture.core.response.campaign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdHocCampaignMeasurementsSummaryResponse {
    private long averageDownloadSpeed;
    private long averageUploadSpeed;
    private float averageLatency;
    private long total;
}
