package com.specture.core.mapper;

import com.specture.core.model.GeoLocation;
import com.specture.core.request.measurement.request.GeoLocationRequest;
import com.specture.core.response.measurement.response.GeoLocationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeoLocationMapper {
    GeoLocation geoLocationRequestToGeoLocation(GeoLocationRequest geoLocationRequest);
    GeoLocationResponse geoLocationToGeoLocationResponse(GeoLocation geoLocation);
}
