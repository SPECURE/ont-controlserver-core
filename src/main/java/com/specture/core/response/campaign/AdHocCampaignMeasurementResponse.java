package com.specture.core.response.campaign;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AdHocCampaignMeasurementResponse {
    private Timestamp timestamp;
    private long download;
    private long upload;
    private float latency;
    private float jitter;
    private float packetLoss;
}
