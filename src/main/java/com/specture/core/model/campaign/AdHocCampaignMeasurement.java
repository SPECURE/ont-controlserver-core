package com.specture.core.model.campaign;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AdHocCampaignMeasurement {
    private Timestamp timestamp;
    private long download;
    private long upload;
    private float ping;
    private float jitter;
    private float packetLoss;
}
