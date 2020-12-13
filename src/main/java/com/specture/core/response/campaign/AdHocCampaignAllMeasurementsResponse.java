package com.specture.core.response.campaign;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AdHocCampaignAllMeasurementsResponse {
    private AdHocCampaignMeasurementsSummaryResponse onNetSummary;
    private AdHocCampaignMeasurementsSummaryResponse offNetSummary;
    private List<AdHocCampaignMeasurementResponse> onNetAllMeasurementsList;
    private List<AdHocCampaignMeasurementResponse> offNetAllMeasurementsList;
}
