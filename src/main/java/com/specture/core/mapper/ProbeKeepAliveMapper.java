package com.specture.core.mapper;

import com.specture.core.model.ProbeKeepAlive;
import com.specture.core.model.ProbeKeepAliveElasticsearch;
import com.specture.core.response.ProbeKeepAliveResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {PackageMapper.class})
public interface ProbeKeepAliveMapper {

    @Mapping(target = "site", source = "site.advertisedId")
    @Mapping(target = "siteId", source = "site.id")
    @Mapping(target = "packageName", source = "APackage.advertisedName")
    @Mapping(target = "packageId", source = "APackage.id")
    @Mapping(target = "provider", source = "APackage.provider.name")
    @Mapping(target = "providerId", source = "APackage.provider.id")
    @Mapping(target = "adHocCampaign", source = "adHocCampaign.id")
    ProbeKeepAliveElasticsearch probeKeepAliveToProbeKeepAliveElasticsearch(ProbeKeepAlive probeKeepAlive);

    @Mapping(target = "site", source = "site.advertisedId")
    @Mapping(target = "aPackage", source = "APackage")
    ProbeKeepAliveResponse probeKeepAliveToProbeKeepAliveResponse(ProbeKeepAlive probeKeepAlive);

    @Mapping(target = "aPackage.advertisedName", source = "packageName")
    @Mapping(target = "aPackage.provider.name", source = "provider")
    @Mapping(target = "aPackage.provider.id", source = "providerId")
    @Mapping(target = "aPackage.id", source = "packageId")
    ProbeKeepAliveResponse probeKeepAliveElasticsearchToProbeKeepAliveResponse(ProbeKeepAliveElasticsearch probeKeepAliveElasticsearch);
}
