package com.specure.core.mapper;

import com.specure.core.model.Probe;
import com.specure.core.model.ProbeHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProbeHistoryMapper {
    @Mapping(source = "id", target = "probeId")
    @Mapping(target = "id", ignore = true)
    ProbeHistory probeToProbeHistory(Probe probe);
}