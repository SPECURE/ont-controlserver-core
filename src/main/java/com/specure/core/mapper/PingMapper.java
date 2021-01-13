package com.specure.core.mapper;

import com.specure.core.model.Ping;
import com.specure.core.request.measurement.request.PingRequest;
import com.specure.core.response.measurement.response.PingResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PingMapper {
    Ping pingRequestToPing(PingRequest pingRequest);
    PingResponse pingToPingResponse(Ping ping);
}
