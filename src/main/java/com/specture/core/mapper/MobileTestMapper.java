package com.specture.core.mapper;

import com.specture.core.model.MobileTest;
import com.specture.core.response.MobileTestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MobileTestMapper {
    MobileTestResponse mobileTestToMobileTestResponse(MobileTest mobileTest);

}
