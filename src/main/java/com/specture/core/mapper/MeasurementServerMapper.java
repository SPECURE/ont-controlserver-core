package com.specture.core.mapper;

import com.specture.core.request.MeasurementServerRequest;
import com.specture.core.response.MeasurementServerForWebResponse;
import com.specture.core.response.MeasurementServerResponse;
import com.specture.core.enums.ClientType;
import com.specture.core.model.MeasurementServer;
import com.specture.core.model.MeasurementServerDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        MeasurementServerDescriptionMapper.class,
        MeasurementServerDescription.class
})
public interface MeasurementServerMapper {

    @Mapping(target = "measurementServerDescription.city", source = "city")
    @Mapping(target = "measurementServerDescription.email", source = "email")
    @Mapping(target = "measurementServerDescription.company", source = "company")
    @Mapping(target = "measurementServerDescription.ipAddress", source = "ipAddress")
    @Mapping(target = "measurementServerDescription.expiration", source = "expiration")
    @Mapping(target = "measurementServerDescription.comment", source = "comment")
    @Mapping(target = "provider.id", source = "providerId")
    MeasurementServer measurementServerRequestToMeasurementServer(MeasurementServerRequest measurementServerRequest);

    @Mapping(target = "city", source = "measurementServerDescription.city")
    @Mapping(target = "email", source = "measurementServerDescription.email")
    @Mapping(target = "company", source = "measurementServerDescription.company")
    @Mapping(target = "ipAddress", source = "measurementServerDescription.ipAddress")
    @Mapping(target = "expiration", source = "measurementServerDescription.expiration")
    @Mapping(target = "comment", source = "measurementServerDescription.comment")
    MeasurementServerResponse measurementServerToMeasurementServerResponse(MeasurementServer measurementServer);

    @Mapping(target = "address", source = "measurementServer.webAddress")
    @Mapping(source = "clientType.serverTechForMeasurement.defaultSslPort", target = "port")
    MeasurementServerForWebResponse measurementServersToMeasurementServerForWebResponse(MeasurementServer measurementServer, ClientType clientType);

}
