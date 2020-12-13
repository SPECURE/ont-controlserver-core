package com.specture.core.mapper;

import com.specture.core.model.AdHocCampaign;
import com.specture.core.model.campaign.AdHocCampaignMeasurement;
import com.specture.core.request.AdHocCampaignRequest;
import com.specture.core.response.campaign.AdHocCampaignMeasurementResponse;
import com.specture.core.response.campaign.AdHocCampaignMeasurementsSummaryResponse;
import com.specture.core.response.campaign.AdHocCampaignResponse;
import com.specture.core.model.campaign.AdHocCampaignMeasurementsSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProviderMapper.class, ProbeMapper.class})
public interface AdHocCampaignMapper {

    @Mapping(target = "status", source = "adHocCampaign.status.value")
    AdHocCampaignResponse adHocCampaignToAdHocCampaignResponse(AdHocCampaign adHocCampaign);

    AdHocCampaign adHocCampaignRequestToAdHocCampaign(AdHocCampaignRequest adHocCampaignRequest);

    @Mapping(target = "latency", source = "ping")
    AdHocCampaignMeasurementResponse adHocCampaignMeasurementToAdHocCampaignMeasurementResponse(AdHocCampaignMeasurement adHocCampaignMeasurement);
    AdHocCampaignMeasurementsSummaryResponse adHocCampaignMeasurementsSummaryToAdHocCampaignMeasurementsSummaryResponse(AdHocCampaignMeasurementsSummary adHocCampaignMeasurementsSummary);

}
