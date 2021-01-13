package com.specure.core.mapper;

import com.specure.core.model.GeoLocation;
import com.specure.core.request.measurement.request.GeoLocationRequest;
import com.specure.core.response.measurement.response.GeoLocationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GeoLocationMapper {
    GeoLocation geoLocationRequestToGeoLocation(GeoLocationRequest geoLocationRequest);
    GeoLocationResponse geoLocationToGeoLocationResponse(GeoLocation geoLocation);
}
