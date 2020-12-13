package com.specture.core.mapper;

import com.specture.core.model.Probe;
import com.specture.core.request.ProbeRequest;
import com.specture.core.response.ProbeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TypeMapper.class, ProbePortMapper.class, AdHocCampaignMapper.class})
public interface ProbeMapper {

    @Mapping(target = "ports", source = "probePorts")
    @Mapping(target = "assigned", source = "assigned")
    @Mapping(target = "siteId", source = "site.id")
    @Mapping(target = "siteName", source = "site.name")
    @Mapping(target = "fixedPortNumber", expression = "java(probe.getFixedPortNumber())")
    @Mapping(target = "mobilePortNumber", expression = "java(probe.mobilePortNumber())")
    @Mapping(target = "siteAdvertisedId", source = "site.advertisedId")
    @Mapping(target = "probePurpose", expression = "java(probe.getProbePurpose().getName())")
    ProbeResponse probeToProbeResponse(Probe probe);

    @Mapping(target = "probePurpose", expression = "java(com.specture.core.enums.ProbePurpose.fromString(probeRequest.getProbePurpose()))")
    Probe probeRequestToProbe(ProbeRequest probeRequest);
}
