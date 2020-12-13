package com.specture.core.model.campaign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdHocCampaignMeasurementsSummary {
    private long averageDownloadSpeed;
    private long averageUploadSpeed;
    private float averageLatency;
    private long total;
}
