package com.specture.core.mapper;

import com.specture.core.model.campaign.AdHocCampaignDowntime;
import com.specture.core.response.campaign.AdHocCampaignDowntimeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AdHocCampaignMapper.class})
public interface AdHocCampaignDowntimeMapper {
    AdHocCampaignDowntimeResponse AdHocCampaignDowntimeToAdHocCampaignDowntimeResponse(AdHocCampaignDowntime adHocCampaignDowntime);
}
