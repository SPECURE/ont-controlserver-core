package com.specture.core.response.campaign;

import com.specture.core.model.Coordinates;
import com.specture.core.response.ProbeResponse;
import com.specture.core.response.ProviderResponse;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class AdHocCampaignResponse {
    private String id;
    private String status;

    private String location;
    private Coordinates coordinates;

    private Timestamp start;
    private Timestamp finish;

    private ProbeResponse probe;
    private ProviderResponse provider;
}
