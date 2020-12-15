package com.specture.core.mapper;

import com.specture.core.model.Ping;
import com.specture.core.request.measurement.request.PingRequest;
import com.specture.core.response.measurement.response.PingResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PingMapper {
    Ping pingRequestToPing(PingRequest pingRequest);
    PingResponse pingToPingResponse(Ping ping);
}
