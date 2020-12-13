package com.specture.core.request;

import com.specture.core.model.Coordinates;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static com.specture.core.constant.ErrorMessage.*;

@Builder
@Data
public class AdHocCampaignRequest {

    @NotNull(message = CAMPAIGN_ID_REQUIRED)
    private String id;

    @NotNull(message = SITE_COORDINATES_REQUIRED)
    private Coordinates coordinates;

    private String location;

    @NotNull(message = CAMPAIGN_START_REQUIRED)
    private Timestamp start;

    @NotNull(message = CAMPAIGN_FINISH_REQUIRED)
    private Timestamp finish;

    @NotNull(message = CAMPAIGN_PROBE_REQUIRED)
    private ProbeRequest probe;

    private ProviderRequest provider;
}
