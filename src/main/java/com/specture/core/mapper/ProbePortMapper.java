package com.specture.core.mapper;

import com.specture.core.model.ProbePort;
import com.specture.core.response.ProbePortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PackageMapper.class})
public interface ProbePortMapper {
    @Mapping(target = "name", expression = "java(probePort.getName().replaceAll(\"(?<=\\\\D)(?=\\\\d)\",\" \"))")
    @Mapping(target = "aPackage", source = "APackage")
    @Mapping(target = "probe", source = "probe.id")
    @Mapping(target = "site", source = "probe.site.advertisedId")
    ProbePortResponse probePortToProbePortResponse(ProbePort probePort);
}
