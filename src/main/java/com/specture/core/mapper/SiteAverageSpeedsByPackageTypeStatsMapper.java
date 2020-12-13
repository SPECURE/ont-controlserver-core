package com.specture.core.mapper;

import com.specture.core.model.stats.SiteAverageSpeedsByPackageTypeStats;
import com.specture.core.response.stats.SiteAverageSpeedsByPackageTypeStatsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AverageSpeedMapper.class})
public interface SiteAverageSpeedsByPackageTypeStatsMapper {
    SiteAverageSpeedsByPackageTypeStatsResponse siteAverageSpeedsByPackageTypeStatsToSiteAverageSpeedsByPackageTypeStatsResponse(SiteAverageSpeedsByPackageTypeStats siteAverageSpeedsByPackageTypeStats);
}
