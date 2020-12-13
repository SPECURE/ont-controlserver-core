package com.specture.core.mapper;

import com.specture.core.model.Site;
import com.specture.core.request.SiteRequest;
import com.specture.core.response.SiteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProbeMapper.class})
public interface SiteMapper {
    SiteResponse siteToSiteResponse(Site site);

    Site siteRequestToSite(SiteRequest siteRequest);
}