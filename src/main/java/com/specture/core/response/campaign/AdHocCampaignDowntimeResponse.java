package com.specture.core.response.campaign;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AdHocCampaignDowntimeResponse {
    private Long id;
    private Timestamp start;
    private Timestamp finish;
    private Long duration;
}
