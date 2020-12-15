package com.specture.core.mapper;

import com.specture.core.model.Probe;
import com.specture.core.model.ProbeHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProbeHistoryMapper {
    @Mapping(source = "id", target = "probeId")
    @Mapping(target = "id", ignore = true)
    ProbeHistory probeToProbeHistory(Probe probe);
}